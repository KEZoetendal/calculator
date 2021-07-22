/* Met deze code heb ik geprobeerd de font-size te verkleinen, wanneer de invoer groter/langer is dan de input width.
   Omdat on('input') niet werkt wanneer value wordt door JS wordt vastgesteld, werkt de code helaas niet.
   Indien de input niet readonly is, werkt het wel.
   Bron: https://jsfiddle.net/dsuv8onp/
*/

$(function(){

    // get the current styles size, in px integer.
    var maxFontSize = parseInt($('.input-autoshrink').css("font-size"));

    function isOverflowed (element){

        return ( $(element)[0].scrollWidth > $(element).innerWidth() );

    };

    function decreaseSize (element){

        var fontSize = parseInt($(element).css("font-size"));
        fontSize = fontSize - 1 + "px";
        $(element).css({'font-size':fontSize});

    }

    function maximizeSize (element){

        var fontSize = parseInt($(element).css("font-size"));
        while (!isOverflowed(element) && fontSize < maxFontSize){
            fontSize = fontSize + 1 + "px";
            $(element).css({'font-size':fontSize});

            // if this loop increases beyond the width, decrease again.
            // hacky.
            if (isOverflowed(element)){
                while (isOverflowed(element)) {
                    decreaseSize(element);
                }
            }

        }

    }

    function fixSize (element){
        if (isOverflowed(element)){
            while (isOverflowed(element)) {
                decreaseSize(element);
            }
        } else {
            maximizeSize(element);
        }
    }

    // execute it onready.
    $('.input-autoshrink').each(function(){
        fixSize(this);
    });

    // bind to it.
    $(function() {
        $('.input-autoshrink').on('input', function() {
            fixSize(this);
        })
    });

});