////__________________ Fct to get Input Details ________________/////

function getUserPassw() {

    let username;
    let password;
    let feedback;

    username = document.getElementById("user").value;
    password = document.getElementById("passw").value;

    if (username && password) {
        // feedback = "Well done!"
        // alert(feedback)
    } else {
        feedback = "OOOPSIE you missed a field! Please enter all!"
        alert(feedback)
    }
    // postUserPassw(username, password)

}

// function postUserPassw() {

// }




// fetch("https://api.myjson.com/bins/zyv02")
//     .then(response => {
//         return response.json()
//     }).then(result => {
//         controller(result)
//     })

// function controller(data) {
//     console.log(data)
//     showData(data.books)
// }

// function showData(data) {
//     const bookBox = document.getElementById("bookList")



// $(function () {

//     // display text in the output area
//     function showOutput(text) {

//         $("#output").text(text);

//     }

//     // load and display JSON sent by server for /players
//     function loadData() {

//         $.get("/players")
//             .done(function (data) {
//                 console.log(data)
//                 showOutput(JSON.stringify(data, null, 2));

//             })

//             .fail(function (jqXHR, textStatus) {
//                 showOutput("Failed: " + textStatus);

//             });

//     }

//     // handler for when user clicks add person

//     function addPlayer() {

//         let name = $("#email").val();
//         if (name) {

//             postPlayer(name);

//         }

//     }

//     // code to post a new player using AJAX
//     // on success, reload and display the updated data from the server

//     function postPlayer(userName) {

//         $.post({

//                 headers: {

//                     'Content-Type': 'application/json'

//                 },

//                 dataType: "text",
//                 url: "/players",
//                 data: JSON.stringify({
//                     "userName": userName,
//                     "password": ""
//                 })

//             })

//             .done(function () {

//                 showOutput("Saved – reloading");
//                 loadData();

//             })

//             .fail(function (jqXHR, textStatus) {

//                 showOutput("Failed: " + textStatus);

//             });

//     }
//     $("#add_player").on("click", addPlayer);
//     loadData();

// });