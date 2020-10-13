package egreso;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import producto.*;
import usuarios.CategoriaDelSistema;

public class Presupuesto implements Categorizable{

    /*CONSTRUCTOR*/
	public Presupuesto(List<Item> itemsoriginal, Proveedor proveedor, MedioDePago medioDePago) throws CloneNotSupportedException {
		super();
		this.items = new ArrayList<Item>();
		this.proveedor = proveedor;
		this.medioDePago = medioDePago;

		llenarItems(itemsoriginal);
	}
	/*
	*   setProveedor(proveedor);
	*   setMedioDePago(medio);
	*   llenarItems(ordenDeCompra.getItems());
	* */

	public Presupuesto() {
		// TODO Auto-generated constructor stub
	}

	private int id;

	/*ATRIBUTOS*/
    private List<Item> items;
    private Proveedor proveedor;
    private MedioDePago medioDePago;
    private boolean aceptado;
    private CategoriaDelSistema categoria = null;
    
    public int getId() {
    	return this.id;
    }
	/*getters*/
	public CategoriaDelSistema getCategoria() {
		return this.categoria;
	}
	public List<Item> getItems() {
		return items;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
    public boolean getAceptado() {
        return aceptado;
    }
    public MedioDePago getMedioDePago() {
        return medioDePago;
    }
    public boolean isAceptado() {
        return aceptado;
    }
    
    public Double getValorTotal() {
    	return this.valorTotal();
    }
    
    public List<String> getNombreItems(){
    	return items.stream().map(a -> a.getProducto()).collect(Collectors.toList()).stream().map(a -> a.getNombre()).collect(Collectors.toList());
    }

    public Double valorTotal() throws SinItemsExcepcion{
			if(items.isEmpty()){
				throw new SinItemsExcepcion();
			}
			return items.stream().mapToDouble(Item::obtenerPrecio).sum();
		} //Es un getter sin el get como prefijo

	/*SETTERS*/
	public void setAceptado() {
		this.aceptado = true;
	}
    public void setItems(List<Item> items) {
        this.items = items;
    }
    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
    public void setMedioDePago(MedioDePago medioDePago) {
        this.medioDePago = medioDePago;
    }
    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
    public void setCategoria(CategoriaDelSistema categoria) {
        this.categoria = categoria;
    }

    /*Lo que hace esta funcion es clonar todos los items que tiene la OC*/
    private void llenarItems(List<Item> itemsoriginales) throws CloneNotSupportedException {
        for(int i = 0;i<itemsoriginales.size();i++){
            items.add(itemsoriginales.get(i).clone());
        }
    }

    /*Esto seria un set categoria? lo tenemos por si tiene que realizar algo mas complejo?*/
    @Override
    public void categorizar(CategoriaDelSistema categoria) {
        this.categoria = categoria;
    }
    
	public boolean esDeCategoria(CategoriaDelSistema unaCategoria) {
		if(categoria == null) return false;
		else {
			if(unaCategoria.esCompuesta()) {
				return unaCategoria.getSubCategorias().stream().anyMatch(c -> this.esDeCategoria(c));
			} else {
				return categoria.getCategoria().equals(unaCategoria.getCategoria());
			}
		}
	}	

    
	public void setId(int i) {
		this.id = i;
		
	}

}
