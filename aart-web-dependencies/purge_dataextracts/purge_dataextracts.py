from optparse import OptionParser
import os
import psycopg2
import sys

def main():
    usage = "usage: [options]"
    parser = OptionParser(usage=usage)

    parser.add_option("-p", "--path",
                    help="Path of files to purge from")    
  
    parser.add_option("-d", "--database_string",
                    help="Required:host='local' dbname='test' user='postgres' password='secret'")    
    (options, args) = parser.parse_args()

#    db = psycopg2.connect(options.database_string)
    db = psycopg2.connect(database = 'aart-prod',user='aart',password = 'aCsWPmgCprkvXLBvgKwkrjvQn', host='pool.prodku.cete.us')

    for file in os.listdir(options.path):
        check_file(options, db, file)

def check_file(options, db, file):
    cursor = db.cursor()

    cursor.execute("SELECT id, activeflag FROM modulereport WHERE filename=%s;" , (str(file),) )

    for row in cursor:
        # This purges all inactive files. 
        if not row[1]: 
            if os.path.isfile("%s%s" % (options.path, file)):
                #print "Delete %s%s" % (options.path, file)
                os.unlink("%s%s" % (options.path, file))

def purge_file(options, db, id, file):
    sys.exit   


if __name__ == "__main__":
    main()

