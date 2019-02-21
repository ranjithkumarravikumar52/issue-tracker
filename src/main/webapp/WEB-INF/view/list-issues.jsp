<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issues List</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <table class="table">
                <thead class="thead-light">
                    <tr>
                        <th>id</th>
                        <th>user_name</th>
                        <th>password</th>
                        <th>email</th>
                        <th>user_role</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="issue" items="${issues}">
                        <tr>
                            <td>${issue.id}</td>
                            <td>${issue.issueDescription}</td>
                            <td>${issue.postedBy.userName}</td>
                            <td>${issue.openedBy.userName}</td>
                            <td>${issue.fixedBy.userName}</td>
                            <td>${issue.closedBy.userName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
