<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@page isELIgnored = "false" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h2>Welcome, ${name}!</h2>


<c:if test="${not empty msg}">
    <p style="color:green;">${msg}</p>
</c:if>

<c:if test="${empty userRating}">
    <form action="rate" method="post">
        <input type="hidden" name="name" value="${name}">
        <div class="star-rating">
            <input type="radio" id="star5" name="rating" value="5"><label for="star5">&#9733;</label>
            <input type="radio" id="star4" name="rating" value="4"><label for="star4">&#9733;</label>
            <input type="radio" id="star3" name="rating" value="3"><label for="star3">&#9733;</label>
            <input type="radio" id="star2" name="rating" value="2"><label for="star2">&#9733;</label>
            <input type="radio" id="star1" name="rating" value="1"><label for="star1">&#9733;</label>
        </div>
        <button type="submit">Submit Rating</button>
    </form>
</c:if>

<c:if test="${not empty userRating}">
    <p>Your Rating:</p>
    <c:forEach begin="1" end="${userRating}" var="i">
        <span style="color: gold; font-size:30px;">&#9733;</span>
    </c:forEach>
    <c:forEach begin="${userRating+1}" end="5" var="i">
        <span style="color: gray; font-size:30px;">&#9733;</span>
    </c:forEach>
    <p>You cannot change or delete your rating.</p>
</c:if>

<style>
    .star-rating {
        direction: rtl;
        display: inline-block;
        font-size: 30px;
    }
    .star-rating input { display: none; }
    .star-rating label {
        color: gray;
        cursor: pointer;
    }
    .star-rating input:checked ~ label,
    .star-rating label:hover,
    .star-rating label:hover ~ label {
        color: gold;
    }
</style>
</body>
</html>