<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">


            <title>Admin Dashboard</title>

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

                .userProfile {
                    padding: 10px;
                    display: flex;
                    justify-content: center;
                    align-item: center;
                    background: grey;
                    width: 38px;
                    border-radius: 60%;
                    cursor: pointer;
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
                        <li class="nav-item text-capitalize nav-link d-flex align-items-center text-white">
                            <div class="">${adminDto.firstName} ${adminDto.lastName}</div>
                        </li>

                        <li class="nav-item nav-link ">
                            <div class="userProfile">
                                <i class="fa-solid fa-user-tie"></i>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>

        <div class="mt-3 container">
            <div class="card text-left">
              Welcome
      
            </div>
          </div>

        

    </body>

    </html>