<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="productReviews" type="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Product Reviews Moderation">
    <h3>Products Reviews Moderation: </h3>

    <c:if test="${productReviews.isEmpty()}">
        There are no reviews for moderation!
    </c:if>

    <form method="post">
        <table>
            <thead>
            <tr>
                <td>User name</td>
                <td>Rating</td>
                <td>Comment</td>
                <td>Id</td>
            </tr>
            </thead>
            <c:forEach var="review" items="${productReviews}">
                <tr>
                    <td>${review.userName}</td>
                    <td> ${review.rating}</td>
                    <td>${review.comment}</td>
                    <td>
                            ${review.id}
                    </td>
                    <td>
                        <button formmethod="post" formaction="${pageContext.servletContext.contextPath}/approveProductReview/${review.id}">
                            Approve
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </form>
</tags:master>
