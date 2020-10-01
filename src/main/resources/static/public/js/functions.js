function deleteAuto(id) {

    $.ajax({
        url: '/auto/' + id,
        type: 'DELETE',
        success: function(result) {
            window.location = "/auto"
        }
    });
}

function filtrarAutos(marca) {
    window.location = "/auto?marca=" + $("#filtroMarca").val();
}


/**Arranco lógica para eliminar, seminario Catedra**/

function confirmarBorrar(id){
    document.getElementById("productoId").value = id;
    document.getElementById("eliminarProductoVentana").style.display = 'block';

}

    function eliminarProducto(){
        var id = document.getElementById("productoId").value;
        $.ajax({
                type: "delete",
                url: id,
                success: function(resultado){
                    window.location.href = "/productos";
                }

            });
    }

function cerrarConfirmacionBorrar(){
    document.getElementsByClassName("modal")[0].style.display = 'none';

}

/**Fin lógica para eliminar**/

/**elimar egreso**/
function confirmarBorrarEgreso(id){
    document.getElementById("egresoId").value = id;
    document.getElementById("eliminarEgreso").style.display = 'block';

}

function eliminarEgreso(){
    var id = document.getElementById("egresoId").value;
    $.ajax({
        type: "delete",
        url: id,
        success: function(resultado){
            window.location.href = "/egresos";
        }

    });
}

