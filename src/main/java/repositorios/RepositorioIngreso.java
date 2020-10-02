package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import egreso.Egreso;
import egreso.Ingreso;
import usuarios.CategoriaDelSistema;

public class RepositorioIngreso {

	static List<Ingreso> ingresos = null;
	static int proximoId = 5;


    public RepositorioIngreso() {
        if (ingresos == null) {
        	
        	ingresos = new ArrayList<>();
        			
        	Ingreso ingreso1 = new Ingreso("Donacion",1000.0);
        	Ingreso ingreso2 = new Ingreso("Venta",10000.0);
        	Ingreso ingreso3 = new Ingreso("Venta",500.0);
        	
        	ingreso1.setId(1001);
        	ingreso2.setId(1002);
        	ingreso3.setId(1003);

        	ingresos.add(ingreso1);
        	ingresos.add(ingreso2);
        	ingresos.add(ingreso3);
        	
        	proximoId = this.proximoId();
        }
    }
    
 
    
    public List<Ingreso> byCategoria(CategoriaDelSistema unaCategoria) {
        return ingresos.stream().filter(a ->
                a.esDeCategoria(unaCategoria)
        ).collect(Collectors.toList());
  }

    public Ingreso byID(int id) {
		Optional<Ingreso> ingreso = ingresos.stream().filter(e -> e.getId() == id).findFirst();

		if (ingreso.isPresent()) {
			return ingreso.get();
		}
		else return null;
	}

    public List<Ingreso> todos() {
        return new ArrayList<>(ingresos);
    }


    public Ingreso buscarIngreso(String descripcion) {
        Ingreso unIngreso = ingresos.stream().filter(ingreso -> ingreso.getDescripcion().equals(descripcion)).findFirst().get();
        return unIngreso;
    }
    public void borrar(String descripcion) {
        ingresos = ingresos.stream().filter(ingreso -> !ingreso.getDescripcion().equals(descripcion)).collect(Collectors.toList());
    }

    public void crear(Ingreso ingreso) {
    	ingresos.add(ingreso);
    }
    
    int ordenarInt(int primero,int segundo){
		if (primero > segundo) return -1;
		if (primero < segundo) return 1;
		return 0;

	}
    
    public int getProximoId() {
    	return proximoId;
    }
    
    public int proximoId(){
    	ingresos.sort((Ingreso egreso1, Ingreso egreso2) -> {
			return ordenarInt(egreso1.getId(),egreso2.getId());
		});
		return ingresos.get(0).getId() + 1;
	}
}
