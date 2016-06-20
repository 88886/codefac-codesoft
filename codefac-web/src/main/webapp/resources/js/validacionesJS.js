/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function noProcesar(event)
{
    // alert(event.keyCode);
    if (event.keyCode == 13)
    {
        //alert('enter');
        return false;
    }
}

function validarEnter(event,nameFuncion)
{
   // alert(nameFuncion);
    if (event.keyCode == 13) {
        //test();
        eval(nameFuncion+"();");
        return false;
    }

}

function mensaje()
{
    alert('mensaje');
}

function focusField(id)
{
   alert(id);
   document.getElementById(id).focus();
  
}

