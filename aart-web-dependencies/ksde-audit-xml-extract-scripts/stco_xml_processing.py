from xml.dom import pulldom
from optparse import OptionParser
from datetime import datetime
import psycopg2
import sys

global logFile

def log(message):
    print >> logFile,str(datetime.now())+": "+message

def isAnExistingColumn(existingColumns,column):
    if len(existingColumns)==0:
        return true
    else:
        return column.lower() in existingColumns

def main():
    usage = "usage: [options]"
    parser = OptionParser(usage=usage)
    #parser.add_option("-v", "--verbose",action="store_true", dest="verbose", default=True,
    #                help="make lots of noise [default]")
    parser.add_option("-l", "--limit", default=1000,
                    help="Optional:Number of records to be fetched per iteration from base table.Default value set as 1000")
    parser.add_option("-o", "--offset",
                    help="Optional:provide 0 as offset only if the staging table is empty otherwise do not specify offset ")
    parser.add_option("-d", "--database_string",
                    help="Required:host='local' dbname='test' user='postgres' password='secret'")    
    parser.add_option("-a", "--audit_database_string",
                    help="Required:host='local' dbname='test' user='postgres' password='secret'")    


    parser.add_option("-e", "--end",
                    help="optinal:provide the ksdexmlaudit id upto which the data should be processed. If not specified all records since the previous successful run will be processed")
    parser.add_option("--logfile", default="ksdexmlaudit_stco_record.log",
                    help="Optinal:--logfile <filepath/filename>.provide a logfile name")
    (options, args) = parser.parse_args()
    
    global logFile
    logFile = open(options.logfile,"a+")
    log("--------------------------------------")
    try:
        #db = psycopg2.connect(database = 'aart',user='postgres',password = 'admin')
        #print "DB: ",options.database_string
        db = psycopg2.connect(options.database_string)
        adb = psycopg2.connect(options.audit_database_string)        
        cursor = db.cursor()
        auditCursor = adb.cursor()
    except Exception,e:
        print sys.exc_info()
        log(str(e))
        print >> sys.stderr,"Aborting:unable to establish database connection"
        sys.exit(2)
    
    columnNameSql="""select column_name from information_schema.columns where table_name='stco_record_staging';"""
    
    try:
        auditCursor.execute(columnNameSql)
        rows=auditCursor.fetchall()
    except Exception,e:
        log(str(e))
        print >> sys.stderr,"Warning: Cannot get column names of target table. Possibility of exception if unknown column name is encountered while inserting data "
    
    #Building Existing Columns Set
    if rows is not None:
        existingColumns=[]
        for row in rows:
            existingColumns.append(row[0].strip())
    
    prevOffset=-1
    #while True:
    offset_sql="select max(ksdexmlaudit_id) from stco_record_staging"
        
    try:
        auditCursor.execute(offset_sql)
        offset=auditCursor.fetchone()[0]
    except Exception,e:
        log(str(e))
        print >> sys.stderr,"Aborting:unable to fetch offset value from database"
        sys.exit(2)
        
    if offset is None and options.offset is None:
        print >> sys.stderr,"Aborting: No offset value. If this is the first time execution, provide -o 0 as input argument. Else, error occurred in fetching offset from staging table"    
        sys.exit(2)
    elif offset is None:
        offset=int(options.offset)
            
    if prevOffset >= offset:
        sys.exit(2)
    elif offset > prevOffset or offset==0:
        prevOffset=offset
        process_STCO_records(db,adb,cursor,auditCursor,offset,options.end,options.limit,existingColumns)
    log("Execution Complete")
            

def kccidcheck(value): 
    """
        Return True if invalid, return False if valid
    """
    for checker in ( '01', '51', '81', '02', '52', '82', '80', '03', '53', '83', '04', '54', '84'):
        if value.startswith(checker):
            return False
    return True


def process_STCO_records(db,adb,cursor,auditCursor,offset,end,limit,existingColumns):
    
    getDataSql="select * from ksdexmlaudit where type='STCO' and xml != '<STCO_Data></STCO_Data>' and id>"+ str(offset)
    if end is not None:
        getDataSql+=" and  id <="+ str(end)
    getDataSql+=" order by id limit "+str(limit)
    
    log(getDataSql)
    log("Start Execution")
    try:
        cursor.execute(getDataSql)
        #rows=cursor.fetchall()
    except Exception,e:
        log(str(e))
        print >> sys.stderr,"Aborting:unable to fetch xml records from ksdexmlaudit table"
        sys.exit(2)
    
    if cursor is not None:
        sqlList=[]
        for row in cursor.fetchall():
            kccidskip = False 
            insert_row_list= []
            log("currently processing ksdexmlaudit id :"+str(row[0]))
            events = pulldom.parseString(row[2])
            try:
                for (event, node) in events:
                    if event == pulldom.START_ELEMENT and node.localName == "STCO_Record":
                        events.expandNode(node)
                        if len(node.childNodes) > 1:
                            pass
                        else:
                            continue
                        columnNameList=[]
                        columnNameList.append("insert into stco_record_staging (ksdexmlaudit_id")
                        valueList=[]
                        valueList_row = []
                        valueList.append(") values (%s")
                        valueList_row.append(row[0])
                        for child in node.childNodes:
                            if child.nodeType == node.ELEMENT_NODE:
                                columnName=child.localName.strip()
                                if child.firstChild is not None and child.firstChild.nodeValue is not None and isAnExistingColumn(existingColumns,columnName):
                                    if columnName == "kcc_id" and kccidcheck(child.firstChild.nodeValue.strip()):
                                        print "Skip !"
                                        kccidskip = True
                                    columnNameList.append(","+columnName)
                                    valueList.append(",%s")
                                    valueList_row.append(child.firstChild.nodeValue.strip())
                        valueList.append(")")
                        sqlList=columnNameList+valueList
                        insertSql = "".join(sqlList)
                        if (insertSql and not kccidskip):
                            #print row[0]
                            insert_row_list.append((insertSql,valueList_row))
                            #cursor.execute(insertSql,valueList_row)
                            #db.commit()
                        #print valueList_row
                        #print len(valueList), len(valueList_row)
                        sqlList = []
                        valueList_row = []
            except:
                print "Not valid XML",row[0]
                log("Not Valid XML : "+str(row[0]))
            if insert_row_list:
                try:
                    for i in insert_row_list:
                        auditCursor.execute(i[0],i[1])
                except e:
                    adb.rollback()
                    print "Error :"+i
                    log("Stopped ksdexmlaudit id :"+str(row[0]))
                    sys.exit(1)
                adb.commit()
                    

if __name__ == "__main__":
    main()
         
