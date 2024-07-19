<%@ include file="../component/TopLinks.jsp" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <%@ include file="../component/HeaderLink.jsp" %>
    <link rel="stylesheet" href="resources/css/ViewEmployeeDetails.css">
    <link rel="stylesheet" href="resources/css/Table.css">

    <%@ include file="../user/UserHeader.jsp" %>

    <title>Employee Details</title>

</head>

<body>

    <%@ include file="../department/DepartmentAdminNav.jsp" %>


    <div class="mt-3 container-fluid d-flex justify-content-center ">

        <div class="">
            <div class="table-responsive-sm p-4 ">
                <h3 class="text-center"> Employee Details </h3>
                <br>
                <form action="departmentAdmin/viewAllEmployees" method="POST">
                    <div class=" d-flex mb-2 p-2 border border-1 rounded-3 border-secondary justify-content-between">
                        <input type="text" class="form-control m-1" id="fname" name="fname" placeholder="First Name">
                        <input type="text" class="form-control m-1" id="lname" name="lname" placeholder="Last Name">
                        <input type="text" class="form-control m-1" id="email" name="email" placeholder="Email">
                        <input type="text" class="form-control m-1" id="mobile" name="mobile" placeholder="Mobile">
                        <%-- <input type="number" class="form-control m-1" id="departmentId" name="departmentId" placeholder="Department ID"> --%>
                        <input type="submit" class="btn btn-primary m-1" id="submitButton" value="Filter" />
                    </div>
                </form>

                <c:if test="${employeeList == null || employeeList.isEmpty() }">
                    <div class=" d-flex mb-2 p-2 justify-content-center customeWidth" style="width: 85vw;">
                                    No Records Found
                    </div>
                </c:if>

                        <c:if test="${employeeList != null && !employeeList.isEmpty() }">
                <div class="customeHeight">
                    <table class="table table-borderless">
                        <thead class="">
                            <tr>
                                <th scope="col"><strong>ID</strong></th>
                                <th scope="col"><strong>First Name</strong></th>
                                <th scope="col"><strong>Last Name</strong></th>
                                <th scope="col"><strong>Email</strong></th>
                                <th scope="col"><strong>Mobile</strong></th>
                                <th scope="col"><strong>Department ID</strong></th>
                                <th scope="col"><strong>Login Count</strong></th>
                                <th scope="col"><strong>Failed Attempts</strong></th>
                                <th scope="col"><strong>Failed Attempts DateTime</strong></th>
                                <th scope="col"><strong>Created By</strong></th>
                                <th scope="col"><strong>Created Date</strong></th>
                                <th scope="col"><strong>Updated By</strong></th>
                                <th scope="col"><strong>Updated Date</strong></th>
                                <th scope="col"><strong>Lock</strong></th>
                            </tr>
                        </thead>

                            <tbody>
                                <c:forEach items="${employeeList}" var="employee">
                                    <tr>
                                        <td>${employee.id}</td>
                                        <td>${employee.fname}</td>
                                        <td>${employee.lname}</td>
                                        <td>${employee.email}</td>
                                        <td>${employee.mobile}</td>
                                        <td>${employee.departmentId}</td>
                                        <td>${employee.loginCount}</td>
                                        <td>${employee.failedAttempts}</td>
                                        <td>${employee.failedAttemptsDateTime}</td>
                                        <td>${employee.createdBy}</td>
                                        <td>${employee.createdDate}</td>
                                        <td>${employee.updatedBy}</td>
                                        <td>${employee.updatedDate}</td>
                                        <td>${employee.lock}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </c:if>
                    </table>
                </div>
            </div>
        </div>

    </div>

    <c:if test="${successMessage.length() > 0}">
        <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel" aria-hidden="true">
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

    <%-- <script type="module" src="resources/js/ViewEmployeeDetails.js"></script> --%>
</body>

</html>
