/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function play() {
    var audio = document.getElementsByTagName("audio")[0];
    //audio.load()
    audio.play();
    var display = document.getElementsByTagName("input")[0];
    display.value = audio.src;
    alert(audio.src);

}
function pause() {
    var audio = document.getElementsByTagName("audio")[0];
    audio.pause();
}

function loadSrc(path)
{
    var audio = document.getElementsByTagName("audio")[0];
    audio.src = path;
    //audio.src=;
}

function imprimir()
{

    var dato = document.getElementsByTagName("formPrueba:dato");
    alert(dato.value);
}

function getVars() {
    // ...
    //alert('aqui');
    
    var x = 12;
    var y = 24;
    
        
    document.getElementById("formId:x").value = x;
    document.getElementById("formId:y").value = y;
    
    alert(x);
}