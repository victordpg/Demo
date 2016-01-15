<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="header_user_info.jsp"%>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>欢迎光临东软云护航门户网站</title>
</head>
<body>

<div class="agreement-main">
    <!-- 消息分类-->
	<div class="agreement-main-btn">
		<a id="typeAll" href="#" class="active" onclick="getMsgByType(-1)">全部类型消息</a>
		<a id="typeWeb" href="#" onclick="getMsgByType(1)">门户网站消息</a>
		<a id="typeDcim" href="#" onclick="getMsgByType(2)">DCIM平台消息</a>
	</div>
	<!-- 消息状态-->
	<form class="agreement-main-radio form-inline">
		<div class="checkbox"><label class="active"><input id="statusAll" type="radio" name="notice-status" checked="checked" onclick="getMsgByStatus(-1)"/> 全部消息</label></div>
		<div class="checkbox"><label><input id="statusUnread" type="radio" name="notice-status" onclick="getMsgByStatus(0)"/> 未读消息</label></div>
		<div class="checkbox"><label><input id="statusRead" type="radio" name="notice-status" onclick="getMsgByStatus(1)"/> 已读消息</label></div>
	</form>
	<!-- 消息列表-->
	<div class="agreement-main-list">
		<table cellspacing="0" cellpadding="0" class="vender-contact-table">
			<thead>
				 <tr>
				     <th width="30"><input id="checkboxAll" type="checkbox" name="itemsall" value="全选" /></th>
					 <th>消息标题</th>
					 <th>提交时间</th>
					 <th>消息类型</th>
				 </tr>
			</thead>
			<tbody id="msgList">
				<c:forEach var="item" items="${page.items}" varStatus="s">
					<tr>
						<td><input type="checkbox" name="items" value="${item.id}" /></td>
						<td><a href="${pageContext.request.contextPath}/customer/msgdetail/${item.id}" target="mainFrame" <c:if test="${1 eq item.msgstatus}">class="read"</c:if>>${item.msgtitle}</a></td>
						<td>${item.msgtimeString}</td>
						<td>${item.msgtypeString}</td>
					</tr>
				</c:forEach>
			</tbody>
	    </table>
	</div>
	<!-- 删除和分页功能-->
	<div class="clearfix">
		<div class="pull-left"><button id="" class="btn btn-default btn-sm" type="submit" onclick="deleteMsg()">删除</button> <button id="" class="btn btn-default btn-sm" type="submit" onclick="markMsg()">标记已读</button></div>
		<div class="pull-right"><div id="Pagination"></div></div>
	</div>
    
    <div id="divID4Alert"></div>
</div>

