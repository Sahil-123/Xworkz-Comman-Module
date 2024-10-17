<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">
            <%@ include file="../user/UserHeader.jsp" %>
            <link rel="stylesheet" href="resources/css/RegisterCommon.css">

            <title>Edit Profile</title>

    </head>

    <body>

        <%@ include file="EmployeeNav.jsp" %>

            <div class="mt-3 container d-flex justify-content-center">
                <div class="card w-50 ps-4 pe-4 pt-2 pb-1 shadow p-3 mb-5 bg-body rounded">
                    <form action="employee/editProfile" method="POST" enctype="multipart/form-data">

                        <div class="card-body d-flex flex-column justify-content-center">

                            <span class="compulsary">
                                ${infoError}

                                <c:forEach items="${errors}" var="objectError">
                                    "${objectError.defaultMessage}" <br>
                                </c:forEach>
                            </span>

                            <h2 class="card-title text-center mb-5">Edit Profile</h2>

                            <div class="mb-3">
                                <label for="fname" class="form-label">First Name</label>
                                <input type="text" class="form-control" id="fname" name="fname"
                                    onblur="nameValidation('fname')" value="${employeeData.fname}">
                                <div id="fnameError" class="text-danger"></div>
                            </div>

                            <div class="mb-3">
                                <label for="lname" class="form-label">Last Name</label>
                                <input type="text" class="form-control" id="lname" name="lname"
                                    onkeyup="nameValidation('lname')" value="${employeeData.lname}">
                                <div id="lnameError" class="text-danger"></div>

                            </div>

                            <div class="mb-3">
                                <label for="email" class="form-label">Email</label>
                                <input type="email" class="form-control" id="email" name="email"
                                    onblur="emailValidation()" value="${employeeData.email}" readonly>
                                <div id="emailError" class="text-danger"></div>
                            </div>

                            <div class="mb-3">
                                <label for="mobile" class="form-label">Mobile </label>
                                <input type="number" class="form-control" id="mobile" name="mobile"
                                    onkeyup="mobileValidation()" value="${employeeData.mobile}">
                                <div id="mobileError" class="text-danger"></div>
                            </div>

                            <div class="mb-3">
                                <label for="formFile" class="form-label">Upload Profile Picture</label>
                                <input class="form-control" accept="image/*" type="file" id="formFile"
                                    name="profilePicture">
                            </div>

                            <input type="submit" class="btn btn-primary" id="submitButton" value="Save" />

                        </div>
                </div>
                </form>
            </div>

            <c:if test="${successMessage.length() > 0}">
            <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="d-flex flex-row-reverse p-3">
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="d-flex flex-column align-items-center p-5 text-white">
                            <img src="resources/images/check.png" class="imgHight"></img>
                        </div>
                        <div class="d-flex flex-column align-items-center p-5">
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


             <%@ include file="../component/Footer.jsp" %>
    </body>

    </html>