<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="order" scope="session" class="com.es.phoneshop.model.order.Order"/>

<tags:master pageTitle="Order overview">
    <h2>Order overview</h2>
    <c:if test="${empty order || order.orderItems.isEmpty() }">
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
                        Total Price:
                    </td>
                    <td>
                            ${order.totalPrice}
                    </td>
                </tr>
            </table>
        </form>
        <form method="post" action="${pageContext.servletContext.contextPath}"> <!--TODO: add redirect to need page-->
            <p>
                <label for="name">Name:</label>
                <input id="name" name="name"/>
            </p>
            <p>
                <label for="surname">Surname:</label>
                <input id="surname" name="surname"/>
            </p>
            <p>
                <label for="phoneNumber">Phone number:</label>
                <input id="phoneNumber" name="phoneNumber"/>
            </p>
            <p>
                <label>Delivery mode:</label>
                <select name="deliveryMode}">
                    <c:forEach var="deliveryMode" items="${deliveryModes}">
                        <option name=${deliveryMode}>
                                ${deliveryMode.name}
                        </option>
                    </c:forEach>
                </select>
            </p>
            <p>
                <label for="deliveryDate">Delivery date:</label>
                <input id="deliveryDate" name="deliveryDate" type="date"/>
            </p>
            <p>
                    <label for="deliveryAddress">Delivery address:</label>
                <input id="deliveryAddress" name="deliveryAddress"/>
            </p>
            <p>
                <label>Payment method:</label>
                <c:forEach var="paymentMethod" items="${paymentMethods}">
                    <input type="radio" name="paymentMethod" value="${paymentMethod}"/>${paymentMethod}
                </c:forEach>
            </p>
            <button>
                Place order
            </button>
        </form>
    </c:if>
</tags:master>