\copy (select * from organizationtreedetail) to '/srv/extracts/helpdesk/automation/stage/tmp_organizationtreedetail.csv' (FORMAT CSV, HEADER TRUE, FORCE_QUOTE *);