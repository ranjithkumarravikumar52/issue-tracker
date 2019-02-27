<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
	<head>
		<title>DB sanity check report</title>
		<%@ include file="sourcefiles.jsp" %>
	</head>
	<body>
		<div class="container">
			<c:out value="${sanity}"/>
		</div>
	</body>
</html>
