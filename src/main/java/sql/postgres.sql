-- 1. json字段查询，排序
-- 其中JSON字段metric_info 样式：{"byteCount":"1","byteIndex":"41","byteFormat":"BYTE_ARRAY2INT"}
-- 查询排序
SELECT  device_type,
	metric_code,
	metric_name,to_number(
		metric_info ->> 'byteIndex',
		'99'
	)
FROM
	"public"."aplus_metric"
WHERE
	metric_code LIKE '0C_F%'
GROUP BY
	device_type,
	metric_code,
metric_name,
to_number(
		metric_info ->> 'byteIndex',
		'99'
	)
ORDER BY metric_code,
	to_number(
		metric_info ->> 'byteIndex',
		'99'
	);

-- JSON字段conn_info原始数据为：[{"pn":2,"host":"10.1.5.112","active":"True","ip_addr":"10.1.5.112","port":"9999","data_collector":23,"main_server":3,"region":44}] 	
-- 查询json中的string
select * from aplus_device where conn_info->0->>'host' = '10.1.5.197';
-- 查询json中的int
select * from aplus_device where to_number(conn_info->0->>'pn','9') = 2;	


-- 2. 利用字符串操作函数，获得指定字符串
-- 字段action_info原始值: {"byteCount":"1","byteIndex":"0","unit":"","timeUnit":"SECONDS","interval":"0","byteFormat":"BYTE_ARRAY2INT"}
select substring(action_info from '\"byteIndex\"\:\"...') byteIndex from aplus_action where action_code = '04_F103' and device_type='SZPDZD';
-- 查询后得到的结果："byteIndex":"0",


-- 3. 字符串操作函数
-- 字段action_info原始值：{"byteCount":"1","byteIndex":"0","unit":"","timeUnit":"SECONDS","interval":"0","byteFormat":"BYTE_ARRAY2INT"}
-- 查询后byteIndex结果：0
SELECT
  action_name,
	SUBSTRING (
		SUBSTRING (
			action_info
			FROM
				'\"byteIndex\"\:\"...'
		),
		'([0-9]|[0-9][0-9])'
	) byteIndex
FROM
	aplus_action
WHERE
	action_code = '04_F21'
AND device_type = 'SZPDZD'
ORDER BY
	to_number(
		SUBSTRING (
			SUBSTRING (
				action_info
				FROM
					'\"byteIndex\"\:\"...'
			),
			'([0-9]|[0-9][0-9]|[0-9][0-9])'
		),
		'99'
	);

-- 4. 获取字符字段中某些特定字符的个数
-- 这里是获取action_info中bitIndex个数
SELECT count(*) FROM aplus_action where action_code='04_F97' AND SUBSTRING(action_info FROM '\"bitIndex\"\:\"...')!=''; 

-- 5. 获取double类型的时间字段对应的时间，并获取text类型是否包含指定字符，数据样式如下：
-- 11010803	RES20151019143964545	{"0D_F5_DAYFREEZE":"15092316","0D_F5_DAYRATE_M1":10,"0D_F5_DAYRATE_M2":5,"0D_F5_DAYRATE_M3":15,"0D_F5_DAYRATE_M4":4,"0D_F5_DAYRATE_M5":16,"0D_F5_DAYRATE_M6":10,"0D_F5_TOTAL":60}	1446096674	2015-10-29 13:31:14
SELECT
	A . ID,
	A .fqn,
	b.monitor_data,
  A .record_time,
	to_timestamp(A.record_time)::timestamp without time zone
FROM
	aplus_monitor_data_fqn A
INNER JOIN aplus_monitor_data_main b ON A . ID = b. ID
WHERE
	A.fqn = 'RES20151019143964545'
	-- text字段中包含'0D_F5'的数据
	and "substring"(b.monitor_data,'0D_F5')!=''
order by record_time desc;

-- 查询当前时间
select current_timestamp(0)::timestamp without time zone;
select current_timestamp(3)::timestamp without time zone;
select current_timestamp(6)::timestamp without time zone;
select cast (current_timestamp(0) as  timestamp without time zone);

-- 6. 更新json字段中的某一个键值队值
-- conn_info更新前：[{"pn":2,"host":"10.1.5.186","active":"True","ip_addr":"10.1.5.186","port":"9999","data_collector":23,"main_server":3,"region":111}]
-- conn_info更新后：[{"pn":2,"host":"10.1.5.186","active":"True","ip_addr":"10.1.5.186","port":"9999","data_collector":23,"main_server":3,"region":222}]
update aplus_device 
set 
conn_info = cast(replace(conn_info||'',conn_info ->0->>'region','222') as json)
where fqn='test1';


-- 当执行JSON字段查询的时候产生如下错误：
-- JSON字段conn_info数据样式：[{"pn":2,"host":"10.1.5.186","active":"True","ip_addr":"10.1.5.186","port":"9999","data_collector":23,"main_server":3,"region":6}]
[SQL]select * from aplus_device where conn_info->0->>'pn'='0';

[Err] ERROR:  cannot extract element from a scalar

-- 原因：
-- 该字段conn_info中存在空值，所以无法获取此空值中指定的元素，故报上面错误。
