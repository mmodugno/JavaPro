package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import egreso.Ingreso;
import usuarios.CategoriaDelSistema;

public class RepositorioIngreso {
	
	static List<Ingreso> ingresos = new ArrayList<>();

    public RepositorioIngreso() {
        if (ingresos == null) {
        	Ingreso ingreso1 = new Ingreso("Donacion",1000.0);
        	Ingreso ingreso2 = new Ingreso("Venta",10000.0);
        	Ingreso ingreso3 = new Ingreso("Venta",500.0);

        	ingresos.add(ingreso1);
        	ingresos.add(ingreso2);
        	ingresos.add(ingreso3);
        }
    }
    
    public List<Ingreso> byCategoria(CategoriaDelSistema unaCategoria) {
        return ingresos.stream().filter(a ->
                a.esDeCategoria(unaCategoria)
        ).collect(Collectors.toList());
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
}
