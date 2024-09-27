<%@ include file="../component/TopLinks.jsp" %>


    <!DOCTYPE html>
    <html lang="en">

    <head>

        <%@ include file="../component/HeaderLink.jsp" %>
            <%@ include file="../user/UserHeader.jsp" %>
                <link rel="stylesheet" href="resources/css/Notification.css">



                <c:choose>

                    <c:when test="${viewAccess.equals('departmentAdmin')}">

                    </c:when>

                    <c:when test="${viewAccess.equals('admin')}">
                    </c:when>

                    <c:otherwise>
                        <link rel="stylesheet" href="resources/css/ViewEmployeeComplaint.css">

                    </c:otherwise>
                </c:choose>


                <title>Complaint View</title>

                <style>
                    label {

                        font-size: 0.80rem;
                        font-weight: bold;
                        font-family: sans-serif;
                    }

                    body {
                        font-family: sans-serif;
                    }
                </style>

    </head>


    <c:choose>

        <c:when test="${viewAccess.equals('departmentAdmin')}">

            <body>

        </c:when>

        <c:when test="${viewAccess.equals('admin')}">

            <body onload="loadDepartment()">
        </c:when>

        <c:otherwise>

            <body>
        </c:otherwise>
    </c:choose>


    <c:choose>

        <c:when test="${viewAccess.equals('departmentAdmin')}">
            <%@ include file="../department/DepartmentAdminNav.jsp" %>

        </c:when>

        <c:when test="${viewAccess.equals('admin')}">
            <%@ include file="../component/AdminHorizontalNavBar.jsp" %>
        </c:when>

        <c:otherwise>
            <%@ include file="../employee/EmployeeNav.jsp" %>
        </c:otherwise>
    </c:choose>

    <div class="container-md mt-3">
        <span class="text-danger">
            ${infoError}
        </span>

        <div id="errorAlert">

        </div>

        <div class=" p-5 border border-secondary rounded-3 mt-3 shadow p-3 mb-5 bg-body rounded">



            <div class="row mb-4">
                <h4>Complaint Details</h4>
            </div>

            <div class="row">
                <div class="col">

                    <div class="mb-3">
                        <label for="complaintType" class="form-label">Complaint Type</label>
                        <select class="form-control" id="complaintType" name="complaintType" disabled>

                            <option value="">${complaint != null ? complaint.complaintType : 'Complaint Type'}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <!-- 2 of 2 -->
                    <div class="mb-3">
                        <label for="country" class="form-label">Country</label>
                        <select class="form-control" id="country" name="country" disabled>
                            <option value="">${complaint != null ? complaint.country : 'Country'}</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">
                    <!-- 1 of 2 -->


                    <div class="mb-3">
                        <label for="state" class="form-label">State</label>
                        <select class="form-control" id="state" name="state" disabled>
                            <option value="">${complaint != null ? complaint.state : 'State'}</option>
                        </select>
                    </div>
                </div>
                <div class="col">
                    <!-- 2 of 2 -->

                    <div class="mb-3">
                        <label for="cities" class="form-label" disabled>City</label>
                        <select class="form-control" id="cities" name="city" disabled>
                            <option value="">${complaint != null ? complaint.city : 'City'}</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col">

                    <div class="mb-3">
                        <label for="address" class="form-label">Address</label>
                        <textarea class="form-control" id="address" name="address" rows="5"
                            disabled>${complaint != null ? complaint.address  : ''}</textarea>
                    </div>
                </div>
                <div class="col">

                    <div class="mb-3">
                        <label for="description" class="form-label">Description</label>
                        <textarea class="form-control" id="description" name="description" rows="5"
                            disabled>${complaint != null ? complaint.description  : ''}</textarea>
                    </div>
                </div>
            </div>


            <c:choose>

                <c:when test="${viewAccess.equals('departmentAdmin')}">
                    <form action="departmentAdmin/departmentAdminUpdateComplaint" method="POST">

                        <div class="row">

                            <div class="col">
                                <select class="form-control m-1" id="${complaint.empId}" name="employeeId">
                                    <option value="">Employee</option>
                                </select>
                            </div>

                            <div class="col">


                                <select class="form-control m-1" id="status" name="status">
                                    <option value="">Status</option>
                                    <option value="PENDING" <c:if test="${'PENDING'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Pending</option>
                                                           <option value="IN_PROGRESS" <c:if test="${'IN_PROGRESS'.equalsIgnoreCase(complaint.status)}">selected</c:if>>In Progress</option>
                                                           <option value="ASSIGNED_TO_EMPLOYEE" <c:if test="${'ASSIGNED_TO_EMPLOYEE'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Assigned to Employee</option>
