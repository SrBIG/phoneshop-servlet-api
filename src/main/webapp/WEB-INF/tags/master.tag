<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" %>
<%@ attribute name="pageTitle" required="true" %>

<jsp:useBean id="cart" scope="session" class="com.es.phoneshop.model.cart.Cart"/>

<html>
<head>
    <title>${pageTitle}</title>
    <link href='http://fonts.googleapis.com/css?family=Lobster+Two' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/main.css">
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/styles/message.css">
</head>
<body class="product-list">
<header>
    <a href="${pageContext.servletContext.contextPath}">
        <img src="${pageContext.servletContext.contextPath}/images/logo.svg"/>
        PhoneShop
    </a>
    <a href="${pageContext.servletContext.contextPath}/cart">
        Cart: ${cart}
        <c:if test="${empty cart}"> empty </c:if>
    </a>
</header>
<main>
    <jsp:doBody/>
</main>
<tags:recentlyViewed/>
</body>
</html>