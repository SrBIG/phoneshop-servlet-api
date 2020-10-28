<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<jsp:useBean id="productReviews" type="java.util.ArrayList" scope="request"/>

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
            <input type="number" min="1" max="${product.stock}" name="quantity"
                   value="${not empty param.quantity ? param.quantity : 1}">
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

    <%--    <tags:productReview productId="${product.id}" productReviews="${productReviews}"/>--%>

    <h3>Product Reviews: </h3>

    <c:if test="${productReviews.isEmpty()}">
        There are no reviews yet! Be first!
    </c:if>

    <hr>
    <c:forEach var="review" items="${productReviews}">
        <h4>${review.userName}</h4>
        <p> Rating: ${review.rating}</p>
        <p> Comment:
            <br>
                ${review.comment}
        </p>
        <hr>
    </c:forEach>


    <form method="post" action="${pageContext.servletContext.contextPath}/postProductReview/${product.id}">
        <h3>Your review: </h3>

        <p>
                <%--    <c:if test="${not empty nameError}">--%>
                <%--        <span class="error">${nameError}</span>--%>
                <%--        <br>--%>
                <%--    </c:if>--%>
            <label for="name">Name:</label>
            <input id="name" name="name" value="${not empty param.name ? param.name : ""}"/>
        </p>
        <p>
            <label>Rating:</label>
            <br>
            <input type="radio" id="rating-1" name="rating" value="1"/>
            <label for="rating-1">1</label>

            <input type="radio" id="rating-2" name="rating" value="2"/>
            <label for="rating-2">2</label>

            <input type="radio" id="rating-3" name="rating" value="3"/>
            <label for="rating-3">3</label>

            <input type="radio" id="rating-4" name="rating" value="4"/>
            <label for="rating-4">4</label>

            <input type="radio" id="rating-5" name="rating" value="5" checked/>
            <label for="rating-5">5</label>
        </p>
        <p>
                <%--    <c:if test="${not empty nameError}">--%>
                <%--        <span class="error">${nameError}</span>--%>
                <%--        <br>--%>
                <%--    </c:if>--%>
            <label for="comment">Comment:</label>
            <input id="comment" name="comment" value="${not empty param.comment ? param.comment : ""}"/>
        </p>

        <button>
            Post Review
        </button>
    </form>


</tags:master>