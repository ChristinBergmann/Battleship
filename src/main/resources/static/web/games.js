async function getData() {
    //await the response of the fetch call
    let response = await fetch('http://localhost:8080/api/games');
    console.log(response);
    //proceed once the first promise is resolved.
    let data = await response.json()
    //proceed only when the second promise is resolved
    return data;
}
//call getData function
getData()
    .then(data => {
        console.log(data);

        let gamesList = document.getElementById("games")

        data.forEach(game => {
            let ul = document.createElement("ul")
            ul.innerHTML = "Game No. : " + game.Game_Id
            gamesList.appendChild(ul);

            game.GamePlayers.forEach(gamePlayer => {
                console.log(gamePlayer)

                let listPlayer = document.createElement("li")
                listPlayer.innerHTML = gamePlayer.GamePlayer_Id
                ul.appendChild(listPlayer);


                let listEmail = document.createElement("li")
                listEmail.innerHTML = "Player : " + gamePlayer.Player[0].Player_Username
                ul.appendChild(listEmail);
                console.log(gamePlayer.Player[0].Player_Username);

            })
        })

    });