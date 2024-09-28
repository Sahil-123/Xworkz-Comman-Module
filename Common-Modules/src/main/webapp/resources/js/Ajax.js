
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
            resolve(response);
        } else if (request.readyState == 4) {
            reject("error in post request");
        }
    };

    request.send(data);
}

export function postWithCallBack(url, data, callback) {
    return new Promise((resolve, reject) => {
        let request = getRequest();
        request.open('POST', url, true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                var response = JSON.parse(request.responseText);
                callback(response);
                resolve(response);
            } else if (request.readyState == 4) {
                reject("Error in post request");
            }
        };
        request.send(data);
    });
}

export function postWithCallBack2(url, data) {
    return new Promise((resolve, reject) => {
        let request = getRequest();
        request.open('POST', url, true);
        request.setRequestHeader('Content-Type', 'application/json');
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                var response = JSON.parse(request.responseText);
                resolve(response);
            } else if (request.readyState == 4) {
                reject("Error in post request");
            }
        };
        request.send(data);
    });
}


// export async function postWithCallBack(url, data,callback) {
//     let request = getRequest();
//     request.open('POST', url, true);
//     request.setRequestHeader('Content-Type', 'application/json');
//     request.onreadystatechange = await function () {
//         if (request.readyState == 4 && request.status == 200) {
//             var response = JSON.parse(request.responseText);
//             callback(response);
//         } 
        
//         // else{
//         //     console.log("Error in post request");
//         // }
//     };

//     request.send(data);
// }

// export async function postWithCallBack(url, data,callback) {
//     fetch(url, {
//         Method: 'POST',
//         Headers: {
//           'Accept': 'application.json',
//           'Content-Type': 'application/json'
//         },
//         Body: data,
//         Cache: 'default'
//       })
//       .then((response)=>  console.log(response.body))
//     //   .then()
//       .catch(error=>console.log(error))
// }


export async function get(url,callback) {
    let request = getRequest();
    request.open('GET', url, true);
    request.onreadystatechange = async function () {
        if (request.readyState == 4 && request.status == 200) {
            var response = await JSON.parse(request.responseText);
            callback(response);
            // resolve(response);
        } else if (request.readyState == 4) {
            console.log("Error in get request.")
            // reject("error in post request");
        }
    };
    request.send();
}

export function getWithPromise(url) {
    return new Promise((resolve, reject) => {
        let request = getRequest();
        request.open('GET', url, true);
        request.onreadystatechange = function () {
            if (request.readyState == 4 && request.status == 200) {
                var response = JSON.parse(request.responseText);
                resolve(response);
            } else if (request.readyState == 4) {
                reject({
                    status : false,
                    message: "error in code"
                });
            }
        };
        request.send();
    });
}


export function demo(){
    alert("fetching module");
    console.log("fetching request");
}

