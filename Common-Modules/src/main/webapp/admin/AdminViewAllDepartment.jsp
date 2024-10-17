<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <%-- <link rel="stylesheet" href="resources/css/AdminViewUser.css"> --%>
                <link rel="stylesheet" href="resources/css/Table.css">
                <%@ include file="../user/UserHeader.jsp" %>
        <%@ include file="../component/AdminHeaderLink.jsp" %>


                    <title>Departments</title>
    </head>

    <body class="fw-normal">
        <%@ include file="../component/AdminHorizontalNavBar.jsp" %>

            <div class="mt-1 container-fluid d-flex justify-content-center">
                <div class="">
                    <div class="table-responsive-xxl p-4">
                        <h5 class="text-center"> Department Details </h5>
                        <br>

                        <c:if test="${departmentslist != null && !departmentslist.isEmpty() }">
                            <%@ include file="../common/CSVDownload.jsp" %>
                        </c:if>

                        <c:if test="${departmentslist == null || departmentslist.isEmpty() }">
                            <div class=" d-flex mb-2 p-2 justify-content-center customeWidth" style="width: 85vw;">
                                No Records Found
                            </div>
                        </c:if>

                        <div class="customeHeight">
                            <c:if test="${departmentslist != null && !departmentslist.isEmpty() }">
                                <table class="table table-borderless">
                                    <thead class="">
                                        <tr>
                                            <th scope="col"><strong>Department ID</strong></th>
                                            <th scope="col"><strong>Department Name</strong></th>
                                            <th scope="col"><strong>Area</strong></th>
                                            <th scope="col"><strong>Address</strong></th>
                                            <th scope="col"><strong>Number of Employees</strong></th>
                                            <th scope="col"><strong>Created By</strong></th>
                                            <th scope="col"><strong>Created Date</strong></th>
                                            <th scope="col"><strong>Updated By</strong></th>
                                            <th scope="col"><strong>Updated Date</strong></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${departmentslist}" var="department">
                                            <tr>
                                                <td>${department.id}</td>
                                                <td>${department.departmentName}</td>
                                                <td>${department.area}</td>
                                                <td>${department.address}</td>
                                                <td>${department.numberOfEmployees}</td>
                                                <td>${department.createdBy}</td>
                                                <td>${department.createdDate}</td>
                                                <td>${department.updatedBy}</td>
                                                <td>${department.updatedDate}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <hr>
                                <%@ include file="../common/Pagination.jsp" %>
                            </c:if>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${successMessage.length() > 0}">
                <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content">
                            <div class="d-flex flex-row-reverse p-3">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
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