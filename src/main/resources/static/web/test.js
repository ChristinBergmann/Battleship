// // MAKE EMPTY LAYOUT
// var letter = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
// var numbers = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"]

// var spielBrettContainer = document.getElementById("spielBrett");

// var outdiv = document.createElement("div");
// spielBrettContainer.appendChild(outdiv);
// outdiv.id = 'letternumber';


// for (var x = 0; x < 11; x++) {
//     var outsquarerow = document.createElement("div");
//     outdiv.appendChild(outsquarerow);

//     outsquarerow.className = "out";

//     outsquarerow.id = (letter[x]);

//     outsquarerow.innerHTML = (letter[x]);

//     var leftPosition = x * 35;

//     // Positioniertung über CSS mit absolute
//     outsquarerow.style.top = 0 + 'px';
//     outsquarerow.style.left = leftPosition + 'px';

// }

// for (var y = 0; y < 10; y++) {
//     var outsquarecol = document.createElement("div");
//     outdiv.appendChild(outsquarecol);

//     outsquarecol.className = "out1";

//     outsquarecol.id = (numbers[y]);

//     outsquarecol.innerHTML = (numbers[y]);

//     var topPosition = y * 35;

//     // Positioniertung über CSS mit absolute
//     outsquarecol.style.top = topPosition + 35 + 'px';
// }


// var indiv = document.createElement("div");
// spielBrettContainer.appendChild(indiv);
// indiv.id = 'spielBereich';

// var squareArray = [];

// for (var i = 1; i < 11; i++) {
//     for (var j = 0; j < 10; j++) {

//         var square = document.createElement("div");
//         indiv.appendChild(square);

//         squareArray.push(square)


//         square.id = (document.getElementsByClassName("out")[i]).id + (document.getElementsByClassName("out1")[j]).id
//         square.className = "square";



//         var topPosition = j * 35;
//         var leftPosition = (i - 1) * 35;

//         // Positioniertung über CSS mit absolute
//         square.style.top = topPosition + 35 + 'px';
//         square.style.left = leftPosition + 35 + 'px';
//     }
// }

// // second grid - salvos

// var outdiv11 = document.createElement("div");
// spielBrettContainer.appendChild(outdiv11);
// outdiv11.id = 'letternumber1';

// var letter1 = ["", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J"]
// for (var x = 0; x < 11; x++) {
//     var outsquarerow1 = document.createElement("div");
//     outdiv11.appendChild(outsquarerow1);

//     outsquarerow1.className = "out11";

//     outsquarerow1.id = (letter1[x]);

//     outsquarerow1.innerHTML = (letter1[x]);

//     var leftPosition1 = x * 35;

//     // Positioniertung über CSS mit absolute
//     outsquarerow1.style.top = topPosition1 + 'px';
//     outsquarerow1.style.left = leftPosition1 + 'px';

// }
// var numbers1 = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"]
// for (var y = 0; y < 10; y++) {
//     var outsquarecol1 = document.createElement("div");
//     outdiv11.appendChild(outsquarecol1);

//     outsquarecol1.className = "out111";

//     outsquarecol1.id = (numbers1[y]);

//     outsquarecol1.innerHTML = (numbers1[y]);

//     var topPosition1 = y * 35;

//     // Positioniertung über CSS mit absolute
//     outsquarecol1.style.top = topPosition1 + 35 + 'px';
// }


// var indiv1 = document.createElement("div");
// spielBrettContainer.appendChild(indiv1);
// indiv1.id = 'spielBereich1';

// var squareArray1 = [];

// for (var i = 1; i < 11; i++) {
//     for (var j = 0; j < 10; j++) {

//         var square1 = document.createElement("div");
//         indiv1.appendChild(square1);

//         squareArray1.push(square1)


//         square1.id = (document.getElementsByClassName("out11")[i]).id + (document.getElementsByClassName("out111")[j]).id
//         square1.className = "square1";

//         square1.addEventListener("click", mySalvos)

//         var topPosition1 = j * 35;
//         var leftPosition1 = (i - 1) * 35;

