package repositorios;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import egreso.Ingreso;

public class RepositorioIngreso {
	
	static List<Ingreso> ingresos = null;

    public RepositorioIngreso() {
        if (ingresos == null) {
        	ingresos = new ArrayList<>();
        }
    }

    public Ingreso buscarIngreso(String descripcion) {
        Ingreso unIngreso = ingresos.stream().filter(ingreso -> ingreso.getDescripcion().equals(descripcion)).findFirst().get();
        return unIngreso;
    }

    public List<Ingreso> todos() {
        return new ArrayList<>(ingresos);
    }

    public void borrar(String descripcion) {
        ingresos = ingresos.stream().filter(ingreso -> !ingreso.getDescripcion().equals(descripcion)).collect(Collectors.toList());
    }

    public void crear(Ingreso ingreso) {
    	ingresos.add(ingreso);
    }
}
