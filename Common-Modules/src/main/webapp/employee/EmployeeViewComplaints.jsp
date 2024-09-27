<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <%-- <link rel="stylesheet" href="resources/css/ViewUserComplaint.css"> --%>
                <link rel="stylesheet" href="resources/css/ViewEmployeeComplaint.css">
                <link rel="stylesheet" href="resources/css/Table.css">
                <%@ include file="../user/UserHeader.jsp" %>
                    <%@ include file="../employee/EmployeeHeaderLinks.jsp" %>

                        <title>Employee Assigned Complaints</title>

    </head>

    <body>

        <%@ include file="./EmployeeNav.jsp" %>

            <div class="mt-3 container-fluid d-flex justify-content-center ">


                <div class="">

                    <div class="table-responsive-sm p-4 ">
                        <div id="errorAlert">

                        </div>
                        <h3 class="text-center"> Complaints Details </h3>
                        <br>


                        <c:choose>

                            <c:when test="${status.equalsIgnoreCase('Resolved')}">

                            </c:when>

                            <c:otherwise>
                                <c:if test="${complaintsList != null || !complaintsList.isEmpty() }">
                                    <%@ include file="../common/CSVDownload.jsp" %>

                                </c:if>

                                <form action="employee/viewEmployeeComplaints/1/7" method="POST">
                                    <div class=" d-flex mb-2 p-2 border border-1 rounded-3 border-secondary justify-content-between customeWidth"
                                        style="width: 85vw;">

                                        <select class="form-control m-1" id="status" name="status" class="">
                                            <option value="">Status</option>
                                            <option value="IN_PROGRESS">In Progress</option>
                                            <option value="PENDING">Pending</option>
                                            <option value="NOT_RESOLVED">Not Resolved</option>

                                        </select>

                                        <input type="submit" class="btn btn-primary m-1" id="submitButton"
                                            value="Filter" />
                                    </div>
                                </form>
                            </c:otherwise>
                        </c:choose>
                        <!-- <c:if test="${complaintsList == null || complaintsList.isEmpty() }">
                        </c:if> -->

                        <c:choose>

                            <c:when test="${complaintsList == null || complaintsList.isEmpty() }">
                                <div class=" d-flex mb-2 p-2 justify-content-center customeWidth" style="width: 85vw;">
                                    No Records Found
                                </div>
                            </c:when>

                            <c:otherwise>


                                <div class="customeHeight " style="width: 85vw;">
                                    <table class="table table-borderless ">
                                        <thead class="">
                                            <tr>
                                                <th scope="col"><strong>Country</strong></th>
                                                <th scope="col"><strong>State</strong></th>
                                                <th scope="col"><strong>City</strong></th>
                                                <th scope="col"><strong>Address</strong></th>
                                                <th scope="col"><strong>Description</strong></th>
                                                <th scope="col"><strong>Status</strong></th>
                                                <c:choose>

                                                    <c:when test="${status.equalsIgnoreCase('Resolved')}">
                                                        <th scope="col"><strong>Comment</strong></th>
                                                    </c:when>

                                                    <c:otherwise>
                                                        <th scope="col"><strong></strong></th>
                                                    </c:otherwise>
                                                </c:choose>
                                            </tr>
                                        </thead>

                                        <c:if test="${complaintsList != null && !complaintsList.isEmpty() }">
                                            <tbody>
                                                <c:forEach items="${complaintsList}" var="complaint">
                                                    <tr>
                                                        <!-- <td>${complaint.id}</td> -->

                                                        <td>${complaint.country}</td>
                                                        <td>${complaint.state}</td>
                                                        <td>${complaint.city}</td>
                                                        <td>${complaint.address}</td>
                                                        <td>${complaint.description}</td>

                                                        <c:choose>
                                                            <c:when test="${status.equalsIgnoreCase('Resolved')}">


                                                                <td>${complaint.comment}</td>
                                                            </c:when>

                                                            <c:otherwise>
                                                                <form id="form-${complaint.id}" method="post"
                                                                    onsubmit="updateStatus(event,this,'${complaint.id}',spinner)">

                                                                    <td>
                                                                        <select class="form-control m-1" name="status">
                                                                            <option value="">Status</option>
                                                                            <option value="IN_PROGRESS" <c:if test="${'IN_PROGRESS'.equalsIgnoreCase(complaint.status)}">selected</c:if>>
                                                                                In Progress
                                                                            </option>
                                                                            <option value="PENDING" <c:if test="${'PENDING'.equalsIgnoreCase(complaint.status)}">selected</c:if>>
                                                                                Pending
                                                                            </option>
                                                                            <option value="NOT_RESOLVED" <c:if test="${'NOT_RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>
                                                                                Not Resolved
                                                                            </option>
                                                                            <option value="RESOLVED" <c:if test="${'RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>
                                                                                Resolved
                                                                            </option>

                                        </select>
                                        </td>
                                        <td class="d-flex justify-content-center">
                                            <button type="submit" class="btn btn btn-success btn-sm m-1">Update</button>
                                        </td>
                                        </form>
                            </c:otherwise>
                        </c:choose>
                        </tr>
                        </c:forEach>

                        </tbody>
                        </c:if>
                        </table>
                        <hr>
                        <%@ include file="../common/Pagination.jsp" %>
                    </div>
                    </c:otherwise>
                    </c:choose>
                </div>
            </div>

            </div>

            <c:choose>

                <c:when test="${status.equalsIgnoreCase('Resolved')}">

                </c:when>

                <c:otherwise>


                    <div class="modal fade" id="otherModel" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div id="resolverOtherModelAlert">

                                    </div>

                                    <div class="mb-3">
                                        <textarea class="form-control" rows="5" id="otherTextArea"
                                            placeholder="Leave a comment here"></textarea>
                                        <span class="text-danger" id="otherTextAreaError"></span>

                                        <!-- <label for="floatingTextarea">Comments</label> -->
                                    </div>
                                </div>
                                <div class="modal-footer d-flex justify-content-between">
                                    <button type="button" class="btn btn-secondary"
                                        onclick="dismisOtherResolveModel(otherModel)">Close</button>
                                    <button type="button" class="btn btn-primary"
                                        onclick="setModelDataOtherStatus(otherModel)">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="resolveModel1" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class="modal-content">

                                <div class="modal-body">
                                    <div id="resolverModelAlert">

                                    </div>

                                    <div class="mb-3">
                                        <!-- <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea> -->
                                        <textarea class="form-control" id="resolveTextArea1" rows="5"
                                            placeholder="Leave a comment here"></textarea>
                                        <span class="text-danger" id="textareaError"></span>

                                        <!-- <label for="floatingTextarea">Comments</label> -->
                                    </div>
                                    <div class="input-group mb-3">
                                        <span class="input-group-text " id="basic-addon1">OTP : </span>
                                        <input type="number" class="form-control numberCenterOtp" id="otp" max="6"
                                            placeholder="6 digit OTP" aria-label="Username"
                                            aria-describedby="basic-addon1">
                                        <button type="button" class=" btn btn-primary input-group-text"
                                            onclick="resendOTP(spinner)">Resend</button>
                                        <%-- <span class="" id="basic-addon2"><a href="">Resend</a></span> --%>
                                    </div>
                                    <span class="text-danger" id="optError"></span>
                                </div>
                                <div class="modal-footer d-flex justify-content-between">
                                    <button type="button" class="btn btn-secondary"
                                        onclick="dismisResolveModel(resolveModel1)">Close</button>
                                    <button type="button" class="btn btn-primary"
                                        onclick="setModelDataForResolve(resolveModel1)">Submit</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal fade" id="spinner" data-bs-backdrop="static" data-bs-keyboard="false"
                        tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                            <div class=" modal-body p-5 bg-transparent text-white d-flex justify-content-center ">
                                <div class="d-flex justify-content-center">
                                    <div class="spinner-border text-light"></div>
                                    <h3 class="ms-3">Loading</h3>
                                </div>
                            </div>
                        </div>
                    </div>


                </c:otherwise>
            </c:choose>

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

            <script type="module" src="resources/js/EmployeeViewComplaints.js"></script>
    </body>

    </html>