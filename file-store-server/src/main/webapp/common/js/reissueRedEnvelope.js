/**
 * 补发红包活动表单js
 */

var title; //红包table生成字符串
var Hname = new Array(); // 红包活动名称	
var Hcode = new Array(); // 红包id
var object = $("#object").val(); // json 字符串
var json = JSON.parse(object); // 得到 json 对象
var currInputValue = ""; // 当前输入值
var flag = true ; // 是否选取到完整红包活动名称
var it = ""; // 得到联想table
var linkDataProperty = ""; // 获得对应的输入框对象
var currentLine=-1; // 当前列的下标
// 将json对象赋值给对应的数组
for (var i = 0; i < json.length; i++) {
	Hcode[i] = json[i].activityCode;
	Hname[i] = json[i].activityName;
}

//  根据姓名得到ID
function getBackCodeByName() {
	var bankName = $("#activityId").val();
	var bankCode = $("#actId");
	for (var i = 0; i < Hname.length; i++) {
		if (Hname[i] == bankName) {
			bankCode.val(Hcode[i]);
			break;
		}
	}
}

//  下拉列表框生成
$(function(){  	
	$('#activityId').bind('input propertychange', function() {
    currentLine=-1; // 将键盘的选中下标还原
	if (document.all)
		 it = document.getElementById("tableId").children[0];
	else
	     it = document.getElementById("tableId");
	linkDataProperty = document.getElementById("activityId"); // 获得对应的输入框对象
	var popupDiv = document.getElementById("popup");// 获得对应的div对象
	var popupBody = document.getElementById("popupBody");// 获得对应的tbody对象
	clearModels();// 清除联想输入提示框的内容
	setOffsets();// 设置联想输入下拉列表提示框的位置
	var tr, td, text;
	if (!linkDataProperty.value) {
		$("#actId").val("");
		clearModels();
		return;
	}
	var j = 0;// 设置联想框的展示条数
	for (var i = 0; i < Hname.length; i++) {// 根据返回的值，手动的拼tbody的内容
		if (Hname[i].indexOf(linkDataProperty.value) >= 0) {
			text = document.createTextNode(Hname[i]);// 从Action中返回的数据中取出linkDataProperty的值
			td = document.createElement("td");// 创建一个td的对象
			tr = document.createElement("tr");// 创建一个tr的对象
			td.mouseOver = function() {
				this.className = "mouseOver;"
			};
			td.mouseOut = function() {
				this.className = "mouseOut;"
			};
			td.onclick = function() {
				populateModel(this)
			};// 单击td是的方法为populateModel
			td.appendChild(text);
			tr.appendChild(td);
			popupBody.appendChild(tr);
			j++;
		}
		if (j > 10) {
			break;
		}
	}
	// 点击下拉列表中的某个选项时调用的方法
	function populateModel(cell) {
		clearSelect();
		if(cell.firstChild != null)
		linkDataProperty.value = cell.firstChild.nodeValue;
		clearModels();// 清除自动完成行
		getBackCodeByName();
		ajaxDate();

	}
	// 清除自动完成行，只要tbody有子节点就删除掉，并且将将外围的div的边框属性设置为不可见的
	function clearModels() {
		while (popupBody.childNodes.length > 0) {
			popupBody.removeChild(popupBody.firstChild);
		}
		popupDiv.style.border = "none";
	}
	// 设置下拉列表的位置和样式
	function setOffsets() {
		var width = linkDataProperty.offsetWidth;// 获取linkDataProperty输入框的相对宽度
		var left = getLeft(linkDataProperty);
		var top = getTop(linkDataProperty) + linkDataProperty.offsetHeight;

		popupDiv.style.border = "black 1px solid";
		popupDiv.style.left = left + "px";
		popupDiv.style.top = top + "px";
		popupDiv.style.width = width + "px";
	}
	// 获取指定元素在页面中的宽度起始位置
	function getLeft(e) {
		var offset = e.offsetLeft;
		if (e.offsetParent != null) {
			offset += getLeft(e.offsetParent);
		}
		return offset;
	}
	// 获取指定元素在页面中的高度起始位置
	function getTop(e) {
		var offset = e.offsetTop;
		if (e.offsetParent != null) {
			offset += getTop(e.offsetParent);
		}
		return offset;
	}	
  });  	  
})
// 清除联想框
function clearModels() {
	var popupDiv = document.getElementById("popup");// 获得对应的div对象
	var popupBody = document.getElementById("popupBody");// 获得对应的tbody对象
	while (popupBody.childNodes.length > 0) {
		popupBody.removeChild(popupBody.firstChild);
	}
	popupDiv.style.border = "none";
}

// 清空输入框中的数据
function clearSelect() {
	var linkDataProperty = document.getElementById("activityId");
	linkDataProperty.value = "";
}

