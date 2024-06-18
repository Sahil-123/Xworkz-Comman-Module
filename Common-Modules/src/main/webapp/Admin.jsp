<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://localhost:8080/Common-Modules/">


            <title>Admin Dashboard</title>

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

                #successIcon {
                    hight: 100vmin;
                    width: 10vmin;
                    background: red;

                }

                .userProfile {
                    display: flex;
                    justify-content: center;
                    align-item: center;
                    background: grey;
                    width: 38px;
                    height: 38px;
                    border-radius: 60%;
                    cursor: pointer;
                    overflow: hidden;
                }

                .userImg{
                    width: 50px;
                    height: 50px;
                }

                .customized-item {
                    width: 6vmax;
                    text-align: center;
                    cursor: pointer;

                }

                .cutomize-vertical-nav {
                    width: 8vmax;
                    height: 41vmax;

                }

                .data-page {
                    padding: 20px
                }

                .customeHeight{
                    height: 70vh;
                    width: 85vw;
                    overflow: scroll;
                    overflow-x=scroll
                }

            </style>

    </head>

    <body>
        <%@ include file="component/AdminHorizontalNavBar.jsp" %>


            <div class=" d-flex ">
                <%@ include file="component/AdminSideBar.jsp" %>
                    <div class="data-page">
                        <c:choose>

                            <c:when test="${action.equalsIgnoreCase('users') }">
                                <%@ include file="component/AdminUsersView.jsp" %>
                            </c:when>

                            <c:otherwise>
                                Welecome ${adminDto.firstName} ${adminDto.lastName}
                            </c:otherwise>
                        </c:choose>
                    </div>
            </div>

    </body>

    </html>