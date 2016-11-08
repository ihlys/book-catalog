<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<layouts:main>

	<div class="container">
		<div class="row">
			<ul class="menu">
			    <li><a href="<c:url value="/books"/>">Список книг</a></li>
			    <li><a href="<c:url value="/authors"/>">Список авторов</a></li>
			</ul>
		</div>
	</div>
</layouts:main>

