<%@ tag description="Author creation and updating form" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<%@ attribute name="actionUrl" required="true" rtexprvalue="true" %>
<%@ attribute name="buttonActionText" required="true" %>
<%@ attribute name="author" type="com.ihordev.bookcatalog.domain.Author" required="true" rtexprvalue="true"%>
<%@ variable name-given="errors"%>

<div class="container">

	<sf:form method="POST" action="${actionUrl}" modelAttribute="author">
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
			<label for="surname" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Фамилия</label>
			<div class="col-md-4 col-sm-6 col-xs-7">
				<sf:input path="surname" id="surname" cssClass="form-control" cssErrorClass="form-control error" />
			</div>
			<sf:errors path="surname" cssClass="error" />
		</div>
		
		<div class="form-group row">
			<label for="forname" class="col-md-1 col-sm-2 col-xs-3 col-form-label">Имя</label>
			<div class="col-md-4 col-sm-6 col-xs-7">
				<sf:input path="forename" id="forname" cssClass="form-control" cssErrorClass="form-control error" />
			</div>
			<sf:errors path="forename" cssClass="error"/>
		</div>
		
	</sf:form>

</div>