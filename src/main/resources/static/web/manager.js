////____________________ Fct to get Input Details __________________/////

function logIn() {
  const username = document.getElementById("user").value;
  const password = document.getElementById("passw").value;

  if (username && password) {
    logInAuthenticationInfo(username, password);
  } else {
    alert("OOOPSIE you missed a field! Please enter all!");
  }
  console.log(username);
}

////__________________ Fct to get SIGN UP Input Details ________________/////

function signUp() {
  const username = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;
  //let passwordConf = document.getElementById("password-confirm").value;

  signUpAuthenticationInfo(username, email, password);
}

//---------------------------------- LOGIN Authentication using AJAX -----------------------//

function logInAuthenticationInfo(username, password) {
  fetch("http://localhost:8080/api/login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `userName=${username}&password=${password}`,
  })
    .then(function (response) {
      console.log(response); /*works*/
      return response.status;
    })
    .then((status) => {
      if (status == 200) {
        window.location.href = "games.html";
      } else {
        alert("Ooh something went wrong..try again!");
      }
      console.log("I`M LOGGED IN", status);
    })
    .catch((error) => console.log(error));
}

//--------------------------- REGISTER Authentication using AJAX ----------------------------//

function signUpAuthenticationInfo(username, email, password) {
  fetch("http://localhost:8080/api/players", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-Type": "application/x-www-form-urlencoded",
    },
    body: `userName=${username}&email=${email}&password=${password}`,
  })
    .then(function (response) {
      //console.log(response);
      return response.status;
    })
    .then((status) => {
      if (status == 201) {
        setTimeout(logInAuthenticationInfo(username, password), 1000);
      } else {
        alert("Ooh something went wrong..try again!");
      }
      console.log("IM LOGGED OUT", status);
    })
    .catch((error) => console.log(error));
}
