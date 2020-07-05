package organizacion;


import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class OrganizacionTest {
	
	EntidadBase entidadBase;
	List<EntidadBase> lista;
	
	Empresa empresa;
	Empresa empresaComercio;
	Empresa empresaServicios;

	Rubro IndustriaYMineria;
	Rubro Servicios;
	Rubro Agropecuario;
	Rubro Construccion;
	Rubro Comercio;
	
	
	
	  @Before
	    public void init() {
		  
		  //Instanciamos los distintos tipos de Rubros:
		IndustriaYMineria = new Rubro(33920000, 243290000, 1651750000, 2540380000.0, 15, 60 , 235, 655);
		Servicios = new Rubro(9900000, 59710000, 494200000, 705790000, 7, 30 , 165, 535);
		Agropecuario = new Rubro(17260000,71960000,426720000,676810000,5,10,50,215);
		Construccion = new Rubro(19450000, 115370000, 643710000, 965460000, 12, 45, 200 ,590);
		Comercio = new Rubro(36320000, 115370000, 643710000, 965460000, 7, 35, 125 ,345);
			
		 
		  
		  entidadBase = new EntidadBase("nombre","desc",null);
		  lista = Arrays.asList(entidadBase);
	
		  empresa = new Empresa("razon","nombre","cuil",1514,1,lista,7,"abc",Construccion,170000000);
		  empresaComercio = new Empresa("razon","nombre","cuil",1514,1,lista,346,"abc",Comercio,170000000);
		  empresaServicios = new Empresa("razon","nombre","cuil",1514,1,lista,6,"abc",Servicios,9900000);
		  
		 
		  
		  
	    }
	
	@Test
	public void testEmpresaConstruccionPequenia() {
		
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
		
		Servicios.setPersMedianaTramoDos(1000);
		
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


