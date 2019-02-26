<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Ranjith
  Date: 2/23/2019
  Time: 1:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>Login</title>
		<%@ include file="sourcefiles.jsp" %>
	</head>
	
	
	<body>
		<div class="container border-dark">
			
			<div id="loginbox" style="margin-top: 50px;"
			     class="mainbox col-md-3 col-md-offset-2 col-sm-6 col-sm-offset-2">
				
				<div class="panel panel-info">
					
					<div class="panel-heading">
						<div class="panel-title">Custom login form, login with your username and password</div>
					</div>
					
					<div style="padding-top: 30px" class="panel-body">
						
						<!-- Login Form -->
						<form:form action="${pageContext.request.contextPath}/authenticateTheUser"
						           method="POST" class="form-horizontal">
							
							<!-- Place for messages: error, alert etc ... -->
							<div class="form-group">
								<div class="col-xs-15">
									<div>
										<!-- Check for login error -->
										<c:if test="${param.error != null}">
											<div class="alert alert-danger col-xs-offset-1 col-xs-10">
												Invalid username and password.
											</div>
										</c:if>
										
										<%--logout status--%>
										<c:if test="${param.logout != null}">
											<div class="alert alert-success col-xs-offset-1 col-xs-10">
												You have been logged out.
											</div>
										</c:if>
									</div>
								</div>
							</div>
							
							<!-- User name -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
								
								<input type="text" name="username" placeholder="username" class="form-control">
							</div>
							
							<!-- Password -->
							<div style="margin-bottom: 25px" class="input-group">
								<span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
								
								<input type="password" name="password" placeholder="password" class="form-control">
							</div>
							
							<!-- Login/Submit Button -->
							<div style="margin-top: 10px" class="form-group">
								<div class="col-sm-6 controls">
									<button type="submit" class="btn btn-success">Login</button>
								</div>
							</div>
						
						</form:form>
					
					
					</div>
					<%--panel body--%>
				</div>
			
			</div>
		</div>
	</body>

</html>
