<div class="d-flex justify-content-end pe-4" >

  <nav aria-label="" class="d-flex  align-items-center" >
    <ul class="pagination">

      <c:choose>

        <c:when test="${currentPage <= 1}">
          <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
        </c:when>

        <c:otherwise>
          <li class="page-item"><a class="page-link" href="${pageURL}/${currentPage - 1}/${pageSize}">Previous</a></li>
        </c:otherwise>
      </c:choose>

      <!-- <c:choose>
      
      <c:when test="${currentPage == pages}">
        <c:forEach var="i" begin="1" end="${pages}">
          <li class="page-item
          
          <c:if test=" ${currentPage==i}">
            active
          </c:if>
          
          "><a class="page-link" href="${pageURL}/${i}/${pageSize}">${i}</a></li>
        </c:forEach>
      </c:when>
      
      <c:otherwise>
        <c:forEach var="i" begin="${currentPage}" end="${pages}">
          <li class="page-item
          
          <c:if test=" ${currentPage==i}">
            active
          </c:if>
          
          "><a class="page-link" href="${pageURL}/${i}/${pageSize}">${i}</a></li>
        </c:forEach>
      </c:otherwise>
    </c:choose> -->


      <c:choose>

        <c:when test="${currentPage == pages}">
          <li class="page-item disabled"><a class="page-link" href="#">Next</a></li>
        </c:when>

        <c:otherwise>
          <li class="page-item"><a class="page-link" href="${pageURL}/${currentPage + 1}/${pageSize}">Next</a></li>
        </c:otherwise>
      </c:choose>

      <div class="ms-3 d-flex justify-content-center  align-items-center" >
        ${((currentPage-1) * pageSize) + 1} - ${(pageSize * (currentPage - 1)) + currentPageRecordSize} of ${totalRecordCount}
      </div>
    </ul>
  </nav>
</div>

<!-- ${currentPage} current page
${pages} total pages
${pageURL} page url
${pageSize} page size -->