<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<layouts:main>

	<div class="container">
		
		<div class="row">
			<form method="GET" action="<c:url value="/books"/>" class="form-inline">
				<div class="form-group">
					<label for="book-title" class="control-label">Введите название книги</label>
				   	<input type="text" name="bookTitle" class="form-control" id="book-title">
			   	</div>
				<button type="submit" class="btn btn-success">
					Искать книгу
				</button>
			</form>
		</div>
	
		<div class="row">
			<div class="greyTable">
		
				<sec:authorize access="hasRole('ADMIN')">
					<div class="pull-right">
						<a href="<c:url value="/books/create"/>" class="btn btn-default">Создать новую книгу</a>
					</div>
				</sec:authorize>
					
				<div>
					<h3>Список книг</h3>
				</div>
				
				<table>
					<thead>
						<tr>
							<th>Название</th>
							<th>Краткое описание</th>
							<th>Год издания</th>
							<th>Авторы</th>
							<sec:authorize access="hasRole('ADMIN')">
								<th>Actions</th>
							</sec:authorize>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${books}" var="book">
							<tr>
								<td>${book.title}</td>
								<td>${book.description}</td>
								<td><fmt:formatDate value="${book.publicationDate}" pattern="yyyy"/></td>
								<td>
									<c:forEach items="${book.authors}" var="author">
										${author.forename}&nbsp${author.surname}&nbsp
									</c:forEach>
								</td>
								<sec:authorize access="hasRole('ADMIN')">
									<td>
										<a href="<c:url value="/books/${book.id}/update"/>">Модифицировать</a>
										<form class="form-link" method="POST" action="<c:url value="/books/delete"/>">	
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<input type="hidden" name="bookId" value="${book.id}" />
											<button class="btn-link">Удалить</button>
										</form>
									</td>
								</sec:authorize>
							</tr>
						</c:forEach>
					</tbody>
				</table>
					
			</div>
		
		</div>

	</div>
	
</layouts:main>