//         // Positioniertung über CSS mit absolute
//         square1.style.top = topPosition1 + 735 + 'px';
//         square1.style.left = leftPosition1 + 35 + 'px';
//     }
// }



// // AJAX CALL
// var firedSalvoLocationArray = [];

// let myTurn;

// $(function () {

//     function loadData() {
//         const urlParams = window.location.href;
//         console.log(urlParams)
//         const url = new URL(urlParams);
//         const myToken = url.searchParams.get("gp");
//         console.log(myToken);
//         $.get("/api/game_view/" + myToken)
//             .done(function (data) {
//                 console.log(data)


//                 var myPlayer = "";
//                 var oppositePlayer = "";

//                 function whoPlays() {

//                     if (data.gamePlayer.id == myToken && data.opponent !== undefined) {
//                         myPlayer = data.gamePlayer.player.userName;
//                         myTurn = data.gamePlayer.salvos.length
//                         oppositePlayer = data.opponent.player.userName

//                     } else if (data.gamePlayer.id == myToken && data.opponent == undefined) {
//                         myPlayer = data.gamePlayer.player.userName;
//                         myTurn = data.gamePlayer.salvos.length;
//                     }
//                 }
//                 whoPlays();

//                 console.log(myPlayer, oppositePlayer)
//                 document.getElementById("versus").innerHTML = myPlayer + "(you) vs. " + oppositePlayer;


//                 var myShip;
//                 var myLocation;
//                 var locationArray = [];

//                 var oppositeShip;
//                 var oppositeLocation;
//                 var oppositeLocationArray = [];

//                 var firedSalvo;
//                 var firedSalvoLoc = [];

//                 var recivedSalvo;
//                 var recivedSalvoLoc = [];
//                 var recivedSalvoTurn = [];

//                 function myShips() {
//                     for (let i = 0; i < 5; i++) {

//                         if (data.gamePlayer.id == myToken && data.opponent !== undefined) {
//                             myShip = data.gamePlayer.ships;
//                             myLocation = myShip.map(x => x.locations)

//                             //   oppositeShip = data.opponent.ships;
//                             //    oppositeLocation = oppositeShip.map(x => x.locations)

//                             firedSalvo = data.gamePlayer.salvos;
//                             firedSalvoLoc = firedSalvo.map(x => x.locations);

//                             recivedSalvo = data.opponent.salvos;
//                             recivedSalvoLoc = recivedSalvo.map(x => x.locations)



//                         } else if (data.gamePlayer.id == myToken && data.opponent == undefined) {
//                             myShip = data.gamePlayer.ships;
//                             myLocation = myShip.map(x => x.locations)

//                             firedSalvo = data.gamePlayer.salvos;
//                             firedSalvoLoc = firedSalvo.map(x => x.locations)


//                         }
//                     }
//                 }
//                 myShips();
//                 console.log(myShip);
//                 console.log(myLocation);


//                 //    console.log(oppositeShip);
//                 //    console.log(oppositeLocation);

//                 console.log(firedSalvo);
//                 console.log(firedSalvoLoc);

//                 console.log(recivedSalvo);
//                 console.log(recivedSalvoLoc);




//                 var recivedSalvoLocationArray = [];

//                 function locateShips() {

//                     for (let i = 0; i < 5; i++) {

//                         myLocation.forEach(x => locationArray.push(x[i]))
//                         locationArray = $.grep(locationArray, function (n) {
//                             return n == 0 || n
//                         });

//                         //   oppositeLocation.forEach(x => oppositeLocationArray.push(x[i]))
//                         //    oppositeLocationArray = $.grep(oppositeLocationArray, function (n) {
//                         //                            return n == 0 || n
//                         //                        });

//                         firedSalvoLoc.forEach(x => firedSalvoLocationArray.push(x[i]))
//                         firedSalvoLocationArray = $.grep(firedSalvoLocationArray, function (n) {
//                             return n == 0 || n
//                         });

//                         recivedSalvoLoc.forEach(y => recivedSalvoLocationArray.push(y[i]))
//                         recivedSalvoLocationArray = $.grep(recivedSalvoLocationArray, function (n) {
//                             return n == 0 || n
//                         });

//                     }


