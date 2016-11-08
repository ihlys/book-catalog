<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts" %>


<layouts:main>

	<div class="container">
	
		<div class="greyTable">
				
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
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${author.books}" var="book">
						<tr>
							<td>${book.title}</td>
							<td>${book.description}</td>
							<td><fmt:formatDate value="${book.publicationDate}" pattern="yyyy"/></td>
							<td>
								<c:forEach items="${book.authors}" var="author">
									${author.forename}&nbsp${author.surname}<br>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
				
		</div>
	
	</div>

</layouts:main>