<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 2022/10/27
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Input</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<c:set value="${pageContext.request.contextPath}/emp" var="url"/>
<c:if test="${employee.id != null}">
    <c:set value="${pageContext.request.contextPath}/emp/${employee.id}" var="url"/>
</c:if>

<form:form action="${url}" method="POST" modelAttribute="employee">
    <c:if test="${employee.id != null}">
        <input type="hidden" name="_oldLastName" value="${employee.lastName}"/>
        <form:hidden path="id"/>
        <input type="hidden" name="_method" value="PUT"/>
    </c:if>
    LastName: <form:input path="lastName"/>
    <br/>
    Email: <form:input path="email"/>
    <br/>
    Birth: <form:input path="birth"/>
    <br/>
    Department:
    <form:select path="dept.id" items="${depts}" itemLabel="deptName" itemValue="id"/>
    <br/>
    <input type="submit" value="Submit"/>
</form:form>
<script>
    $(function() {
        $("#lastName").change(function () {
            let val = $(this).val();
            val = $.trim(val);
            $(this).val(val);

            let _oldLastName = $("#_oldLastName").val();
            _oldLastName = $.trim(_oldLastName);
            if (_oldLastName !== null && _oldLastName !== "" && _oldLastName === val) {
                return;
            }

            let url = "${pageContext.request.contextPath}/ajaxValidateLastName";
            let args = {"lastName": val, "date": new Date()};

            $.post(url, args, function(data) {
                if (data === "1"){
                    alert("lastName 不可用！");
                } else if (data !== "0") {
                    alert("网络或程序出错。");
                }
            })
        })
    })
</script>
</body>
</html>
