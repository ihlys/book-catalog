<%@ tag description="Book creation and updating form" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="devutil" uri="http://com.ihordev.bookcatalog/util" %>

<%@ attribute name="actionUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="buttonActionText" required="true" %>
<%@ attribute name="book" type="com.ihordev.bookcatalog.domain.Book" required="true" rtexprvalue="true"%>
<%@ variable name-given="errors"%>

<div class="container">
	
		<sf:form method="POST" action="${actionUrl}" modelAttribute="book">
			<div class="form-group row">
				<label class="control-label">
					Заполните необходимые значения и нажмите кнопку
				</label>
				<button type="submit" class="btn btn-success">
					${buttonActionText}
				</button>
			</div>
			
			<sf:hidden path="id" />
			
			<div class="form-group row">
				<label for="title" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Название</label>
				<div class="col-md-4 col-sm-6 col-xs-7">
					<sf:input path="title" id="title" cssClass="form-control" cssErrorClass="form-control error" />
					<sf:errors path="title" cssClass="error" />
				</div>
			</div>
			
			<div class="form-group row">
				<label for="description" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Краткое описание</label>
				<div class="col-md-4 col-sm-6 col-xs-7">
					<sf:textarea path="description" id="description" cssClass="form-control" cssErrorClass="form-control error" />
					<sf:errors path="description" cssClass="error" />
				</div>
			</div>
			
			<div class="form-group row">
				<label for="publicationDate" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Год издания</label>
				<div class="col-md-4 col-sm-6 col-xs-7">
					<sf:input path="publicationDate" id="publicationDate" cssClass="form-control" cssErrorClass="form-control error" />
					<sf:errors path="publicationDate" cssClass="error" />
				</div>
			</div>
			
			<div class="form-group row">
				<label for="authors" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Автор(ы)</label>
				<div class="col-md-4 col-sm-6 col-xs-7">
					<sf:select path="authors" multiple="multiple" size="5" cssErrorClass="error">
						<c:forEach items="${authorsList}" var="author">
							<c:choose>
							    <c:when test="${devutil:contains(book.authors, author)}">
							        <option value="${author.id}" selected="selected">${author.forename}&nbsp${author.surname}</option>
							    </c:when>    
							    <c:otherwise>
							        <option value="${author.id}">${author.forename}&nbsp${author.surname}</option>							        
							    </c:otherwise>
							</c:choose>
						</c:forEach>
					</sf:select>
					<sf:errors path="authors" cssClass="error" />
				</div>
			</div>
		</sf:form>
	
	</div>