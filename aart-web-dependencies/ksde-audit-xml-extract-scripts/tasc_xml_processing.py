from xml.dom import pulldom
from optparse import OptionParser    
from datetime import datetime
import psycopg2
import sys


def log(message):
    print >> logFile,str(datetime.now())+": "+message

def isAnExistingColumn(existingColumns,column):
    if len(existingColumns)==0:
        return True
    else:
        return column.lower() in existingColumns

def main():
    usage = "usage: [options]"
    parser = OptionParser(usage=usage)
    #parser.add_option("-v", "--verbose",action="store_true", dest="verbose", default=True,
    #                help="make lots of noise [default]")
    parser.add_option("-l", "--limit", default=1000,
                    help="Optional:Number of records to be fetched per iteration from base table.Default value set as 1000")
    #parser.add_option("-t", "--type",help="required :\"STCO\" or \"KIDS\"")
    parser.add_option("-o", "--offset",
                    help="Optional:provide 0 as offset only if the staging table is empty otherwise do not specify offset ")
    #parser.add_option("-d", "--database_string",
                    #help="Required:host='local' dbname='test' user='postgres' password='secret'")
    parser.add_option("-a", "--audit_database_string",
                    help="Required:host='local' dbname='test' user='postgres' password='secret'")    

    parser.add_option("-e", "--end",
                    help="optinal:provide the ksdexmlaudit id upto which the data should be processed. If not specified all records since the previous successful run will be processed")
    parser.add_option("--logfile", default="ksdexmlaudit_tasc_stco_record.log",
                    help="Optinal:--logfile <filepath/filename>.provide a logfile name")
    (options, args) = parser.parse_args()
    
    global logFile
    global limit
    global end
    #global db
    global adb
    #global cursor
    global auditCursor
    global recordtype
    global recordtypeXMLString
    global targettable
    global existingColumns
    
    logFile = open(options.logfile,"a+")
    log("--------------------------------------")
    
    if options.end is not None:        
        end=options.end
    else:
        end = None
        
    limit=options.limit 
    
    try:
        #db = psycopg2.connect(database = 'aart',user='postgres',password = 'admin')        
        #db = psycopg2.connect(options.database_string)
        adb = psycopg2.connect(options.audit_database_string)        
        #cursor = db.cursor()
        auditCursor = adb.cursor()
    except Exception,e:
        log(str(e))
        print >> sys.stderr,"Aborting:unable to establish database connection"
        sys.exit(2)
    
    targettable = "tasc_record_staging"
    recordtypeXMLString="TASC_Record"
    recordtype="TASC"
        
    columnNameSql="select column_name from information_schema.columns where table_name='"+targettable+"';"
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
    offset_sql="select max(ksdexmlaudit_id) from "+targettable
        
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
        process_XML_records(offset)
    adb.close()
    log("Execution Complete")        

def process_XML_records(offset):
   
    getDataSql="select * from ksdexmlaudit where type='"+recordtype+"' and xml != '<TASC_Data></TASC_Data>' and xml NOT SIMILAR TO  '<error_message>%' and id>"+ str(offset)
    if end is not None:
        getDataSql+=" and  id <="+ str(end)
    getDataSql+=" order by id limit " + str(limit)
    
    log(getDataSql)
    log("Start Execution")
    try:
        auditCursor.execute(getDataSql)
        #rows=cursor.fetchall()
    except Exception,e:
        log(str(e))
        print >> sys.stderr,"Aborting:unable to fetch xml records from ksdexmlaudit table"
        sys.exit(2)
    
    if auditCursor is not None:
        sqlList=[]
        for row in auditCursor.fetchmany(size=100):
            insert_row_list=[]
            log("currently processing ksdexmlaudit id :"+str(row[0]))
            events = pulldom.parseString(row[2])
            log("parsed ksdexmlaudit id :"+str(row[0]))
            try:
                for (event, node) in events:
                    if event == pulldom.START_ELEMENT and node.localName == recordtypeXMLString:
                        events.expandNode(node)
                        if len(node.childNodes) > 1:
                            pass
                        else:
                            continue
                        columnNameList=[]
                        columnNameList.append("insert into "+targettable+" (ksdexmlaudit_id")
                        valueList=[]
                        valueList_row = []
                        valueList.append(") values (%s")
                        valueList_row.append(row[0])
                        for child in node.childNodes:
                            if child.nodeType == node.ELEMENT_NODE:
                                columnName=child.localName.strip()
                                if child.firstChild is not None and child.firstChild.nodeValue is not None and isAnExistingColumn(existingColumns,columnName):
                                    if (','+columnName in columnNameList):
                                        pass
                                    else:
                                        columnNameList.append(","+columnName)
                                        valueList.append(",%s")
                                        valueList_row.append(child.firstChild.nodeValue.strip())
                        valueList.append(")")
                        sqlList=columnNameList+valueList
                        insertSql="".join(sqlList)
                        if insertSql:
                            #insert_row_list.append((insertSql,valueList_row)) 
                            try:
                                auditCursor.execute(insertSql,valueList_row)
                            except:
                                print "Error : ",sys.exc_info()
                                log("Error for ksdexmlaudit_id: "+str(row[0]))
                                log("Error: "+insertSql)
                                log("Error: "+valueList_row)
                                sys.exit(2)
                        sqlList = []
                        valueList_row = []
            except:
                log("Not valid XML :"+str(row[0]))
            events.clear()
            adb.commit()

if __name__ == "__main__":
    main()