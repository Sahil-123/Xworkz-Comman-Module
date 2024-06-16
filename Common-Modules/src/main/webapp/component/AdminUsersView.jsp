<div>
  <div class="table-responsive-sm p-4 ">
    <h1 class="text-center"> Users Details </h1>
    <c:choose>
      <c:when test="${userslist.isEmpty()}">
        <tr>
          <h1>No records found</h1>
        </tr>
      </c:when>
      <c:otherwise>
        <table class="table">
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
      </c:otherwise>
    </c:choose>
  </div>
</div>