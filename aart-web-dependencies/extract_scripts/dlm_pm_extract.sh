#Send mail Function
DLM_RECIPIENTS=bnash@ku.edu,akclark@ku.edu,karvonen@ku.edu,marilangas@ku.edu,jakethompson@ku.edu,ats_infrastructure_dl@ku.edu
EMAIL_CC_RECIPIENTS=pgopisetty@ku.edu,tao-guo@ku.edu,ginsburgs_sta@ku.edu,sdmartin@ku.edu
send_email ()
{
   echo "The $1 SQLite database failed to create." | mailx -s "$1 - SQLite Database failed" -c "$EMAIL_CC_RECIPIENTS" "$2" 
}

python /srv/extracts/source/extract_sqlite.py -a "3" -y "2018"  -d /srv/extracts/data/dlm.pm.sqlite -t "all"  -n "host='pg3.prodku.cete.us' dbname='aart-prod' user='aart_reader'"
DLM_EXIT_STATUS=$?

# mail if failed
if [ $DLM_EXIT_STATUS -ne 0 ]; then
   send_email "DLM PM" $DLM_RECIPIENTS
   exit;
fi
cp /srv/extracts/data/dlm.pm.sqlite /srv/extracts/data/dlm.pm_bkup.sqlite
cp /srv/extracts/data/dlm.pm_bkup.sqlite /srv/extracts/data/dlm.pm_tmp.sqlite
sqlite3 /srv/extracts/data/dlm.pm_tmp.sqlite < /srv/extracts/helpdesk/rally_requests/DLM_SQLITE/masterpull_query_original2017.sql
sqlite3 /srv/extracts/data/dlm.pm_tmp.sqlite < /srv/extracts/helpdesk/rally_requests/DLM_SQLITE/masterpull_query_testletlevel2017.sql
sqlite3 /srv/extracts/data/dlm.pm_tmp.sqlite < /srv/extracts/helpdesk/rally_requests/DLM_SQLITE/masterpull_query_survey2017.sql
sqlite3 /srv/extracts/data/dlm.pm_tmp.sqlite < /srv/extracts/helpdesk/rally_requests/DLM_SQLITE/masterpull_query_survey_dcps.sql
sqlite3 /srv/extracts/data/dlm.pm_tmp.sqlite < /srv/extracts/helpdesk/rally_requests/DLM_SQLITE/masterpull_query_inactive2017.sql
mv /srv/extracts/data/dlm.pm_tmp.sqlite /srv/extracts/data/dlm.pm.sqlite