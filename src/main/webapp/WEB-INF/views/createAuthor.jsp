<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layouts" tagdir="/WEB-INF/tags/layouts" %>
<%@ taglib prefix="partial" tagdir="/WEB-INF/tags/partial" %>


<layouts:main>

	<c:url value="/authors/create" var="actionUrl" />
	<partial:authorForm author="${author}" 
						actionUrl="${actionUrl}" 
						buttonActionText="Создать">
	</partial:authorForm>
	
</layouts:main>