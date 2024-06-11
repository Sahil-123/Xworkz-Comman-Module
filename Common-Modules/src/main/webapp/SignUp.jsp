<%@ include file="./component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <link rel="icon" type="image/x-icon" href="xworkz-logo.png">
        <%@ include file="component/HeaderLink.jsp" %>
            <script src="js/Signup.js"></script>

            <title>Sign Up</title>
    </head>

    <body>
        <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
            <div class="container-fluid">
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
                aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
              </button>
                <a class="" href="index.jsp">
                    <img src="https://www.x-workz.in/static/media/Logo.cf195593dc1b3f921369.png" style="width: 70px; height:30px; " />
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
                            <a class="nav-link" href="SignIn.jsp">
                                <i class="fa-solid fa-right-to-bracket"></i>
                                Sign in
                            </a>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>


        <div class="mt-3 container d-flex justify-content-center">
            <div class="card w-50 ps-4 pe-4 pt-2 pb-1">
                <form>
                    <div class="card-body d-flex flex-column justify-content-center">
                        <h2 class="card-title text-center mb-5">Signup</h2>

                        <div class="mb-3">
                            <label for="fname" class="form-label">First Name</label>
                            <input type="text" class="form-control" id="fname" name="fname" onblur="nameValidation('fname')">
                            <div id="fnameError" class="text-danger"></div>
                        </div>

                        <div class="mb-3">
                            <label for="lname" class="form-label">Last Name</label>
                            <input type="text" class="form-control" id="lname" name="lname" onkeyup="nameValidation('lname')">
                            <div id="lnameError" class="text-danger"></div>

                        </div>

                        <div class="mb-3">
                            <label for="email" class="form-label">Email</label>
                            <input type="email" class="form-control" id="email" name="email" onblur="emailValidation()">
                            <div id="emailError" class="text-danger"></div>
                        </div>

                        <div class="mb-3">
                            <label for="mobile" class="form-label">Mobile </label>
                            <input type="number" class="form-control"
                                id="mobile"
                                name="mobile"
                                onkeyup="mobileValidation()"
                            >
                            <div id="mobileError" class="text-danger"></div>
                        </div>

                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="exampleCheck1">
                            <label class="form-check-label" for="exampleCheck1">Agreement</label>
                        </div>

                        <button type="submit" class="btn btn-primary" id="submitButton" disabled>Signup</button>

                        <div class="mb-1 mt-3 d-flex justify-content-center">
                            <p>
                                Already have an account? <a href="SignIn.jsp" style="text-decoration: none">
                                    <strong> Signin </strong> </a>
                            </p>
                        </div>

                    </div>
            </div>
            </form>
        </div>

    </body>

    </html>