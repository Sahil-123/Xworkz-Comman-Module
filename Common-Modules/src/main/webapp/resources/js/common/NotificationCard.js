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