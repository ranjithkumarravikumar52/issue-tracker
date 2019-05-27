<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Projects by Users</title>
		<%@ include file="../sourcefiles.html" %>
	</head>
	<body>
		<div class="container">
			
			<div id="accordion">
				
				<c:forEach var="project" items="${projects}">
					<div class="card">
						<div class="card-header text-center">
							<a class="card-link" data-toggle="collapse" href="#${project.projectDescription.replace(" ", "")}">
								<td>${project.projectDescription}</td>
							</a>
						</div>
						<div id="${project.projectDescription.trim().replace(" ", "")}" class="collapse fade show" data-parent="#accordion">
							<div class="card-body">
								<table class="table table-striped table-hover table-sm">
									<thead class="thead-light">
										<tr>
											<th>id</th>
											<th>user_name</th>
											<th>password</th>
											<th>email</th>
											<th>user_role</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="user" items="${project.userList}">
											<tr>
												<td>${user.id}</td>
												<td>${user.userName}</td>
												<td>${user.password}</td>
												<td>${user.email}</td>
												<td>${user.userRole}</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>

			<a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-sm">Home</a>
		</div>
	</body>
</html>
