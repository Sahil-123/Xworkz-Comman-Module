<%@ include file="./component/TopLinks.jsp" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="xworkz-logo.png">
    <%@ include file="component/HeaderLink.jsp" %>


      <title>Welcome</title>
  </head>

  <body>
    <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
      <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
          aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
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
            <!-- <li class="nav-item">
              <a class="nav-link" href="admin/signinPage">
                <i class="fa-solid fa-user-tie"></i>
                Admin
              </a>
            </li> -->

            <li class="nav-item">
              <div class="dropdown dropstart">
                <a class=" nav-link" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  Department Admin
                </a>

                <ul class="dropdown-menu  navbar-dark bg-dark" aria-labelledby="dropdownMenuLink">
                  <li class="nav-item">
                    <a class="nav-link" href="departmentAdmin/signinPage">
                      <i class="fa-solid fa-right-to-bracket"></i>
                      Sign in
                    </a>
                  </li>
                </ul>
              </div>
            </li>


            <li class="nav-item">
              <div class="dropdown dropstart">
                <a class=" nav-link" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  Admin
                </a>

                <ul class="dropdown-menu  navbar-dark bg-dark" aria-labelledby="dropdownMenuLink">
                  <li class="nav-item">
                    <a class="nav-link" href="admin/signinPage">
                      <i class="fa-solid fa-right-to-bracket"></i>
                      Sign in
                    </a>
                  </li>
                </ul>
              </div>
            </li>

            <li class="nav-item">
              <div class="dropdown dropstart">
                <a class=" nav-link" href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
                  aria-expanded="false">
                  User
                </a>

                <ul class="dropdown-menu  navbar-dark bg-dark" aria-labelledby="dropdownMenuLink">
                  <li class="nav-item">
                    <a class="nav-link" href="SignIn.jsp">
                      <i class="fa-solid fa-right-to-bracket"></i>
                      Sign In
                    </a>
                  </li>
                  <li class="nav-item">
                    <a class="nav-link" href="SignUp.jsp">
                      <i class="fa-solid fa-user"></i>
                      Sign Up
                    </a>
                  </li>


                </ul>
              </div>
            </li>


          </ul>
        </div>
      </div>
    </nav>


    <div class="mt-3 container">
      <div class="card text-left">
        <div class="card-body">
          <h5 class="card-title">Tech Stack:</h5>
          <p class="card-text">Java, Spring, JSP, Servlet</p>
        </div>

        <div class="card-body">
          <h5 class="card-title">Start Date: </h5>
          <p class="card-text">Wednesday, 12 June 2024</p>
        </div>

        <div class="card-body">
          <h5 class="card-title">Version Control System:</h5>
          <p class="card-text">Github: <a
              href="https://github.com/Sahil-123/Xworkz-Comman-Module/tree/main/Common-Modules" target="_blank"> Common
              Modules</a></p>
        </div>

        <div class="card-body">
          <h5 class="card-title">Description:</h5>
          <p class="card-text">------</p>
        </div>

      </div>
    </div>

  </body>

  </html>