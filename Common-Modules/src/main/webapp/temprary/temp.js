var data = {};

var model = '';

function onResolve(value) {
    console.log(value.id);
    dataToSend = {
        complaintId: 2,
    };

    let myModal = new bootstrap.Modal(document.getElementById("resolveModel1"));
    myModal.show();
}

function onOtherStatus(value) {
    console.log(value);

    dataToSend = {
        complaintId: 1,
    };

    let myModal = new bootstrap.Modal(document.getElementById("otherModel"));
    myModal.show();
}

function setModelDataForResolve(element) {
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
            'otp': otp
        }
        console.log(newData);

        textareaElement.value='';
        otpElement.value='';

        bootstrap.Modal.getOrCreateInstance(element).hide();
        // window.location.reload();
        // myModal.hide();

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

function setModelDataOtherStatus(element) {
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
            'comment': textarea
        }
        console.log(newData);

        textareaElement.value='';

        bootstrap.Modal.getOrCreateInstance(element).hide();
        // window.location.reload();
        // myModal.hide();

    } else {

        if (textarea.length < 10) {
            textareaError.innerHTML = "Comments should contain atleast 10 character";
        }
    }
}

// window.onload = onOtherStatus;

function checkSubmit(event, element, complaintId) {
    event.preventDefault();
    // console.log(element);
    // console.log(element.employeeId);
    console.log(element.employeeId.value);

    // console.log(element.status);
    console.log(element.status.value);

    const employeeId = element.employeeId.value;

    const status = element.status.value;

    data = {
        complaintId,
        status,
        employeeId,
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

        let modelElement = document.getElementById("resolveModel1");
        model = modelElement;
        new bootstrap.Modal(modelElement).show();
    }
}
