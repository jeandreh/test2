package org.jboss.tools.examples.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.tools.examples.model.Product;
import org.jboss.tools.examples.rest.CategoryService;
import org.jboss.tools.examples.rest.ProductService;
import org.jboss.tools.examples.rest.SupplyService;
import org.jboss.tools.examples.rest.dto.CategoryDTO;
import org.jboss.tools.examples.rest.dto.CompositionDTO;
import org.jboss.tools.examples.rest.dto.ProductDTO;
import org.jboss.tools.examples.rest.dto.RetailOptionDTO;
import org.jboss.tools.examples.rest.dto.SupplyDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ProductCreationTest {

	@Deployment
    public static WebArchive deployment() {
        return TestDeploymentWS.deployment();
    }
	
	@Inject
	ProductService productService;
	
	@Inject
	CategoryService categoryService;
	
	@Inject
	SupplyService supplyService;
	
	@Test
	public void testProductCompleteCreation() {
		
		ProductDTO pDto1 = this.createValidProduct();
		
		Response r = productService.createProduct(pDto1);
		
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		assertTrue(r.getEntity().equals(2L));
		
		Product prod = productService.getSingleInstance((Long) r.getEntity());
		
		/**  
		 *  Important to note that pDto1 and pDto2 fail an equality test as pDto1 doesn't have 
		 *  Supply objects associated to the respective Composition of a RetailOption. It will 
		 *  be assigned by the DTO as it doesn't exist.
		 */
		ProductDTO pDto2 = new ProductDTO(prod);
		
		assertEquals(pDto1.getName(), pDto2.getName());
		assertEquals(pDto1.getImageUrl(), pDto2.getImageUrl());
		assertEquals(pDto1.getIngredients(), pDto2.getIngredients());
		assertEquals(pDto1.getCategories(), pDto2.getCategories());
		assertEquals(pDto2.getId(), new Long(2L));
		
//		Set<RetailOptionDTO> roDtoSet2 = pDto2.getRetailOptions();
//		i = 0;
//		for (RetailOptionDTO roDto : roDtoSet2) {
//			System.out.println("2. RetailOptionDTO " + ++i + "#" + roDto.hashCode());
//			if (roDto.getId() != null) System.out.println(" Id:" + roDto.getId().hashCode());
//			System.out.println(" Name:" + roDto.getName().hashCode());
//			System.out.println(" Description:" + roDto.getDescription().hashCode());
//			System.out.println(" Short Name:" + roDto.getShortName().hashCode());
//			System.out.println(" Price:" + roDto.getPrice().hashCode());
//			Set<CompositionDTO> compDtoSet = roDto.getCompositions();
//			for(CompositionDTO compDto : compDtoSet) {
//				System.out.println("Composition#" + compDto.hashCode());
//				if (compDto.getId() != null) System.out.println(" Id:" + compDto.getId().hashCode());
//				System.out.println(" Quantity: " + compDto.getQuantity().hashCode());
//				System.out.println(" Supply: " + compDto.getSupply().hashCode());
//			}
//		}

	}
	
	private ProductDTO createValidProduct() {
		ProductDTO pDto = new ProductDTO();
		pDto.setName("White Coffee");
		pDto.setImageUrl("/cloudpos/img/white_coffee.jpg");
		pDto.setCategories(this.createCategories());
		pDto.setIngredients(this.createIngredients());
		pDto.setRetailOptions(this.createRetailOptions());
		return pDto;
	}
	
	private Set<SupplyDTO> createIngredients() {
		SupplyDTO supplyDto1 = new SupplyDTO(supplyService.getSingleInstance(3L));
		SupplyDTO supplyDto2 = new SupplyDTO(supplyService.getSingleInstance(4L));
		Set<SupplyDTO> supplyList = new HashSet<SupplyDTO>();
		supplyList.add(supplyDto1);
		supplyList.add(supplyDto2);
		return supplyList;
	}
	
	private Set<CategoryDTO> createCategories() {
		CategoryDTO catDto1 = new CategoryDTO(categoryService.getSingleInstance(1L));
		CategoryDTO catDto2 = new CategoryDTO(categoryService.getSingleInstance(2L));
		Set<CategoryDTO> catList = new HashSet<CategoryDTO>();
		catList.add(catDto1);
		catList.add(catDto2);
		return catList;
	}
	
	private Set<RetailOptionDTO> createRetailOptions() {
		RetailOptionDTO roDto1 = new RetailOptionDTO();
		roDto1.setName("Large");
		roDto1.setPrice(3.579833D);
		roDto1.setShortName("L");
		roDto1.setDescription("Delicious Large 500ml cold white coffee");
		
		CompositionDTO compDto1 = new CompositionDTO();
		compDto1.setQuantity(0.00005D);
		
		CompositionDTO compDto2 = new CompositionDTO();
		compDto2.setQuantity(0.000003D);
		
		Set<CompositionDTO> compList1 = new HashSet<CompositionDTO>();
		compList1.add(compDto1);
		compList1.add(compDto2);
		
		roDto1.setCompositions(compList1);
		
		RetailOptionDTO roDto2 = new RetailOptionDTO();
		roDto2.setName("Medium Size");
		roDto2.setPrice(111.579833D);
		roDto2.setShortName("MD");
		roDto2.setDescription("Awesome and expensive Large 500ml cold white coffee");
		
		CompositionDTO compDto3 = new CompositionDTO();
		compDto3.setQuantity(0.002D);
		
		CompositionDTO compDto4 = new CompositionDTO();
		compDto4.setQuantity(0.001D);
		
		Set<CompositionDTO> compList2 = new HashSet<CompositionDTO>();
		compList2.add(compDto3);
		compList2.add(compDto4);
		
		roDto2.setCompositions(compList2);
		
		Set<RetailOptionDTO> roList = new HashSet<RetailOptionDTO>();
		roList.add(roDto1);
		roList.add(roDto2);
		
		return roList;
	}
	
}
