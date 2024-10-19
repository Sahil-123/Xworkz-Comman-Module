<%@ include file="./component/TopLinks.jsp" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="xworkz-logo.png">
    <link rel="stylesheet" href="resources/css/index.css">
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

        <div class="collapse navbar-collapse navigation-buttons" id="navbarTogglerDemo03">
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
                  Department
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
                  Employee
                </a>

                <ul class="dropdown-menu  navbar-dark bg-dark" aria-labelledby="dropdownMenuLink">
                  <li class="nav-item">
                    <a class="nav-link" href="employee/signinPage">
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


    <div class="mt-3">
      <div class="vh90">
        <div class="container p-3 welcome">
          <!-- <h1>Welcome</h1> -->
          <div class="content">
            <h1>Users Issue Management System</h1>
            <p>
              Users Issue Management System simplifies complaint management by acting as a bridge between users, admins,
              and departments, ensuring that issues are quickly addressed, tracked, and resolved efficiently. It's a
              one-stop solution for streamlining communication and improving response times, making life easier for
              everyone involved.
            </p>
          </div>
          <div class="img-container">
            <img src="resources/images/landing-img1.png" class="img" />
          </div>
        </div>
      </div>

      <div class="vh100 technology-outer">
        <div class="container p-3 technology">
          <h1>Key Technologies </h1>
          <div class="tech d-flex flex-wrap">

            <div class="tech-card">HTML</div>
            <div class="tech-card">CSS</div>
            <div class="tech-card">Bootstrap</div>
            <div class="tech-card">JavaScript</div>
            <div class="tech-card">Java</div>
            <div class="tech-card">JPA</div>
            <div class="tech-card">Spring</div>
            <div class="tech-card">JSP</div>
            <div class="tech-card">Spring Transaction</div>
            <div class="tech-card">Spring AOP</div>
            <div class="tech-card">Spring ORM (Hibernate)</div>
            <div class="tech-card">Spring MVC</div>
            <div class="tech-card">Hikari</div>

            <div class="tech-card">MySQL (DataBase)</div>
          </div>
        </div>
      </div>

      <div class="vh90">
        <div class="container p-3 about mt-5">
          <div class="container p-3 about-inner">
            <!-- <h1>Welcome</h1> -->
            <div class="content">
              <!-- <h1>Users Issue Management System</h1> -->
              <h1>About Me</h1>
              <p>
                <span class="capito">H</span>i, I'm Sahil Naikwadi, and I recently graduated with a degree in Computer Science Engineering. I'm
                passionate about technology and love developing software that solves real-world problems. Throughout my
                journey, I've worked on several projects that have helped me build a solid understanding of key
                technologies like Spring MVC, JPA, MySQL, JavaScript, and Bootstrap.

                I enjoy tackling challenges head-on and am always looking for ways to improve my skills. Whether it's
                learning about new tools or working on exciting projects, I'm eager to keep growing and applying my
                knowledge to make a meaningful impact.
              </p>
            </div>
            <div class="img-container">
              <img src="resources/images/about-us-removebg.png" class="img" />
            </div>
          </div>

        </div>
      </div>

    </div>

    <%@ include file="../component/Footer.jsp" %>

  </body>

  </html>