<script>
	$(function (){
	    //消息分类切换
		$(".agreement-main-btn > a").click(function(){
			$(".agreement-main-btn > a.active").removeClass("active");
			$(this).addClass("active");
	    });
		
		//消息全选checkbox触发事件
		$('#checkboxAll').on('click',function(){
			var isChecked = $("#checkboxAll").prop("checked");
			if(isChecked){
				$("#checkboxAll").prop("checked","checked");
				$('[name=items]:checkbox').prop("checked","checked");
			}
			else{
				$("#checkboxAll").removeAttr("checked");
		    	$('[name=items]:checkbox').removeAttr("checked");
			}
		}); 
		
		//消息列表中的checkbox触发事件
		$('.vender-contact-table > tbody > tr > td > input').on('click',function(){
		    var thisTag = $(this);
			var isChecked = thisTag.prop("checked");
			if(isChecked){
				thisTag.prop("checked","checked");
			}
			else{
				thisTag.removeAttr("checked");
			}
		});	
	});
	
	$(document).ready(function(){
	    var opt = $.extend(pageOpt,{
	        callback:pageSelectCallback,
	        current_page:'${page.currentPage}'
	    });
	    var pg=$("#Pagination");
	    pg.pagination('${page.totalRecords}', opt);
	});
	
	var pageOpt = {
	    prev_text:'前一页',
	    next_text:'后一页',
	    items_per_page:'${page.recordsPerPage}',//因为第一次获取的数据,并没有传这个每页的记录数过去,所以这里应从后台获取.
	    num_display_entries:5
	};
	
	var status=-1;	//全局查询条件：消息状态
	var type=-1;	//全局查询条件：消息类型
	
	//根据消息类型查询
	function getMsgByType(typeParam){
		type = typeParam;
		if($("#statusAll").attr("checked")=="checked")
			status = -1;
		if($("#statusUnread").attr("checked")=="checked")
			status = 0;
		if($("#statusRead").attr("checked")=="checked")
			status = 1;
		
		doPagination();
	};
	
	//根据消息状态查询
	function getMsgByStatus(statusParam){
		status = statusParam;
		if(statusParam=="-1"){
			$("#statusUnread").removeAttr("checked");
			$("#statusRead").removeAttr("checked");
			$("#statusAll").attr("checked","checked");
		}
		if(statusParam=="0"){
			$("#statusAll").removeAttr("checked");
			$("#statusRead").removeAttr("checked");	
			$("#statusUnread").attr("checked","checked");
		}
		if(statusParam=="1"){
			$("#statusUnread").removeAttr("checked");
			$("#statusAll").removeAttr("checked");	
			$("#statusRead").attr("checked","checked");
		}
		
		doPagination();
	};	
	
	//删除消息
	function deleteMsg(){
		var aArray = new Array();
		//获得需要删除的位置
		var candidites = $('.vender-contact-table > tbody > tr > td > input:checked');
		var length = candidites.length;
		if(length==0){
			showAlert("danger","请选择要删除的数据！",5000);
			return;
		}
		candidites.each(function(){
			aArray.push($(this).attr("value"));
		});
		$.post("/cloud/customer/msgdelete",{'ids':aArray.toString(),'status':status,'type':type},function(data){
			if(data=="success")
				showAlert("info","删除成功！",2000);
			else
				showAlert("info","删除失败！",3000);
			doPagination();
		}); 
	};
	
	//标记消息
	function markMsg(){
		var aArray = new Array();
		//获得需要标记的位置
		var candidites = $('.vender-contact-table > tbody > tr > td > input:checked');
		var length = candidites.length;
		if(length==0){
			showAlert("danger","请选择要标记的数据！",5000);
			return;
		}		
		candidites.each(function(){
			aArray.push($(this).attr("value"));
		});
		$.post("/cloud/customer/msgmark",{'ids':aArray.toString(),'status':status,'type':type},function(data){
			if(data=="success")
				showAlert("info","标记成功！",2000);
			else
				showAlert("info","标记失败！",3000);
			doPagination();
		});
	};	
	
	//执行分页查询
	function doPagination(){
		$('#checkboxAll').removeAttr("checked"); //重置全选按钮
		$("#Pagination").empty();
	    
		var opt = $.extend(pageOpt,{
	        callback:pageSelectCallback,
	        current_page:'${page.currentPage}'
	    });
	    
		var pg=$("#Pagination");
	    
	    $.getJSON(
            "usermsg?msgstatus="+status+"&msgtype="+type+"",
            {"currentPage":opt.current_page,"recordsPerPage":opt.items_per_page},
            function(page){
                var userListHtml='';
                for(var i=0;i<page.items.length;i++){
                    var item=page.items[i];
                    var classproperty='';
                    if(item.msgstatus=='1')
                    	classproperty="class='read'";
                    userListHtml += '<tr><td><input type="checkbox" name="items" value="'+item.id+'" /></td><td><a href="${pageContext.request.contextPath}/customer/msgdetail/'+item.id+'" target="mainFrame" '+classproperty+'>'+item.msgtitle+'</a></td><td>'+item.msgtimeString+'</td><td>'+item.msgtypeString+'</td></tr>';
                }
                pg.pagination(page.totalRecords, opt);
                $("#msgList").empty().append(userListHtml);
            }
	    );
	};
	
	//分页回调函数
	function pageSelectCallback(page_index, jq){
		$('#checkboxAll').removeAttr("checked"); //重置全选按钮
		
	    //对公共配置进行扩展,用于后台获取数据,用到两个参数:当前页和每页的记录数
	    var opt = $.extend(pageOpt,{
	        current_page:page_index,
	    });
	    
	    //通过ajax发请求获取数据来修改页面
	    $.getJSON(
	    	"usermsg?msgstatus="+status+"&msgtype="+type+"",
            {"currentPage":opt.current_page,"recordsPerPage":opt.items_per_page},
            function(page){
                var userListHtml='';
                for(var i=0;i<page.items.length;i++){
                    var item=page.items[i];
                    var classproperty='';
                    if(item.msgstatus=='1')
                    	classproperty="class='read'";
                    userListHtml += '<tr><td><input type="checkbox" name="items" value="'+item.id+'" /></td><td><a href="${pageContext.request.contextPath}/customer/msgdetail/'+item.id+'" target="mainFrame" '+classproperty+'>'+item.msgtitle+'</a></td><td>'+item.msgtimeString+'</td><td>'+item.msgtypeString+'</td></tr>';
                }
                $("#msgList").empty().append(userListHtml);
            }
	    );
	};
</script>
</body>
</html>