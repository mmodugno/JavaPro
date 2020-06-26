package organizacion;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;


public class OrganizacionTest {
	
	@Test
	public void testEmpresaComercioPeque() {
		
		EntidadBase entidadBase = new EntidadBase("nombre","desc",null);
		
		List<EntidadBase> lista = Arrays.asList(entidadBase);
		
		Empresa empresa = new Empresa("razon","nombre","cuil",1514,1,lista,7,"abc",Categoria.Comercio,170000000.0);
		
		Assert.assertEquals(empresa.getTipo(), TipoEmpresa.Pequenia);
		
	}
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
}


