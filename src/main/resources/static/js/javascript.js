// TODO Text font krimpen wanneer langer dan display width

let a = "";
let b = "";
let num = [];
let countUnmatchedOpenParenthesis = 0;
let separatorPossible = true;
let separators = getNumberSeparators();
let prohibited = String.fromCodePoint(0x1F6AB)
let exclamation = String.fromCodePoint(0x2757)


// MaxLength vastgesteld omdat het anders niet meer zichtbaar is in het scherm. Poging tot autoshrink (zie jquery) werkt
// helaas niet wanneer text wordt geset door JS.
const maxLength = 26;
const operators = ["+", "-", "*", "/", "^", "^2"];
const operands = ["1", "2", "3", "4", "5", "6", "7", "8", "9", "0"];
const negative = "_";
const openParenthesis = "(";
const isDecimalSeparator = e => e == ".";
const isOpenParenthesis = e => e == "(";
const isCloseParenthesis = e => e == ")";
const isSquare = e => e == "^2";
const isPower = e => e == "^";
const isNegative = e => e == "_";
const isOperator = e => operators.includes(e);
const isOperand = e => operands.includes(e);


// Alle cijfers en operators worden opgeslagen in een array "num"
function sendNum(digit){

    // Om te voorkomen dat er meer haakjes sluiten dan haakjes openen zijn
    let countOpen = num.filter(x => isOpenParenthesis(x)).length;
    let countClose = num.filter(x => isCloseParenthesis(x)).length;
    countUnmatchedOpenParenthesis = countOpen - countClose;

    // Indien invoer aan volgende eisen voldoet, dan niets doen
    // Expressie mag niet beginnen met operator, decimaalteken, of haakje sluiten
    if(!(num.length == 0 && (isOperator(digit) || isDecimalSeparator(digit) || isCloseParenthesis(digit)))){

        // Geen 2 operators na elkaar       of
        if(!((isOperator(num.peek()) && isOperator(digit)) ||

            // Alleen operand na decimaalteken     of
            (isDecimalSeparator(num.peek()) && !isOperand(digit)) ||

            // Alleen negatiefteken of operand na negatiefteken     of
            (isNegative(num.peek()) && !isOperand(digit) && !isNegative(digit)) ||

            // Niet delen door 0        of
            ((num.peek() == "0" && num[num.length -2] == "/") && (isOperator(digit) || isCloseParenthesis(digit))) ||

            // Na vorige decimaalteken moet minstens 1 operator komen       of
            (isDecimalSeparator(digit) && !separatorPossible) ||

            // Geen operator, haakje sluiten of decimaalteken na haakje openen      of
            (isOpenParenthesis(num.peek()) && (isOperator(digit) || isCloseParenthesis(digit) ||
            isDecimalSeparator(digit))) ||

            // Alleen operator of haakje sluiten na haakje sluiten      of
            (isCloseParenthesis(num.peek()) && !isCloseParenthesis(digit) && !isOperator(digit)) ||

            // Geen haakje sluiten of decimaalteken na operator     of
            (isOperator(num.peek()) && (isCloseParenthesis(digit) || isDecimalSeparator(digit))) ||

            // Geen of haakje openen na operand
            (isOperand(num.peek()) && isOpenParenthesis(digit))  ||

            // Alleen haakje sluiten als er een haakje openen tegenover staat
            (countUnmatchedOpenParenthesis <= 0 && isCloseParenthesis(digit)))){

                if(num.length < maxLength) {
                    // Indien kwadraat, operator en 2 los van elkaar toevoegen aan array "num" (i.v.m. backspace button)
                    if(isSquare(digit)){
                        num.push("^");
                        num.push("2");

                    // Indien negatief/positief knop en scherm is niet leeg, handleNegative uitvoeren
                    } else if(isNegative(digit) && num.length != 0){
                        handleNegative(num);

                    // Anders invoer toevoegen aan array "num"
                    } else {
                    num.push(digit);
                    }
                }

                // Na pushen van digit, opnieuw balans haakjes vaststellen
                let countOpen = num.filter(x => isOpenParenthesis(x)).length;
                let countClose = num.filter(x => isCloseParenthesis(x)).length;
                countUnmatchedOpenParenthesis = countOpen - countClose;

                // Instellen of invoer decimaalteken toegestaan is
                if(isDecimalSeparator(num.peek())){
                separatorPossible = false;
                } else if(isOperator(num.peek()) && !(isPower(num.peek()))){
                separatorPossible = true;
                } else if(isPower(num.peek())){
                separatorPossible = false;
                }
        }
    }

	if(num.length != 0){
		a = "";
		document.getElementById("inputDisplay").value = a;		// scherm is leeg
		b = "";
		document.getElementById("infixExpression").value = b;
	}

    // Maak string a van alle elementen in "num"
	for(i=0; i<num.length; i++){

	    // Voor betere leesweergave, * door x vervangen
	    if(num[i] == "*") {
            a = a + "x";

        // Voor betere leesweergave, _ door - vervangen
        } else if(isNegative(num[i])){
            a = a + "-";

        // Indien decimaalteken, haal locale versie op
        } else if(isDecimalSeparator(num[i])){
            a = a + separators.decimal;
        } else {
		    a = a + num[i];
	    }
	}

	a = addThousandSeparator(a);

	// Toon ingevoerde waarde in scherm
    document.getElementById("inputDisplay").value = a;

    // Letterlijke string als invoer controller
    for(i=0; i<num.length; i++){
       b = b + num[i];
    }
    document.getElementById("infixExpression").value = b;
}


