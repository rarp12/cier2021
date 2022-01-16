// Author: stackpointer
// Version: 1.0
// Copyright 2017 stackpointer
// www: http://stackpointer.co/
// mail: info@stackpointer.co


$(document).ready(function() {           
var conn = checkConnection();
//console.log(conn);
//llamamos al servlet CheckGmap para que el atributo internetConnectionGmap quede seteado
jQuery.ajax({
  url: 'checkGmap',
  type: 'GET',
  data: 'data='+conn,
  success: function(data) {
        //called when successful
        //console.log(data);
  },
  error: function(e) {
        //called when there is an error
        //console.log(e.message);
  }
});

});

//verifica si el usuario tiene conexion a gmaps
function checkConnection() {
var online;
// No cambiar el valor de la variable x
try {
  var x = google.maps.MapTypeId.TERRAIN;
  online = true;
} catch (e) {
  online = false;
}
return online;
}