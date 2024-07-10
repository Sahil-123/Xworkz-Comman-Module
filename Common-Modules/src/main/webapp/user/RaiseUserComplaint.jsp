<%@ include file="../component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="../component/HeaderLink.jsp" %>
            <link rel="stylesheet" href="resources/css/RaiseUserComplaint.css">
            <%@ include file="UserHeader.jsp" %>


                <title>${edit? "Edit Complaint": 'Raise Complaint' }</title>

    </head>

    <body>
        <%@ include file="UserNav.jsp" %>


            <div class="mt-3 container d-flex justify-content-center">
                <div class="card w-50 ps-4 pe-4 pt-2 pb-1 shadow p-3 mb-5 bg-body rounded">

                    <c:if test="${edit}">
                        <form action="complaints/updateComplaint" method="POST">
                            <input type="number" value="${complaintData.id}" name="complaintId" hidden />
                    </c:if>

                    <c:if test="${!edit}">
                        <form action="complaints/raiseComplaint" method="POST">
                    </c:if>

                    <div class="card-body d-flex flex-column justify-content-center">

                        <span class="compulsary">
                            ${infoError}

                            <c:forEach items="${errors}" var="objectError">
                                "${objectError.defaultMessage}" <br>
                            </c:forEach>
                        </span>

                        <h2 class="card-title text-center mb-5">Raise Complaint</h2>

                        <div class="mb-3">
                            <label for="complaintType" class="form-label">Complaint Type</label>
                            <select class="form-control" id="complaintType" name="complaintType" <c:if test="${edit}">
                                disabled
                                </c:if>
                                >
                                <option value="">Complaint Type</option>
                                <option value="Noise" <c:if test="${complaintData.complaintType == 'Noise'}">selected
                                    </c:if>>Noise</option>
                                <option value="Pollution" <c:if test="${complaintData.complaintType == 'Pollution'}">
                                    selected</c:if>>Pollution</option>
                                <option value="Drainage Problem" <c:if
                                    test="${complaintData.complaintType == 'Drainage Problem'}">selected</c:if>>Drainage
                                    Problem</option>
                                <option value="Billing" <c:if test="${complaintData.complaintType == 'Billing'}">
                                    selected</c:if>>Billing</option>
                                <option value="Service" <c:if test="${complaintData.complaintType == 'Service'}">
                                    selected</c:if>>Service</option>
                                <option value="Delivery" <c:if test="${complaintData.complaintType == 'Delivery'}">
                                    selected</c:if>>Delivery</option>
                                <option value="Product" <c:if test="${complaintData.complaintType == 'Product'}">
                                    selected</c:if>>Product</option>
                                <option value="Support" <c:if test="${complaintData.complaintType == 'Support'}">
                                    selected</c:if>>Support</option>
                            </select>
                        </div>

                        <c:if test="${!edit}">

                            <div class="mb-3">
                                <label for="country" class="form-label">Country</label>
                                <select class="form-control" id="country" name="country" onchange="getStates()">
                                    <option value="">Country</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="state" class="form-label">State</label>
                                <select class="form-control" id="state" name="state" onchange="getCities()">
                                    <option value="">State</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="cities" class="form-label">City</label>
                                <select class="form-control" id="cities" name="city">
                                    <option value="">City</option>
                                </select>
                            </div>

                            <div class="mb-3">
                                <label for="address" class="form-label">Address</label>
                                <textarea class="form-control" id="address" name="address" rows="3"></textarea>
                            </div>
                        </c:if>


                        <div class="mb-3">
                            <label for="description" class="form-label">Description</label>
                            <textarea class="form-control" id="description" name="description"
                                rows="3">${complaintData.description}</textarea>
                        </div>

                        <c:if test="${edit}">
                            <input type="submit" class="btn btn-primary" id="submitButton" value="Edit Complaint" />
                        </c:if>
                        <c:if test="${!edit}">
                            <div class="mb-3 d-flex justify-content-between">
                                <input type="submit" class="btn btn-primary" id="submitButton"
                                    value="Raise Complaint" />
                                <input type="reset" class="btn btn-primary" id="submitButton" value="Reset" />
                            </div>
                        </c:if>

                    </div>
                </div>
                </form>
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