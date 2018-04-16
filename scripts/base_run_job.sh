#!/bin/sh

OOZIE_CMN_FW=/home/ylai02/oozie-workflow

MYNAME=OozieExecutionClient
PID_FILE=$MYNAME.${hostname}.PID
LIB_FOLDER=$OOZIE_CMN_FW/lib
CONFIG_FOLDER=$OOZIE_CMN_FW/config

HBASE_FOLDER=/usr/hdp/current/hbase-client
HDFS_FOLDER=/usr/hdp/current/hadoop-hdfs-client
HADOOP_FOLDER=/usr/hdp/current/hadoop-client
HIVE_FOLDER=/usr/hdp/current/hive-server2
OOZIE_FOLDER=/usr/hdp/current/oozie-client

MAIN_CLASS=com.altarmoss.oozie.client.AuthOozieExecutionClient

CLASSPATH="$CONFIG_FOLDER:$LIB_FOLDER/*:$HBASE_FOLDER/lib/*:$HDFS_FOLDER/*:$HADOOP_FOLDER/*:$HIVE_FOLDER/lib/*:$OOZIE_FOLDER/lib/*"
ADD_ARGUMENT="-Dmapred.job.queue.name=llap -DPRINCIPAL=$PRINCIPAL -DKEYTAB=$KEYTAB"
if [[ ! -z $HDFS_KEYTAB ]]
then
    ADD_ARGUMENT="$ADD_ARGUMENT -DHDFS_KEYTAB=$HDFS_KEYTAB"

VM_ARGUMENT="$1 $2 $3 $4 $5 $6"

java -cp $CLASSPATH $ADD_ARGUMENT $MAIN_CLASS $VM_ARGUMENT
STATUS="${?}"
exit $STATUS
