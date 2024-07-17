import * as ajax from "./Ajax.js";

var data = {};

var model = "";

var message = "";

// function onResolve(value) {
//     console.log(value.id);
//     dataToSend = {
//         complaintId: 2,
//     };

//     let myModal = new bootstrap.Modal(document.getElementById("resolveModel1"));
//     myModal.show();
// }

// function onOtherStatus(value) {
//     console.log(value);

//     dataToSend = {
//         complaintId: 1,
//     };

//     let myModal = new bootstrap.Modal(document.getElementById("otherModel"));
//     myModal.show();
// }

async function dismisResolveModel(element) {
    console.log("closing resolve model");

    document.getElementById("resolveTextArea1").value = '';
    document.getElementById("otp").value = '';

    document.getElementById("resolverModelAlert").innerHTML = "";
    bootstrap.Modal.getOrCreateInstance(element).hide();
}

async function dismisOtherResolveModel(element) {
    console.log("closing other resolve model");

    document.getElementById("otherTextArea").value = '';

    document.getElementById("resolverOtherModelAlert").innerHTML = "";
    bootstrap.Modal.getOrCreateInstance(element).hide();
}

async function setModelDataForResolve(element) {
    console.log(element);

    let textareaElement = document.getElementById("resolveTextArea1");
    let textarea = textareaElement.value.trim();

    let otpElement = document.getElementById("otp");
    let otp = otpElement.value.trim();

    let otpError = document.getElementById("optError");
    let textareaError = document.getElementById("textareaError");

    textareaError.innerHTML = "";
    otpError.innerHTML = "";

    console.log(otp.length);
    console.log(textarea.length);

    if (otp.length == 6 && textarea.length > 10) {
        let newData = {
            ...data,
            'comment': textarea,
            'otp': otp,
        };
        console.log(newData);

        // main process

        try {

            await ajax.postWithCallBack(
                "employee/resolveComplaint",
                JSON.stringify(newData),
                (response) => {
                    if (response.status) {
                        addAlert(response.message, "success", "errorAlert");

                        textareaElement.value = "";
                        otpElement.value = "";

                        bootstrap.Modal.getOrCreateInstance(element).hide();

                    } else {
                        addAlert(response.message, "danger", "resolverModelAlert");
                    }
                }
            );
        } catch (error) {
            console.log(error);
            alert("error");
        }
    } else {
        if (otp.length != 6) {
            otpError.innerHTML = "OTP should be in 6 digit only";
        }

        if (textarea.length < 10) {
            textareaError.innerHTML = "Comments should contain atleast 10 character";
        }
    }

    return;
}



async function setModelDataOtherStatus(element) {
    console.log(element);

    let textareaElement = document.getElementById("otherTextArea");
    let textarea = textareaElement.value.trim();
    let textareaError = document.getElementById("otherTextAreaError");

    textareaError.innerHTML = "";

    console.log(otp.length);
    console.log(textarea.length);

    if (textarea.length > 10) {
        let newData = {
            ...data,
            comment: textarea,
        };
        console.log(newData);

        try {
            await ajax.postWithCallBack(
                "employee/resolveOtherStatusComplaint",
                JSON.stringify(newData),
                (response) => {
                    if (response.status) {
                        addAlert(response.message, "success", "errorAlert");

                        textareaElement.value = "";
                        bootstrap.Modal.getOrCreateInstance(element).hide();
                    } else {
                        addAlert(response.message, "danger", "resolverOtherModelAlert");
                    }
                }
            );
        } catch (error) {
            console.log(error);
            alert("error");
        }

    } else {
        if (textarea.length < 10) {
            textareaError.innerHTML = "Comments should contain atleast 10 character";
        }
    }
}

// window.onload = onOtherStatus;

async function updateStatus(event, element, complaintId, spinner) {
    event.preventDefault();

    complaintId = parseInt(complaintId);

    console.log(element.status.value);

    const status = element.status.value;

    data = {
        'complaintId': complaintId,
        'status': status
    };

    if (status === "Pending") {
        let modelElement = document.getElementById("otherModel");
        model = modelElement;
        new bootstrap.Modal(modelElement).show();
    } else if (status === "Not Resolved") {
        let modelElement = document.getElementById("otherModel");
        model = modelElement;
        new bootstrap.Modal(modelElement).show();
    } else if (status === "Resolved") {
        try {


            bootstrap.Modal.getOrCreateInstance(spinner).show();
            await ajax.get(
                "employee/resolveComplaintOtp?complaintId=" + complaintId,
                (response) => {
                    if (response.status) {
                        bootstrap.Modal.getOrCreateInstance(spinner).hide();

                        message = response.message;

                        addAlert(response.message, "success", "resolverModelAlert");

                        let modelElement = document.getElementById("resolveModel1");
                        model = modelElement;
                        new bootstrap.Modal(modelElement).show();
                    } else {
                        alert(response.message);
                    }
                }
            );

        } catch (error) {
            console.log(error);
            alert("error");
        }

        console.log("waited or not");
    }
}

async function resendOTP() {
    try {
        await ajax.get(
            "employee/resolveComplaintOtp?complaintId=" + data.complaintId,
            (response) => {
                if (response.status) {
                    message = response.message;
                    addAlert(response.message, "success", "resolverModelAlert");
                } else {
                    alert(response.message);
                }
            }
        );
    } catch (error) {
        console.log(error);
        alert("error");
    }
}


function addAlert(message, type, element) {
    const alertPlaceholder = document.getElementById(element);
    const wrapper = document.createElement("div");
    wrapper.innerHTML = [
        `<div class="alert alert-${type} alert-dismissible" role="alert">`,
        `   <div>${message}</div>`,
        '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
        "</div>",
    ].join("");

    alertPlaceholder.append(wrapper);
}


window.dismisResolveModel = dismisResolveModel;
window.updateStatus = updateStatus;
window.setModelDataOtherStatus = setModelDataOtherStatus;
window.setModelDataForResolve = setModelDataForResolve;
window.dismisOtherResolveModel = dismisOtherResolveModel;

window.resendOTP = resendOTP;


// window.onload = () => {
//     let modelElement = document.getElementById("spinner");
//                         model = modelElement;
//                         new bootstrap.Modal(modelElement).show();
// };
