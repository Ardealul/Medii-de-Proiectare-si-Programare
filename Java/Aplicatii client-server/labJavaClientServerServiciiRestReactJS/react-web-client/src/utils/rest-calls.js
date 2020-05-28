import {BASE_URL} from "./consts";

function status(response){
    console.log('response status ' + response.status);
    if(response.status >= 200 && response.status < 300){
        return Promise.resolve(response);
    }
    else{
        return Promise.reject(new Error(response.statusText));
    }
}

function json(response){
    return response.json();
}

export function GetRaces(){
    var headers = new Headers();
    headers.append('Accept', 'application/json');
    var myInit = {
        method: 'GET',
        headers: headers,
        mode: 'cors'
    };
    var request = new Request(BASE_URL, myInit);

    console.log('before fetch for ' + BASE_URL);

    return fetch(request)
        .then(status)
        .then(json)
        .then(data => {
            console.log('Request succeeded with JSON response ', data);
            return data;
        }).catch(error => {
            console.log('Request failed ', error);
            return error;
        });
}

export function DeleteRace(id) {
    var headers = new Headers();
    headers.append('Accept', 'application/json');
    var myInit = {
        method: 'DELETE',
        headers: headers,
        mode: 'cors'
    };

    var url = BASE_URL + '/' + id;

    console.log('before fetch delete');

    return fetch(url, myInit)
        .then(status)
        .then(response => {
            console.log('Delete status ' + response.status);
            return response.text();
        }).catch(e => {
            console.log('Request failed ' + e);
            return Promise.reject(e);
        });
}

export function AddRace(race) {
    var headers = new Headers();
    headers.append('Accept', 'application/json');
    headers.append('Content-Type', 'application/json');
    var myInit = {
        method: 'POST',
        headers: headers,
        mode: 'cors',
        body: JSON.stringify(race)
    };

    console.log('before fetch post ' + JSON.stringify(race));

    return fetch(BASE_URL, myInit)
        .then(status)
        .then(response => {
            return response.text();
        }).catch(error => {
            console.log('Request failed', error);
            return Promise.reject(error);
        });
}