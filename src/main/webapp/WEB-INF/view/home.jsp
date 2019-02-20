<%--
  Created by IntelliJ IDEA.
  User: Ranjith
  Date: 2/19/2019
  Time: 1:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issue Tracker</title>
        <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
        <script src="/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css" />
    </head>
    <body>
        <div class="container"><br/>
            <div class="alert alert-success">
                <a href="#" class="close" data-dismiss="alert"
                   aria-label="close">×</a>
                <strong>Success!</strong> It is working as we expected.
            </div>
        </div>
        <a href="${pageContext.request.contextPath}/TestJDBCServlet">Test DB Connection</a>
        <a href="${pageContext.request.contextPath}/user/listusers">List Users</a>
    </body>
</html>
