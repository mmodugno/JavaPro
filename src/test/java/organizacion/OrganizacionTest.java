package organizacion;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import egreso.OrdenDeCompra;
import egreso.Validador;
import usuarios.CreationError;
import usuarios.Usuario;


public class OrganizacionTest {
	
	EntidadBase entidadBase;
	List<EntidadBase> lista;
	Servicios servicio;
	Empresa empresa;
	Empresa empresaComercio;
	Empresa empresaServicios;
	
	  @Before
	    public void init() {
		  
		  servicio = new Servicios();
		  
		  entidadBase = new EntidadBase("nombre","desc",null);
		  lista = Arrays.asList(entidadBase);
	
		  empresa = new Empresa("razon","nombre","cuil",1514,1,lista,7,"abc",new Construccion(),170000000);
		  empresaComercio = new Empresa("razon","nombre","cuil",1514,1,lista,346,"abc",new Comercio(),170000000);
		  empresaServicios = new Empresa("razon","nombre","cuil",1514,1,lista,6,"abc",servicio,9900000);
		  
	    }
	
	
	
	@Test
	public void testEmpresaConstruccionMedianaTramo2() {
		
		empresa.actualizarTipoEmpresa();
		
		Assert.assertEquals(TipoEmpresa.Pequenia, empresa.getTipoEmpresa());
		
	}
	
	@Test
	public void testEmpresaComercioMedianaTramo2() {
		
		empresaComercio.actualizarTipoEmpresa();
		
		Assert.assertEquals(TipoEmpresa.MedianaTramo2, empresaComercio.getTipoEmpresa());
		
	}
	
	@Test
	public void testEmpresaServicioMedianaTramo2() {
		
		empresaServicios.actualizarTipoEmpresa();
		
		Assert.assertEquals(TipoEmpresa.Micro, empresaServicios.getTipoEmpresa());
		
		empresaServicios.setCantidadDePersonal(536);
		
		Assert.assertEquals(TipoEmpresa.MedianaTramo2, empresaServicios.getTipoEmpresa());
	}
	
	@Test
	public void testEmpresaServicioLimites() {
		
		empresaServicios.setCantidadDePersonal(536);
		
		Assert.assertEquals(TipoEmpresa.MedianaTramo2, empresaServicios.getTipoEmpresa());
		
		servicio.setPersMedianaTramoDos(1000);
		
		empresaServicios.actualizarTipoEmpresa();
		
		Assert.assertEquals(TipoEmpresa.MedianaTramo1,empresaServicios.getTipoEmpresa());
	}
	
	/*
	@Test
	public void testRecategorizarComercioPequenio() {
		
		EntidadBase entidadBase = new EntidadBase("nombre","desc",null);
		
		List<EntidadBase> lista = Arrays.asList(entidadBase);
		
		Empresa empresa = new Empresa("razon","nombre","cuil",1514,1,lista,7,"abc",Categoria.Comercio,170000000.0);
		
		empresa.setPromedioDeVentas(1500000000.0);
		
		Assert.assertNotEquals(empresa.getTipo(), TipoEmpresa.Pequenia);
		
	}
	
	@Test
	public void testRecategorizarServiciosTramo1aTramo2() {
		
		EntidadBase entidadBase = new EntidadBase("nombre","desc",null);
		
		List<EntidadBase> lista = Arrays.asList(entidadBase);
		
		Empresa empresa = new Empresa("razon","nombre","cuil",1514,1,lista,7,"abc",Categoria.Servicios,385170000.0);
		
		empresa.setPromedioDeVentas(590000000.0);
		
		Assert.assertEquals(empresa.getTipo(), TipoEmpresa.MedianaTramo2);
		
	}
	*/
	
	
}


