package egreso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import producto.Item;
import producto.Producto;
import producto.Proveedor;
import producto.TipoItem;
import usuarios.CreationError;

public class CrearPresupuestosTest {

	
	 	Producto p1 = new Producto(1,"Monitor", "Monitor 32", TipoItem.ARTICULO);
	    Producto p2 = new Producto(2,"Notebook", "Notebook Lenovo", TipoItem.ARTICULO);
	    Proveedor proveedor1 = new Proveedor("Info Tech","22412145696", "6725");
	    Item item1 = new Item(p1, 1, 0.00);
	    Item item2 = new Item(p2, 2, 0.00);

	    OrdenDeCompra ordenDeCompra;

	    Presupuesto presupuesto1;
	    Presupuesto presupuesto2;
	    MedioDePago medioDePago = new MedioDePago(TipoMedioPago.Argencard, 221144);
	    String JSON;
	    Gson gson;
	    
	 
	    @Before
	    public void init() throws ClassNotFoundException, FileNotFoundException, SQLException, CreationError, CloneNotSupportedException {
	    		    	
	    	ordenDeCompra = new OrdenDeCompra(1,5);
	        ordenDeCompra.agregarItem(item1);
	        ordenDeCompra.agregarItem(item2);
	        presupuesto1 = new Presupuesto(ordenDeCompra.getItems(),proveedor1,medioDePago);
	        presupuesto1.getItems().get(0).setPrecioUnitario(80.00);
	        presupuesto1.getItems().get(1).setPrecioUnitario(30.00);
	        ordenDeCompra.agregarPresupuesto(presupuesto1);
	        presupuesto1.setAceptado();
	        
	       
	    }

	
	
	
	 @Test
	    public void testeandoJsonDePresupuesto() throws CloneNotSupportedException, IOException {
		         
		 Gson gson = new Gson();
	        String JSON = gson.toJson(presupuesto1);
	        
	    	//RUTA PARA CREAR UNOS PRESUPUESTOS EN JSON
	    	String ruta = "/C:\\Users/Maga/Desktop/Jsons/prueba.json";
	    	
	         File file = new File(ruta);
	         file.createNewFile();
	         FileWriter fw = new FileWriter(file);
	         BufferedWriter bw = new BufferedWriter(fw);
	         
	         bw.write(JSON);
	         bw.close();
	    	
	    	System.out.println(JSON);
	    }
	 
	 @Test
	 public void leyendoUnArchivoJson() throws FileNotFoundException {
		 	
		 
		 //File file = new File("/C:\\\\Users/Maga/Desktop/Jsons/prueba.txt");
		 FileReader fr = new FileReader("/C:\\\\Users/Maga/Desktop/Jsons/prueba.json");
	        
		 String datos = gson.fromJson(fr, null);
		 Presupuesto pres = gson.fromJson(JSON, Presupuesto.class);
		 assertEquals(0, pres.getId());
		 assertEquals(2,pres.getItems().size());
		 assertTrue(pres.getAceptado());
		    
	 }
	
	
	
	
	
	
	
	
}
