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
                            <div class="nav-link nav-item ">${adminDto.firstName} ${adminDto.lastName}</div>
                            <div class="userProfile nav-item nav-link ms-1">
                                 <i class="fa-solid fa-user-tie"></i>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        </nav>