//                 }

//                 locateShips();
//                 console.log(locationArray);
//                 //  console.log(oppositeLocationArray);
//                 console.log(firedSalvoLocationArray);
//                 console.log(recivedSalvoLocationArray);


//                 var orangeSquares = [];

//                 function addClass() {

//                     for (let i = 0; i < 100; i++) {

//                         for (let j = 0; j < locationArray.length; j++) {

//                             if (squareArray[i].id == locationArray[j]) {

//                                 squareArray[i].classList.add("blue")



//                             }
//                         }
//                     }
//                 }


//                 addClass();

//                 var greenSquares = [];

//                 function addClassFS() {

//                     for (let i = 0; i < 100; i++) {

//                         for (let j = 0; j < firedSalvoLocationArray.length; j++) {

//                             for (let x = 0; x < firedSalvo.length; x++) {

//                                 if (squareArray1[i].id == firedSalvo[x].locations[j]) {
//                                     squareArray1[i].innerHTML = firedSalvo[x].turn

//                                     greenSquares.push(squareArray1[i]);
//                                     squareArray1[i].classList.add("green");


//                                 }
//                             }
//                         }
//                     }
//                 }

//                 addClassFS();


//                 function addClassFSR() {

//                     for (let i = 0; i < greenSquares.length; i++) {
//                         for (let y = 0; y < oppositeLocationArray.length; y++) {

//                             if (greenSquares[i].id == oppositeLocationArray[y]) {

//                                 greenSquares[i].classList.remove("green");
//                                 greenSquares[i].classList.add("red");

//                             }
//                         }
//                     }
//                 }

//                 addClassFSR();



//                 function addClassRS() {

//                     for (let i = 0; i < 100; i++) {

//                         for (let j = 0; j < recivedSalvoLocationArray.length; j++) {

//                             for (let x = 0; x < recivedSalvo.length; x++) {

//                                 if (squareArray1[i].id == recivedSalvo[x].locations[j]) {
//                                     squareArray[i].innerHTML = recivedSalvo[x].turn

//                                     squareArray[i].classList.remove("blue")
//                                     squareArray[i].classList.add("orange")
//                                     orangeSquares.push(squareArray[i])

//                                 }
//                             }
//                         }
//                     }
//                 }

//                 addClassRS();


//                 function addClassRSR() {

//                     for (let i = 0; i < orangeSquares.length; i++) {
//                         for (let y = 0; y < locationArray.length; y++) {

//                             if (orangeSquares[i].id == locationArray[y]) {

//                                 orangeSquares[i].classList.remove("orange");
//                                 orangeSquares[i].classList.add("red");

//                             }
//                         }
//                     }



//                 }

//                 addClassRSR();





//             })
//             .fail(function (jqXHR, textStatus) {
//                 showOutput("Failed: " + textStatus);
//             });




//     }
//     loadData();
// });






// function allowDrop(ev) {
//     ev.preventDefault();
// }

// function drag(ev) {
//     ev.dataTransfer.setData("text", ev.target.id);
//     console.log(ev.target.id)
// }

// let myShip = null;
// let shipvalue = "";
// let myPosition = "";
// let fullPosition = [];
// let myPlacedShips = {};
// let allplaces = [];
// let myshippos = [];
// let allPosition = [];
// let lastship = [];

// function drop(ev) {

//     Object.values(myPlacedShips).flat();

//     ev.preventDefault();

//     var data = ev.dataTransfer.getData("text");

//     console.log("Data: " + data);

//     console.log("Element ", document.getElementById(data));


//     let targets = ev.target.id;


//     if (document.getElementById(data).classList.contains("horizontal")) {

//         let myNumber = targets.slice(1)
//         let myLetter = targets.slice(0, 1)


//         shipvalue = parseInt((document.getElementById(data)).className);

//         myPosition = letter.indexOf(myLetter);

//         for (let i = 0; i < shipvalue + 1; i++) {

//             myshippos = (letter.slice(myPosition, (myPosition + i)))
//             myshippos = myshippos.map(pos => pos + myNumber)

//         }
//     } else if (document.getElementById(data).classList.contains("vertical")) {

