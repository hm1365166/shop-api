<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/file/upload" method="post" enctype="multipart/form-data">
    <input type="text" name="userId" value=${user.userId}>
    <input type="file" name="file" multiple>
    <input type="submit" name="提交">
</form>
<form action="/file/downloadMul" method="get">
    <input type="text" name="userId" value=${user.userId}>
    <input type="submit" name="下载我的附件"/>
</form>
<table class="checkbox">
    <tr>
        <th>名字</th>
        <th>说明</th>
        <th>图片预览</th>
    </tr>
    <c:forEach items="${fileDate}" var="item" varStatus="status">
        <tr>
            <td><input type="checkbox" id="index${status.index + 1}" value="${item.filePath}"></td>
            <td>${item.fileName}</td>
            <td>无</td>
            <td><img style="width: 30px;height: 30px;" src='/images/20171013/${user.userId}/${item.fileName}'/></td>
        </tr>
    </c:forEach>
    <tr>
        <td><img style="width: 30px;height: 30px;" src='https://www.yyfax.com/fileStore/0/2017/5/3/7497.jpg'/></td>
    </tr>
</table>

<form action="/file/downloadMulByUrls" method="post">
    <input type="hidden" name="url" id="loadUrl" value="">
    <input type="submit" value="下载文件">
</form>
<div>
    <div class="col-md-4 column">
        <label>本页共${pageUser.size}个图片,共${pageUser.pages}页${pageUser.total}个图片</label>
    </div>
    <div>
        <table>
            <tr>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=1"<c:if test="${pageUser.pageNum==1}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>首页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.prePage}"<c:if
                        test="${pageUser.pageNum==1}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>上页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.nextPage}" <c:if
                        test="${pageUser.pageNum==pageUser.pages}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>下页</a></th>
                <th><a href="${pageContext.request.contextPath}/file/toFile?pageNum=${pageUser.pages}" <c:if
                        test="${pageUser.pageNum==pageUser.pages}"> onclick=" return document.execCommand('refresh',false,0)"</c:if>>尾页</a></th>
            </tr>
        </table>
    </div>
</div>
<script type="text/javascript" src="${pageContext.request.contextPath}/resource/js/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

    $(function () {
        $("input:checkbox").each(function (i) {
            $(this).on('click', function () {
                var id = "index" + (i + 1);
                console.log(id);
                var s = $("#loadUrl").val();
                if (document.getElementById(id).checked) {
                    s = s + ";" + $(this).val();
                    $("#loadUrl").val(s);
                } else {
                    s = getStringByIndex(id, s);
                    $("#loadUrl").val(s);
                }
            });
        });

        function getStringByIndex(id, url) {
            var string = document.getElementById(id).value;
            var s = url.replace(";" + string, "");
            return s;
        }
    });
</script>
</body>
</html>
