<%@ page isErrorPage="true" %>
<%@ include file="../component/TopLinks.jsp" %>

<html>
<head>
    <link rel="icon" type="image/x-icon" href="xworkz-logo.png">
    <%@ include file="../component/HeaderLink.jsp" %>
    <title>Error Page</title>
</head>
<body>

    <p>We're sorry, but something went wrong. Please try again later.</p>
    <p>${errorMessage}</p>

</body>
</html>
