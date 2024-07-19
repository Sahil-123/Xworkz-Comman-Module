import * as ajax from "../Ajax.js";

let data = {
    "departmentName": false,
    "area": false,
    "address": false
};

function validateAndEnableSubmit() {
    let flag = false;
    for (let value of Object.values(data)) {
        if (!value) {
            flag = true;
            break;
        }
    }
    if (!flag) {
        document.getElementById("submitButton").removeAttribute("disabled");
    } else {
        document.getElementById("submitButton").setAttribute("disabled", "");
    }
}

export function departmentNameValidation() {
    let element = document.getElementById("departmentName");
    let error = document.getElementById("departmentNameError");

    const regex = /^[A-Za-z\s]{2,50}$/;
    if (regex.test(element.value)) {
        error.innerHTML = "";
        data["departmentName"] = true;

        ajax.get("department/check-departmentName?departmentName="+element.value,(response)=>{
            if(response.data.found){
                error.innerHTML = "Department with name is already exists.";
            }
            console.log(response.data.found);
        });

    } else {
        error.innerHTML = "Invalid Department Name. It should be between 3 and 50 characters, with only letters and spaces.";
        data["departmentName"] = false;
    }
    validateAndEnableSubmit();
}

export function areaValidation() {
    let element = document.getElementById("area");
    let error = document.getElementById("areaError");

    const regex = /^[A-Za-z0-9\s]{2,50}$/;
    if (regex.test(element.value)) {
        error.innerHTML = "";
        data["area"] = true;
    } else {
        error.innerHTML = "Invalid Area. It should be between 3 and 50 characters, with only letters and spaces.";
        data["area"] = false;
    }
    validateAndEnableSubmit();
}

export function addressValidation() {
    let element = document.getElementById("address");
    let error = document.getElementById("addressError");

    const regex = /^[A-Za-z0-9\s]{10,100}$/;
    if (regex.test(element.value)) {
        error.innerHTML = "";
        data["address"] = true;
    } else {
        error.innerHTML = "Invalid Address. It should be between 10 and 100 characters.";
        data["address"] = false;
    }
    validateAndEnableSubmit();
}

window.departmentNameValidation = departmentNameValidation;
window.areaValidation = areaValidation;
window.addressValidation = addressValidation;
