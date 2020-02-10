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
        //field.onDragOver(ev);

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


        field2.id = 'f2' + (document.getElementsByClassName("outrow2")[i]).id + (document.getElementsByClassName("outcol2")[j]).id
        field2.className = "field2";

        let topPosition2 = j * 35;
        let leftPosition2 = (i - 1) * 35;

        field2.style.top = topPosition2 + 735 + 'px';
        field2.style.left = leftPosition2 + 735 + 'px';

    }
}

///____________________________________________________________ get DATA of LOGGED IN GAMEPLAYER  ____________________________________________________///

const urlParams = new URLSearchParams(window.location.search);
const myParam = window.location.search.split("=")[1]
//console.log("this is my Param", myParam)


getData()
async function getData() {
    console.log("I am working")
    console.log(myParam)
    let response = await fetch(`http://localhost:8080/api/game_view/${myParam}`);
    console.log(response); /*works*/
    let data = await response.json() /*works*/
    console.log(data);
    renderGamePlayerInfos(data)
}

function renderGamePlayerInfos(data) {
    console.log(data)


    /////--------------------------------- Displays VERSUS Board ------------------------------/////
    const versusDiv = document.getElementById("versus")
    let h3 = document.createElement("h3")

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

    let myLocationArray = [];

    data.Curr_ships.forEach(ship => {
        myLocationArray.push(ship.Location);
    })

    myLocationArray.forEach(x => {
        if (x.length == 2) {
            x.forEach(y => {
                document.getElementById(y).style.backgroundSize = "33px 33px";
                document.getElementById(y).style.backgroundImage = "url('Images/battleship.png')";
            })
        }
        if (x.length == 3) {
            x.forEach(y => {
                document.getElementById(y).style.backgroundSize = "33px 33px";
                document.getElementById(y).style.backgroundImage = "url('Images/battleship.png')";
            })
        }
        if (x.length == 4) {
            x.forEach(y => {
                document.getElementById(y).style.backgroundSize = "33px 33px";
                document.getElementById(y).style.backgroundImage = "url('Images/battleship.png')";
            })
        }
        if (x.length == 5) {
            x.forEach(y => {
                document.getElementById(y).style.backgroundSize = "33px 33px";
                document.getElementById(y).style.backgroundImage = "url('Images/battleship.png')";
            })
        }
    })

    /////------------------- Displays SHOTS of the GP in the Shots Board -------------------/////

    data.Curr_shots.forEach(s => {
        document.getElementById("f2" + s).style.backgroundSize = "33px 33px";
        document.getElementById("f2" + s).style.backgroundImage = "url('Images/bomb.png')";
    })


    // /////----------------- Displays HITS by OpponentGP in the Ships Board -----------------////// 

    //console.log(dataPlayer.Hits_mine)

    data.Curr_hits.forEach(s => {
        document.getElementById(s).style.backgroundSize = "33px 33px";
        document.getElementById(s).style.backgroundImage = "url('Images/explosion.png')";
    })
}


/////--------------- GAME RESULTS ------------------/////

// function getGameResult(score1, score2) {
//   return (score1 > score2) ? 'You win!' 
//     : (score1 < score2) ? 'You lost!'
//     : 'It was a tie!';
// }


// function back() {

//     window.location.href = "games.html"

// }

/////------------------------------- Displays SHIPS (in the Board) DRAG n DROP ---------------------------/////

function allowDrop(ev) {
    ev.preventDefault();
};

function drag(ev) {
    ev.dataTransfer.setData("text/plain", ev.target.id);
    console.log("EVENT ID", ev.target.id) /*works*/
    ev.currentTarget.style.backgroundColor = "lightblue";

    setTimeout(function () {
        document.getElementsByClassName(".4 horizontal").fadeIn(500);
    }, 8800);
    document.getElementsByClassName(".field").fadeIn(0);
}

function onDragOver(ev) {
    ev.preventDefault();
}

function drop(ev) {
    alert("dosomethingondrop");

    //take my ships array compare if the drag item has the same location in it already so you cant place it
    //to place a ship loop through length and check if it can be placed
    //else : place ship to fieldsArray
    Object.values(mylocationArray).flat();
    ev.preventDefault();

    let data = ev.dataTransfer.getData("text");
    console.log("Data: " + data);
    console.log("Element ", document.getElementById(data));

    let targetsDragID = ev.target.id;

}

//  $(document).ready(function () {
//      $(".vhstwo").one("load", function (e) {
//          var el = $(this);
//          $(function () {
//              el.fadeIn(500);
//          });
//      }).each(function () {
//          if (this.complete) $(this).load();
//      });
//      //Custom Ghost Image 
//      document.getElementById("tape").addEventListener("dragstart", function (e) {
//          e.dataTransfer.setDragImage(img, 0, 200);
//      }, false);
//      var img = document.createElement("img");
//      img.src = "https://i.imgur.com/FLqpRMq.png";

//  });




// function allowDrop(ev) {
//     ev.preventDefault();
// }

// function drag(ev) {
//     ev.dataTransfer.setData("text", ev.target.id);
//     console.log("EVENT ID", ev.target.id)
// }

