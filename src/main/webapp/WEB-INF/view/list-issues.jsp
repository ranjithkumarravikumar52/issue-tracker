<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issues List</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <table class="table table-striped table-hover table-sm">
                <thead class="thead-dark">
                    <tr>
                        <th>Issue Id</th>
                        <th>Issue Description</th>
                        <th>Posted By</th>
                        <th>Opened By</th>
                        <th>Fixed By</th>
                        <th>Closed By</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="issue" items="${issues}">
                        <c:url var="updateIssueLink" value="/issue/showUpdateForm">
                            <c:param name="issueId" value="${issue.id}"/>
                        </c:url>
                        <c:url var="deleteIssueLink" value="/issue/delete">
                            <c:param name="issueId" value="${issue.id}"/>
                        </c:url>
                        <tr>
                            <td>${issue.id}</td>
                            <td>${issue.issueDescription}</td>
                            <td>${issue.postedBy.userName == null ? "-" : issue.postedBy.userName}</td>
                            <td>${issue.openedBy.userName == null ? "-" : issue.openedBy.userName}</td>
                            <td>${issue.fixedBy.userName == null ? "-" : issue.fixedBy.userName}</td>
                            <td>${issue.closedBy.userName == null ? "-" : issue.closedBy.userName}</td>
                            <td>
                                <div class="btn-group">
                                    <a href="${updateIssueLink}" class="btn btn-sm btn-dark">Update</a>
                                    <a href="${deleteIssueLink}" class="btn btn-sm btn-danger">Delete</a>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-sm">Home</a>
            <a href="${pageContext.request.contextPath}/issue/showAddForm" class="btn btn-warning btn-sm">Add New Issue</a>
        </div>
    </body>
</html>
