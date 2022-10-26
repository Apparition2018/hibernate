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
</head>
<body>
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
                <td><a href="">Edit</a></td>
                <td><a href="">Delete</a></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="8">
                共 ${page.totalElements} 条记录
                共 ${page.totalPages} 页
                当前 ${page.number + 1} 页
                <a href="?pageNo=${page.number + 1 - 1}">上一页</a>
                <a href="?pageNo=${page.number + 1 + 1}">下一页</a>
            </td>
        </tr>
    </table>
</c:if>
</body>
</html>
