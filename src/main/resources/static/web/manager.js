////__________________ Fct to get Input Details ________________/////

function getLogIn() {

    let username;
    let password;
    let feedback;

    username = document.getElementById("user").value;
    password = document.getElementById("passw").value;

    if (username && password) {
        feedback = "Well done!"
        alert(feedback)
    } else {
        feedback = "OOOPSIE you missed a field! Please enter all!"
        alert(feedback)
    }
    AuthenticationInfo(username, password);
}

//----- code to post a new player using AJAX------//

function AuthenticationInfo(username, password) {
    fetch("http://localhost:8080/api/login?userName=" + username + "&password=" + password, {
            method: "post",
        })
        .then(function (response) {
            console.log(response);
            return response.status
        })
        .then((status) => {

            if (status == 200) {
                window.location.href = "games.html"

            } else {
                feedback = "Try again!"
            }
        })
        .catch(function (error) {
            console.log(error);
        })
}

//     // handler for when user clicks add person

//     function addPlayer() {

//         let name = $("#email").val();
//         if (name) {

//             postPlayer(name);

//         }

//     }
//     $("#add_player").on("click", addPlayer);
//     loadData();

// });
// load and display JSON sent by server for /players

// fetch.get("/players")
//     .done(function (data) {
//         console.log(data)
//         showOutput(JSON.stringify(data, null, 2));

//     })

//     .fail(function (jqXHR, textStatus) {
//         showOutput("Failed: " + textStatus);

//     });