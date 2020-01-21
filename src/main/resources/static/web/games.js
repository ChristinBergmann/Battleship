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

/////------------------------------- GET DATA OF Scores --------------------/////
async function getDataRanking() {

    let response = await fetch('http://localhost:8080/api/leaderboard');
    console.log(response);
    let dataPlayers = await response.json()
    return dataPlayers;
}
getDataRanking()
    .then(dataPlayers => {
        console.log(dataPlayers)

        let tableHead = document.getElementById("rankingTable");
        let tr = document.getElementById("tr")

        dataPlayers.forEach(x => {
            let name = document.getElementById("name")
            let win = document.getElementById("won")
            let lost = document.getElementById("lost")
            let tie = document.getElementById("tie")
            let total = document.getElementById("total")

            if (x.length > 0) {
                name.innerHTML = x.Player
                win.innerHTML = x.Scores.Win
                lost.innerHTML = x.Scores.Lost
                tie.innerHTML = x.Scores.Tie
                total.innerHTML = x.Scores.Total
            } else {
                name.innerHTML = " / "
                win.innerHTML = " / "
                lost.innerHTML = " / "
                tie.innerHTML = " / "
                total.innerHTML = " / "
            }

            tableHead.appendChild(tr)
            tr.appendChild(name)
            tr.appendChild(win)
            tr.appendChild(lost)
            tr.appendChild(tie)
            tr.appendChild(total)
        })

        /////------------------------------- GET SUM of Total Scores --------------------/////

        let totalAll = [];

        dataPlayers.forEach(x => {
            totalAll.push(x.Scores.Total)
        })
        let scoreSum = totalAll.reduce(function (a, b) {
            return a + b;
        }, 0);

        console.log(scoreSum);
        // Math.sum(totalAll);

        // let rankingList = document.getElementById("ranking")
        // rankingList.createElement("p")
        // rankingList.innerHTML = rankings;

    })
    .catch(error => console.log(error));