<button class="btn btn-light d-lg-none my-3 ms-3" id="navbarSidebarToggle">
    <i class="fa-solid fa-arrow-right"></i>
</button>

<div class="sidebar flex-column my-height" id="sidebar">
    <button class="btn btn-light mb-3 d-lg-none ms-1" id="sidebarToggle">
        <i class="fa-solid fa-arrow-left"></i>
    </button>
    <ul class="nav nav-pills flex-column p-2">
        <li class="nav-item">
            <a href="user" class="nav-link

             <c:if test=" ${link.equalsIgnoreCase('user')}">
                active
                </c:if>

                ">Home</a>
        </li>

        <li class="nav-item">
            <div class="btn-group dropend nav-item">
                <button type="button" class="btn nav-link dropdown-toggle" style="color: #ffffff" data-bs-toggle="dropdown" aria-expanded="false">
                  Compliant
                </button>
                <ul class="dropdown-menu" style="background: #4f555c" aria-labelledby="dropdownMenuLink">
                    <li class="nav-item">
                        <a class="nav-link" href="raiseComplaint">
                            Raise Complaint
                        </a>
    
                        <a class="nav-link" href="viewUserComplaints">
                            View Compliants
                        </a>
                    </li>
                </ul>
              </div>
            
        </li>



    </ul>
</div>

<script src="resources/js/UserSidrBar.js"></script>