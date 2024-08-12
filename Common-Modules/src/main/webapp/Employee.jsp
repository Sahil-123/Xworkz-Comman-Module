<%@ include file="component/TopLinks.jsp" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <%@ include file="component/HeaderLink.jsp" %>
            <base href="http://192.168.139.7:8080/Common-Modules/">

                <%@ include file="../employee/EmployeeHeaderLinks.jsp" %>



            <title>Employee Dashboard</title>

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

                .userImg {
                    width: 50px;
                    height: 50px;
                }

                .defaultUserIcon {
                    margin-top: 10px;
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

                .customeHeight {
                    height: 70vh;
                    width: 85vw;
                    overflow: scroll;
                    overflow-x: scroll;
                }
            </style>

    </head>

    <body>
        <%@ include file="employee/EmployeeNav.jsp" %>

            <div class=" container">

                Welecome ${employeeData.fname} ${employeeData.lname}

            </div>

    </body>

    </html>