package org.jboss.tools.examples.test.it;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;

public class CreateProductIntegrationTest {

	WebDriver wd;	
	
	@FindBy(linkText = "Products")
	private WebElement productsLink;
	
	@FindBy(id = "welcome")
	private WebElement welcomeBillboard;
	
	@BeforeClass
	public void configureDriver() {
		System.setProperty("webdriver.chrome.driver", "/Applications/chromedriver");
		this.wd = new ChromeDriver();
		this.wd.get("http://localhost:8080/cloudpos");
	}
	
	@Test
	public void open() {
		assertTrue(this.welcomeBillboard.getText().contains("Welcome to Cloud POS"));
	}
	
	@Test
	public void accessProductsPage() {
		this.productsLink.click();
		assertTrue(this.wd.getPageSource().contains("Black Coffee"));
		
		
	}

	@After
	public void tearDown() {
		this.wd.quit();
	}
}
