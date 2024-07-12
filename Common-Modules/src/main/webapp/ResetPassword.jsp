<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">


            <title>Reset Password</title>

            <style>
                label {
                    font-weight: bold;
                }

                .error {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    margin-top: 8px;
                    color: red;
                }

                .compulsary {
                    color: red;
                }

                #successIcon {
                    hight: 100vmin;
                    width: 10vmin;
                    background: red;

                }
            </style>

    </head>

    <body>
        <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false"
                    aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <a class="" href="index.jsp">
                    <img src="https://www.x-workz.in/static/media/Logo.cf195593dc1b3f921369.png"
                        style="width: 70px; height:30px; " />
                </a>

                <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    </ul>
                    <ul class="navbar-nav">

                        <li class="nav-item">
                            <a class="nav-link" aria-current="page" href="index.jsp">
                                <i class="fa-solid fa-house"></i>
                                Home
                            </a>
                        </li>


                        <li class="nav-item">

                        <c:choose>
                            <c:when test="${userAccess.equals('departmentAdmin')}">
                                <%-- <form action="departmentAdmin/signin" method="POST"> --%>
                            </c:when>

                            <c:when test="${userAccess.equals('admin')}">
                                <a class="nav-link" href="admin/signinPage">
                            </c:when>

                            <c:when test="${userAccess.equals('employee')}">
                                <a class="nav-link" href="employee/signinPage">
                            </c:when>

                            <c:otherwise>
                                <a class="nav-link" href="SignIn.jsp">
                            </c:otherwise>
                        </c:choose>

                            <%-- <c:if test="${admin}">
                                <a class="nav-link" href="admin/signinPage">
                            </c:if>
                            <c:if test="${!admin}">
                                <a class="nav-link" href="SignIn.jsp">
                            </c:if> --%>
                                <i class="fa-solid fa-right-to-bracket"></i>
                                Sign in
                            </a>
                        </li>


                    </ul>
                </div>
            </div>
        </nav>

        ${userAccess}

        <div class="mt-3 container d-flex justify-content-center">
            <div class="card w-50 ps-4 pe-4 pt-2 pb-1 shadow p-3 mb-5 bg-body rounded">

                <c:choose>

                    <c:when test="${userAccess.equals('departmentAdmin')}">
                        <%-- <form action="departmentAdmin/signin" method="POST"> --%>
                    </c:when>

                    <c:when test="${userAccess.equals('admin')}">
                        <form action="admin/resetPassword" method="POST">
                    </c:when>

                    <c:when test="${userAccess.equals('employee')}">
                        <form action="employee/resetPassword" method="POST">
                    </c:when>

                    <c:otherwise>
                        <form action="resetPassword" method="POST">
                    </c:otherwise>
                </c:choose>

                <%-- <c:if test="${admin}">
                    <form action="admin/resetPassword" method="POST">
                </c:if>
                <c:if test="${!admin}">
                    <form action="resetPassword" method="POST">
                </c:if> --%>

                    <div class="card-body d-flex flex-column justify-content-center">

                        <span class="compulsary">
                            ${infoError}

                            <c:forEach items="${errors}" var="objectError">
                                "${objectError.defaultMessage}" <br>
                            </c:forEach>
                        </span>

                        <h2 class="card-title text-center mb-5">Reset Password</h2>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" onblur="emailValidation()"
                                value="${dto.email}">
                            <div id="emailError" class="text-danger"></div>
                        </div>

                        <div class="mb-3">
                            <label for="password" class="form-label">Old Password</label>
                            <input type="password" class="form-control" id="password" name="password"
                                value="${dto.password}">
                            <div id="passwordError" class="text-danger"></div>
                        </div>

                        <div class="mb-3">
                            <label for="newPassword" class="form-label">New Password</label>
                            <input type="password" class="form-control" id="newPassword" name="newPassword">
                            <div id="newPasswordError" class="text-danger"></div>
                        </div>
                        <div class="mb-3">
                            <label for="confirmPassword" class="form-label">Confirm Password</label>
                            <input type="password" class="form-control" id="confirmPassword" name="confirmPassword">
                            <div id="confirmPasswordError" class="text-danger"></div>
                        </div>

                        <input type="submit" class="btn btn-primary mt-3 mb-3" id="submitButton" value="Reset" />

                    </div>
            </div>
            </form>

        </div>

    </body>

    </html>