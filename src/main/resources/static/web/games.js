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
console.log("hi")
//call getData function
getData()
    .then(dataGames => {
        console.log(dataGames);

        let gamesList = document.getElementById("games")

        dataGames.forEach(game => {
            let ul = document.createElement("ul")
            ul.innerHTML = "Game No. " + game.Game_Id
            gamesList.appendChild(ul);

            game.GamePlayers.forEach(gamePlayer => {
                console.log(gamePlayer)

                let listPlayer = document.createElement("li")
                listPlayer.innerHTML = "GP Id   : " + gamePlayer.GamePlayer_Id
                ul.appendChild(listPlayer);


                let listEmail = document.createElement("li")
                console.log(gamePlayer)
                listEmail.innerHTML = "Player : " + gamePlayer.Player.Player_Username
                ul.appendChild(listEmail);
                console.log(gamePlayer.Player.Player_Username);

            })
        })

    }).catch(error => console.log(error));