package org.jboss.tools.examples.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.jboss.tools.examples.rest.dto.CategoryDTO;
import org.jboss.tools.examples.rest.dto.CompositionDTO;
import org.jboss.tools.examples.rest.dto.ProductDTO;
import org.jboss.tools.examples.rest.dto.RetailOptionDTO;
import org.jboss.tools.examples.rest.dto.SupplyDTO;
import org.junit.Test;

public class ProductDTOTest {

	@Test
	public void testProductEquality() {
		ProductDTO pDto1 = createValidProduct();
		ProductDTO pDto2 = createValidProduct2();
		assertEquals(pDto1, pDto2);
	}
	
	@Test
	public void testRetailOptionEquality() {
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
		
		System.out.println(roList.hashCode());
		System.out.println(roDto1);
		System.out.println(roDto2);
		roDto1.setId(1L);
		roDto2.setId(2L);
		
		compDto1.setId(1L);
		compDto2.setId(2L);
		compDto3.setId(3L);
		compDto4.setId(4L);
		System.out.println(roDto1);
		System.out.println(roDto2);
		System.out.println(roList.hashCode());
		assertTrue(roList.contains(roDto1));
		assertTrue(roList.contains(roDto2));
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
	
	private ProductDTO createValidProduct2() {
		ProductDTO pDto = new ProductDTO();
		pDto.setName("White Coffee");
		pDto.setImageUrl("/cloudpos/img/white_coffee.jpg");
		pDto.setCategories(this.createCategories2());
		pDto.setIngredients(this.createIngredients2());
		pDto.setRetailOptions(this.createRetailOptions2());
		return pDto;
	}
	
	private Set<SupplyDTO> createIngredients() {
		SupplyDTO supplyDto1 = new SupplyDTO();
		supplyDto1.setId(1L);
		supplyDto1.setName("Suggar");
		supplyDto1.setPrice(5.5F);
		supplyDto1.setStock(145F);
		supplyDto1.setUnity("box");
		
		SupplyDTO supplyDto2 = new SupplyDTO();
		supplyDto2.setId(2L);
		supplyDto2.setName("Coffee Beans");
		supplyDto2.setPrice(38.50F);
		supplyDto2.setStock(null);
		supplyDto2.setUnity("kg");
		
		Set<SupplyDTO> supplyList = new HashSet<SupplyDTO>();
		supplyList.add(supplyDto1);
		supplyList.add(supplyDto2);
		return supplyList;
	}
	
	private Set<SupplyDTO> createIngredients2() {
		SupplyDTO supplyDto1 = new SupplyDTO();
		supplyDto1.setId(1L);
		supplyDto1.setName("Suggar");
		supplyDto1.setPrice(5.5F);
		supplyDto1.setStock(145F);
		supplyDto1.setUnity("box");
		
		SupplyDTO supplyDto2 = new SupplyDTO();
		supplyDto2.setId(2L);
		supplyDto2.setName("Coffee Beans");
		supplyDto2.setPrice(38.50F);
		supplyDto2.setStock(null);
		supplyDto2.setUnity("kg");
		
		Set<SupplyDTO> supplyList = new HashSet<SupplyDTO>();
		supplyList.add(supplyDto2);
		supplyList.add(supplyDto1);
		return supplyList;
	}
	
	private Set<CategoryDTO> createCategories() {
		CategoryDTO catDto1 = new CategoryDTO();
		catDto1.setId(1L);
		catDto1.setName("Favourites");
		catDto1.setDescription("Favourite products");
		
		CategoryDTO catDto2 = new CategoryDTO();
		catDto2.setId(2L);
		catDto2.setName("Hot Drinks");
		catDto2.setDescription("Delicious hot drinks");
		
		Set<CategoryDTO> catList = new HashSet<CategoryDTO>();
		catList.add(catDto1);
		catList.add(catDto2);
		return catList;
	}
	
	private Set<CategoryDTO> createCategories2() {
		CategoryDTO catDto1 = new CategoryDTO();
		catDto1.setId(1L);
		catDto1.setName("Favourites");
		catDto1.setDescription("Favourite products");
		
		CategoryDTO catDto2 = new CategoryDTO();
		catDto2.setId(2L);
		catDto2.setName("Hot Drinks");
		catDto2.setDescription("Delicious hot drinks");
		
		Set<CategoryDTO> catList = new HashSet<CategoryDTO>();
		catList.add(catDto2);
		catList.add(catDto1);
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
	
	private Set<RetailOptionDTO> createRetailOptions2() {
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
		compList1.add(compDto2);
		compList1.add(compDto1);
		
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
		compList2.add(compDto4);
		compList2.add(compDto3);	
		
		roDto2.setCompositions(compList2);
		
		Set<RetailOptionDTO> roList = new HashSet<RetailOptionDTO>();
		roList.add(roDto2);
		roList.add(roDto1);
		
		return roList;
	}
	
}
