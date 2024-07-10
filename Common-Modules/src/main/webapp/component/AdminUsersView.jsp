<%@ include file="./TopLinks.jsp" %>
  <!DOCTYPE html>
  <html lang="en">

  <head>
    <meta charset="UTF-8">
    <%@ include file="./HeaderLink.jsp" %>
      <link rel="stylesheet" href="resources/css/ViewUserComplaint.css">
      <%@ include file="../user/UserHeader.jsp" %>


        <title>Users</title>
  </head>
  <body>
    <%@ include file="./AdminHorizontalNavBar.jsp" %>

      <div class="mt-3 container d-flex justify-content-center ">
        <div class="">
          <div class="table-responsive-sm p-4 ">
            <h3 class="text-center"> User Details </h3>
            <br>
            <!-- <form action="complaints/viewUserComplaints" method="POST">
              <div class=" d-flex mb-2 p-2 border border-1 rounded-3 border-secondary justify-content-between">
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
            </form> -->

            <c:if test="${userslist == null || userslist.isEmpty() }">
              No Records Found
            </c:if>

            <div class="customeHeight">
              <table class="table ">
                <thead class="table-dark">
                  <tr>
                    <th scope="col"><strong>Email Address</strong></th>
                    <th scope="col"><strong>First Name</strong></th>
                    <th scope="col"><strong>Last Name</strong></th>
                    <th scope="col"><strong>Mobile</strong></th>
                    <th scope="col"><strong>Created By</strong></th>
                    <th scope="col"><strong>Created Date</strong></th>
                    <th scope="col"><strong>Updated By</strong></th>
                    <th scope="col"><strong>Updated Date</strong></th>
                    <th scope="col"><strong>Login Count</strong></th>
                    <th scope="col"><strong>Failed Attempts Count</strong></th>
                    <th scope="col"><strong>Failed Attempt Date Time</strong></th>
                    <th scope="col"><strong>User Lock</strong></th>
                    <!-- <th scope="col"><strong></strong></th>
                    <th scope="col"><strong></strong></th> -->
                  </tr>
                </thead>

                <c:if test="${userslist != null && !userslist.isEmpty() }">
                  <tbody>
                    <c:forEach items="${userslist}" var="user">
                  <tr>
                    <td>${user.email}</td>
                    <td>${user.fname}</td>
                    <td>${user.lname}</td>
                    <td>${user.mobile}</td>
                    <td>${user.createdBy}</td>
                    <td>${user.createdDate}</td>
                    <td>${user.updatedBy}</td>
                    <td>${user.updatedDate}</td>
                    <td>${user.loginCount}</td>
                    <td>${user.failedAttemptsCount}</td>
                    <td>${user.failedAttemptDateTime}</td>
                    <td>${user.lock}</td>
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

      <script type="module" src="resources/js/RaiseUserComplaint.js"></script>

  </body>

  </html>


















  <!-- <div class="">

    <div class="table-responsive-sm p-4 ">
      <h1 class="text-center"> Users Details </h1>
      <c:choose>
        <c:when test="${userslist.isEmpty()}">
          <tr>
            <h1>No records found</h1>
          </tr>
        </c:when>
        <c:otherwise>
          <div class="customeHeight">
            <table class="table ">
              <thead class="table-dark">
                <tr>
                  <th scope="col"><strong>Email Address</strong></th>
                  <th scope="col"><strong>First Name</strong></th>
                  <th scope="col"><strong>Last Name</strong></th>
                  <th scope="col"><strong>Mobile</strong></th>
                  <th scope="col"><strong>Created By</strong></th>
                  <th scope="col"><strong>Created Date</strong></th>
                  <th scope="col"><strong>Updated By</strong></th>
                  <th scope="col"><strong>Updated Date</strong></th>
                  <th scope="col"><strong>Login Count</strong></th>
                  <th scope="col"><strong>Failed Attempts Count</strong></th>
                  <th scope="col"><strong>Failed Attempt Date Time</strong></th>
                  <th scope="col"><strong>User Lock</strong></th>
                </tr>
              </thead>
              <tbody>
                <c:forEach items="${userslist}" var="user">
                  <tr>
                    <td>${user.email}</td>
                    <td>${user.fname}</td>
                    <td>${user.lname}</td>
                    <td>${user.mobile}</td>
                    <td>${user.createdBy}</td>
                    <td>${user.createdDate}</td>
                    <td>${user.updatedBy}</td>
                    <td>${user.updatedDate}</td>
                    <td>${user.loginCount}</td>
                    <td>${user.failedAttemptsCount}</td>
                    <td>${user.failedAttemptDateTime}</td>
                    <td>${user.lock}</td>
                  </tr>
                </c:forEach>
              </tbody>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </div>
  </div> -->