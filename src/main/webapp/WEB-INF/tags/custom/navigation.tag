<%@ tag description="Navigation bar" pageEncoding="UTF-8"%>
<%@ attribute name="navigationPaths" type="java.util.List" rtexprvalue="true" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:forEach items="${navigationPaths}" var="navPath" varStatus="loop">
	<c:choose>
		<c:when test="${not loop.last}">
			<c:url value="${navPath.path}" var="navPathUrl"/>
			<a href="${navPathUrl}">${navPath.pathText}</a>&nbsp>&nbsp
		</c:when>
		<c:otherwise>
			${navPath.pathText}
		</c:otherwise>
	</c:choose>

</c:forEach>