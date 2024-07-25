import * as ajax from "./Ajax.js";

var departments = [];

async function loadDepartment() {
  alert("loading department")
  console.log("loading department");
  await ajax.get("department/getAllDepartments", (response) => {
    console.log(response);
    departments = response;
    console.log(departments);

    const element = document.getElementsByName("department");
    element.forEach((e) => {
      response.forEach((department)=>{
        let opt = document.createElement("option");
        opt.innerHTML = department.departmentName;
        opt.value = department.id;

        if(department.id == e.id){
          opt.setAttribute("selected",'');
        }

        e.appendChild(opt)
      })
    });

  });

}

loadDepartment();

window.loadDepartment = loadDepartment;
// window.onload = loadDepartment;

