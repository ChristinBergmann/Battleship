////____________________________________________ CREATES FIRST Board (Ships) _______________________________________////
const boardContainer = document.getElementById("boards");

let divOutside = document.createElement("div");
boardContainer.appendChild(divOutside);
divOutside.id = 'numbersletters';

//------Letters Div-----//
let letters = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
for (let x = 0; x < 11; x++) {
    let lettersRow = document.createElement("div");
    divOutside.appendChild(lettersRow);

    lettersRow.className = "outrow";
    lettersRow.id = (letters[x]);
    lettersRow.innerHTML = (letters[x]);

    let leftPosition = x * 35;

    lettersRow.style.top = 0 + 'px';
    lettersRow.style.left = leftPosition + 'px';
}

//------Numbers Div-----//
let numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"]
for (let y = 0; y < 10; y++) {
    let numbersCol = document.createElement("div");
    divOutside.appendChild(numbersCol);

    numbersCol.className = "outcol";
    numbersCol.id = (numbers[y]);
    numbersCol.innerHTML = (numbers[y]);

    let topPosition = y * 35;

    numbersCol.style.top = topPosition + 35 + 'px';
}

//-------Squares Field-----//
let divInside = document.createElement("div");
boardContainer.appendChild(divInside);
divInside.id = 'boardgame';

let fieldsArray = [];

for (let i = 1; i < 11; i++) {
    for (let j = 0; j < 10; j++) {

        let field = document.createElement("div");
        divInside.appendChild(field);

        fieldsArray.push(field)

        field.id = (document.getElementsByClassName("outrow")[i]).id + (document.getElementsByClassName("outcol")[j]).id
        field.className = "field";

        let topPosition = j * 35;
        let leftPosition = (i - 1) * 35;

        field.style.top = topPosition + 35 + 'px';
        field.style.left = leftPosition + 35 + 'px';
    }
}

////____________________________________________ CREATES SECOND Board(Shots) _______________________________________////

let divOutside2 = document.createElement("div");
boardContainer.appendChild(divOutside2);
divOutside2.id = 'numbersletters2';

//------Letters Div-----//
let letters2 = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
for (let x = 0; x < 11; x++) {
    let lettersRow2 = document.createElement("div");
    divOutside2.appendChild(lettersRow2);

    lettersRow2.className = "outrow2";
    lettersRow2.id = (letters2[x]);
    lettersRow2.innerHTML = (letters2[x]);

    let leftPosition2 = x * 35;

    lettersRow2.style.top = 0 + 'px';
    lettersRow2.style.left = leftPosition2 + 'px';
}

//------Numbers Div-----//
let numbers2 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"]
for (let y = 0; y < 10; y++) {
    let numbersCol2 = document.createElement("div");
    divOutside2.appendChild(numbersCol2);

    numbersCol2.className = "outcol2";
    numbersCol2.id = (numbers2[y]);
    numbersCol2.innerHTML = (numbers2[y]);

    let topPosition2 = y * 35;

    numbersCol2.style.top = topPosition2 + 35 + 'px';
}

//-------Squares Field-----//
let divInside2 = document.createElement("div");
boardContainer.appendChild(divInside2);
divInside2.id = 'boardgame2';

let fieldsArray2 = [];
for (let i = 1; i < 11; i++) {
    for (let j = 0; j < 10; j++) {

        let field2 = document.createElement("div");
        divInside2.appendChild(field2);

        fieldsArray2.push(field2)

        field2.id = (document.getElementsByClassName("outrow2")[i]).id + (document.getElementsByClassName("outcol2")[j]).id
        field2.className = "field2";
        field2.addEventListener("click", myPlacedShots)

        let topPosition2 = j * 35;
        let leftPosition2 = (i - 1) * 35;

        field2.style.top = topPosition2 + 735 + 'px';
        field2.style.left = leftPosition2 + 735 + 'px';
    }
}
divInside.ondragover = function () {
    allowDrop(event);
};
divInside.ondrop = function () {
    drop(event);
};

///___________________________________________________________ get DATA of LOGGED IN GAMEPLAYER  __________________________________________________///

const urlParams = new URLSearchParams(window.location.search);
const myParam = window.location.search.split("=")[1]

getData()
async function getData() {
    console.log("I am working", myParam)
    let response = await fetch(`http://localhost:8080/api/game_view/${myParam}`);
    console.log(response);
    let data = await response.json()
    console.log(data)
    renderGamePlayerInfos(data)

}
let myTurn;
console.log(myTurn)

