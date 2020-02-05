/////___________________________________ GET DATA OF GAMES with PLAYERS IN IT _________________________________/////
getData()
async function getData() {
    let response = await fetch('http://localhost:8080/api/games');
    //console.log(response)
    let dataGames = await response.json()
    console.log(dataGames)
    renderGames(dataGames);


}
////______________________________________________ POSTS Games _________________________________________////

function renderGames(dataGames) {
    console.log("all games should be here:", dataGames) /*works right*/
    const gamesList = document.getElementById("games")

    dataGames.forEach(game => {

        //--------------just GAMES with 1Player------------//

        let GAME_Id = game.Game_Id;
        let opponent = game.GamePlayers
        let current = game.Curr_PlayerName
        console.log(opponent)
        if (game.GamePlayers.length === 1 && opponent.Player_Username !== current && game.GamePlayers[0].Player.Player_Username !== current) {

            let divGames = document.createElement("div")
            divGames.setAttribute("class", "game-div alert alert-primary", "role", "alert")

            let thGameNO = document.createElement("th")
            thGameNO.innerHTML = "Game No. " + GAME_Id

            let gameLink = document.createElement("a")
            gameLink.setAttribute("class", "alert-link")
            gameLink.setAttribute("data-GameId", GAME_Id)
            gameLink.innerHTML = "JOIN"
            gameLink.addEventListener("click", function () {

                postOptionalGame(GAME_Id)
                //console.log("this is the gameID", game.Game_Id)/*works*/
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

        //-------------------just GAMES of currentPlayer----------------//

        else if (game.GamePlayers[0].Player.Player_Username === game.Curr_PlayerName || game.GamePlayers[1].Player.Player_Username === game.Curr_PlayerName) {

            let joinedGP_Id = game.GamePlayers[0].GamePlayer_Id
            console.log(joinedGP_Id)

            let divGames = document.createElement("div")
            divGames.setAttribute("class", "game-div alert alert-primary", "role", "alert")

            let thGameNO = document.createElement("th")
            thGameNO.innerHTML = "Game No. " + GAME_Id

            let gameLink = document.createElement("a")
            gameLink.setAttribute("class", "alert-link")
            gameLink.setAttribute("data-GameId", GAME_Id)
            gameLink.innerHTML = "Return"
            gameLink.addEventListener("click", function () {
                
                window.location.href = "game.html?gp=" + JOIN_GP_Id
                
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

/////____________________________________________ POSTS JOIN GAME _____________________________________//////
let JOIN_GP_Id;
function postOptionalGame(GAME_Id) {
    console.log(GAME_Id)
    fetch(`http://localhost:8080/api/game/${GAME_Id}/players`, {
            method: 'POST',
            headers: {
                Accept: "application/json",
                "Content-Type": "application/x-www-form-urlencoded",
                credentials: "include"
            }
        })
        .then(response => {
            console.log(response)
            return response.json()
        })
        .then((res) => {
            JOIN_GP_Id = res.GP_Id
            if (JOIN_GP_Id) {
                window.location.href = "game.html?gp=" + JOIN_GP_Id
            }
        })
        .catch(error => console.log(error))
}

/////____________________________________________________ POST NEW GAME ________________________________________________/////
let NEW_GP_Id
function postNewGame() {
    fetch('http://localhost:8080/api/games', {
            method: 'POST',
            headers: {
                Accept: "application/json",
                "Content-Type": "application/x-www-form-urlencoded",
                credentials: "include"
            },
        })
        .then(function (response) {
            //console.log(response)
            return response.json()
        })
        .then((res) => {
            console.log(res)
            NEW_GP_Id = res.GP_Id
            if (NEW_GP_Id) {
                window.location.href = "game.html?gp=" + NEW_GP_Id
            }
        })
        .catch(error => console.log(error));
}

/////______________________________________________ GET DATA OF Scores __________________________________________/////

getDataRanking()
async function getDataRanking() {
    let response = await fetch('http://localhost:8080/api/leaderboard');
    //console.log(response) /*works*/
    let dataPlayers = await response.json()
    renderRankingPlayers(dataPlayers);
    //console.log(dataPlayers) /*works*/

}
/////------------------ POSTS RANKING (Player with its Total Scores) -----------------/////

function renderRankingPlayers(dataPlayers) {
    console.log("Ranking:", dataPlayers) /*works right*/

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

/////____________________________________________________ LogOut ________________________________________________/////

function logOut() {
    fetch('http://localhost:8080/api/logout', {
            method: 'POST',
            headers: {
                Accept: "application/json",
                "Content-Type": "application/x-www-form-urlencoded"
            },
        })
        .then(function (response) {
            console.log("logged out", response);
            return response.status
        })
        .then((status) => {
            if (status == 200) {
                window.location.href = "manager.html"
            } else {
                alert("Ooh something went wrong..try again!")
            }
        })
        .catch(error => console.log(error));
}