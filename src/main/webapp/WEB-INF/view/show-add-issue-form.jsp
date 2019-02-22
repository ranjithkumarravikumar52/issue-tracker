<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new issue</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <form:form action="${pageContext.request.contextPath}/issue/addIssue" modelAttribute="issue"
                               method="POST">
                        <form:hidden path="id"/>

                        <div class="form-group">
                            <label>Issue description*: </label>
                            <form:textarea class = "textarea form-control" path="issueDescription" rows="5" />
                            <form:errors path="issueDescription"/>
                        </div>

                        <div class="form-group">
                            <label>Posted by Id:*: </label>
                            <form:input class = "form-control" path="postedBy.id" />
                            <form:errors path="issueDescription"/>
                        </div>

                        <button type="submit" class="btn btn-primary">New Issue</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
