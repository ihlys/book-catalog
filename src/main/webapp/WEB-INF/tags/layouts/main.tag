<%@ tag description="Main pages general structure" pageEncoding="UTF-8"%>

<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/custom" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>

<html>

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
		<title>Book catalog main page</title>
		
		<!-- Bootstrap -->
		<link href="<c:url value="/resources/css/bootstrap.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/custom-bootstrap.css"/>" rel="stylesheet">
		<link href="<c:url value="/resources/css/styles.css"/>" rel="stylesheet">
   	</head>

	<body>
		
		<div class="jumbotron header">
			<div class="container">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
			      		<span id="header-title">Книжный каталог</span>
					</div>
				</div>
			</div>
		</div>
       
		<div class="container navbar">
			<div class="row">
				<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
					<nav:navigation navigationPaths="${navigationPaths}" />
				</div>
				<div class="col-xs-2 col-sm-2 col-md-2 col-lg-2 text-right">
					<c:url value="/logout" var="logoutUrl"/>
					<sf:form action="${logoutUrl}" method="post">
						<button class="btn-link">Logout</button>
					</sf:form>
				</div>
			</div>
		</div>

		<!-- Render content  -->
		<jsp:doBody/>

		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	    <!-- Include all compiled plugins (below), or include individual files as needed -->
	    <script src="<c:url value="/resources/js/bootstrap.js"/>"></script>
	
	</body>
</html>