//         let myLetter = targets.slice(0, 1)
//         let myNumber = targets.slice(1)
//         console.log(myNumber)

//         shipvalue = parseInt((document.getElementById(data)).className);


//         let myPosition = numbers.indexOf(myNumber);
//         console.log(myPosition)


//         for (let i = 0; i < shipvalue + 1; i++) {

//             myshippos = (numbers.slice(myPosition, (myPosition + i)))
//             myshippos = myshippos.map(pos => myLetter + pos)


//         }
//     }

//     console.log(myshippos, "my ship positions")
//     myshippos.forEach(sq => console.log(document.getElementById(sq)))




//     if (ev.target.classList.contains("blue") || ev.target.id == myShip || myshippos.some(pos => allPosition.includes(pos))) {

//         alert("Can't place here, other ship!")
//     } else {



//         ev.target.appendChild(document.getElementById(data));

//         myShip = document.getElementById(data).id;

//         console.log("My ship: " + myShip);

//         //Remove classes of last positions if the ship was already placed
//         if (Object.keys(myPlacedShips).includes(myShip)) {
//             myPlacedShips[myShip].forEach(sq => document.getElementById(sq).classList.remove("blue"))

//             allPosition = allPosition.filter(f => !myPlacedShips[myShip].includes(f))

//             console.log(myPlacedShips[myShip])

//         }



//         if (document.getElementById(data).classList.contains("horizontal")) {

//             let myNumber = targets.slice(1)

//             console.log(myNumber)

//             let myLetter = targets.slice(0, 1)


//             shipvalue = parseInt((document.getElementById(data)).className);

//             myPosition = letter.indexOf(myLetter);

//             for (let i = 0; i < shipvalue + 1; i++) {

//                 myshippos = (letter.slice(myPosition, (myPosition + i)))
//                 myshippos = myshippos.map(pos => pos + myNumber)

//             }



//             // myPositions = letter.slice(myPosition, (myPosition + shipvalue));
//             //
//             // fullPosition = myPositions.map(pos => pos + myNumber);

//             //We have to check if the boat fits in the table
//             if (11 - myPosition < shipvalue) {
//                 fullPosition = letter.slice(-shipvalue)
//                     .map(pos => pos + myNumber);
//             } else {
//                 fullPosition = letter.slice(myPosition, (myPosition + shipvalue))
//                     .map(pos => pos + myNumber);
//             }

//         } else if (document.getElementById(data).classList.contains("vertical")) {

//             let myLetter = targets.slice(0, 1)
//             console.log(myLetter)
//             let myNumber = targets.slice(1)
//             console.log(myNumber)

//             shipvalue = parseInt((document.getElementById(data)).className);


//             let myPosition = numbers.indexOf(myNumber);
//             console.log(myPosition)


//             for (let i = 0; i < shipvalue + 1; i++) {

//                 myshippos = (numbers.slice(myPosition, (myPosition + i)))
//                 myshippos = myshippos.map(pos => myLetter + pos)


//             }

//             // myPositions = letter.slice(myPosition, (myPosition + shipvalue));
//             //
//             // fullPosition = myPositions.map(pos => pos + myNumber);

//             //We have to check if the boat fits in the table


//             if (11 - myPosition < shipvalue) {
//                 fullPosition = numbers.slice(-shipvalue)
//                     .map(pos => myLetter + pos);
//             } else {
//                 fullPosition = numbers.slice(myPosition, (myPosition + shipvalue))
//                     .map(pos => myLetter + pos);
//             }





//         }


//         //A more fancy way of the function above
//         // 11 - myPosition < shipvalue
//         //     ? fullPosition = letter.slice(-shipvalue).map(pos => pos + myNumber)
//         //     : fullPosition = letter.slice(myPosition, (myPosition + shipvalue)).map(pos => pos + myNumber);



//         // for (let i=0; i < fullPosition.length; i++){
//         //
//         //     placedSq = squareArray.filter(el => el.id == fullPosition[i])
//         //     placedSq.forEach( sq=> sq.classList.add("blue"))
//         //
//         // }


