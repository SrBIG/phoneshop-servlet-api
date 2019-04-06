<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" scope="session" class="com.es.phoneshop.model.cart.Cart"/>

<tags:master pageTitle="Product Details">
    <c:if test="${empty cart || cart.totalQuantity <= 0}">
        Cart is empty!
    </c:if>
    <c:if test="${not empty cart && cart.totalQuantity > 0}">
        <table>
            <thead>
            <tr>
                <td>Image</td>
                <td>Description</td>
                <td>Quantity</td>
                <td class="price">Price</td>
            </tr>
            </thead>
            <c:forEach var="cartItem" items="${cart.cartItems}">
                <c:set var="product" value="${cartItem.product}"/>
                <tr>
                    <td>
                        <img class="product-tile"
                             src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                    </td>
                    <td>
                        <a href="products/${product.id}"> ${product.description}</a>
                    </td>
                    <td>
                        <input type="number" name="quantity" value="${cartItem.quantity}">
                        <input type="hidden" name="productId" value="${product.id}">
                    </td>
                    <td class="price">
                        <fmt:formatNumber value="${product.price}" type="currency"
                                          currencySymbol="${product.currency.symbol}"/>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
</tags:master>