import * as geocoading from "./Geocoading.js";

document.addEventListener("DOMContentLoaded", function () {
  const country = document.getElementById("country");

  geocoading.getCountries((response) => {
    console.log(response);
    response.forEach((element) => {
      var opt = document.createElement("option");
      opt.value = element.country_name;
      opt.innerHTML = element.country_name;
      country.appendChild(opt);
    });
  });
});

function removeOptionElements(selectElement) {
  while (selectElement.children[0]) {
    selectElement.removeChild(selectElement.firstChild);
  }
}

async function getStates() {
  const country = document.getElementById("country");
  const state = document.getElementById("state");

  // removeOptionElements(state);
  // var opt = document.createElement("option");
  // opt.value = "";
  // opt.innerHTML = "State";
  // state.appendChild(opt);

  // if (country.value) {
  //   geocoading.getStates(country.value, (response) => {
  //     console.log(response);
  //     response.forEach((element) => {
  //       var opt = document.createElement("option");
  //       opt.value = element.state_name;
  //       opt.innerHTML = element.state_name;
  //       state.appendChild(opt);
  //     });
  //   });
  // }

  populateOptions(state, "State", country, "state_name",geocoading.getStates);

  console.log("state change");
  getCities();

  console.log(country.value);
  // console.log(response);
}

async function getCities() {
  const state = document.getElementById("state");
  const city = document.getElementById("cities");

  // removeOptionElements(city);
  // var opt = document.createElement("option");
  // opt.value = "";
  // opt.innerHTML = "City";
  // city.appendChild(opt);

  // if (state.value) {
  //   geocoading.getCities(state.value, (response) => {
  //     console.log(response);
  //     response.forEach((element) => {
  //       var opt = document.createElement("option");
  //       opt.value = element.city_name;
  //       opt.innerHTML = element.city_name;
  //       city.appendChild(opt);
  //     });
  //   });
  // }

  populateOptions(city, "City", state, "city_name",geocoading.getCities);

  console.log(state.value);
  // console.log(response);
}

function populateOptions(target, defaultValue, reference, field, callback) {
  removeOptionElements(target);
  var opt = document.createElement("option");
  opt.value = "";
  opt.innerHTML = defaultValue;
  target.appendChild(opt);

  if (reference.value) {
    callback(reference.value, (response) => {
      console.log(response);
      response.forEach((element) => {
        var opt = document.createElement("option");
        opt.value = element[field];
        opt.innerHTML = element[field];
        target.appendChild(opt);
      });
    });
  }
}

window.getStates = getStates;
window.getCities = getCities;
