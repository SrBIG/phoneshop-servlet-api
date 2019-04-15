<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="order" scope="session" class="com.es.phoneshop.model.order.Order"/>

<tags:master pageTitle="Checkout">
    <h2>Checkout</h2>
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
        <form method="post" action="${pageContext.servletContext.contextPath}/checkout">
            <p>
                <c:if test="${not empty nameError}">
                    <span class="error">${nameError}</span>
                    <br>
                </c:if>
                <label for="name">Name:</label>
                <input id="name" name="name" value="${not empty param.name ? param.name : ""}"/>
            </p>
            <p>
                <c:if test="${not empty surnameError}">
                    <span class="error">${surnameError}</span>
                    <br>
                </c:if>
                <label for="surname">Surname:</label>
                <input id="surname" name="surname" value="${not empty param.surname ? param.surname : ""}"/>
            </p>
            <p>
                <c:if test="${not empty phoneNumberError}">
                    <span class="error">${phoneNumberError}</span>
                    <br>
                </c:if>
                <label for="phoneNumber">Phone number:</label>
                <input id="phoneNumber" name="phoneNumber" value="${not empty param.phoneNumber ? param.phoneNumber : ""}"/>
            </p>
            <p>
                <c:if test="${not empty deliveryModeError}">
                    <span class="error">${deliveryModeError}</span>
                    <br>
                </c:if>
                <label>Delivery mode:</label>
                <br>
                <c:forEach var="deliveryMode" items="${deliveryModes}">
                    <input type="radio" id="${deliveryMode}" name="deliveryMode" value="${deliveryMode}" checked/>
                    <label for="${deliveryMode}">${deliveryMode.name} (${deliveryMode.cost})</label>
                </c:forEach>
            </p>
            <p>
                <c:if test="${not empty deliveryDateError}">
                    <span class="error">${deliveryDateError}</span>
                    <br>
                </c:if>
                <label for="deliveryDate">Delivery date:</label>
                <input id="deliveryDate" name="deliveryDate" type="date" value="${not empty param.deliveryDate ? param.deliveryDate : ""}"/>
            </p>
            <p>
                <c:if test="${not empty deliveryAddressError}">
                    <span class="error">${deliveryAddressError}</span>
                    <br>
                </c:if>
                <label for="deliveryAddress">Delivery address:</label>
                <input id="deliveryAddress" name="deliveryAddress" value="${not empty param.deliveryAddress ? param.deliveryAddress : ""}"/>
            </p>
            <p>
                <c:if test="${not empty paymentMethodError}">
                    <span class="error">${paymentMethodError}</span>
                    <br>
                </c:if>
                <label>Payment method:</label>
                <br>
                <c:forEach var="paymentMethod" items="${paymentMethods}">
                    <input type="radio" id="${paymentMethod}" name="paymentMethod" value="${paymentMethod}" checked/>
                    <label for="${paymentMethod}">${paymentMethod}</label>
                </c:forEach>
            </p>
            <button>
                Place order
            </button>
        </form>
    </c:if>
</tags:master>