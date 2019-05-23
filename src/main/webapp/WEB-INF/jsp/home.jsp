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
			<%--TODO implement thymeleaf templates here--%>
			<%--list entities--%>
			<div class="row mt-5">
				<div class="col">
					<a href="${pageContext.request.contextPath}/listUsers" class="btn btn-primary">List Users</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/issue/issueList" class="btn btn-primary">List Issues</a>
				</div>
				<div class="col">
                    <a href="${pageContext.request.contextPath}/listProjects" class="btn btn-primary">List Project</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/listRoles" class="btn btn-primary">List Roles</a>
				</div>
			</div>
			
			<%--admin buttons here--%>
			<div class="row mt-5">
				<div class="col">
					<a href="${pageContext.request.contextPath}/sanityCheck/dbConnection" class="btn btn-info">Test DB Connection</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/project/projectUserList" class="btn btn-warning">List Projects by Users(WIP)</a>
				</div>
				<div class="col">
					<a href="${pageContext.request.contextPath}/user/userProjectList" class="btn btn-warning">List Users by Project(WIP)</a>
				</div>
			</div>
			
			
			<%--Logout button--%>
			<div class="row mt-5">
				<div class="col align-center">
					<div class="col">
						<form:form action="${pageContext.request.contextPath}/logout" method="post">
							<input type="submit" class="btn btn-danger" value="logout"/>
						</form:form>
					</div>
				</div>
			</div>
			
		</div>
	</body>
</html>