// let myShip = null;
// let shipValue = "";
// let myPosition = "";
// let fullPosition = [];
// let myPlacedShips = {};
// let allPlaces = [];
// let myShips = [];
// let allPosition = [];
// let lastShip = [];

// function drop(ev) {

//     Object.values(myPlacedShips).flat();
//     ev.preventDefault();

//     let data = ev.dataTransfer.getData("text");
//     console.log("Data: " + data);
//     console.log("Element ", document.getElementById(data));

//     let targets = ev.target.id;

//     if (document.getElementById(data).classList.contains("horizontal")) {
//         let myNumber = targets.slice(1)
//         let myLetter = targets.slice(0, 1)

//         shipValue = parseInt((document.getElementById(data)).className);
//         myPosition = letter.indexOf(myLetter);

//         for (let i = 0; i < shipvalue + 1; i++) {
//             myShips = (letter.slice(myPosition, (myPosition + i)))
//             myShips = myShips.map(pos => pos + myNumber)
//         }
//     } else if (document.getElementById(data).classList.contains("vertical")) {
//         let myLetter = targets.slice(0, 1)
//         let myNumber = targets.slice(1)
//         console.log(myNumber)

//         shipValue = parseInt((document.getElementById(data)).className);

//         let myPosition = numbers.indexOf(myNumber);
//         console.log(myPosition)

//         for (let i = 0; i < shipvalue + 1; i++) {
//             myShips = (numbers.slice(myPosition, (myPosition + i)))
//             myShips = myShips.map(pos => myLetter + pos)
//         }
//     }
//     console.log(myShips, "my ship positions")
//     myShips.forEach(sq => console.log(document.getElementById(sq)))
//     // if (ev.target.classList.contains('Images/battleship.png') || ev.target.id == myShip || myShips.some(pos => allPosition.includes(pos))) {
//     if (ev.target.classList.contains("blue") || ev.target.id == myShip || myShips.some(pos => allPosition.includes(pos))) {

//         alert("Can't place here, other ship!")
//     } else {
//         ev.target.appendChild(document.getElementById(data));
//         myShip = document.getElementById(data).id;
//         console.log("My ship: " + myShip);

//         //-------Removes classes of last positions if the ship was already placed-----//
//         if (Object.keys(myPlacedShips).includes(myShip)) {
//             // myPlacedShips[myShip].forEach(sq => document.getElementById(sq).classList.remove('Images/battleship.png'))
//             myPlacedShips[myShip].forEach(sq => document.getElementById(sq).classList.remove("blue"))
//             allPosition = allPosition.filter(f => !myPlacedShips[myShip].includes(f))
//             console.log(myPlacedShips[myShip])

//         }
//         if (document.getElementById(data).classList.contains("horizontal")) {

//             let myNumber = targets.slice(1)
//             console.log(myNumber)
//             let myLetter = targets.slice(0, 1)

//             shipValue = parseInt((document.getElementById(data)).className);
//             myPosition = letter.indexOf(myLetter);

//             for (let i = 0; i < shipvalue + 1; i++) {
//                 myShips = (letter.slice(myPosition, (myPosition + i)))
//                 myShips = myShips.map(pos => pos + myNumber)
//             }

//             if (11 - myPosition < shipValue) {
//                 fullPosition = letter.slice(-shipValue)
//                     .map(pos => pos + myNumber);
//             } else {
//                 fullPosition = letter.slice(myPosition, (myPosition + shipValue))
//                     .map(pos => pos + myNumber);
//             }

//         } else if (document.getElementById(data).classList.contains("vertical")) {
//             let myLetter = targets.slice(0, 1)
//             console.log(myLetter)
//             let myNumber = targets.slice(1)
//             console.log(myNumber)

//             shipValue = parseInt((document.getElementById(data)).className);

//             let myPosition = numbers.indexOf(myNumber);
//             console.log(myPosition)

//             for (let i = 0; i < shipvalue + 1; i++) {
//                 myShips = (numbers.slice(myPosition, (myPosition + i)))
//                 myShips = myShips.map(pos => myLetter + pos)
//             }
//             if (11 - myPosition < shipValue) {
//                 fullPosition = numbers.slice(-shipValue)
//                     .map(pos => myLetter + pos);
//             } else {
//                 fullPosition = numbers.slice(myPosition, (myPosition + shipValue))
//                     .map(pos => myLetter + pos);
//             }
//         }
//         myPlacedShips[myShip] = fullPosition;
//         allPosition.push(...fullPosition)
//         lastShip.push(myShip)
//         lastShip = [...new Set(lastShip)];
//         console.log(lastShip);
//         console.log(allPosition);
//     }
// }

// divInside.ondragover = function () {
//     allowDrop(event)
// };
// divInside.ondrop = function () {
//     drop(event)
// };

// function vertical(el) {

//     if (el.classList.contains("horizontal")) {
//         el.classList.remove("horizontal");
//         el.classList.add("vertical");
//     } else if (el.classList.contains("vertical")) {
//         el.classList.remove("vertical")
//         el.classList.add("horizontal")
//     }
//     console.log(el.classList)
// }