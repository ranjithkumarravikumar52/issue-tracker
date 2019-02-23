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
                    <form:form action="${pageContext.request.contextPath}/user/addUser" modelAttribute="user"
                               method="POST">
                        <form:hidden path="id"/>
                        <div class="form-group">
                            <label>Username*: </label>
                            <form:input path="userName" type="text" class="form-control"/>
                            <form:errors path="userName"/>
                        </div>
                        <div class="form-group">
                            <label>Password*: </label>
                            <form:input path="password" type="text" class="form-control"/>
                            <form:errors path="password"/>
                        </div>
                        <div class="form-group">
                            <label>Email*: </label>
                            <form:input path="email" type="email" class="form-control"/>
                            <form:errors path="email"/>
                        </div>
                        <%--radio boxes--%>
                        <label>Role*: </label>
                        <br>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole" value="developer" class="form-check-input" />Developer
                                <form:errors path="userRole"/>
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole" value="tester" class="form-check-input"/>Tester
                                <form:errors path="userRole"/>
                            </label>
                        </div>
                        <div class="form-check-inline">
                            <label class="form-check-label">
                                <form:radiobutton path="userRole" value="admin" class="form-check-input"/>Admin
                                <form:errors path="userRole"/>
                            </label>
                        </div>
                        <br>
                        <br>
                        <button type="submit" class="btn btn-primary">Save User</button>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
