#!/bin/sh

MYNAME=OozieWorkUnit
PID_FILE=$MYNAME.${hostname}.PID
OOZIE_CMN_FW=/home/ylai02/oozie-workflow
LIB_FOLDER=$OOZIE_CMN_FW/lib
CONFIG_FOLDER=$OOZIE_CMN_FW/config

INSTALL_FOLDER=$(echo $PWD | sed 's_/_\\/_g')

HBASE_FOLDER=/usr/hdp/current/hbase-client
HDFS_FOLDER=/usr/hdp/current/hadoop-hdfs-client
HADOOP_FOLDER=/usr/hdp/current/hadoop-client
HIVE_FOLDER=/usr/hdp/current/hive-server2

MAIN_CLASS=com.altarmoss.oozie.client.OozieWorkUnit

CLASSPATH="$CONFIG_FOLDER:$LIB_FOLDER/*:$HBASE_FOLDER/lib/*:$HDFS_FOLDER/*:$HADOOP_FOLDER/*:$HIVE_FOLDER/lib/*"

ADD_ARGUMENT="-DPRINCIPAL=$PRINCIPAL -DLOCAL_KEYTAB_DIR=$LOCAL_KEYTAB_DIR -DKEYTAB=$KEYTAB"

java -cp $CLASSPATH $ADD_ARGUMENT $MAIN_CLASS $1 $2 $3 $4 $5 $6 $7 $8 $9 ${10} ${11} ${12} ${13} ${14} ${15} ${16} ${17} ${18} ${19} ${20} ${21} ${22}

LOWER_WORKUNIT=$(echo $1 | tr '[:upper:]' '[:lower:]')

RUN_JOB_SCRIPT=run_job_"$LOWER_WORKUNIT".sh
if [ -f "$RUN_JOB_SCRIPT" ]
then
    echo "$RUN_JOB_SCRIPT exists."
else
    cp $OOZIE_CMN_FW/scripts/run_job_template.sh run_job_"$LOWER_WORKUNIT".sh
    sed -i "s/<WORK_UNIT_NAME>/$1/g" run_job_"$LOWER_WORKUNIT".sh
    sed -i "s/<CURRENT_FOLDER>/$INSTALL_FOLDER/g" run_job_"$LOWER_WORKUNIT".sh
fi
