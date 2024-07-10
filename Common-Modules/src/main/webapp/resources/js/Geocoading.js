const apiKey = "RM9Dua9Xu2BUfc8pXm18yPyuaC-pjMKyar8-yBqyjR3VjWR7KIPsDJxKJFxSXdxbnHE";
const mail = "jiwaxa7837@furnato.com";
var access_token;

function getRequest() {
    let request;
    if (window.XMLHttpRequest) {
        request = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        request = new ActiveXObject("Microsoft.XMLHTTP");
    }
    return request;
}

export async function getToken() {
    let request = getRequest();
    return new Promise((resolve, reject) => {
        request.open('GET', 'https://www.universal-tutorial.com/api/getaccesstoken', true);

        request.setRequestHeader('Content-Type', 'application/json');
        request.setRequestHeader('accept', 'application/json');
        request.setRequestHeader('api-token', apiKey);
        request.setRequestHeader('user-email', mail);

        request.onreadystatechange = function () {
            if (request.readyState == 4) {
                if (request.status == 200) {
                    var response = JSON.parse(request.responseText);
                    access_token = response.auth_token;
                    console.log(response);
                    console.log("token " + access_token);
                    resolve(access_token);
                } else {
                    console.log("Error in get access token.");
                    reject("Error in get access token.");
                }
            }
        };
        request.send();
    });
}

async function get(url, callback) {
    if (!access_token) {
        try {
            console.log("token need to fetch");
            await getToken();
        } catch (error) {
            console.error(error);
            return;
        }
    }

    let request = getRequest();
    request.open('GET', url, true);
    request.setRequestHeader('Content-Type', 'application/json');
    request.setRequestHeader('accept', 'application/json');
    request.setRequestHeader('Authorization', 'Bearer ' + access_token);

    request.onreadystatechange = function () {
        if (request.readyState == 4) {
            if (request.status == 200) {
                var response = JSON.parse(request.responseText);
                callback(response);
            } else {
                console.log("Error in get request.");
            }
        }
    };
    request.send();
}

export async function getCountries(callback) {
    await get("https://www.universal-tutorial.com/api/countries", callback);
}

export async function getStates(country, callback) {
    await get("https://www.universal-tutorial.com/api/states/" + country, callback);
}

export async function getCities(state, callback) {
    await get("https://www.universal-tutorial.com/api/cities/" + state, callback);
}

export function demo() {
    alert("fetching module");
    console.log("fetching request");
}
