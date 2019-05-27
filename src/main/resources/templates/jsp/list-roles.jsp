<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Listing Roles</title>
		<%@ include file="../sourcefiles.html" %>
	</head>
	<body>
		<div class="container">
			<table class="table table-striped table-hover table-sm">
				<thead class="thead-dark">
					<tr>
						<th>Role Id</th>
						<th>Role Name</th>
						<th class="text-center">action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="role" items="${roles}">
						<%--update link--%>
						<c:url var="updateLink" value="/role/showUpdateForm">
							<c:param name="roleId" value="${role.id}"/>
						</c:url>
						
						<%--delete link--%>
						<c:url var="deleteLink" value="/role/delete">
							<c:param name="roleId" value="${role.id}"/>
						</c:url>
						
						<tr>
							<td>${role.id}</td>
							<td>${role.name}</td>
							
							<%--TODO get users based on this role--%>
							<%--<c:forEach var = "role" items="${user.roleList}">
								<td>
										${role.name}
								</td>
							</c:forEach>--%>
							
							<td class="text-center">
								<div class="btn-group">
									<a href="${updateLink}" class="btn btn-sm btn-info">Update</a>
									<a href="${deleteLink}" class="btn btn-sm btn-danger">Delete</a>
								</div>
							</td>
						</tr>
					
					</c:forEach>
				</tbody>
			</table>
			<a href="${pageContext.request.contextPath}/" class="btn btn-primary btn-sm">Home</a>
			<a href="${pageContext.request.contextPath}/role/showAddForm" class="btn btn-warning btn-sm">Add New Role</a>
		
		</div>
	</body>
</html>
