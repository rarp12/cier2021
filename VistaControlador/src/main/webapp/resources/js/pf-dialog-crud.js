
function handleSubmitRequest(xhr, status, args, dialog) {
    var jqDialog = jQuery('#' + dialog.id);
    if (args.validationFailed) {
        jqDialog.effect("shake", {times: 5}, 100);
    }
    else {
        dialog.hide();
    }
}

// Esto se valida cuando escribe
function validateFloatKeyPress(el, evt) {

  var charCode = (evt.which) ? evt.which : event.keyCode;
  var number = el.value.split('.');
  // permitir el signo de - (45)
  if (charCode != 45 && charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
  }
  //just one dot
  if (number.length > 1 && charCode == 46) {
    return false;
  }
  //get the carat position
  var caratPos = getSelectionStart(el);
  // no permitir que se ponega el - en una posicion diferente de la inicial
  if (caratPos > 0 && charCode == 45) {
    return false;
  }
  //No permitir un punto en la posicin inicial
  
   if (caratPos == 0 && charCode == 46) {
    return false;
  }
  
  // no permtir mas de un - en el numero
  if (charCode == 45 && el.value.charAt(0) == "-") {
    return false;
  }
  var dotPos = el.value.indexOf(".");
  if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
    return false;
  }
  return true;
}

function validateFloatKeyPressNoNegativo(el, evt) {

  var charCode = (evt.which) ? evt.which : event.keyCode;
  var number = el.value.split('.');
  // permitir el signo de - (45)
  if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
    return false;
  }
  //just one dot
  if (number.length > 1 && charCode == 46) {
    return false;
  }
  //get the carat position
  var caratPos = getSelectionStart(el);
  // no permitir que se ponega el - en una posicion diferente de la inicial
  //No permitir un punto en la posicin inicial
  
   if (caratPos == 0 && charCode == 46) {
    return false;
  }
  
  var dotPos = el.value.indexOf(".");
  if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
    return false;
  }
  return true;
}

function validateFloatKeyPressEntero(el, evt) {

  var charCode = (evt.which) ? evt.which : event.keyCode;
  if (charCode < 48 || charCode > 57) {
    return false;
  }

  return true;
}

function getSelectionStart(o) {
  if (o.createTextRange) {
    var r = document.selection.createRange().duplicate()
    r.moveEnd('character', o.value.length)
    if (r.text == '') return o.value.length
    return o.value.lastIndexOf(r.text)
  } else { return o.selectionStart }
}

// Para cuando pierde el foco
function validarPuntos (el, evt){
    alert("A");
}