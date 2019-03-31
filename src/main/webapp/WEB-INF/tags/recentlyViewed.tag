<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ tag trimDirectiveWhitespaces="true" %>

<c:if test="${not empty recentlyViewed}">
    <h3>Recently viewed</h3>
    <table style="border-collapse: separate; border: none">
        <c:forEach var="product" items="${recentlyViewed}">
            <td style="width: 100px; text-align: center">
                <img class="product-tile"
                     src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${product.imageUrl}">
                <br>
                <a href="/phoneshop-servlet-api/products/${product.id}"> ${product.description}</a>
                <br>
                <fmt:formatNumber value="${product.price}" type="currency"
                                  currencySymbol="${product.currency.symbol}"/>
            </td>
        </c:forEach>
    </table>
</c:if>