// 绑定事件
$(function() {
	
	// 提交确认
	$(".sumbitForme").click(function() {
		if (flgs) {
			var confirmMsg = "确定补发红包活动吗";
			if (confirm(confirmMsg)) {
				return true;
			}
			return false;
		}
	});

	// 未输入正确信息清除框
	$("#activityId").change(function() {
		 flag = true;
		for (var i = 0; i < Hname.length; i++) {
			if (Hname[i] == $("#activityId").val()) {
				flag = false;
				clear = false;
				break;
			}
		}
		if (flag) {
			title = $("#redPacketTable").find("tr:first");
			title.nextAll("tr").empty();
			clearSelect();
			document.getElementById("popup").style.border = "none";
		}
	});
	// 清除未选择联想框
	$("#code").focus(function() {
		clearModels();
	});
});

// 加载活动信息函数
function ajaxDate() {
	if (!$("#activityId").val()) {
		return;
	}
	var actId = $('#actId').val();
	$.ajax({
		type : 'GET',
		dataType : 'Json',
		url : $('#example').attr('action'),
		data : {
			activityId : actId
		},
		cache : false,
		success : function(data) {
			title = $("#redPacketTable").find("tr:first");
			title.nextAll("tr").empty();
			var num = data.length + 1;
			var limitTime = null;
			$.each(data, function(i, v) {
				num--;
				if (!v.f04 && !v.limitTimeUnitName) {
					limitTime = v.limitTimeStr;
				} else {
					limitTime = v.f04 + v.limitTimeUnitName;
				}
				title.after("<tr style='text-align: center;'>" + "<td>" + num
						+ "</td>" + "<td>" + v.f03 + "</td>" + "<td>"
						+ v.sourceName + "</td>" + "<td>" + limitTime + "</td>"
						+ "<td>" + v.statusName + "</td>" + "<td>投资满" + v.f06
						+ "元即可使用</td>" + "</tr>");
			});
		},
		error : function(e) {
		}
	});
}

// 错误提示
function setErro(id, msg) {
	$("#" + id).html(msg);
	$("#" + id).addClass("error_tip");
	$("#" + id).show();
}

// form表单模拟异步，回调函数
function callBack(resultStatus, Msg, exiteCode, notExiteCode,
		havedRedEnvelopeCode) {

	$(".notExitecode_li").hide();
	$(".havedRedEnvelopecode_li").hide();

	if (exiteCode) {
		$("#code").val(exiteCode);
	} else {
		$("#code").val("");
	}

	setErro("codeErroMsg", "已经过滤错误输入项");

	if ('success' == resultStatus) {
		setErro("submitErro", Msg);
		setErro("codeErroMsg", "");
		window.location.href = $("a#back").attr("href");
	} else if ('codeErro' == resultStatus) { // 有错误推荐码 

		$("#codeErro").val(notExiteCode);
		$(".notExitecode_li").show();

	} else if ('codeHaved' == resultStatus) {

		$("#codeHaved").val(havedRedEnvelopeCode);
		$(".havedRedEnvelopecode_li").show();
	} else if ('codeErro_codeHaved' == resultStatus) {

		$("#codeErro").val(notExiteCode);
		$(".notExitecode_li").show();
		$("#codeHaved").val(havedRedEnvelopeCode);
		$(".havedRedEnvelopecode_li").show();
	} else {
		setErro("submitErro", Msg);
	}
}
// 键盘监听事件
document.onkeydown=function(e){
  e=window.event||e;
  switch(e.keyCode){
  	case 13:
  	  currentLine=-1;
  	  if(currInputValue != null)
  	  populateModel(currInputValue);
      break;
    case 38: 
      currentLine--;
      changeItem();
      break;
    case 40: 
      currentLine++;
      changeItem();
      break;
    default:
      break;
  }
}
// 回车键调用选中下拉列表
function populateModel(cell) {
	if(cell != null){
	clearSelect();
	if(cell.firstChild != null)
	linkDataProperty.value = cell.firstChild.nodeValue;
	clearModels();// 清除自动完成行
	getBackCodeByName();
	ajaxDate();
	}
}
//  完成键盘选择下拉和列表高亮
function changeItem(){
	  if(it.rows != null){
	  for(i=0;i<it.rows.length;i++){
	    it.rows[i].className="";
	  }
	  if(currentLine<0){
	    currentLine=it.rows.length-1;
	  }
	  if(currentLine==it.rows.length){
	  currentLine=0;
	  }
	  if(it.rows.length>0){
	  var objrow=it.rows[currentLine].cells[0];
	  currInputValue = objrow;
	  it.rows[currentLine].className="highlight";
	  }
	  }
	}