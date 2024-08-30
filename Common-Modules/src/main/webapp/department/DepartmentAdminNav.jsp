<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
    <div class="container-fluid">
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
            aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <a class="">
            <img src="https://www.x-workz.in/static/media/Logo.cf195593dc1b3f921369.png"
                style="width: 70px; height:30px; " />
        </a>

        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            </ul>
            <ul class="navbar-nav align-items-center">

            <a class=" nav-link nav-item text-capitalize nav-link d-flex align-items-center"
                    href="departmentAdmin/home">
                    Home
                </a>
                <pre>  </pre>
                <!-- <li class="nav-item text-capitalize nav-link d-flex align-items-center text-white">
                    <div class="nav-link nav-item ">${adminData.firstName} ${adminData.lastName}</div>
                    <div class="nav-item nav-link ms-1">
                        <div class="userProfile ">
                            <i class="fa-solid fa-user-tie mt-2"></i>
                        </div>
                    </div>
                </li> -->

                <!-- <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                        data-bs-toggle="dropdown" aria-expanded="false">
                        Department
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="#">View Users</a></li>
                    </ul>
                </li> -->

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                        data-bs-toggle="dropdown" aria-expanded="false">
                        Employee
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="employee/RegisterEmployee.jsp">Register Employee</a></li>
                        <li><a class="dropdown-item" href="departmentAdmin/viewAllEmployees/1/7">View Employee's</a></li>
                    </ul>
                </li>

                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                        data-bs-toggle="dropdown" aria-expanded="false">
                        Complaint
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li><a class="dropdown-item" href="departmentAdmin/viewDepartmentComplaints/1/7">View Complaints</a></li>
                    </ul>
                </li>

                <li class="nav-item">
                    <div class="dropdown dropstart">
                        
                        <a class=" nav-link nav-item text-capitalize nav-link d-flex align-items-center text-white"
                            href="#" role="button" id="dropdownMenuLink" data-bs-toggle="dropdown"
                            aria-expanded="false">
                            <div class="nav-link nav-item ">${departmentAdminData.fname} ${departmentAdminData.lname}</div>
                            <div class="nav-item nav-link ms-1">
                                <div class="userProfile ">
                                    <%-- <c:if test="${adminImageData != null}">
                                        <img src="profileImages" alt="Profile picture" class="userImg"></img>
                                    </c:if> --%>
                                    <%-- <c:if test="${adminImageData == null}"> --%>
                                        <i class="fa-solid fa-user-tie defaultUserIcon"></i>
                                    <%-- </c:if> --%>

                                </div>
                            </div>
                        </a>

                        <ul class="dropdown-menu" aria-labelledby="dropdownMenuLink">
                            <li class="nav-item">
                                <a class=" dropdown-item" href="#">
                                    Edit Profile
                                </a>
                            </li>
                            <li>
                                <hr class="dropdown-divider">
                            </li>
                            <li><a class="dropdown-item" href="#">Logout</a></li>
                        </ul>
                    </div>
                </li>

                <li class="me-3">
                    <a class="position-relative nav-link" data-bs-toggle="offcanvas" href="#offcanvasExample"
                        role="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight"
                        aria-controls="offcanvasRight">
                        <i class="fa-solid fa-bell fa-lg"></i>
                        <!-- <span class="badge" id="notificationCount">3</span> -->
                        <!-- <span class="position-absolute top-0 start-100 translate-middle badge border border-light rounded-circle bg-danger p-2"><span class="visually-hidden">unread messages</span></span> -->
                        <span
                            class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-secondary bg-danger" id="notificationCount">0
                            <span class="visually-hidden">unread messages</span></span>

                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<%-- ${departmentAdminData} --%>

<div class="offcanvas offcanvas-end bg-dark bg-gradient rounded-3 p-2" data-bs-scroll="true" data-bs-backdrop="false"
    tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
    <div class="offcanvas-header border-bottom nav-link">
        <h5 id="offcanvasRightLabel" class="text-white">Notification</h5>
        <button type="button" class="btn-close text-reset btn-close-white" data-bs-dismiss="offcanvas"
            aria-label="Close"></button>
    </div>

    <div class="offcanvas-body" id="notificationBody">

        <!-- <div class="row mb-2" id="2" onclick="showComplaint(this)">
            <div class="card w-100 hoverEffect">
                <div class="card-body ">
                    <div class="d-flex justify-content-between cardHead">
                        <span class="card-title">HR Department</span>
                        <span>hi</span>
                    </div>
                    <div class="cardBody">
                        <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                    </div>
                </div>
            </div>
        </div> -->

    </div>
</div>
