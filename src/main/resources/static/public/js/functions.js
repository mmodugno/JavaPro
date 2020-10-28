function deleteAuto(id) {

    $.ajax({
        url: '/auto/' + id,
        type: 'DELETE',
        success: function(result) {
            window.location = "/auto"
        }
    });
}

function filtrarTipoPorCategoria(tipoDoc, categoria) {


    window.location = "/categoria?tipoDoc=" + $("#tipoDoc").val() + "&categoria=" + $("#categoria").val();
}

/**Arranco lógica para eliminar, seminario Catedra**/

function confirmarBorrar(id) {
    document.getElementById("productoId").value = id;
    document.getElementById("eliminarProductoVentana").style.display = 'block';

}

function eliminarProducto() {
    let id = document.getElementById("productoId").value;
    $.ajax({
        type: "delete",
        url: id,
        success: function(resultado) {
            window.location.href = "/productos";
        }

    });
}

function cerrarConfirmacionBorrar() {
    document.getElementsByClassName("modal")[0].style.display = 'none';

}

/**Fin lógica para eliminar**/

/**eliminar egreso**/
function confirmarBorrarEgreso(id) {
    document.getElementById("egresoId").value = id;
    document.getElementById("eliminarEgreso").style.display = 'block';

}

function eliminarEgreso() {
    var id = document.getElementById("egresoId").value;
    $.ajax({
        type: "delete",
        url: id,
        success: function(resultado) {
            window.location.href = "/egresos";
        }

    });



}

/**Eliminar ingreso**/
function confirmarBorrarIngreso(id) {
    document.getElementById("ingresoId").value = id;
    document.getElementById("eliminarIngresoVentana").style.display = 'block';

}
function eliminarIngreso() {
    let id = document.getElementById("ingresoId").value;
    $.ajax({
        type: "delete",
        url: id,
        success: function(resultado) {
            window.location.href = "/ingresos";
        }

    });}


    
/**Eliminar ingreso**/
function confirmarBorrarIngreso(id) {
    document.getElementById("ordenId").value = id;
    document.getElementById("eliminarOrdenVentana").style.display = 'block';

}
function eliminarOrden() {
    let id = document.getElementById("ordenId").value;
    $.ajax({
        type: "delete",
        url: id,
        success: function(resultado) {
            window.location.href = "/ordenes";
        }

    });}