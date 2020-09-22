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