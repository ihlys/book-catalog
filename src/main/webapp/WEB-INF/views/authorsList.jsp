<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<layouts:main>

	<div class="container">
	
		<div class="greyTable">
	
			<sec:authorize access="hasRole('ADMIN')">
				<div class="pull-right">
					<a href="<c:url value="/authors/create"/>" class="btn btn-default">Создать автора</a>
				</div>
			</sec:authorize>
				
			<div>
				<h3>Список авторов</h3>
			</div>
			
			<table>
				<thead>
					<tr>
						<th>Фамилия</th>
						<th>Имя</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${authors}" var="author">
						<tr>
							<td>${author.forename}</td>
							<td>${author.surname}</td>
							<td>
								<sec:authorize access="hasRole('ADMIN')">
									<a href="<c:url value="/authors/${author.id}/update"/>">Модифицировать</a>
									<form class="form-link" method="POST" action="<c:url value="/authors/delete"/>">
										<input type="hidden" name="authorId" value="${author.id}"/>
										<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
										<button class="btn-link">Удалить</button>
									</form>
								</sec:authorize>
								<a href="<c:url value="/authors/${author.id}"/>">Список книг</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				
		</div>
	
	</div>

</layouts:main>