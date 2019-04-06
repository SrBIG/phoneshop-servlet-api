<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="cart" scope="session" class="com.es.phoneshop.model.cart.Cart"/>

<tags:master pageTitle="Cart">
    <h2>Cart</h2>
    <c:if test="${not empty errors}">
        <p class="error">Error updating cart</p>
    </c:if>
    <c:if test="${empty cart || cart.totalQuantity <= 0}">
        Cart is empty!
    </c:if>
    <c:if test="${not empty cart && cart.totalQuantity > 0}">
        <form method="post" action="${pageContext.servletContext.contextPath}/cart">
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Quantity</td>
                    <td class="price">Price</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${cart.cartItems}" varStatus="status">
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
                            <input type="number" name="quantity"
                                   value="${not empty paramValues.quantity[status.index] ? paramValues.quantity[status.index] : cartItem.quantity}">
                            <input type="hidden" name="productId" value="${product.id}">
                            <br>
                            <c:if test="${not empty errors[status.index]}">
                                <span class="error">${errors[status.index]}</span>
                            </c:if>
                        </td>
                        <td class="price">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                        Total Price:
                    </td>
                    <td>
                            ${cart.totalPrice}
                    </td>
                </tr>
            </table>
            <br>
            <button>
                Update
            </button>
        </form>
    </c:if>
</tags:master>