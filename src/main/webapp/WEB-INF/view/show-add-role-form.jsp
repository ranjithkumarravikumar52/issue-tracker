<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new user</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
        <div class="container">
            <div class="row">
                <div class="col">
                    <form:form action="${pageContext.request.contextPath}/role/addRole" modelAttribute="role" method="POST">
                        <form:hidden path="id"/>

                        <div class="form-group">
                            <label>Role*: </label>
                            <form:input path="name" type="text" class="form-control"/>
                            <form:errors path="name"/>
                        </div>

                        <button type="submit" class="btn btn-primary">Save Role</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
