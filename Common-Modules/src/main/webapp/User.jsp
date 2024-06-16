<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
        <base href="http://localhost:8080/Common-Modules/">


            <title>User</title>

            <style>
                    label {
                        font-weight: bold;
                    }

                    .error {
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        margin-top: 8px;
                        color: red;
                    }

                    .compulsary {
                        color: red;
                    }

                    #successIcon{
                        hight: 100vmin;
                        width: 10vmin;
                        background: red;

                    }

                    .userProfile{
                        padding: 10px;
                        display: flex;
                        justify-content: center;
                        align-item: center;
                        background: grey;
                        width: 38px;
                        border-radius: 60%;
                        cursor: pointer;
                    }

                </style>

    </head>

    <body>
        <nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark" style="background-color: #e3f2fd;">
                   <div class="container-fluid">
                       <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo03"
                       aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Toggle navigation">
                       <span class="navbar-toggler-icon"></span>
                     </button>
                       <a class="" href="index.jsp">
                           <img src="https://www.x-workz.in/static/media/Logo.cf195593dc1b3f921369.png" style="width: 70px; height:30px; " />
                       </a>

                       <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
                           <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                           </ul>
                           <ul class="navbar-nav">

                                <li class="nav-item text-capitalize nav-link d-flex align-items-center text-white">
                                                            <div class="nav-link nav-item ">${userDto.fname} ${userDto.lname}</div>
                                                            <div class="userProfile nav-item nav-link ms-1">
                                                                                       <i class="fa-solid fa-user"></i>

                                                            </div>
                                                        </li>

                           </ul>
                       </div>
                   </div>
               </nav>



<c:if test="${successMessage.length() > 0}">
    <div class="modal fade " id="exampleModal" tabindex="0" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content" style="background-color: #4BB543">
                <div class="d-flex flex-row-reverse p-3" style="background-color: #4BB543">
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                 <div class="d-flex flex-column align-items-center p-5 text-white" >
                                    <img src="check.png"></img>
                 </div>
                <div class="d-flex flex-column align-items-center p-5 text-white" >
                    <h5>  ${successMessage}</h5>
                </div>
                <div class="d-flex flex-row-reverse p-3">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", function() {
            var exampleModal = new bootstrap.Modal(document.getElementById('exampleModal'));
            exampleModal.show();
        });
    </script>
</c:if>



    </body>

    </html>