-- 1. json字段查询，排序
-- 查询排序
SELECT
		device_type,
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
