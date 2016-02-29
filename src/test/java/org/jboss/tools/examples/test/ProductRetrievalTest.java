package org.jboss.tools.examples.test;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Composition;
import org.jboss.tools.examples.model.Product;
import org.jboss.tools.examples.model.RetailOption;
import org.jboss.tools.examples.model.Supply;
import org.jboss.tools.examples.rest.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductRetrievalTest {

	
	@Deployment
    public static WebArchive deployment() {
        return TestDeploymentWS.deployment();
    }
	
	@Inject
	ProductService productService;
	
	@Test(expected=EJBException.class)
	public void testInexistentProductRetrieval() {
		productService.getSingleInstance(10L);
	}
	
	@Test
	public void testProductRetrieval() {
		Product p = productService.getSingleInstance(1L);
		Assert.assertNotNull(p);
		Assert.assertEquals("Black Coffee", p.getName());
		Assert.assertTrue(1L == p.getId());
		Assert.assertEquals("/cloudpos/img/black_coffee.jpg", p.getImageUrl());
		
		Iterator<Supply> itSupply = p.getIngredients().iterator();
		
		Supply supply1 = itSupply.next();
		Assert.assertNotNull(supply1);
		Assert.assertTrue(3L == supply1.getId());
		Assert.assertEquals("Suggar", supply1.getName());
		Assert.assertEquals("kg", supply1.getUnity());
		Assert.assertTrue(3.0F == supply1.getPrice());
		Assert.assertTrue(100.0F == supply1.getStock());
		
		Supply supply2 = itSupply.next();
		Assert.assertNotNull(supply2);
		Assert.assertTrue(1L == supply2.getId());
		Assert.assertEquals("Coffee Beans", supply2.getName());
		Assert.assertEquals("kg", supply2.getUnity());
		Assert.assertTrue(40.0F == supply2.getPrice());
		Assert.assertTrue(100.0F == supply2.getStock());
		
		
		Iterator<RetailOption> itRetailOption = p.getRetailOptions().iterator();
		
		RetailOption retailOption1 = itRetailOption.next();
		Assert.assertNotNull(retailOption1);
		Assert.assertTrue(1L == retailOption1.getId());
		Assert.assertEquals("Large", retailOption1.getName());
		Assert.assertEquals("L", retailOption1.getShortName());
		Assert.assertTrue(4.5D == retailOption1.getPrice());
		
		Iterator<Composition> itComposition = retailOption1.getCompositions().iterator();
		
		Composition composition1 = itComposition.next();
		Assert.assertNotNull(composition1);
		Assert.assertTrue(1L == composition1.getId());
		Assert.assertTrue(0.000005D == composition1.getQuantity());
		Assert.assertEquals(supply1, composition1.getSupply());
		
		Composition composition2 = itComposition.next();
		Assert.assertNotNull(composition2);
		Assert.assertTrue(2L == composition2.getId());
		Assert.assertTrue(0.00000003D == composition2.getQuantity());
		Assert.assertEquals(supply2, composition2.getSupply());
	}
	
	@Test
	public void testProductListRetrieval() {
		List<Product> pl = productService.getAll(new MultivaluedHashMap<String, String>());
		
		Product p = pl.get(0);
		Assert.assertNotNull(p);
		
		Assert.assertEquals("Black Coffee", p.getName());
		Assert.assertTrue(1L == p.getId());
		Assert.assertEquals("/cloudpos/img/black_coffee.jpg", p.getImageUrl());
		
		Iterator<Supply> itSupply = p.getIngredients().iterator();
		
		Supply supply1 = itSupply.next();
		Assert.assertNotNull(supply1);
		Assert.assertTrue(3L == supply1.getId());
		Assert.assertEquals("Suggar", supply1.getName());
		Assert.assertEquals("kg", supply1.getUnity());
		Assert.assertTrue(3.0F == supply1.getPrice());
		Assert.assertTrue(100.0F == supply1.getStock());
		
		Supply supply2 = itSupply.next();
		Assert.assertNotNull(supply2);
		Assert.assertTrue(1L == supply2.getId());
		Assert.assertEquals("Coffee Beans", supply2.getName());
		Assert.assertEquals("kg", supply2.getUnity());
		Assert.assertTrue(40.0F == supply2.getPrice());
		Assert.assertTrue(100.0F == supply2.getStock());
		
		Iterator<RetailOption> itRetailOption = p.getRetailOptions().iterator();
		
		RetailOption retailOption1 = itRetailOption.next();
		Assert.assertNotNull(retailOption1);
		Assert.assertTrue(1L == retailOption1.getId());
		Assert.assertEquals("Large", retailOption1.getName());
		Assert.assertEquals("L", retailOption1.getShortName());
		Assert.assertTrue(4.5D == retailOption1.getPrice());
		
		Iterator<Composition> itComposition = retailOption1.getCompositions().iterator();
		
		Composition composition1 = itComposition.next();
		Assert.assertNotNull(composition1);
		Assert.assertTrue(1L == composition1.getId());
		Assert.assertTrue(0.000005D == composition1.getQuantity());
		Assert.assertEquals(supply1, composition1.getSupply());
		
		Composition composition2 = itComposition.next();
		Assert.assertNotNull(composition2);
		Assert.assertTrue(2L == composition2.getId());
		Assert.assertTrue(0.00000003D == composition2.getQuantity());
		Assert.assertEquals(supply2, composition2.getSupply());
		
	}
	
}
