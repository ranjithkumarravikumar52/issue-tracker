<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <title>List users</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <table class="table">
                <thead class="thead-dark">
                    <tr>
                        <th>id</th>
                        <th>user_name</th>
                        <th>password</th>
                        <th>email</th>
                        <th>user_role</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.userName}</td>
                            <td>${user.password}</td>
                            <td>${user.email}</td>
                            <td>${user.userRole}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Home</a>
        </div>
    </body>
</html>