function renderGamePlayerInfos(data) {
    console.log(data)
    myTurn = data.Curr_turns
    console.log(myTurn)
    console.log(data.Curr_shots)
    /////--------------------------------- Displays VERSUS Board ------------------------------/////
    const versusDiv = document.getElementById("versus")
    let h3 = document.createElement("h3")
    console.log(data.Curr_name)
    if (data.Opp_name === undefined) {
        h3.innerHTML = data.Curr_name + " vs. " + " ¿? "
    } else
        h3.innerHTML = data.Curr_name + " vs. " + data.Opp_name;

    versusDiv.appendChild(h3)

    /////------------------- Displays SCORES of both GPs in the between Boards ---------------///// 
    const scoreVersus = document.getElementById("score")
    let scoreH3 = document.createElement("h3")

    if (data.Curr_score === undefined || data.Opp_score === undefined) {
        scoreH3.innerHTML = " / "
    } else
        scoreH3.innerHTML = data.Curr_score + " : " + data.Opp_score;

    scoreVersus.appendChild(scoreH3)

    /////--------------------- Displays SHIPS of the GP in the Ships Board -------------------/////
    let locationArray = [];
    data.Curr_ships.forEach(ship => {
        locationArray.push(ship.Location)
    });
    locationArray.forEach(x => {
        console.log(x)
        x.forEach(sh => {
            document.getElementById(sh).classList.add("boat")
        })

    })
    /////------------------- Displays SHOTS of the GP in the Shots Board -------------------/////
    data.Curr_shots.forEach(y => {
        console.log(y)
        document.getElementById(y).classList.add("bomb")
    })


    // /////----------------- Displays HITS by OpponentGP in the Ships Board -----------------////// 
    // let hitsArray = [];
    // data.Curr_hits.forEach(hit => {
    //     hitsArray.push(hit.Location)
    // });
    //hitsArray.forEach(z => {
    //console.log(z)
    data.Curr_hits.forEach(h => {
        console.log(h)
        document.getElementById(h).classList.add("fire")
    })

}

/////--------------- GAME RESULTS ------------------/////

// function getGameResult(data) {

//     let score1 = data.Curr_hits;
//     let score2 = data.Opp_hits;

//     return (score1 > score2) ? 'You win!' :
//         (score1 < score2) ? 'You lost!' :
//         'It was a tie!';
// }


// function back() {

//     window.location.href = "games.html"

// }

/////________________________________________________________ PLACE SHIPS DRAG n DROP __________________________________________________________/////

function drag(ev) {
    ev.dataTransfer.setData("text/plain", ev.target.id);
    console.log("EVENT ID", ev.target.id) /*works*/
    //ev.currentTarget.style.backgroundColor = "lightblue";
}

function allowDrop(ev) {
    ev.preventDefault();
}

let myShip = null;
let shipValue = "";
let myPosition = "";
let fullPosition = [];
let placedShips = {};
let allPlaces = [];
let myShips = [];
let allPosition = [];
let lastShip = [];

function drop(ev) {
    Object.values(placedShips).flat();
    ev.dataTransfer.clearData();

    let data = ev.dataTransfer.getData("text");
    let draggableElement = document.getElementById(data);
    let target = ev.target;

    //*------get the positions in board------*//
    if (draggableElement.classList.contains("horizontal")) {
        let myNumber = target.id.slice(1)
        let myLetter = target.id.slice(0, 1)

        shipValue = parseInt(draggableElement.className);
        myPosition = letters.indexOf(myLetter);

        for (let i = 0; i < shipValue + 1; i++) {
            myShips = (letters.slice(myPosition, (myPosition + i)))
            myShips = myShips.map(pos => pos + myNumber)
        }
    } else if (draggableElement.classList.contains("vertical")) {
        let myLetter = target.id.slice(0, 1)
        let myNumber = target.id.slice(1)
        shipValue = parseInt(draggableElement.className);

        let myPosition = numbers.indexOf(myNumber);
        for (let i = 0; i < shipValue + 1; i++) {
            myShips = (numbers.slice(myPosition, (myPosition + i)))
            myShips = myShips.map(pos => myLetter + pos)
        }
    }
    //*------get positions/locations of ships in board------*//
    console.log(myShips)
    if (myShips.some(pos => allPosition.includes(pos))) {
        alert("NO SPACE, take another ship please!")
    } else {
        myShips.forEach(x => document.getElementById(x).classList.add("boat"))

        target.appendChild(draggableElement);

        myShip = draggableElement.id
        if (draggableElement.classList.contains("horizontal")) {
            let myNumber = target.id.slice(1)
            let myLetter = target.id.slice(0, 1)

            shipValue = parseInt(draggableElement.className);
            myPosition = letters.indexOf(myLetter);

            for (let i = 0; i < shipValue + 1; i++) {
                myShips = (letters.slice(myPosition, (myPosition + i)))
                myShips = myShips.map(pos => pos + myNumber)
            }
            //*--checks if ships fit in the board--*//
            if (11 - myPosition < shipValue) {
                fullPosition = letters.slice(-shipValue)
                    .map(pos => pos + myNumber);
            } else {
                fullPosition = letters.slice(myPosition, (myPosition + shipValue))
                    .map(pos => pos + myNumber);
            }
        } else if (draggableElement.classList.contains("vertical")) {
            let myLetter = target.id.slice(0, 1)
            let myNumber = target.id.slice(1)

            shipValue = parseInt(draggableElement.className);
            let myPosition = numbers.indexOf(myNumber);
            for (let i = 0; i < shipValue + 1; i++) {
                myShips = (numbers.slice(myPosition, (myPosition + i)))
                myShips = myShips.map(pos => myLetter + pos)
            }
            //*--checks if ships fit in the board--*//
            if (11 - myPosition < shipValue) {
                fullPosition = numbers.slice(-shipValue)
                    .map(pos => myLetter + pos);
            } else {
                fullPosition = numbers.slice(myPosition, (myPosition + shipValue))
                    .map(pos => myLetter + pos);
            }
        }
        placedShips[myShip] = fullPosition;
        fullPosition.forEach(x => allPosition.push(x));
        lastShip.push(myShip)
        console.log(lastShip);
        // console.log(allPosition);
    }
}

