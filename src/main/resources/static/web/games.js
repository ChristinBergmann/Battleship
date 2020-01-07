/////--------------- GET DATA OF GAMES WITH PLAYERS IN IT ------------/////

async function getData() {
    //await the response of the fetch call
    let response = await fetch('http://localhost:8080/api/games');
    console.log(response);
    //proceed once the first promise is resolved
    let dataGames = await response.json()
    //proceed only when the second promise is resolved
    console.log(dataGames)
    return dataGames;
}

//call getData function
getData()
    .then(dataGames => {
        console.log(dataGames);

        let gamesList = document.getElementById("games")

        dataGames.forEach(game => {
            let div = document.createElement("div")
            div.setAttribute("class", " game-div alert alert-primary", "role", "alert")
            let ul = document.createElement("ul")
            ul.innerHTML = "Game No. " + game.Game_Id

            let gameLink = document.createElement("a")
            gameLink.setAttribute("class", "alert-link")
            gameLink.setAttribute("data-gameId", game.Game_Id)
            gameLink.setAttribute("href", "http://localhost:8080/web/game.html${myParam}") //fix right url
            gameLink.innerHTML = "JOIN"

            gamesList.appendChild(div);
            div.appendChild(ul);
            div.appendChild(gameLink);

            game.GamePlayers.forEach(gamePlayer => {

                let listEmail = document.createElement("li")
                listEmail.innerHTML = "Player : " + gamePlayer.Player.Player_Username
                ul.appendChild(listEmail);

                console.log(gamePlayer);
            })
        })

    }).catch(error => console.log(error));