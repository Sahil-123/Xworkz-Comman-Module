<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://192.168.139.7:8080/Common-Modules/">
            <%@ include file="user/UserHeader.jsp" %>

            <title>User</title>
            

            <style>

            </style>

    </head>

    <body>

        <%@ include file="user/UserNav.jsp" %>

            <c:if test="${successMessage.length() > 0}">
                <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-dialog-centered">
                        <div class="modal-content" style="background-color: #4BB543">
                            <div class="d-flex flex-row-reverse p-3" style="background-color: #4BB543">
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
                                <img src="check.png"></img>
                            </div>
                            <div class="d-flex flex-column align-items-center p-5 text-white">
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

            <!-- <img src="profileImages" alt="Profile picture"></img> -->

    </body>

    </html>