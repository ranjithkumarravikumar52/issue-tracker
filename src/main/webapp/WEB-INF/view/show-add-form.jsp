<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Ranjith
  Date: 2/20/2019
  Time: 10:24 PM
  To change this template use File | Settings | File Templates.
--%>
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
                    <form:form action="${pageContext.request.contextPath}/user/addUser" modelAttribute="user"
                               method="POST">
                        <div class="form-group"><label>Username: </label> <form:input path="userName" type="text" class="form-control"/><br></div>
                        <div class="form-group"><label>Password: </label> <form:input path="password" type="text" class="form-control"/><br></div>
                        <div class="form-group"><label>Email: </label> <form:input path="email" type="email" class="form-control"/><br></div>
                        <%--radio boxes--%>
                        <label>Role: </label>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole"
                                                  value="developer"
                                                  class="form-check-input"/>Developer</label>
                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole"
                                                  value="tester"
                                                  class="form-check-input"/>Tester</label>
                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole"
                                                  value="admin"
                                                  class="form-check-input"/>Admin</label>
                        </div>
                        <button type="submit" class="btn btn-primary">Add User</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
