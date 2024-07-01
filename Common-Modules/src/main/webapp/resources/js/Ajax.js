


function getRequest() {
    let request;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return request;
}

export async function post(url, data) {
    let request = getRequest();
    request.open('POST', url, true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.onreadystatechange = await function () {
        if (request.readyState == 4 && request.status == 200) {
            var response = JSON.parse(request.responseText);
            return response;
        } else if (request.readyState == 4) {
            return {
                error: "found error"
            }
        }
    };

    request.send(data);
}


export async function get(url,callback) {
    let request = getRequest();
    request.open('GET', url, true);
    request.onreadystatechange = async function () {
        if (request.readyState == 4 && request.status == 200) {
            var response = await JSON.parse(request.responseText);
            callback(response);
        } else if (request.readyState == 4) {
            console.log("Error in get request.")
        }
    };
    request.send();
}

export function demo(){
    alert("fetching module");
    console.log("fetching request");
}

