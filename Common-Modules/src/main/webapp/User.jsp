<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">
            <%@ include file="user/UserHeader.jsp" %>

                <title>User</title>


                <style>

                </style>

    </head>

    <body>

        <%@ include file="user/UserNav.jsp" %>


            <div class="mt-5 container">

                <div class="card text-left p-5 d-flex flex-row align-content-center justify-content-between p-shadow">

                    <div class="card-body text-center align-content-center ">
                        <h1 class="fw-bolder ">Welcome, <span class="text-capitalize">"${userData.fname}
                                ${userData.lname}"!</span></h1>
                        <hr>
                        <br />
                        <p class="fs-5 fw-normal">
                            It's great to have you here. Your dashboard is ready and waiting. If you need any help, don't hesitate to ask. Let's make the most of today!.
                        </p>
                    </div>

                    <div class="card-body d-flex flex-column justify-content-between p-5">


                        <div class="dropdown">
                            <button class="btn btn-secondary dropdown-toggle ps-4 pe-4" type="button"
                                id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                Complaint
                            </button>
                            <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">

                                <li class="nav-item text-white">
                                    <a class="dropdown-item" href="user/RaiseUserComplaint.jsp">
                                        Raise Complaint
                                    </a>
                                </li>
                                <li>
                                    <a class="dropdown-item" href="complaints/viewUserComplaints/1/7">
                                        View Compliant's
                                    </a>
                                </li>
                            </ul>
                        </div>
                        


                    </div>

                </div>
            </div>





            <c:if test="${successMessage.length() > 0}">
                <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content" style="background-color: #4BB543">
                            <div class="d-flex flex-row-reverse p-3" style="background-color: #4BB543">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
                                <img src="check.png"></img>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
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

            <!-- <img src="profileImages" alt="Profile picture"></img> -->

    </body>

    </html>