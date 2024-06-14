<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">


            <title>Signin</title>

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

                .imgHight {
                    height: 40vmin;
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
                            <a class="nav-link" href="SignUp.jsp">
                                <i class="fa-solid fa-user"></i>
                                Sign up
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>



        <div class="mt-3 container d-flex justify-content-center">
            <div class="card w-50 ps-4 pe-4 pt-2 pb-1 shadow p-3 mb-5 bg-body rounded">

                <c:if test="${admin}">
                    <form action="admin/signin" method="POST">
                </c:if>
                <c:if test="${!admin}">
                    <form action="signin" method="POST">
                </c:if>

                <div class="card-body d-flex flex-column justify-content-center">

                    <span class="compulsary">
                        ${infoError}

                        <c:forEach items="${errors}" var="objectError">
                            "${objectError.defaultMessage}" <br>
                        </c:forEach>
                    </span>

                    <h2 class="card-title text-center mb-5">Signin</h2>

                    <div class="mb-3">
                        <label for="email" class="form-label">Email</label>
                        <input type="email" class="form-control" id="email" name="email" onblur="emailValidation()"
                            value="${dto.email}">
                        <div id="emailError" class="text-danger"></div>
                    </div>

                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="password"
                            value="${dto.password}">
                        <div id="passwordError" class="text-danger"></div>
                    </div>

                    <div class="mb-1 mt-3 d-flex justify-content-center">
                        <p>
                            <a href="" style="text-decoration: none">
                                <strong> Forgot password? </strong> </a>
                        </p>
                    </div>

                    <input type="submit" class="btn btn-primary mb-3" id="submitButton" value="Signin" />

                    <c:if test="${!admin}">
                        <div class="mb-1 mt-3 d-flex justify-content-center">
                            <p>
                                Don`t have an account? <a href="SignUp.jsp" style="text-decoration: none">
                                    <strong> Signup </strong> </a>
                            </p>
                        </div>
                    </c:if>

                </div>
            </div>
            </form>

            <c:if test="${successMessage.length() > 0}">
                <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="d-flex flex-row-reverse p-3">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
                                <img src="resources/images/check.png" class="imgHight"></img>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5">
                                <h5> ${successMessage}</h5>
                            </div>
                            <div class="d-flex flex-row-reverse p-3">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            </div>
                        </div>
                    </div>
                </div>

                <script>
                    document.addEventListener("DOMContentLoaded", function () {
                        var exampleModal = new bootstrap.Modal(document.getElementById('exampleModal'));
                        exampleModal.show();
                    });
                </script>
            </c:if>

        </div>

    </body>

    </html>