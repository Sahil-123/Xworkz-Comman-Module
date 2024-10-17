<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">

            <link rel="stylesheet" href="resources/css/AdminDashboard.css">
            <link rel="stylesheet" href="resources/css/common.css">

            <%@ include file="../component/AdminHeaderLink.jsp" %>


                <title>Admin Dashboard</title>

                <style>

                </style>

    </head>

    <body>
        <%@ include file="component/AdminHorizontalNavBar.jsp" %>


            <div class="mt-5 container">

                <div class="card text-left p-5 d-flex flex-row align-content-center justify-content-between p-shadow">

                    <div class="card-body text-center align-content-center " >
                        <h1 class="fw-bolder text-capitalize">Welcome, "${adminData.firstName} ${adminData.lastName}"!</h1>
                        <hr>
                        <br />
                        <p class="fs-6 fw-normal">
                            We're glad to have you here. Your dashboard is ready for you to manage everything smoothly. If you need any assistance, feel free to reach out. Let's make today a productive one!
                        </p>
                    </div>

                    <div class="card-body d-flex flex-column justify-content-between p-5" >
         
                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                                User
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <li><a class="dropdown-item " href="admin/users/1/7">View Users</a></li>
                            </ul>
                        </div>
                        <br>

                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                                Complaint
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <li><a class="dropdown-item" href="complaints/viewAllComplaints/1/7">View Complaint</a></li>
                            </ul>
                        </div>
                        <br>

                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button" id="dropdownMenuButton1"
                                data-bs-toggle="dropdown" aria-expanded="false">
                                Department
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                <li><a class="dropdown-item" href="admin/RegisterDepartment.jsp">Add Department</a></li>
                                <li><a class="dropdown-item" href="admin/RegisterDepartmentAdmin.jsp">Add Department Admin</a>
                                </li>
                                <li><a class="dropdown-item" href="admin/departments/1/7">View Department</a></li>
                                <li><a class="dropdown-item" href="departmentAdmin/departmentAdmins/1/7">View Department
                                        Admin</a></li>

                            </ul>
                        </div>
                                                
                    </div>

                </div>
            </div>

             <%@ include file="../component/Footer.jsp" %>
    </body>

    </html>