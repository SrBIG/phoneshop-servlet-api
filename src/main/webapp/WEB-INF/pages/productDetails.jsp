<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product Details">

    <h3>
            ${product.description}
    </h3>
    <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
    <p>
        Price: <fmt:formatNumber value="${product.price}" type="currency"
                                 currencySymbol="${product.currency.symbol}"/>
    </p>
    <p>
        Stock: ${product.stock}
    </p>
    <form method="post" action="${pageContext.servletContext.contextPath}/products/${product.id}">
        <p>
            <input type="number" min="1" max="${product.stock}" name="quantity" value="${not empty param.quantity ? param.quantity : 1}">
            <button>Add to cart</button>
            <c:if test="${not empty error}">
                <span class="error">
                        <br>${error}
                </span>
            </c:if>
            <c:if test="${not empty param.message}">
                <span class="success">
                    <br>${param.message}
                </span>
            </c:if>
        </p>
    </form>
</tags:master>