//         //The same as the function above
//         fullPosition.forEach(sq => document.getElementById(sq).classList.add("blue"));

//         //Save the position of the ship. I will need it on the future if a have to relocate the ship



//         myPlacedShips[myShip] = fullPosition;
//         allPosition.push(...fullPosition)
//         lastship.push(myShip)
//         lastship = [...new Set(lastship)];
//         console.log(lastship);






//         console.log("Targets: " + targets, "Shipvalue: " + shipvalue, "FullPosition: " + fullPosition);



//         console.log(allPosition);



//     }

// }




// console.log(myPlacedShips);



// indiv.ondragover = function () {
//     allowDrop(event)
// };
// indiv.ondrop = function () {
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




// function postShips(type, locations) {
//     try {
//         const urlParams = window.location.href;
//         console.log(urlParams)
//         const url = new URL(urlParams);
//         const myToken = url.searchParams.get("gp");
//         console.log(myToken);
//         const gpId = myToken;

//         let response = fetch(`http://localhost:8080/api/games/players/${gpId}/ships`, {
//             method: 'POST',
//             credentials: 'include',
//             headers: {
//                 'Content-Type': 'application/json',
//                 'Accept': 'application/json'
//             },



//             body: JSON.stringify([{
//                 type,
//                 locations
//             }])



//         });
//         if (response.status === 201) {
//             console.log("post good")
//         } else if (response.status === 403) {} else {}
//     } catch (error) {
//         console.log("Error: ", error)
//     }
// }


// function allShips() {

//     if (lastship.length == 5) {

//         Object.keys(myPlacedShips).forEach(function (myShip) {

//             postShips(myShip, myPlacedShips[myShip])

//         });
//         alert("Ships successfully placed!")

//         document.getElementById('battleship').setAttribute('draggable', false)
//         document.getElementById('patrol').setAttribute('draggable', false)
//         document.getElementById('destroyer').setAttribute('draggable', false)
//         document.getElementById('carrier').setAttribute('draggable', false)
//         document.getElementById('submarine').setAttribute('draggable', false)


//     } else {

//         alert("Not all ships have been placed!")

//     }

// }

// let firingSalvos = [];

// function mySalvos() {

//     if (firingSalvos.length < 5) {

//         if (this.classList.contains("selected")) {


//             this.classList.remove("selected")

//             firingSalvos = firingSalvos.filter(el => el != this.id)

//         } else if (this.classList.contains("green")) {


//             alert("already fired this location!")

//         } else if (this.classList.contains("red")) {


//             alert("already fired this location!")

//         } else {

//             this.classList.add("selected")
//             firingSalvos.push(this.id)
//         }

//     } else if (firingSalvos.length >= 5) {

//         if (this.classList.contains("selected")) {


//             this.classList.remove("selected")
//             firingSalvos = firingSalvos.filter(el => el != this.id)

//         }
//     } else {

//         alert("already 5 shots for this salvo!")
//     }

// }


// console.log(firingSalvos);

// let mySavedSalvos = [];




// function postSalvos(turn, locations) {

//     if (firingSalvos.length > 0) {
//         firingSalvos.forEach(salvo => mySavedSalvos.push(salvo));




//         try {
//             const urlParams = window.location.href;
//             console.log(urlParams)
//             const url = new URL(urlParams);
//             const myToken = url.searchParams.get("gp");
//             console.log(myToken);
//             const gpId = myToken;

//             let response = fetch(`http://localhost:8080/api/games/players/${gpId}/salvos`, {
//                 method: 'POST',
//                 credentials: 'include',
//                 headers: {
//                     'Content-Type': 'application/json',
//                     'Accept': 'application/json'
//                 },



//                 body: JSON.stringify({
//                     turn: myTurn + 1,
//                     locations: firingSalvos
//                 })



//             })

//             ;
//             if (response.status === 201) {
//                 console.log("post good")
//             } else if (response.status === 403) {} else {
//                 alert("Salvo saved!")
//                 firingSalvos = [];
//                 location.reload();
//             }
//         } catch (error) {
//             console.log("Error: ", error)
//         }
//     } else {

//         alert("You need to choose a location to fire a salvo!")
//     }
// }