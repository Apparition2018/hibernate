<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2022/10/27
  Time: 1:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>List</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<form action="" method="POST" id="_form">
    <input type="hidden" id="_method" name="_method"/>
</form>
<c:if test="${page == null || page.numberOfElements == 0}">
    没有任何记录.
</c:if>
<c:if test="${page != null || page.numberOfElements > 0}">
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>LastName</th>
            <th>Email</th>
            <th>Birth</th>
            <th>CreateTime</th>
            <th>Department</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        <c:forEach items="${page.content}" var="emp">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.lastName}</td>
                <td>${emp.email}</td>
                <td>
                    <fmt:formatDate value="${emp.birth}" pattern="yyyy-MM-dd"/>
                </td>
                <td>
                    <fmt:formatDate value="${emp.createTime}" pattern="yyyy-MM-dd hh:mm:ss"/>
                </td>
                <td>${emp.dept.deptName}</td>
                <td><a href="${pageContext.request.contextPath}/emp/${emp.id}">Edit</a></td>
                <td>
                    <a href="${pageContext.request.contextPath}/emp/${emp.id}" class="delete">Delete</a>
                    <input type="hidden" value="${emp.lastName}"/>
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8">
                共 ${page.totalElements} 条记录
                共 ${page.totalPages} 页
                当前 ${page.number + 1} 页
                <c:if test="${page.number + 1 != 1}">
                    <a href="?pageNo=${page.number + 1 - 1}">上一页</a>
                </c:if>
                <c:if test="${page.number + 1 != page.totalPages}">
                    <a href="?pageNo=${page.number + 1 + 1}">下一页</a>
                </c:if>
            </td>
        </tr>
    </table>
</c:if>
<script>
    $(function () {
        $(".delete").click(function() {
            let label = $(this).next(":hidden").val();
            let flag = confirm("确定要删除" + label+ "的信息吗？");
            if (flag) {
                let url = $(this).attr("href");

                <!-- GET 请求转为 DELETE 请求 -->
                $("#_form").attr("action", url);
                $("#_method").val("DELETE");
                $("#_form").submit();
            }
            return false;
        })
    })
</script>
</body>
</html>