function vertical(el) {

    if (el.classList.contains("horizontal")) {
        el.classList.remove("horizontal");
        el.classList.add("vertical");
    } else if (el.classList.contains("vertical")) {
        el.classList.remove("vertical")
        el.classList.add("horizontal")
    }
}

/////____________________________________ SAVE SHIPS _________________________________/////

function saveShips() {

    if (lastShip.length == 5 && allPosition.length == 17) {
        Object.keys(placedShips).forEach(function (myShip) {
            console.log(myShip)
            console.log(placedShips)
            postShips(myShip, placedShips[myShip])
        });
        alert("Yay, your ships are saved!")

        document.getElementById('PATROLBOAT').setAttribute('draggable', false)
        document.getElementById('BATTLESHIP').setAttribute('draggable', false)
        document.getElementById('SUBMARINE').setAttribute('draggable', false)
        document.getElementById('CARRIER').setAttribute('draggable', false)
        document.getElementById('DESTROYER').setAttribute('draggable', false)

    } else {
        alert("Not all ships have been placed!")
    }
}

//////________________________________________________ POST SHIPS ________________________________//////////////

function postShips(type, locations) {
    try {
        const urlParam = window.location.href;
        console.log(urlParam)
        const url = new URL(urlParam);
        const id = url.searchParams.get("gm");
        console.log(id);

        let response = fetch(`http://localhost:8080/api/players/${id}/ships`, {
            method: 'POST',
            headers: {
                Accept: 'application/json',
                "Content-Type": "application/json",
                credentials: 'include',
            },
            body: JSON.stringify([{
                type,
                locations,
            }])
        });
        if (response.status === 201) {
            console.log("works")
        } else if (response.status === 403) {} else {}
    } catch (error) {
        console.log("Error: ", error)
    }
}


/////____________________________________ PLACE SHOTS _________________________________/////

let myShots = [];

function myPlacedShots() {
    // Object.values().flat();

    if (myShots.length < 3) {
        console.log(myShots)

        if (this.classList.contains("selected")) {
            this.classList.remove("selected")
            //this.classList.add("bomb")
            myShots = myShots.filter(el => el != this.id)
            console.log(myShots)
        } else if (this.classList.contains("bomb") || this.classList.contains("fire")) {
            alert("already fired this location!")
        } else {
            this.classList.add("bomb")
            myShots.push(this.id)
        }
    } else if (myShots.length > 3) {
        if (this.classList.contains("selected")) {
            this.classList.remove("selected")

            myShots = myShots.filter(el => el != this.id)
            //this.classList.add("bomb")

        }
    } else {
        alert("already 3 shots for this round!")
    }
}
console.log(myShots);

/////____________________________________ SAVE SHOTS _________________________________/////

let mySavedShots = [];

function saveShots() {
    myShots.forEach(shot => mySavedShots.push(shot));
    if (myShots.length == 3) {
        Object.keys(mySavedShots).forEach(function (myShots) {
            console.log(myShots)
            console.log(mySavedShots[myShots]) /*works till here*/
            postShots(myShots, mySavedShots[myShots])
        });
        alert("Yay, your shots are saved!")
    } else {
        alert("Not all shots have been placed!")
    }
}

/////_________________________________________________ POST SHOTS _______________________________________________/////

function postShots() {
    try {
        const urlParam = window.location.href;
        console.log(urlParam)
        const url = new URL(urlParam);
        const id = url.searchParams.get("gm");
        console.log(id);

        let response = fetch(`http://localhost:8080/api/players/${id}/shots`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json;charset=UTF-8',
                'Content-type': 'application/json;charset=UTF-8',
                credentials: 'include'
            },
            body: JSON.stringify([{
                turn: this.myTurn + 1,
                //turn,
                //locations
                locations: mySavedShots
            }])
        });
        if (response.status === 201) {
            console.log(response)
            console.log("works")
        } else if (response.status === 403) {} else {
            myShots = [];
            location.reload();
        }
    } catch (error) {
        console.log("Error: ", error)
    }
}