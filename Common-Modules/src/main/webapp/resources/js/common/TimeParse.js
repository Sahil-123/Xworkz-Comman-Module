export function createComplaintCard(department, createdDate, description, id) {
    // Create a container div for the card
    var container = document.createElement('div');
    container.className = 'row mb-2';
    container.id = id;
    container.setAttribute('onclick', 'showComplaint(this)');

    // Set the innerHTML using a template literal
    container.innerHTML = `
        <div class="card w-100 hoverEffect">
            <div class="card-body">
                <div class="d-flex justify-content-between cardHead">
                    <span class="card-title">${department}</span>
                    <span>${createdDate}</span>
                </div>
                <div class="cardBody">
                    <p class="card-text">${description}</p>
                </div>
            </div>
        </div>
    `;

    return container;
}

export function generateDataArray(count) {
    const data = [];
    const departments = ['HR', 'IT', 'Sales', 'Marketing', 'Finance', 'Operations'];

    for (let i = 0; i < count; i++) {
        const randomDepartment = departments[Math.floor(Math.random() * departments.length)];
        const randomId = Math.floor(Math.random() * 900) + 100; // Random id between 100 and 999

        data.push({
            department: randomDepartment,
            message: `Message for ${randomDepartment}`,
            description: `Description for ${randomDepartment}`,
            id: randomId
        });
    }

    return data;
}

export function convertTo12HourTime(isoDateTime) {
    // Create a Date object from the ISO string
    var date = new Date(isoDateTime);

    // Get hours, minutes, and seconds
    var hours = date.getHours();
    var minutes = date.getMinutes();
    // var seconds = date.getSeconds();

    // Determine AM/PM and adjust hours
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // Convert hour '0' to '12' for 12 AM/PM

    // Pad single digits with leading zero
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    // seconds = seconds < 10 ? '0' + seconds : seconds;

    // Format the time string
    var time = hours + ':' + minutes + ' ' + ampm;
    return time;
}


 export function formatDateTime(isoDateTime) {
    // Create a Date object from the ISO string
    var date = new Date(isoDateTime);

    // Extract date components
    var year = date.getFullYear();
    var month = date.getMonth() + 1; // Months are zero-indexed
    var day = date.getDate();

    // Extract time components
    var hours = date.getHours();
    var minutes = date.getMinutes();
    // var seconds = date.getSeconds();

    // Determine AM/PM and adjust hours
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // Convert hour '0' to '12' for 12 AM/PM

    // Pad single digits with leading zero
    month = month < 10 ? '0' + month : month;
    day = day < 10 ? '0' + day : day;
    hours = hours < 10 ? '0' + hours : hours;
    minutes = minutes < 10 ? '0' + minutes : minutes;
    // seconds = seconds < 10 ? '0' + seconds : seconds;

    // Format the date and time string
    var formattedDate = year + '-' + month + '-' + day;
    var formattedTime = hours + ':' + minutes + ' ' + ampm;
    return formattedDate + ' ' + formattedTime;
}
