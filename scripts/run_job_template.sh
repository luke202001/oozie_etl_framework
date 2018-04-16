#!/bin/sh

OOZIE_CMN_FW=/home/ylai02/oozie-workflow
ETL_HOME=<CURRENT_FOLDER>

$OOZIE_CMN_FW/scripts/base_run_job.sh <WORK_UNIT_NAME> $1 $2 $3 $4
STATUS="${?}"
echo "STATUS: $STATUS"
exit $STATUS
