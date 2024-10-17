<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <%-- <link rel="stylesheet" href="resources/css/ViewUserComplaint.css"> --%>
                <link rel="stylesheet" href="resources/css/AdminViewUser.css">

                <%@ include file="../user/UserHeader.jsp" %>
                    <%@ include file="../component/AdminHeaderLink.jsp" %>



                        <title>Complaints</title>

    </head>

    <body onload="loadDepartment()">

        <%@ include file="../component/AdminHorizontalNavBar.jsp" %>

            <div class="mt-3 container-fluid d-flex justify-content-center main-content">

                <div class="">
                    <div class="table-responsive-sm p-4 ">
                        <h3 class="text-center"> Complaints Details </h3>
                        <br>
                        <c:if test="${complaintsList != null && !complaintsList.isEmpty()}">

                            <!-- <div id="filterData" class=" d-flex mb-2 p-2 justify-content-end customeWidth">
                                <form action="${downloadCSV}/${currentPage}/${pageSize}" method="post" target="_blank">

                                    <input type="hidden" value="${filter.complaintType}" name="complaintType" />
                                    <input type="hidden" value="${filter.country}" name="country" />
                                    <input type="hidden" value="${filter.state}" name="state" />
                                    <input type="hidden" value="${filter.city}" name="city" />
                                    <input type="hidden" value="${filter.status}" name="status" />

                                    <button type="submit" class="btn btn-default border border-primary" role="button">
                                        <i class="fa-solid fa-file-csv"></i> Download CSV
                                    </button>
                                </form>
                            </div> -->

                            <%@ include file="../common/CSVDownload.jsp" %>

                        </c:if>

                        <form action="complaints/viewAllComplaints/1/7" method="POST">
                            <div
                                class=" d-flex mb-2 p-2 border border-1 rounded-3 border-secondary justify-content-between">
                                <select class="form-control m-1" id="complaintType" name="complaintType">
                                    <option value="">Complaint Type</option>
                                    <option value="Noise">Noise</option>
                                    <option value="Pollution">Pollution</option>
                                    <option value="Drainage Problem">Water Leakage</option>
                                    <option value="Billing">Billing</option>
                                    <option value="Service">Service</option>
                                    <option value="Delivery">Delivery</option>
                                    <option value="Product">Product</option>
                                    <option value="Support">Support</option>
                                </select>

                                <select class="form-control m-1" id="country" name="country" onchange="getStates()">
                                    <option value="">Country</option>
                                </select>

                                <select class="form-control m-1" id="state" name="state" onchange="getCities()">
                                    <option value="">State</option>
                                </select>

                                <select class="form-control m-1" id="cities" name="city">
                                    <option value="">City</option>
                                </select>

                                <select class="form-control m-1" id="status" name="status">
                                   <option value="">Status</option>
                                   <option value="RECEIVED">Received</option>
                                   <option value="OPEN">Open</option>
                                   <option value="IN_REVIEW">In Review</option>
                                   <option value="ASSIGNED_TO_DEPARTMENT">Assigned to Department</option>
                                   <option value="ASSIGNED_TO_EMPLOYEE">Assigned to Employee</option>
                                   <option value="IN_PROGRESS">In Progress</option>
                                   <option value="PENDING">Pending</option>
                                   <option value="RESOLVED">Resolved</option>
                                   <option value="REOPENED">Reopened</option>
                                   <option value="NOT_RESOLVED">Not Resolved</option>
                                   <option value="REJECTED">Rejected</option>

                                </select>

                                <input type="submit" class="btn btn-primary m-1" id="submitButton" value="Filter" />
                            </div>
                        </form>



                        <c:if test="${complaintsList == null || complaintsList.isEmpty() }">
                            <div class=" d-flex mb-2 p-2 justify-content-center customeWidth" style="width: 85vw;">
                                No Records Found
                            </div>
                        </c:if>


                        <div class="customeHeight">
                            <c:if test="${complaintsList != null && !complaintsList.isEmpty() }">
                                <table class="table ">
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col"><strong>ID</strong></th>
                                            <th scope="col"><strong>Complaint Type</strong></th>
                                            <th scope="col"><strong>Country</strong></th>
                                            <th scope="col"><strong>State</strong></th>
                                            <th scope="col"><strong>City</strong></th>
                                            <th scope="col"><strong>Address</strong></th>
                                            <th scope="col"><strong>Employee ID</strong></th>
                                            <th scope="col"><strong>Description</strong></th>
                                            <th scope="col"><strong>Created By</strong></th>
                                            <th scope="col"><strong>Created Date</strong></th>
                                            <th scope="col"><strong>User ID</strong></th>
                                            <th scope="col"><strong>Department</strong></th>
                                            <th scope="col"><strong>Status</strong></th>
                                            <th scope="col"><strong></strong></th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${complaintsList}" var="complaint">
                                            <tr>
                                                <td>${complaint.id}</td>
                                                <td>${complaint.complaintType}</td>
                                                <td>${complaint.country}</td>
                                                <td>${complaint.state}</td>
                                                <td>${complaint.city}</td>
                                                <td>${complaint.address}</td>
                                                <td>${complaint.empId}</td>
                                                <td>${complaint.description}</td>
                                                <td>${complaint.createdBy}</td>
                                                <td>${complaint.createdDate}</td>
                                                <td>${complaint.userId}</td>
                                                <form action="complaints/adminUpdateComplaint" method="POST">
                                                    <td>
                                                        <select class="form-control m-1" id="${complaint.deptId}"
                                                            name="department">
                                                            <option value="">Department</option>
                                                        </select>
                                                    </td>
                                                    <td>
                                                        <select class="form-control m-1" id="status" name="status">
                                                            <option value="">Status</option>
                                                           <option value="IN_PROGRESS" <c:if test="${'IN_PROGRESS'.equalsIgnoreCase(complaint.status)}">selected</c:if>>In Progress</option>
                                                           <option value="ASSIGNED_TO_EMPLOYEE" <c:if test="${'ASSIGNED_TO_EMPLOYEE'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Assigned to Employee</option>
                                                           <option value="PENDING" <c:if test="${'PENDING'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Pending</option>
                                                           <option value="REOPENED" <c:if test="${'REOPENED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Reopened</option>
                                                           <option value="NOT_RESOLVED" <c:if test="${'NOT_RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Not Resolved</option>
                                                           <option value="REJECTED" <c:if test="${'REJECTED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Rejected</option>

                                                        </select>
                                                        
                            </td>
                            <td class="">
                                <input type="number" value="${complaint.id}" name="complaintId" hidden />
                                <button type="submit" class="btn btn btn-success btn-sm m-1">Submit</button>

                            </td>
                            </form>

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

            <script type="module" src="resources/js/RaiseUserComplaint.js"></script>
            <script type="module" src="resources/js/viewAllComplaintsForAdmin.js"></script>

             <%@ include file="../component/Footer.jsp" %>
    </body>

    </html>