<option value="REOPENED" <c:if test="${'REOPENED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Reopened</option>
                                                           <option value="REJECTED" <c:if test="${'REJECTED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Rejected</option>

                                </select>
                            </div>

                            <div class="col">

                                <input type="number" value="${complaint.id}" name="complaintId" hidden />
                                <button type="submit" class="btn btn btn-success btn-sm m-1">Assign</button>
                            </div>
                        </div>

                    </form>
                </c:when>

                <c:when test="${viewAccess.equals('admin')}">
                    <form action="complaints/adminUpdateComplaint" method="POST">

                        <div class="row">
                            <div class="col">
                                <select class="form-control m-1" id="${complaint.deptId}" name="department">
                                    <option value="">Department</option>
                                    <c:forEach var="department" items="${departments}">
                                        <option value="${department.id}">${department.departmentName}</option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="col">

                                <select class="form-control m-1" id="status" name="status">
                                    <option value="">Status</option>
                                     <option value="IN_PROGRESS" <c:if test="${'IN_PROGRESS'.equalsIgnoreCase(complaint.status)}">selected</c:if>>In Progress</option>
                                                                                               <option value="ASSIGNED_TO_DEPARTMENT" <c:if test="${'ASSIGNED_TO_DEPARTMENT'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Assigned to Department</option>
                                                                                               <option value="PENDING" <c:if test="${'PENDING'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Pending</option>
                                                                                               <option value="REOPENED" <c:if test="${'REOPENED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Reopened</option>
                                                                                               <option value="NOT_RESOLVED" <c:if test="${'NOT_RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Not Resolved</option>
                                                                                               <option value="REJECTED" <c:if test="${'REJECTED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Rejected</option>

                                </select>
                            </div>

                            <div class="col">

                                <input type="number" value="${complaint.id}" name="complaintId" hidden />
                                <button type="submit" class="btn btn btn-success btn-sm m-1">Submit</button>
                            </div>
                        </div>

                    </form>


                </c:when>

                <c:otherwise>
                    <form id="form-${complaint.id}" method="post"
                        onsubmit="updateStatus(event,this,'${complaint.id}',spinner)">
                        <div class="row">
                            <div class="col">
                                <select class="form-control m-1" name="status">
                                    <option value="">Status</option>
                                    <option value="IN_PROGRESS" <c:if test="${'IN_PROGRESS'.equalsIgnoreCase(complaint.status)}">selected</c:if>>In Progress</option>
                                    <option value="PENDING" <c:if test="${'PENDING'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Pending</option>
                                    <option value="NOT_RESOLVED" <c:if test="${'NOT_RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Not Resolved</option>
                                    <option value="RESOLVED" <c:if test="${'RESOLVED'.equalsIgnoreCase(complaint.status)}">selected</c:if>>Resolved</option>

                                </select>
                            </div>
                            <div class="col">

                                <button type="submit" class="btn btn btn-success btn-sm m-1">Update</button>
                            </div>
                        </div>
                    </form>
                </c:otherwise>
            </c:choose>



        </div>


    </div>

    <c:choose>

        <c:when test="${viewAccess.equals('departmentAdmin')}">
            <script type="module" src="resources/js/DepartmentViewComplaints.js"></script>
        </c:when>

        <c:when test="${viewAccess.equals('admin')}">
            <script type="module" src="resources/js/admin/AdminNotification.js"></script>
        </c:when>

        <c:otherwise>

            <script type="module" src="resources/js/employee/EmployeeNotification.js"></script>


            <div class="modal fade" id="otherModel" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
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

            <div class="modal fade" id="resolveModel1" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
                aria-labelledby="staticBackdropLabel" aria-hidden="true">
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
                                    placeholder="6 digit OTP" aria-label="Username" aria-describedby="basic-addon1">
                                <button type="button" class=" btn btn-primary input-group-text"
                                    onclick="resendOTP()">Resend</button>
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

            <script type="module" src="resources/js/EmployeeViewComplaints.js"></script>
        </c:otherwise>
    </c:choose>


    </body>

    </html>