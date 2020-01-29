/////___________________________________ GET DATA OF GAMES with PLAYERS IN IT _________________________________/////
getData()
async function getData() {
    let response = await fetch('http://localhost:8080/api/games');
    //console.log(response)
    let dataGames = await response.json()
    console.log(dataGames)
    renderGames(dataGames);
}
//------------------------POSTS Available Games-----------------//

function renderGames(dataGames) {
    console.log("all games should be here:", dataGames) /*works right*/
    const gamesList = document.getElementById("games")
    //----just GAMES with 1Player----//

    dataGames.forEach(game => {
        if (game.GamePlayers.length === 1) {
            console.log("only with 1 player:", game) /*works right*/

            let divGames = document.createElement("div")
            divGames.setAttribute("class", "game-div alert alert-primary", "role", "alert")

            let thGameNO = document.createElement("th")
            thGameNO.innerHTML = "Game No. " + game.Game_Id

            let gameLink = document.createElement("a")
            gameLink.setAttribute("class", "alert-link")
            gameLink.setAttribute("data-GameId", game.Game_Id)
            gameLink.innerHTML = "JOIN"
            gameLink.addEventListener("click", function () {

                postGameViewPage(game.Game_Id)
                //console.log("this is the gameID", game.Game_Id)
            })

            gamesList.appendChild(divGames);
            divGames.appendChild(thGameNO);
            divGames.appendChild(gameLink);

            game.GamePlayers.forEach(x => {
                let listEmail = document.createElement("tr")
                listEmail.innerHTML = "Player : " + x.Player.Player_Username
                thGameNO.appendChild(listEmail);
            })
        }
    })
}

/////______________________________________________ GET DATA OF Scores __________________________________________/////
getDataRanking()
async function getDataRanking() {
    let response = await fetch('http://localhost:8080/api/leaderboard');
    //console.log(response)/*works*/
    let dataPlayers = await response.json()
    renderRankingPlayers(dataPlayers);
    console.log(dataPlayers)

}
/////------------------ POSTS RANKING (Player with its Total Scores) -----------------/////

function renderRankingPlayers(dataPlayers) {
    //console.log("Ranking:",dataPlayers)/*works right*/

    let divRankingList = document.getElementById("ranking")
    divRankingList.setAttribute("class", "alert alert-primary")
    let table = document.createElement("table")
    table.setAttribute("class", "table table-hover table-dark")
    let tHead = document.createElement("thead")
    let trHeader = document.createElement("tr")
    trHeader.setAttribute("class", "alert-primary")
    let tdName = document.createElement("th")
    tdName.innerHTML = "NAME"
    let tdTotal = document.createElement("th")
    tdTotal.innerHTML = "TOTAL SCORE"

    divRankingList.appendChild(table)
    table.appendChild(tHead)
    tHead.appendChild(trHeader)
    trHeader.appendChild(tdName)
    trHeader.appendChild(tdTotal)

    let tBody = document.createElement("tbody")

    dataPlayers.forEach(x => {

        let trRanking = document.createElement("tr")
        let tdRankingName = document.createElement("td")
        let tdRankingTotal = document.createElement("td")

        if (x.Scores.Total === undefined) {
            tdRankingName.innerHTML = x.Player
            tdRankingTotal.innerHTML = " / "
        } else {
            tdRankingName.innerHTML = x.Player
            tdRankingTotal.innerHTML = x.Scores.Total
        }

        table.appendChild(tBody)
        tBody.appendChild(trRanking)
        trRanking.appendChild(tdRankingName)
        trRanking.appendChild(tdRankingTotal)
    })
}





/////____________________________________________ POSTS The Actual Game _____________________________________//////

function postGameViewPage(Game_Id) {
    //console.log(Game_Id)
    fetch(`http://localhost:8080/api/game/${Game_Id}/players`, {
            method: 'POST',
            headers: {
                Accept: "application/json",
                "Content-Type": "application/x-www-form-urlencoded",
                credentials: "include"
            }
        })
        .then(function (response) {
            console.log(response);
            if (response.status == 201) {
                //console.log("game id in games ", Game_Id)/*works*/
                //window.location.href = "game.html?gp=" + Game_Id
                return response.json();
            } else {
                alert("There was an error of 400");
            }
        })
        .then((data) => {
            //console.log(status)
            console.log(data)
        })
        .catch(error => console.log(error));
}


/////____________________________________________________ LogOut ________________________________________________/////

function logOut() {
    fetch('http://localhost:8080/api/logout', {
            method: "post",
            headers: {
                Accept: "application/json",
                "Content-Type": "application/x-www-form-urlencoded"
            },
        })
        .then(function (response) {
            //console.log(response);
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