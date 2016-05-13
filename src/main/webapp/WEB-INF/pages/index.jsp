<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<h1>Welcome to the weather report Web Application</h1>
<p>Please click on a City to view its weather report.</p>
<c:forEach items="${cities}" var="city">
    <p><a href="/WeatherWebApp/WeatherReport/${city}">${city}</a></p>
</c:forEach>
</body>
</html>