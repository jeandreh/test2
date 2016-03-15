package org.jboss.tools.examples.test;

import static org.junit.Assert.*;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.tools.examples.model.Supply;
import org.jboss.tools.examples.rest.SupplyService;
import org.jboss.tools.examples.rest.RestServiceException;
import org.jboss.tools.examples.rest.dto.SupplyDTO;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
@SuppressWarnings("unchecked")
public class SupplyCreationTest extends BaseWSTestAbstract {
	
	@Inject
	private SupplyService supplyService;
	
	@Test
	public void testCreateValidSupply() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.VALID, 
			InputStatus.VALID, 
			InputStatus.VALID);
		
		Response r = this.supplyService.createSupply(newSupplyDTO);
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long)r.getEntity());	
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
	}

	@Test
	public void testCreateInvalidSupplyName() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.INVALID, 
			InputStatus.VALID,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
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
	public void testCreateEmptySupplyName() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.EMPTY, 
			InputStatus.VALID,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
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
	public void testCreateNullSupplyName() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.NULL, 
			InputStatus.VALID,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
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
	public void testCreateNullSupplyUnity() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.NULL,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors = (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("unity");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.contains("must not be null"));
		}
	}
	
	@Test
	public void testCreateEmptySupplyUnity() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.EMPTY,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors = (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("unity");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.contains("size must be between 1 and 3"));
		}
	}
	
	@Test
	public void testCreateInvalidSupplyUnity() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.INVALID,
			InputStatus.VALID, 
			InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors = (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("unity");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.contains("size must be between 1 and 3"));
		}
	}
	
	@Test
	public void testCreateNullSupplyStock() {
		SupplyDTO newSupplyDTO = this.createSupply(
				InputStatus.VALID, 
				InputStatus.VALID,
				InputStatus.NULL, 
				InputStatus.VALID);
		Response r = this.supplyService.createSupply(newSupplyDTO);
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long)r.getEntity());
		assertNull(savedSupply.getStock());
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
	}
	
	@Test
	public void testCreateEmptySupplyStock() {
		SupplyDTO newSupplyDTO = this.createSupply(
				InputStatus.VALID, 
				InputStatus.VALID,
				InputStatus.EMPTY, 
				InputStatus.VALID);	
		
		Response r = this.supplyService.createSupply(newSupplyDTO);
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long)r.getEntity());
		assertEquals(savedSupply.getStock(),new Float(0.0F));
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
	}
	
	@Test
	public void testCreateInvalidSupplyStock() {
		SupplyDTO newSupplyDTO = this.createSupply(
				InputStatus.VALID, 
				InputStatus.VALID,
				InputStatus.INVALID, 
				InputStatus.VALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("stock");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("must be less than or equal to 9999.99"));
		}
	}
	
	@Test
	public void testCreateNullSupplyPrice() {
		SupplyDTO newSupplyDTO = this.createSupply(
				InputStatus.VALID, 
				InputStatus.VALID,
				InputStatus.VALID, 
				InputStatus.NULL);
		Response r = this.supplyService.createSupply(newSupplyDTO);	
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long)r.getEntity());	
		assertNull(savedSupply.getPrice());
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
	}
	
	@Test
	public void testCreateEmptySupplyPrice() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.VALID, 
			InputStatus.VALID, 
			InputStatus.EMPTY);
			
		Response r = this.supplyService.createSupply(newSupplyDTO);	
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long)r.getEntity());
		assertEquals(savedSupply.getPrice(),new Float(0.0F));
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
	}
	
	@Test
	public void testCreateInvalidSupplyPrice() {
		SupplyDTO newSupplyDTO = this.createSupply(
			InputStatus.VALID, 
			InputStatus.VALID,
			InputStatus.VALID, 
			InputStatus.INVALID);
		try {
			this.supplyService.createSupply(newSupplyDTO);
			fail("Should have thrown RestServiceException");
		}
		catch(RestServiceException ex) {
			Response r = ex.getResponse();
			assertTrue(r.getStatus() == Response.Status.BAD_REQUEST.getStatusCode());
			
			Map<String,String> errors =  (Map<String,String>) r.getEntity();
			String errorMessage = errors.get("price");
			
			assertNotNull(errorMessage);
			assertTrue(errorMessage.equals("must be less than or equal to 9999.99"));
		}
	}
	
	@Test
	public void testCreateNonUniqueSupply() {
		SupplyDTO newSupplyDTO = this.createSupply(
				InputStatus.VALID, 
				InputStatus.VALID,
				InputStatus.VALID, 
				InputStatus.VALID);
		Response r = this.supplyService.createSupply(newSupplyDTO);
		
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		Supply savedSupply = this.supplyService.getSingleInstance((Long) r.getEntity());
		
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
		
		newSupplyDTO.setName(this.createSupplyName(InputStatus.VALID));
		
		r = this.supplyService.createSupply(newSupplyDTO);
		assertTrue(r.getStatus() == Response.Status.OK.getStatusCode());
		
		savedSupply = this.supplyService.getSingleInstance((Long) r.getEntity());
		
		assertEquals(newSupplyDTO,new SupplyDTO(savedSupply));
		
	}
	
	public SupplyDTO createSupply(InputStatus nameStatus, InputStatus unityStatus, 
			InputStatus stockStatus, InputStatus priceStatus) {
		SupplyDTO supplyDTO = new SupplyDTO();
		supplyDTO.setName(this.createSupplyName(nameStatus));
		supplyDTO.setUnity(this.createSupplyUnity(unityStatus));
		supplyDTO.setStock(this.createSupplyStock(stockStatus));
		supplyDTO.setPrice(this.createSupplyPrice(priceStatus));
		return supplyDTO;
	}
	
	private String createSupplyName(InputStatus status) {
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
	
	private String createSupplyUnity(InputStatus status) {
		switch (status) {
		case VALID:
			return this.createRandomString(3);
		case INVALID:
			return this.createRandomString(4);
		case EMPTY:
			return "";
		default: 
			return null;
		}	
	}
	
	private Float createSupplyStock(InputStatus status) {
		switch (status) {
		case VALID:
			return this.createRandomFloat(9999.99F);
		case INVALID:
			return 10000.00F;
		case EMPTY:
			return 0.0F;
		default: 
			return null;
		}	
	}
	
	private Float createSupplyPrice(InputStatus status) {
		switch (status) {
		case VALID:
			return this.createRandomFloat(9999.99F);
		case INVALID:
			return 10000.00F;
		case EMPTY:
			return 0.0F;
		default: 
			return null;
		}	
	}
	
}
