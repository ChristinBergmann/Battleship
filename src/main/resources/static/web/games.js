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

        const gamesList = document.getElementById("games")

        dataGames.forEach(game => {
            let div = document.createElement("div")
            div.setAttribute("class", "game-div alert alert-primary", "role", "alert")
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

/////------------------------------- GET DATA OF Scores ------------------------------/////
async function getDataRanking() {

    let response = await fetch('http://localhost:8080/api/leaderboard');
    console.log(response);
    let dataPlayers = await response.json()
    return dataPlayers;
}
getDataRanking()
    .then(dataPlayers => {
        console.log(dataPlayers)

        /////------------------ DISPLAYS Player with its Total Scores -----------------/////

        let rankingList = document.getElementById("ranking")
        rankingList.setAttribute("class", "alert alert-primary")
        let table = document.createElement("table")
        table.setAttribute("class", "table table-hover table-dark")
        let tHead = document.createElement("thead")
        let trHeader = document.createElement("tr")
        trHeader.setAttribute("class", "alert-primary")

        let tdName = document.createElement("th")
        tdName.innerHTML = "NAME"
        let tdTotal = document.createElement("th")
        tdTotal.innerHTML = "TOTAL SCORE"

        rankingList.appendChild(table)
        table.appendChild(tHead)
        tHead.appendChild(trHeader)
        trHeader.appendChild(tdName)
        trHeader.appendChild(tdTotal)

        let tBody = document.createElement("tbody")

        dataPlayers.forEach(x => {
            let trRanking = document.createElement("tr")
            let tdRankingName = document.createElement("td")
            let tdRankingTotal = document.createElement("td")


            tdRankingName.innerHTML = x.Player
            tdRankingTotal.innerHTML = x.Scores.Total

            table.appendChild(tBody)
            tBody.appendChild(trRanking)
            trRanking.appendChild(tdRankingName);
            trRanking.appendChild(tdRankingTotal);

        })
    })
    .catch(error => console.log(error));

/////______
function logOut(username, password) {
    fetch("http://localhost:8080/api/logout?userName=" + username + "&password=" + password, {
            method: "post",
        })
        .then(function (response) {
            console.log(response);
            return response.status
        })
        .then((status) => {

            if (status == 200) {
                window.location.href = "manager.html"

            } else {
                feedback = "Ooh something went wrong..try again!"
            }
        })
        .catch(error => console.log(error));

}