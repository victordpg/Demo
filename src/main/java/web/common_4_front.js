/*
 * 前台通用接口组件，封装些常用的公共组件。
 * 主要是基于前台bootstrap与jQuery进行的封装，方便统一调用。
 * 
 * @author Victor_Diao
 * @date 2016年1月14日
 */

/**
 * 显示操作后的提示信息，并可在指定时间time4out内消失。
 * 提供了4种样式，通过alertType选择。
 * <p>
 * <strong>用法：</strong>
 * <ul>
 * <li>首先，前台页面需要提示的地方写入&lt;div id='divID4Alert'>&lt;/div></li>
 * <li>完后，在完成对应操作（如：添加，删除，提交等）后直接调用该函数即可。</li>
 * </ul>
 * <p>
 * 
 * @param alertType: success, info, warning, danger 中的一个<br>
 * @param text: 为自定义的提示内容<br>
 * @param time4out: 毫秒，存在时间；0，则一直存在直到手动关闭<br>
 * 
 * @author Victor_Diao
 * @date 2016年1月14日
 */
function showAlert(alertType, text, time4out) {
	var firstPart = "";
	var legalAlertTypeFlag = 0; //alertType 合法性标识

	if (alertType == "success") {
		legalAlertTypeFlag = 1;
		firstPart = '<div class="alert alert-success alert-dismissible fade in" role="alert">';
	}
	if (alertType == "info") {
		legalAlertTypeFlag = 1;
		firstPart = '<div class="alert alert-info alert-dismissible fade in" role="alert">';
	}
	if (alertType == "warning") {
		legalAlertTypeFlag = 1;
		firstPart = '<div class="alert alert-warning alert-dismissible fade in" role="alert">';
	}
	if (alertType == "danger") {
		legalAlertTypeFlag = 1;
		firstPart = '<div class="alert alert-danger alert-dismissible fade in" role="alert">';
	}
	if (legalAlertTypeFlag == 0) {
		alert("通知框类型不合法！应该为：success, info, warning, danger中的一种。");
		return;
	}

	var alertHtml = firstPart
			+ '<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>'
			+ '<strong></strong>' + text + '</div>'
	$("#divID4Alert").empty().append(alertHtml);
	if(time4out!=0)
		setTimeout("$('#divID4Alert').empty()", time4out);
}