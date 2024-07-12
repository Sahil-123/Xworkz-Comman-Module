import * as ajax from "./Ajax.js";

async function loadContent() {
  console.log("loading content");

  await ajax.get("departmentAdmin/getDepartmentEmployees", (response) => {
    console.log(response);

    const element = document.getElementsByName("employeeId");
    element.forEach((e) => {
      response.forEach((employee)=>{
        let opt = document.createElement("option");
        opt.innerHTML = employee.employeeName;
        opt.value = employee.id;

        if(employee.id == e.id){
          opt.setAttribute("selected",'');
        }

        e.appendChild(opt)
      })
    });

  });

}

window.onload = loadContent;
