<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <link rel="stylesheet" href="resources/css/Table.css">
            <%-- <link rel="stylesheet" href="resources/css/ViewUserComplaint.css"> --%>
                <link rel="stylesheet" href="resources/css/UserComplaintStatus.css">
                <%@ include file="../user/UserHeader.jsp" %>


                    <title>User Complaints</title>

    </head>

    <body>
        <%@ include file="../user/UserNav.jsp" %>


            <div class="mt-3 container-fluid d-flex justify-content-center ">


                <div class="">
                    <div class="table-responsive-sm p-4 ">
                        <h3 class="text-center"> Complaints Details </h3>
                        <br>
                        <form action="complaints/viewUserComplaints/1/7" method="POST">
                            <div
                                class=" d-flex mb-2 p-2 border border-1 rounded-3 border-secondary justify-content-between">
                                <select class="form-control m-1" id="complaintType" name="complaintType">
                                    <option value="">Complaint Type</option>
                                    <option value="Noise">Noise</option>
                                    <option value="Pollution">Pollution</option>
                                    <option value="Drainage Problem">Water Leakage</option>
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

                        <!-- <c:choose>
                            <c:when test="${complaintsList == null || complaintsList.isEmpty()}">
                                <tr>
                                    <h3>No records found</h3>
                                </tr>
                            </c:when>
                            <c:otherwise> -->

                        <c:if test="${complaintsList != null && !complaintsList.isEmpty() }">
                            <div class="customeHeight" style="width: 85vw;">
                                <table class="table table-borderless">
                                    <thead class="">
                                        <tr>
                                            <th scope="col"><strong>Complaint Type</strong></th>
                                            <th scope="col"><strong>Country</strong></th>
                                            <th scope="col"><strong>State</strong></th>
                                            <th scope="col"><strong>City</strong></th>
                                            <th scope="col"><strong>Address</strong></th>
                                            <th scope="col"><strong>Description</strong></th>
                                            <th scope="col"><strong>Created Date</strong></th>
                                            <th scope="col"><strong>Status</strong></th>
                                            <th scope="col"><strong></strong></th>
                                            <th scope="col"><strong></strong></th>


                                        </tr>
                                    </thead>

                                    <tbody>
                                        <c:forEach items="${complaintsList}" var="complaint">
                                            <tr>
                                                <td>${complaint.complaintType}</td>
                                                <td>${complaint.country}</td>
                                                <td>${complaint.state}</td>
                                                <td>${complaint.city}</td>
                                                <td>${complaint.address}</td>
                                                <td>${complaint.description}</td>
                                                <td>${complaint.createdDate}</td>
                                                <td class="status-text">${complaint.status.getDisplayValue()}</td>
                                                <td><a href="complaints/updateComplaintPage?id=${complaint.id}" class=" btn btn-success btn-sm
                                                        
                                                        <c:if test="
                                                        ${complaint.status.getDisplayValue().equalsIgnoreCase('resolved')
                                                        ||
                                                        complaint.status.getDisplayValue().equalsIgnoreCase('not_resolved')
                                                        }">
                                                        disabled
                        </c:if>

                        ">
                        <%-- <i class="fa-solid fa-pen-to-square">
                            </i> --%>
                            Edit
                            </a>
                            </td>
                            <td>
                                <button class="btn btn-success btn-sm"
                                    onclick="getComplaintStatus(${complaint.id}, complaintStatusModel, spinner)">
                                    Status
                                </button>
                            </td>

                            </tr>
                            </c:forEach>

                            </tbody>
                            </c:if>
                            </table>

                            <hr>
                            <%@ include file="../common/Pagination.jsp" %>
                    </div>
                    <!-- </c:otherwise>
                        </c:choose> -->
                </div>
            </div>

            </div>


            <div class="modal fade modal-dialog-scrollable" id="complaintStatusModel" data-bs-backdrop="static"
                data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable modal-xl">
                    <div class="modal-content">

                        <div class="modal-header">
                            <h5 class="modal-title text-center w-100" id="staticBackdropLabel">Complaint Status</h5>
                        </div>

                        <div class="modal-body">

                            <div class="timeline-wrapper" id="complaintStatus">
                                <div class="node">
                                    <h5 style="color: rgb(0, 123, 255);">Received</h5>
                                    <p>Your complaint has been submitted and is currently under review by the
                                        administration.</p>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer  d-flex justify-content-end">
                            <button type="button" class="btn btn-secondary"
                                onclick="dismisComplaintStatusModel(complaintStatusModel)">Close</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="spinner" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                    <div class=" modal-body p-5 bg-transparent text-white d-flex justify-content-center ">
                        <div class="d-flex justify-content-center">
                            <div class="spinner-border text-light"></div>
                            <h3 class="ms-3">Loading</h3>
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
            <script type="module" src="resources/js/ViewUserComplaint.js"></script>



    </body>

    </html>