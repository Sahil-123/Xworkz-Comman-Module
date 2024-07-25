import * as ajax from "../Ajax.js";
import * as time from "../common/TimeParse.js";
import * as card from "../common/NotificationCard.js";

async function loadNotification () {
    console.log("Getting data from the server for notification.");
    // let array = generateDataArray(100);
    let notificationBar = document.getElementById("notificationBody");
    let notificationCount = document.getElementById("notificationCount");


    await ajax.get("admin/notification", (response) => {
        console.log(response);
        notificationCount.innerHTML = response.count;

        notificationBar.innerHTML = '';

        const today = document.createElement('p');
        today.className = 'text-white';
        today.textContent = 'Today';
        notificationBar.appendChild(today);

        response.toDays.forEach(e => {
            let obj = card.createComplaintCard(e.complaintType, time.convertTo12HourTime(e.createdDate), e.description, e.id);
            notificationBar.appendChild(obj);
        })

        notificationBar.appendChild(document.createElement('br'));
        const older = document.createElement('p');
        older.className = 'text-white';
        older.textContent = 'Older';
        notificationBar.appendChild(older);

        response.older.forEach(e => {
            let obj = card.createComplaintCard(e.complaintType, time.formatDateTime(e.createdDate), e.description, e.id);
            notificationBar.appendChild(obj);
        })
    });



    // array.forEach(e => {
    //     let obj = createComplaintCard(e.department, e.message, e.description, e.id);
    //     element.appendChild(obj);
    // });
}

function showComplaint(element){
    console.log(element.id);
    window.location.href = "admin/viewComplaint?complaintId="+element.id;
}

setInterval(loadNotification, 10000);

window.loadNotification = loadNotification;
// window.onload = loadNotification;
// window.ononline = loadNotification;
window.showComplaint = showComplaint;

loadNotification();