<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<title>Issue Tracker</title>
		<%@ include file="sourcefiles.jsp" %>
	</head>
	<body>
		<div class="container">
			<div class="row">
				<div class="col">
					<p class="lead text-center">
						Welcome to Issue Tracker. Start using this website, by choosing any of the below option.
					</p>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<a href="${pageContext.request.contextPath}/TestJDBCServlet" class="btn btn-info">Test DB
						Connection</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/user/listusers" class="btn btn-primary">List Users</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/issue/issueList" class="btn btn-primary">List Issues</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/project/list" class="btn btn-primary">List Project</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/project/projectUserList" class="btn btn-warning">List
						Projects by Users</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/user/userProjectList" class="btn btn-warning">List Users
						by Project</a>
				</div>
				<div class="col">
					<form:form action="${pageContext.request.contextPath}/logout" method="post">
						<input type="submit" value="logout"/>
					</form:form>
					
				</div>
			</div>
		</div>
	</body>
</html>
