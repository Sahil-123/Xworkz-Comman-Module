<nav class="navbar  navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
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

                <!-- <li class="nav-item text-capitalize nav-link d-flex align-items-center text-white">
                    <div class="nav-link nav-item ">${userData.fname} ${userData.lname}</div>

                    <div class="nav-item nav-link ms-1">
                        <div class="userProfile ">
                            <i class="fa-solid fa-user"></i>
                        </div>
                    </div>
                </li> -->

                <li class="nav-item">
                    <div class="dropdown dropstart">
                        <a class=" nav-link nav-item text-capitalize nav-link d-flex align-items-center text-white"
                            href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            <div class="nav-link nav-item ">${userData.fname} ${userData.lname}</div>

                            <div class="nav-item nav-link ms-1">
                                <div class="userProfile ">
                                    <c:if test="${imageData != null}">
                                        <img src="profileImages" alt="Profile picture" class="userImg"></img>
                                    </c:if>
                                    <c:if test="${imageData == null}">
                                        <i class="fa-solid fa-user defaultUserIcon"></i>
                                    </c:if>
                                </div>
                            </div>
                        </a>

                        <ul class="dropdown-menu" style="background: #343a40" aria-labelledby="dropdownMenuLink">
                            <li class="nav-item">
                                <a class="nav-link" href="user/UserEditProfilePage.jsp">
                                    Edit Profile
                                </a>
                                <%-- <hr> --%>
                                <a class="nav-link" href="index">
                                    Log out
                                </a>
                            </li>
                        </ul>
                    </div>
                </li>

            </ul>
        </div>
    </div>
</nav>


<!-- <p> ${imageData}</p> -->