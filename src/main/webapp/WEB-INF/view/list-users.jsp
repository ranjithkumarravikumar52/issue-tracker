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
                        <th>action</th>
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

                            <td>
                                <div class="btn-group">
                                    <a href="${updateLink}" class="btn btn-sm btn-info">Update</a>
                                    <a href="${deleteLink}" class="btn btn-sm btn-danger">Delete</a>
                                </div>
                            </td>
                            <%--<td><a href="${updateLink}" class="btn btn-sm btn-info">Update</a>--%>
                            <%--<a href="${deleteLink}" class="btn btn-sm btn-danger">Update</a></td>--%>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/user/showAddForm" class="btn btn-primary">Add New User</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Home</a>

        </div>
    </body>
</html>