// Wordt aangeroepen bij "C" button
function clearScr(){
	document.getElementById("inputDisplay").value = "";
	
	while(num.length > 0){
    	num.pop();				// array "num" leegmaken
	}
	a ="";
}


// Wordt aangeroepen bij backspace button
function backSpace(){
    num.pop();
    let value = document.getElementById("inputDisplay").value;
    document.getElementById("inputDisplay").value = value.substr(0, value.length - 1);
}


// Kijkt of er geen missende haakjes sluiten zijn, dat er gedeeld wordt door 0, of dat expressie eindigt met operator
// Geprobeerd om alles zonder woorden duidelijk te maken, zodat je geen vertalingen hoeft in te voeren
function validateForm() {
        let countOpen = num.filter(x => isOpenParenthesis(x)).length;
        let countClose = num.filter(x => isCloseParenthesis(x)).length;

        if (countUnmatchedOpenParenthesis > 0) {
        alert('"(" > ")" ');
            return false;
        } else if((num.peek() == "0" && num[num.length -2] == "/")){
        alert(prohibited + " /0")
            return false;
        } else if(isOperator(num.peek())) {
        alert(exclamation + " " + operands.toString())
            return false;
      }
}


Array.prototype.peek = function () {
    if(this.length > 0){
    return this[this.length - 1];}
 };

function handleNegative(array) {
    // Haal index van negatiefteken op
    let negativeIndex = array.lastIndexOf(negative);

    // Haal index van laatste haakje openen op (indien aanwezig)
    let openParIndex = array.lastIndexOf(openParenthesis);

    // Kijk welke operators in array staan
    let operatorsInArray = array.filter(x => isOperator(x));

    // Haal index van laatste operator op
    let operatorIndex = array.lastIndexOf(operatorsInArray[operatorsInArray.length - 1]);

    // Stel vast welke index het hoogst is (haakje openen of operator)
    let highestIndex = operatorIndex >= openParIndex ? operatorIndex : openParIndex;

    // Als voorgaande digit negatiefteken is, behandelen als backspace
    if(isNegative(array.peek())){
        backSpace();
    // Als negatiefteken na laatste operator of haakje openen komt, verwijderen uit array
    } else if(negativeIndex > highestIndex){
        array.splice(negativeIndex, 1);
    // Als haakje openen of operator voorkomt in array na het laatste negatiefteken, invoegen na index van dat teken
    } else if(highestIndex != -1){
        array.splice(highestIndex + 1, 0, negative);
    // Als nog geen operators of haakje openen, toevoegen voor 1e cijfer
    } else {
        array.splice(0, 0, negative);
    }
}


// Geeft een object terug welke de locale decimaalteken en duizendseparator bevat
function getNumberSeparators() {

  // default
  var res = {
    "decimal": ".",
    "thousand": ""
  };

  // converteer naar locale number format
  var str = parseFloat(1234.56).toLocaleString();

  // als het resultaat niet het voorgaande nr bevat, (bijv. sommige Arabische formats), defaults teruggeven
  if (!str.match("1"))
    return res;

  // haal decimaal- en duizendseparator op uit de string
  res.decimal = str.replace(/.*4(.*)5.*/, "$1");
  res.thousand = str.replace(/.*1(.*)2.*/, "$1");

  // geef resultaat terug
  return res;
}

// voegt duizendseparator toe in string num
function addThousandSeparator(num) {
    let t = separators.thousand;
    return num.replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1" + t);
}