<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" scope="session" class="com.es.phoneshop.model.cart.Cart"/>

<a href="${pageContext.servletContext.contextPath}/cart" style="color: snow;">
    Cart: ${cart}
    <c:if test="${empty cart}"> empty </c:if>
</a>