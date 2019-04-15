<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="order" scope="session" class="com.es.phoneshop.model.order.Order"/>

<tags:master pageTitle="Order overview">
    <h2>Order overview</h2>
    <c:if test="${empty order || order.orderItems.isEmpty()}">
        Order is empty!
    </c:if>
    <c:if test="${not empty order && !order.orderItems.isEmpty()}">
        <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
            <table>
                <thead>
                <tr>
                    <td>Image</td>
                    <td>Description</td>
                    <td>Quantity</td>
                    <td class="price">Price</td>
                </tr>
                </thead>
                <c:forEach var="cartItem" items="${order.orderItems}" varStatus="status">
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
                                ${cartItem.quantity}
                        </td>
                        <td class="price">
                            <fmt:formatNumber value="${product.price}" type="currency"
                                              currencySymbol="${product.currency.symbol}"/>
                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <td colspan="3">
                            ${order.deliveryMode}
                    </td>
                    <td class="price">
                            ${order.deliveryMode.cost}
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        Total Price:
                    </td>
                    <td class="price">
                            ${order.totalPrice}
                    </td>
                </tr>
            </table>
        </form>
        <p>
            <label>Name: ${order.customer.name}</label>
        </p>
        <p>
            <label>Surname: ${order.customer.surname}</label>
        </p>
        <p>
            <label>Phone number: ${order.customer.phoneNumber}</label>
        </p>
        <p>
            <label>Delivery mode: ${order.deliveryMode.name}</label>
        </p>
        <p>
            <label>Delivery date: <fmt:formatDate value="${order.deliveryDate}" pattern="dd.MM.yyyy"/></label>
        </p>
        <p>
            <label>Delivery address: ${order.deliveryAddress}</label>
        </p>
        <p>
            <label>Payment method: ${order.paymentMethod}</label>
        </p>
    </c:if>
</tags:master>