<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8"%>
<%@ attribute name="sortBy" required="true" %>

<a href="?query=${param.query}&sortBy=${sortBy}&order=asc">⇓</a>
<a href="?query=${param.query}&sortBy=${sortBy}&order=desc">⇑</a>