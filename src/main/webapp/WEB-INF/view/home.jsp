<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issue Tracker</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
      <div class="container">
         <div class ="row">
             <div class="col"><a href="${pageContext.request.contextPath}/TestJDBCServlet">Test DB Connection</a></div>
             <div class="col"><a href="${pageContext.request.contextPath}/user/listusers">List Users</a></div>
             <div class="col"><a href="${pageContext.request.contextPath}/issue/issueList">List Issues</a></div>
         </div>
      </div>
    </body>
</html>
