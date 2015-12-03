CREATE SEQUENCE "public"."aplus_action_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."aplus_action_id_seq" OWNER TO "postgres";



CREATE SEQUENCE "public"."aplus_alert_detail_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."aplus_alert_detail_sequence" OWNER TO "postgres";



CREATE SEQUENCE "public"."aplus_ip_id"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 5;

ALTER TABLE "public"."aplus_ip_id" OWNER TO "postgres";

SELECT setval('"public"."aplus_ip_id"', 1, true);



CREATE SEQUENCE "public"."aplus_log_operation_log_id_seq1"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."aplus_log_operation_log_id_seq1" OWNER TO "postgres";

SELECT setval('"public"."aplus_log_operation_log_id_seq1"', 1, true);



CREATE SEQUENCE "public"."aplus_monitor_data_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."aplus_monitor_data_sequence" OWNER TO "postgres";

SELECT setval('"public"."aplus_monitor_data_sequence"', 1, true);



CREATE SEQUENCE "public"."aplus_policy_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."aplus_policy_sequence" OWNER TO "postgres";

SELECT setval('"public"."aplus_policy_sequence"', 1, true);



CREATE SEQUENCE "public"."aplus_record_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
 --OWNED BY "public"."aplus_record"."id";

ALTER TABLE "public"."aplus_record_id_seq" OWNER TO "postgres";



CREATE SEQUENCE "public"."aplus_time_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
 --OWNED BY "public"."aplus_time"."id";

ALTER TABLE "public"."aplus_time_id_seq" OWNER TO "postgres";

SELECT setval('"public"."aplus_time_id_seq"', 3, true);



CREATE SEQUENCE "public"."exception_id"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 2
 CACHE 1;

ALTER TABLE "public"."exception_id" OWNER TO "postgres";

SELECT setval('"public"."exception_id"', 2, true);



CREATE SEQUENCE "public"."hibernate_sequence"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;

ALTER TABLE "public"."hibernate_sequence" OWNER TO "postgres";

SELECT setval('"public"."hibernate_sequence"', 1, true);
