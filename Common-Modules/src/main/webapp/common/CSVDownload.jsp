<c:choose>
    <c:when test="${filter != null}">
        <div id="filterData" class=" d-flex mb-2 p-2 justify-content-end customeWidth">
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
        </div>
    </c:when>

    <c:when test="${employeesFilter != null}">
        <div id="filterData" class=" d-flex mb-2 p-2 justify-content-end customeWidth">

            <form action="${downloadCSV}/${currentPage}/${pageSize}" method="post" target="_blank">

                <input type="hidden" value="${employeesFilter.fname}" name="fname" />
                <input type="hidden" value="${employeesFilter.lname}" name="lname" />
                <input type="hidden" value="${employeesFilter.email}" name="email" />
                <input type="hidden" value="${employeesFilter.mobile}" name="mobile" />

                <button type="submit" class="btn btn-default border border-primary" role="button">
                    <i class="fa-solid fa-file-csv"></i> Download CSV
                </button>
            </form>
        </div>

    </c:when>

    <c:when test="${employeeFilter != null}">
        <div id="filterData" class=" d-flex mb-2 p-2 justify-content-end customeWidth">

            <form action="${downloadCSV}/${currentPage}/${pageSize}" method="post" target="_blank">

                <input type="hidden" value="${employeeFilter.status}" name="status" />

                <button type="submit" class="btn btn-default border border-primary" role="button">
                    <i class="fa-solid fa-file-csv"></i> Download CSV
                </button>
            </form>
        </div>

    </c:when>

    <c:when test="${employeeFilter == null}">
        <div id="filterData" class=" d-flex mb-2 p-2 justify-content-end customeWidth">

            <a href="${downloadCSV}/${currentPage}/${pageSize}" class="btn btn-default border border-primary"
                role="button">
                <i class="fa-solid fa-file-csv"></i> Download CSV</a>
        </div>

    </c:when>

    <c:otherwise>
        <div class=" d-flex mb-2 p-2 justify-content-end customeWidth">
            <a href="${downloadCSV}/${currentPage}/${pageSize}" class="btn btn-default border border-primary"
                role="button">
                <i class="fa-solid fa-file-csv"></i> Download CSV</a>
        </div>
    </c:otherwise>
</c:choose>