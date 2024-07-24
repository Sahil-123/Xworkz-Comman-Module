<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">

            <link rel="stylesheet" href="resources/css/AdminDashboard.css">

            <title>Admin Dashboard</title>

            <style>

            </style>

    </head>

    <body>
        <%@ include file="component/AdminHorizontalNavBar.jsp" %>


            <div class=" container">

                Welecome ${adminDto.firstName} ${adminDto.lastName}

            </div>

    </body>

    </html>