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
            let th = document.createElement("th")
            th.innerHTML = "Game No. " + game.Game_Id

            let gameLink = document.createElement("a")
            gameLink.setAttribute("class", "alert-link")
            gameLink.innerHTML = "JOIN"

            gamesList.appendChild(div);
            div.appendChild(th);
            div.appendChild(gameLink);

            game.GamePlayers.forEach(gamePlayer => {

                let listEmail = document.createElement("tr")
                listEmail.innerHTML = "Player : " + gamePlayer.Player.Player_Username
                th.appendChild(listEmail);

                console.log(gamePlayer);
            })

        })

    }).catch(error => console.log(error));