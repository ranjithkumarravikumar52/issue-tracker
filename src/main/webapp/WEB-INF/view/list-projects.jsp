<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>List all projects</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <table class="table table-striped table-hover table-sm">
                <thead class="thead-dark">
                    <tr>
                        <th>id</th>
                        <th>description</th>
                        <th>action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="project" items="${projects}">
                        <c:url var="updateLink" value="/project/showUpdateForm">
                            <c:param name="projectId" value="id"/>
                        </c:url>
                        <tr>
                            <td>${project.id}</td>
                            <td>${project.projectDescription}</td>
                            <td><a href="${updateLink}" class="btn btn-sm btn-info">Update</a></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-sm">Home</a>
            <a href="${pageContext.request.contextPath}/project/showAddForm" class="btn btn-primary btn-sm btn-warning">Add New Project</a>
        </div>
    </body>
</html>
