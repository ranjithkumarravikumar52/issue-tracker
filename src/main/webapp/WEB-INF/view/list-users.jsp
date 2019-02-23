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
            <table class="table table-striped table-hover table-sm">
                <thead class="thead-dark">
                    <tr>
                        <th>id</th>
                        <th>user_name</th>
                        <th>password</th>
                        <th>email</th>
                        <th>user_role</th>
                        <th class="text-center">action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="user" items="${users}">
                        <%--update link--%>
                        <c:url var="updateLink" value="/user/showUpdateForm">
                            <c:param name="userId" value="${user.id}"/>
                        </c:url>

                        <%--delete link--%>
                        <c:url var="deleteLink" value="/user/delete">
                            <c:param name="userId" value="${user.id}"/>
                        </c:url>

                        <tr>
                            <td>${user.id}</td>
                            <td>${user.userName}</td>
                            <td>${user.password}</td>
                            <td>${user.email}</td>
                            <td>${user.userRole}</td>

                            <td class="text-center">
                                <div class="btn-group">
                                    <a href="${updateLink}" class="btn btn-sm btn-info">Update</a>
                                    <a href="${deleteLink}" class="btn btn-sm btn-danger">Delete</a>
                                </div>
                            </td>
                        </tr>

                    </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-sm">Home</a>
            <a href="${pageContext.request.contextPath}/user/showAddForm" class="btn btn-warning btn-sm">Add New User</a>

        </div>
    </body>
</html>
