<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new project</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <form:form action="${pageContext.request.contextPath}/project/addProject" modelAttribute="project"
                               method="POST">
                        <div class="form-group">
                            <form:input path="id" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label>Project Description*: </label>
                            <form:textarea path="projectDescription" type="text" class="form-control"/>
                            <form:errors path="projectDescription"/>
                        </div>

                        <br><br>

                        <button type="submit" class="btn btn-primary">Save Project</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
