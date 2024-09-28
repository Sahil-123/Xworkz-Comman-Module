import * as ajax from "./Ajax.js";

const complaintHistory = [
    {
        historyId: 5,
        complaintID: 3,
        role: "USER",
        id: 1,
        comment: "Your complaint has been submitted and is currently under review by the administration.",
        createdDate: "2024-09-28T01:07:06",
        status: "RECEIVED"
    },
    {
        historyId: 6,
        complaintID: 3,
        role: "ADMIN",
        id: 1,
        comment: "Your complaint has been reviewed and assigned to the appropriate department for further action.",
        createdDate: "2024-09-28T02:08:41",
        status: "ASSIGNED_TO_DEPARTMENT"
    },
    {
        historyId: 10,
        complaintID: 3,
        role: "ADMIN",
        id: 1,
        comment: "Your complaint has been reviewed and assigned to the appropriate department for further action.",
        createdDate: "2024-09-28T02:24:06",
        status: "IN_PROGRESS"
    },
    {
        historyId: 11,
        complaintID: 3,
        role: "ADMIN",
        id: 1,
        comment: "Your complaint has been reviewed and assigned to the appropriate department for further action.",
        createdDate: "2024-09-28T02:25:54",
        status: "ASSIGNED_TO_DEPARTMENT"
    },
    {
        historyId: 12,
        complaintID: 3,
        role: "ADMIN",
        id: 1,
        comment: "Your complaint has been reviewed and assigned to the appropriate department for further action.",
        createdDate: "2024-09-28T02:40:26",
        status: "ASSIGNED_TO_DEPARTMENT"
    },
    {
        historyId: 14,
        complaintID: 3,
        role: "ADMIN",
        id: 1,
        comment: "Your complaint has been reviewed and assigned to the appropriate department for further action.",
        createdDate: "2024-09-28T02:45:15",
        status: "IN_PROGRESS"
    },
    {
        historyId: 15,
        complaintID: 3,
        role: "DEPARTMENT_ADMIN",
        id: 11,
        comment: "Your complaint has been assigned to a specific employee for resolution.",
        createdDate: "2024-09-28T04:14:46",
        status: "ASSIGNED_TO_EMPLOYEE"
    },
    {
        historyId: 16,
        complaintID: 3,
        role: "EMPLOYEE",
        id: 40,
        comment: "Your complaint is pending further information or action from you.",
        createdDate: "2024-09-28T04:27:32",
        status: "PENDING"
    },
    {
        historyId: 17,
        complaintID: 3,
        role: "EMPLOYEE",
        id: 40,
        comment: "Your complaint could not be resolved at this time and requires further attention.",
        createdDate: "2024-09-28T04:27:53",
        status: "NOT_RESOLVED"
    },
    {
        historyId: 18,
        complaintID: 3,
        role: "EMPLOYEE",
        id: 40,
        comment: "Your complaint has been successfully resolved.",
        createdDate: "2024-09-28T04:28:28",
        status: "RESOLVED"
    },
    {
        historyId: 19,
        complaintID: 3,
        role: "DEPARTMENT_ADMIN",
        id: 11,
        comment: "Your complaint has been assigned to a specific employee for resolution.",
        createdDate: "2024-09-28T22:08:15",
        status: "ASSIGNED_TO_EMPLOYEE"
    }
];



async function setModelDataOtherStatus(element) {
    console.log(element);
    bootstrap.Modal.getOrCreateInstance(element).hide();

}

async function dismisComplaintStatusModel(complaintStatusModel) {
    console.log("closing model");
    bootstrap.Modal.getOrCreateInstance(complaintStatusModel).hide();
}

async function getComplaintStatus(complaintId, complaintStatusModel, spinner) {
    console.log(complaintId);
    console.log(complaintStatusModel);

    try {
        // new bootstrap.Modal(spinner).show();
        const spinnerModel = bootstrap.Modal.getOrCreateInstance(spinner);
        spinnerModel.show();

        await ajax.get(
            "complaintHistory/getComplaintHistory/" + complaintId,
            (response) => {
                setTimeout(() => {
                    spinnerModel.hide();  // Hide the spinner with a small delay
                }, 500); 

                console.log(response);
                
                mapComplaintHistory(response, "complaintStatus");
                new bootstrap.Modal(complaintStatusModel).show();
            }
        );
    } catch (error) {
        console.log(error);
        alert("error");
    }

}

function mapComplaintHistory(complaintHistoryArray, containerId) {
    // Get the container element by its ID
    const complaintStatusDiv = document.getElementById(containerId);

    // Clear any existing content in the container
    complaintStatusDiv.innerHTML = '';

    // Loop through the complaintHistory array and create HTML elements
    complaintHistoryArray.forEach(item => {
        // Create a new div with class "node"
        const nodeDiv = document.createElement('div');
        nodeDiv.className = 'node';

        // Create h5 element for status
        const statusHeading = document.createElement('h5');

        const statusDetails = getStatusDetails(item.status);

        statusHeading.textContent = statusDetails.displayName;
        statusHeading.style.color = statusDetails.color;

        // Create p element for comment
        const commentPara = document.createElement('p');
        commentPara.textContent = item.comment;

        // Append the h5 and p to the div
        nodeDiv.appendChild(statusHeading);
        nodeDiv.appendChild(commentPara);

        // Append the div to the complaintStatus element
        complaintStatusDiv.appendChild(nodeDiv);
    });
}


function getStatusDetails(status) {
    const statusMap = {
        RECEIVED: { displayName: "Received", color: "#007bff" }, // Blue
        OPEN: { displayName: "Open", color: "#28a745" }, // Green
        IN_REVIEW: { displayName: "In Review", color: "#ffc107" }, // Yellow
        ASSIGNED_TO_DEPARTMENT: { displayName: "Assigned to Department", color: "#17a2b8" }, // Cyan
        ASSIGNED_TO_EMPLOYEE: { displayName: "Assigned to Employee", color: "#20c997" }, // Light Green
        IN_PROGRESS: { displayName: "In Progress", color: "#fd7e14" }, // Orange
        PENDING: { displayName: "Pending", color: "#6c757d" }, // Gray
        RESOLVED: { displayName: "Resolved", color: "#28a745" }, // Green
        REOPENED: { displayName: "Reopened", color: "#dc3545" }, // Red
        NOT_RESOLVED: { displayName: "Not Resolved", color: "#dc3545" }, // Red
        REJECTED: { displayName: "Rejected", color: "#dc3545" } // Red
    };

    // Return display name and color if the status is found, else a default
    return statusMap[status] || { displayName: status, color: "#000000" }; // Default to black
}

function formatStatus(status) {
    return status
        .toLowerCase()
        .split('_')
        .map(word => word.charAt(0).toUpperCase() + word.slice(1))
        .join(' ');
}


window.getComplaintStatus = getComplaintStatus;
window.dismisComplaintStatusModel = dismisComplaintStatusModel;

window.onload = function () {
    // let modelElement = document.getElementById("complaintStatusModel");
    // new bootstrap.Modal(complaintStatusModel).show();

    const statusElements = document.querySelectorAll('.status-text');

    statusElements.forEach(statusElement => {
        const originalStatus = statusElement.textContent.trim();
        const formattedStatus = formatStatus(originalStatus);
        statusElement.textContent = formattedStatus;
    });

}
