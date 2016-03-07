package org.jboss.tools.examples.test;

import static org.junit.Assert.*;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.tools.examples.model.Category;
import org.jboss.tools.examples.rest.CategoryService;
import org.jboss.tools.examples.rest.RestServiceException;
import org.jboss.tools.examples.rest.dto.CategoryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class CategoryCreationTest extends BaseWSTestAbstract {
	
	@Inject
	private CategoryService categoryService;
	
	@Test
	public void testCreateValidCategory() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.VALID, InputStatus.VALID);
		Response r = this.categoryService.createCategory(newCategoryDTO);
		
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Category savedCategory = this.categoryService.getSingleInstance((Long)r.getEntity());
		
		assertEquals(newCategoryDTO,new CategoryDTO(savedCategory));
	}

	@Test
	public void testCreateInvalidCategoryName() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.INVALID, InputStatus.VALID);
		try {
			this.categoryService.createCategory(newCategoryDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("name");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("size must be between 3 and 25"));
		}
	}
	
	@Test
	public void testCreateEmptyCategoryName() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.EMPTY, InputStatus.VALID);
		try {
			this.categoryService.createCategory(newCategoryDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("name");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("size must be between 3 and 25"));
		}
	}
	
	@Test
	public void testCreateNullCategoryName() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.NULL, InputStatus.VALID);
		try {
			this.categoryService.createCategory(newCategoryDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors = (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("name");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.contains("must not be null"));
		}
	}
	
	@Test
	public void testCreateNullCategoryDescription() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.VALID, InputStatus.NULL);
		Response r = this.categoryService.createCategory(newCategoryDTO);
		
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Category savedCategory = this.categoryService.getSingleInstance((Long)r.getEntity());
		
		assertEquals(newCategoryDTO,new CategoryDTO(savedCategory));
	}
	
	@Test
	public void testCreateEmptyCategoryDescription() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.VALID, InputStatus.EMPTY);
		try {
			this.categoryService.createCategory(newCategoryDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("description");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("size must be between 5 and 100"));
		}
	}
	
	@Test
	public void testCreateInvalidCategoryDescription() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.VALID, InputStatus.INVALID);
		try {
			this.categoryService.createCategory(newCategoryDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("description");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("size must be between 5 and 100"));
		}
	}
	
	@Test
	public void testCreateNonUniqueCategory() {
		CategoryDTO newCategoryDTO = this.createCategory(InputStatus.VALID, InputStatus.VALID);
		Response r = this.categoryService.createCategory(newCategoryDTO);
		
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Category savedCategory = this.categoryService.getSingleInstance((Long) r.getEntity());
		
		assertEquals(newCategoryDTO,new CategoryDTO(savedCategory));
		
		newCategoryDTO.setDescription(this.createCategoryDescription(InputStatus.VALID));
		
		r = this.categoryService.createCategory(newCategoryDTO);
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		savedCategory = this.categoryService.getSingleInstance((Long) r.getEntity());
		
		assertEquals(newCategoryDTO,new CategoryDTO(savedCategory));
		
	}
	
	public CategoryDTO createCategory(InputStatus nameStatus, InputStatus descriptionStatus) {
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setName(this.createCategoryName(nameStatus));
		categoryDTO.setDescription(this.createCategoryDescription(descriptionStatus));
		return categoryDTO;
	}
	
	private String createCategoryName(InputStatus status) {
		switch (status) {
		case VALID:
			return this.createRandomString(25);
		case INVALID:
			return this.createRandomString(27);
		case EMPTY:
			return "";
		default: 
			return null;
		}	
	}
	
	private String createCategoryDescription(InputStatus status) {
		switch (status) {
		case VALID:
			return this.createRandomString(100);
		case INVALID:
			return this.createRandomString(110);
		case EMPTY:
			return "";
		default: 
			return null;
		}	
	}
	
}
