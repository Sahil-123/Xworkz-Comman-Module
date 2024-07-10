<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <link rel="stylesheet" href="resources/css/ViewUserComplaint.css">
            <%@ include file="../user/UserHeader.jsp" %>


                <title>User Complaints</title>

    </head>

    <body >
        <%@ include file="../user/UserNav.jsp" %>


            <div class="mt-3 container d-flex justify-content-center ">


                <div class="">
                    <div class="table-responsive-sm p-4 ">
                        <h3 class="text-center"> Complaints Details </h3>
                        <br>
                        <form action="complaints/viewUserComplaints" method="POST">
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
                                    <option value="Open">Open</option>
                                    <option value="Pending">Pending</option>
                                    <option value="InProgess">InProgess</option>
                                </select>

                                <input type="submit" class="btn btn-primary m-1" id="submitButton" value="Filter" />
                            </div>
                        </form>

                        <c:if test="${complaintsList == null || complaintsList.isEmpty() }">
                            No Records Found
                        </c:if>

                        <!-- <c:choose>
                            <c:when test="${complaintsList == null || complaintsList.isEmpty()}">
                                <tr>
                                    <h3>No records found</h3>
                                </tr>
                            </c:when>
                            <c:otherwise> -->

                        <div class="customeHeight">
                            <table class="table ">
                                <thead class="table-dark">
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

                                <c:if test="${complaintsList != null && !complaintsList.isEmpty() }">
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
                                                <td>${complaint.status}</td>
                                                <td><a href="complaints/updateComplaintPage?id=${complaint.id}" class="nav-link nav-item"><i
                                                            class="fa-solid fa-pen-to-square"></a></i></td>
                                                <td><a href="#" class="nav-link nav-item"><i
                                                            class="fa-solid fa-circle-info"></a></i></td>

                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </c:if>
                            </table>
                        </div>
                        <!-- </c:otherwise>
                        </c:choose> -->
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

    </body>

    </html>