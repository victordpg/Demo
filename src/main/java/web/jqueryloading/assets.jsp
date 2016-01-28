<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="header_user.jsp"%>
<script type="text/javascript" src="<c:url value='/styles/js/page/jquery.loading.js'/>"></script>
<link href="<c:url value='/styles/css/page/jquery.loading.css'/>" rel="stylesheet">

<script type="text/javascript" src="<c:url value='/styles/js/page/bootstrap-treeview.js'/>"></script>

<div class="asset-bg">
	<div class="container">
		<div class="row">
			<div class="col-md-3">
				<div class="asset-sidebar">
				    <div id="id-treeview" style="overflow:auto; height:200px;"></div>
				</div>
			</div>
			<div  id="id-loading" class="col-md-9">
			    <div class="asset-pic"></div>
			</div>
		</div>
	</div>
</div>
<input id="id-property-tree" type="hidden" value="${propertyTree}"/>
<%@ include file="footer_user.jsp"%>

<script>
	$(document).ready(function (){
		$("#assets").attr("class","active");
	    //信息中心菜单切换
		$(".info-sidebar > ul > li > a").click(function(){
			$(".info-sidebar > ul > li > a.active").removeClass("active");
			$(this).addClass("active");
	    });
		
		$('#id-treeview').treeview({
			color: "#428bca",
			expandIcon: 'glyphicon glyphicon-chevron-right',
			collapseIcon: 'glyphicon glyphicon-chevron-down',
			showBorder: false,
			backColor: "#eef5fd",
			onhoverColor: "#eef5fd",
			selectedColor: "#428bca",
			selectedBackColor: "#cfe0ed",
			levels:1,
			data : getTree()
		});	
		
		//点击设备后弹出设备摘要页信息
		$('#id-treeview').on('nodeSelected', function(event, data) {
			if(data.nodes==undefined){
				//调用该方法页面高度始终为第一次加载时div的高度
				//$("#id-loading").loading();
				//调用该方法loading页面的高度为上次页面对应div加载高度
				$("#id-loading").loading({
					  theme: 'light'
				});
                var userListHtml='';
        		$.get("/cloud/customer/assets/device/"+data.href,function(data){
        			$('#id-loading').loading('stop');
        			userListHtml = data;
        			$('.col-md-9').empty().append(userListHtml);
        		}); 
			}
		});
		
		//资产管理高度自适应方法调用
		infoHeight();
	});
	
	function getTree() {
		//var treeJson = [{"nodes":[{"nodes":[{"nodes":[{"icon":"icon-device","href":"25c0a58a8fd842808de7daa5b124e4db","text":"空调2021(OPC)(信息港一楼)"}],"icon":"icon-devicetype","href":"71e425134f6d4614822f1c924ea3f424","text":"艾默生空调U480"},{"nodes":[{"icon":"icon-device","href":"08d3be2420d14ff799dbc2ba03878316","text":"空调2023(SNMP)(信息港一楼)"},{"icon":"icon-device","href":"759a790d758241df9ea3fc9a569a3b81","text":"空调2022(SNMP)(信息港一楼)"}],"icon":"icon-devicetype","href":"c2817bb543f24c688d4241664c98897","text":"美的空调Q812"},{"nodes":[{"icon":"icon-device","href":"925d18baed424632905862eafa18ec48","text":"安静(信息港一楼)"}],"icon":"icon-devicetype","href":"d9b5680bc3de4ad2a5e4eb61b661dbcc","text":"美的空调T1413"}],"icon":"icon-devicetype","href":"c2817bb543f24c688d4241664c987aaf","text":"空调"},{"nodes":[{"nodes":[{"icon":"icon-device","href":"c787f985f2a24b8f8677ec8f563119d1","text":"列头柜01(信息港一楼)"}],"icon":"icon-devicetype","href":"fe52cc420b7c4e63859a61a11de3af6b","text":"艾默生列头柜T100"}],"icon":"icon-devicetype","href":"e164b30771134e75882b107a5c42e61a","text":"列头柜"}],"icon":"icon-devicetype","href":"c2817bb543f24c62342351664c987235","text":"基础设施"},{"nodes":[{"nodes":[{"nodes":[{"icon":"icon-device","href":"7f22fc0ea67c4d3690f51a54a54891fe","text":"机柜B01(信息港一楼)"},{"icon":"icon-device","href":"fe307af7de9546278ef956fd5f8e534b","text":"机柜B02(信息港一楼)"}],"icon":"icon-devicetype","href":"24f8ec17a49d11e5a1dc6c0b8409f355","text":"大唐机柜001"},{"nodes":[{"icon":"icon-device","href":"07e36f88fb354e2f861423aaa32dbe37","text":"机柜01(信息港一楼)"}],"icon":"icon-devicetype","href":"c2817bb543f24c63536351664c987531","text":"大唐机柜8842"}],"icon":"icon-devicetype","href":"c2817bb543f24c63536351664c987878","text":"机柜"}],"icon":"icon-devicetype","href":"c2817bb543f24c63536351664c987657","text":"纯资产"}]
		var treeJson = $('#id-property-tree').val();
		treeJson = eval(treeJson); //将json串转换成数组
		return treeJson;
	};
	
	//资产管理高度自适应方法
	function infoHeight(){
		var bodyh=$(document).height();
		var headerh=$('.user-header').outerHeight();
		var footerh=$('.user-footer').outerHeight();
		var infoh=bodyh-headerh-footerh;
		$('#id-treeview').outerHeight(infoh-62);
	}
</script>
