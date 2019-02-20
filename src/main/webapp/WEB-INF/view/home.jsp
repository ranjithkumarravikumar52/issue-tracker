<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Issue Tracker</title>
        <%@ include file="sourcefiles.jsp" %>
    </head>
    <body>
      <div class="container">
          <a href="${pageContext.request.contextPath}/TestJDBCServlet">Test DB Connection</a>
          <br>
          <a href="${pageContext.request.contextPath}/user/listusers">List Users</a>
      </div>
    </body>
</html>
