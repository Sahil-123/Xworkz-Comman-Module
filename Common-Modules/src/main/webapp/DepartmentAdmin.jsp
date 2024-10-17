<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">
            <%@ include file="../department/DepartmentAdminHeaderLinks.jsp" %>


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
                        display: flex;
                        justify-content: center;
                        align-item: center;
                        background: grey;
                        width: 38px;
                        height: 38px;
                        border-radius: 60%;
                        cursor: pointer;
                        overflow: hidden;
                    }

                    .userImg {
                        width: 50px;
                        height: 50px;
                    }

                    .defaultUserIcon {
                        margin-top: 10px;
                    }

                    .customized-item {
                        width: 6vmax;
                        text-align: center;
                        cursor: pointer;

                    }

                    .cutomize-vertical-nav {
                        width: 8vmax;
                        height: 41vmax;

                    }

                    .data-page {
                        padding: 20px
                    }

                    .customeHeight {
                        height: 70vh;
                        width: 85vw;
                        overflow: scroll;
                        overflow-x: scroll;
                    }
                </style>

    </head>

    <body>
        <%@ include file="department/DepartmentAdminNav.jsp" %>


            <div class="mt-5 container">

                <div class="card text-left p-5 d-flex flex-row align-content-center justify-content-between p-shadow">

                    <div class="card-body text-center align-content-center ">
                        <h1 class="fw-bolder ">Welcome, <span class="text-capitalize">"${departmentAdminData.fname}
                                ${departmentAdminData.lname}"!</span></h1>
                        <hr>
                        <br />
                        <p class="fs-6 fw-normal">
                            It's great to have you here. Your dashboard is set up to help you manage your department efficiently. If you need any assistance, don't hesitate to reach out. Let's make today a productive one!
                        </p>
                    </div>

                    <div class="card-body d-flex flex-column justify-content-between p-5">

                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button"
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                Employee
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <li><a class="dropdown-item" href="employee/RegisterEmployee.jsp">Register Employee</a></li>
                                <li><a class="dropdown-item" href="departmentAdmin/viewAllEmployees/1/7">View Employee's</a></li>
                            </ul>
                        </div>
                        <br>

                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button"
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                Complaint
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">

                                <li class="nav-item text-white">
                                    <a class="dropdown-item" href="departmentAdmin/viewDepartmentComplaints/1/7">View Complaints</a>
                                </li>
                            </ul>
                        </div>

                    </div>

                </div>
            </div>
             <%@ include file="../component/Footer.jsp" %>
    </body>

    </html>