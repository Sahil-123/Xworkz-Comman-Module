import * as ajax from "./Ajax.js";

let data = {
    "fname": false,
    "lname": false,
    "email": false,
    "mobile": false,
    "agreement": true
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

export function nameValidation(field) {
    let element = document.getElementById(field);
    let error = document.getElementById(field + "Error");

    const regex = /^[A-Za-z\s]{3,30}$/;
    if (regex.test(element.value)) {
        error.innerHTML = "";
        data[field] = true;
    } else {
        error.innerHTML = `Invalid ${field === "fname" ? "First Name" : "Last Name"}. Name should be between 3 and 30 characters, with only letters and spaces.`;
        data[field] = false;
    }
    validateAndEnableSubmit();
}


export function mobileValidation() {
    let element = document.getElementById("mobile");
    let error = document.getElementById("mobileError");

    const pattern = /^[0-9]{10}$/;
    if (pattern.test(element.value)) {
        error.innerHTML = "";
        data["mobile"] = true;

        ajax.get("check-mobile?mobile="+element.value,(response)=>{
            if(response.data.found){
                error.innerHTML = "Account with this mobile number is already exists.";
            }
            console.log(response.data.found);
        });

    } else {
        error.innerHTML = "Invalid Mobile Number.";
        data["mobile"] = false;
    }
    validateAndEnableSubmit();
}

export async function emailValidation() {
    let element = document.getElementById("email");
    let error = document.getElementById("emailError");

    const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    if (emailPattern.test(element.value)) {
        error.innerHTML = "";
        data["email"] = true;

        ajax.get("check-email?email="+element.value,(response)=>{
            if(response.data.found){
                error.innerHTML = "Account with this email is already exists.";
            }
            console.log(response.data.found);
        });
        // console.log(check);

    } else {
        error.innerHTML = "Invalid Email.";
        data["email"] = false;
    }
    validateAndEnableSubmit();
}

window.nameValidation = nameValidation;
window.emailValidation = emailValidation;
window.mobileValidation = mobileValidation;


