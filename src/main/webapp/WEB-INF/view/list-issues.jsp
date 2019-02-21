<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issues List</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <table class="table table-striped table-hover">
                <thead class="thead-dark">
                    <tr>
                        <th>Issue Id</th>
                        <th>Issue Description</th>
                        <th>Posted By</th>
                        <th>Opened By</th>
                        <th>Fixed By</th>
                        <th>Closed By</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="issue" items="${issues}">
                        <tr>
                            <td>${issue.id}</td>
                            <td>${issue.issueDescription}</td>
                            <td>${issue.postedBy.userName == null ? "-" : issue.postedBy.userName}</td>
                            <td>${issue.openedBy.userName == null ? "-" : issue.openedBy.userName}</td>
                            <td>${issue.fixedBy.userName == null ? "-" : issue.fixedBy.userName}</td>
                            <td>${issue.closedBy.userName == null ? "-" : issue.closedBy.userName}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
