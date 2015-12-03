/*
Navicat PGSQL Data Transfer

Source Server         : postgres 35
Source Server Version : 90303
Source Host           : 10.3.11.35:5432
Source Database       : TJTest
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90303
File Encoding         : 65001

Date: 2015-12-01 16:13:41
*/


-- ----------------------------
-- Table structure for aplus_access_control
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_access_control";
CREATE TABLE "public"."aplus_access_control" (
"visitor_ip" varchar(32) COLLATE "default" NOT NULL,
"visitor_limit" int4 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_action
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_action";
CREATE TABLE "public"."aplus_action" (
"device_type" varchar(50) COLLATE "default" NOT NULL,
"device_version" varchar(32) COLLATE "default" NOT NULL,
"action_name" varchar(50) COLLATE "default" NOT NULL,
"action_code" varchar(50) COLLATE "default" NOT NULL,
"action_defkey" varchar(50) COLLATE "default",
"action_type" varchar(1) COLLATE "default",
"action_info" varchar(1000) COLLATE "default",
"action_active" bool NOT NULL,
"value_type" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_device
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_device";
CREATE TABLE "public"."aplus_device" (
"fqn" varchar COLLATE "default" NOT NULL,
"name" varchar COLLATE "default",
"ip" varchar COLLATE "default",
"category" varchar COLLATE "default",
"device_type" varchar COLLATE "default",
"device_version" varchar COLLATE "default",
"create_date" date,
"attr" json,
"location" varchar COLLATE "default",
"is_remove" bool,
"is_subsystem" bool,
"client_id" varchar COLLATE "default",
"conn_info" json
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_device_attr
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_device_attr";
CREATE TABLE "public"."aplus_device_attr" (
"device_type" varchar COLLATE "default" NOT NULL,
"device_version" varchar COLLATE "default" NOT NULL,
"attr" json,
"category" varchar COLLATE "default" NOT NULL,
"supplier_id" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_exception
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_exception";
CREATE TABLE "public"."aplus_exception" (
"id" int4 NOT NULL,
"ecode" varchar(32) COLLATE "default" NOT NULL,
"stackmsg" text COLLATE "default",
"keypoints" bytea,
"datetime" int8
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_fqn_metric
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_fqn_metric";
CREATE TABLE "public"."aplus_fqn_metric" (
"fqn" varchar COLLATE "default" NOT NULL,
"metric_name" varchar COLLATE "default" NOT NULL,
"metric_code" varchar COLLATE "default",
"metric_unit" varchar COLLATE "default",
"metric_interval" int4,
"metric_timeunit" varchar COLLATE "default",
"metric_active" bool,
"metric_type" int4,
"metric_defkey" varchar COLLATE "default",
"metric_info" json,
"device_type" varchar COLLATE "default",
"device_version" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_ip
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_ip";
CREATE TABLE "public"."aplus_ip" (
"id" int4 DEFAULT nextval('aplus_ip_id'::regclass) NOT NULL,
"fqn" varchar(255) COLLATE "default",
"ip" varchar(255) COLLATE "default",
"port" int4,
"type" int4,
"status" int2,
"times" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;
COMMENT ON COLUMN "public"."aplus_ip"."type" IS '1:手动采集 2：主动上报';
COMMENT ON COLUMN "public"."aplus_ip"."status" IS '1：成功 0：失败';

-- ----------------------------
-- Table structure for aplus_log_operation
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_log_operation";
CREATE TABLE "public"."aplus_log_operation" (
"log_id" int4 NOT NULL,
"log_ip" varchar COLLATE "default",
"log_date" timestamp(6),
"log_method" varchar COLLATE "default",
"log_uri" varchar COLLATE "default",
"log_status_code" varchar COLLATE "default",
"log_status_exception" varchar COLLATE "default",
"log_status_description" varchar COLLATE "default",
"log_status_reason" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_metric
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_metric";
CREATE TABLE "public"."aplus_metric" (
"device_type" varchar COLLATE "default" NOT NULL,
"device_version" varchar COLLATE "default" NOT NULL,
"metric_name" varchar COLLATE "default" NOT NULL,
"metric_code" varchar COLLATE "default" NOT NULL,
"metric_unit" varchar COLLATE "default" NOT NULL,
"metric_interval" int4 NOT NULL,
"metric_timeunit" varchar COLLATE "default" NOT NULL,
"metric_active" bool NOT NULL,
"metric_type" int4 NOT NULL,
"metric_defkey" varchar COLLATE "default" NOT NULL,
"metric_info" json
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_monitor_data_fqn
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_monitor_data_fqn";
CREATE TABLE "public"."aplus_monitor_data_fqn" (
"id" int8 DEFAULT nextval('aplus_monitor_data_sequence'::regclass) NOT NULL,
"fqn" varchar(50) COLLATE "default" NOT NULL,
"monitor_data" text COLLATE "default",
"record_time" int8 NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_monitor_data_main
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_monitor_data_main";
CREATE TABLE "public"."aplus_monitor_data_main" (
"id" int8 NOT NULL,
"monitor_data" text COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_policy
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_policy";
CREATE TABLE "public"."aplus_policy" (
"fqn" varchar(255) COLLATE "default" NOT NULL,
"record_time" int8,
"remark" varchar(255) COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_record
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_record";
CREATE TABLE "public"."aplus_record" (
"fqn" varchar(255) COLLATE "default",
"type" int8,
"id" int4 DEFAULT nextval('aplus_record_id_seq'::regclass) NOT NULL,
"record_time" int8,
"port" int4
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_task
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_task";
CREATE TABLE "public"."aplus_task" (
"task_id" varchar COLLATE "default" NOT NULL,
"task_cmd" json,
"fqn" varchar COLLATE "default" NOT NULL,
"push" bool NOT NULL,
"sub_system" bool NOT NULL,
"client_id" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Table structure for aplus_time
-- ----------------------------
DROP TABLE IF EXISTS "public"."aplus_time";
CREATE TABLE "public"."aplus_time" (
"id" int8 NOT NULL,
"time" varchar COLLATE "default"
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table aplus_access_control
-- ----------------------------
ALTER TABLE "public"."aplus_access_control" ADD PRIMARY KEY ("visitor_ip");

-- ----------------------------
-- Primary Key structure for table aplus_action
-- ----------------------------
ALTER TABLE "public"."aplus_action" ADD PRIMARY KEY ("device_type", "device_version", "action_name");

-- ----------------------------
-- Primary Key structure for table aplus_device
-- ----------------------------
ALTER TABLE "public"."aplus_device" ADD PRIMARY KEY ("fqn");

-- ----------------------------
-- Primary Key structure for table aplus_device_attr
-- ----------------------------
ALTER TABLE "public"."aplus_device_attr" ADD PRIMARY KEY ("device_type", "device_version", "category");

-- ----------------------------
-- Primary Key structure for table aplus_exception
-- ----------------------------
ALTER TABLE "public"."aplus_exception" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table aplus_fqn_metric
-- ----------------------------
ALTER TABLE "public"."aplus_fqn_metric" ADD PRIMARY KEY ("fqn", "metric_name");

-- ----------------------------
-- Primary Key structure for table aplus_ip
-- ----------------------------
ALTER TABLE "public"."aplus_ip" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table aplus_log_operation
-- ----------------------------
ALTER TABLE "public"."aplus_log_operation" ADD PRIMARY KEY ("log_id");

-- ----------------------------
-- Uniques structure for table aplus_metric
-- ----------------------------
ALTER TABLE "public"."aplus_metric" ADD UNIQUE ("device_type", "device_version", "metric_name");

-- ----------------------------
-- Primary Key structure for table aplus_metric
-- ----------------------------
ALTER TABLE "public"."aplus_metric" ADD PRIMARY KEY ("device_type", "device_version", "metric_name");

-- ----------------------------
-- Indexes structure for table aplus_monitor_data_fqn
-- ----------------------------
CREATE INDEX "INDEX_MONITOR_FQN_fqn" ON "public"."aplus_monitor_data_fqn" USING btree (fqn DESC);
CREATE INDEX "INDEX_MONITOR_FQN_record_time" ON "public"."aplus_monitor_data_fqn" USING hash (record_time);

-- ----------------------------
-- Triggers structure for table aplus_monitor_data_fqn
-- ----------------------------
CREATE TRIGGER "aplus_monitor_data_fqn_trigger" BEFORE INSERT ON "public"."aplus_monitor_data_fqn"
FOR EACH ROW
EXECUTE PROCEDURE "partition_aplus_monitor_data"();

-- ----------------------------
-- Uniques structure for table aplus_monitor_data_fqn
-- ----------------------------
ALTER TABLE "public"."aplus_monitor_data_fqn" ADD UNIQUE ("fqn", "record_time");

-- ----------------------------
-- Primary Key structure for table aplus_monitor_data_fqn
-- ----------------------------
ALTER TABLE "public"."aplus_monitor_data_fqn" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table aplus_monitor_data_main
-- ----------------------------
CREATE INDEX "INDEX_MONITOR_MAIN_monitor_data" ON "public"."aplus_monitor_data_main" USING btree (monitor_data);

-- ----------------------------
-- Primary Key structure for table aplus_monitor_data_main
-- ----------------------------
ALTER TABLE "public"."aplus_monitor_data_main" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table aplus_policy
-- ----------------------------
ALTER TABLE "public"."aplus_policy" ADD PRIMARY KEY ("fqn");

-- ----------------------------
-- Primary Key structure for table aplus_record
-- ----------------------------
ALTER TABLE "public"."aplus_record" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table aplus_task
-- ----------------------------
ALTER TABLE "public"."aplus_task" ADD UNIQUE ("fqn", "push");

-- ----------------------------
-- Primary Key structure for table aplus_task
-- ----------------------------
ALTER TABLE "public"."aplus_task" ADD PRIMARY KEY ("task_id");

-- ----------------------------
-- Primary Key structure for table aplus_time
-- ----------------------------
ALTER TABLE "public"."aplus_time" ADD PRIMARY KEY ("id");
