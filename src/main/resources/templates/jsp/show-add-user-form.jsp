<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Add new user</title>
        <%@ include file="../sourcefiles.html" %>
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
                            <label>First Name*: </label>
                            <form:input path="firstName" type="text" class="form-control"/>
                            <form:errors path="firstName"/>
                        </div>
    
                        <div class="form-group">
                            <label>Last Name*: </label>
                            <form:input path="lastName" type="text" class="form-control"/>
                            <form:errors path="lastName"/>
                        </div>
                        
                        <div class="form-group">
                            <label>Email*: </label>
                            <form:input path="email" type="email" class="form-control"/>
                            <form:errors path="email"/>
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
