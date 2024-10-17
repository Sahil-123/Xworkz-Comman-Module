<%@ include file="../component/TopLinks.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <%@ include file="../component/HeaderLink.jsp" %>
    <base href="http://localhost:8080/Common-Modules/">

    <link rel="stylesheet" href="resources/css/RegisterCommon.css">
        <%@ include file="../component/AdminHeaderLink.jsp" %>


    <title>Register Department</title>
</head>

<body>
    <%@ include file="../component/AdminHorizontalNavBar.jsp" %>
    
    <div class="main-content">

    <div class="mt-3 container d-flex justify-content-center ">
        <div class="card w-50 ps-4 pe-4 pt-2 pb-1 shadow p-3 mb-5 bg-body rounded">
            <form action="department/register" method="POST">

                <div class="card-body d-flex flex-column justify-content-center">

                    <span class="compulsary">
                        ${infoError}

                        <c:forEach items="${errors}" var="objectError">
                            "${objectError.defaultMessage}" <br>
                        </c:forEach>
                    </span>

                    <h2 class="card-title text-center mb-5">Register Department</h2>

                    <div class="mb-3">
                        <label for="departmentName" class="form-label">Department Name</label>
                        <input type="text" class="form-control" id="departmentName" name="departmentName" onblur="departmentNameValidation()">
                        <div id="departmentNameError" class="text-danger"></div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="area" class="form-label">Area</label>
                        <input type="text" class="form-control" id="area" name="area" onblur="areaValidation()">
                        <div id="areaError" class="text-danger"></div>
                    </div>
                    
                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <textarea class="form-control" id="address" name="address" onblur="addressValidation()"></textarea>
                        <div id="addressError" class="text-danger"></div>
                    </div>                    

                    <div class="mb-3 d-flex justify-content-between">
                        <input type="submit" class="btn btn-primary" id="submitButton" value="Register" disabled />
                        <input type="reset" class="btn btn-primary" id="resetButton" value="Reset" />
                    </div>
                </div>
            </form>
        </div>
    </div>
    </div>


    <c:if test="${successMessage.length() > 0}">
        <div class="modal fade" id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="d-flex flex-row-reverse p-3">
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="d-flex flex-column align-items-center p-5 text-white">
                        <img src="resources/images/check.png" class="imgHight"></img>
                    </div>
                    <div class="d-flex flex-column align-items-center p-5">
                        <h5>${successMessage}</h5>
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

    <script type="module" src="resources/js/department/RegisterDepartment.js"></script>
             <%@ include file="../component/Footer.jsp" %>
</body>

</html>
