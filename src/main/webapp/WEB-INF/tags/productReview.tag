<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
<%@ attribute name="productId" required="true" %>
<%@ attribute name="productReviews" required="true" %>

<h3>Product Reviews: </h3>

<c:if test="${productReviews.isEmpty()}">
    There are no reviews yet! Be first!
</c:if>

<table>
    <c:forEach var="review" items="${productReviews}">
        <tr>
            <h5>${review.userName}</h5>
            <p> Rating: ${review.rating}</p>
            <p> Comment:
                <br>
                    ${review.comment}
            </p>
        </tr>
    </c:forEach>
</table>

<form method="post" action="${pageContext.servletContext.contextPath}/postProductReview/${productId}">
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


