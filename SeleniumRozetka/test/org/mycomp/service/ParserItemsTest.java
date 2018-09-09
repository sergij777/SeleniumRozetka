package org.mycomp.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mycomp.model.Good;
import org.openqa.selenium.WebDriver;

public class ParserItemsTest {
	
	Good good;
	ParserItems parser;
	List<Good> goods;
	WebDriver driver;
	String testTitle;
	String expectTitle;
	List<String> testLinks;
	String currentLink;
	

	@Before
	public void setUpBeforeClass() throws Exception {
		good = new Good();
		parser = new ParserItems();
		goods = new ArrayList();
		testLinks = new ArrayList();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	public void testSelectListGoods() {
		driver = parser.selectListGoods("Смартфоны", "Телефоны", "xiaomi");		
		testTitle = driver.getCurrentUrl();		
		expectTitle = "https://rozetka.com.ua/mobile-phones/c80003/producer=xiaomi/";
		
		assertEquals(expectTitle, testTitle);
		driver.quit();
		
		driver = parser.selectListGoods("Бытовая", "Холодильники", "bosch");		
		testTitle = driver.getCurrentUrl();		
		expectTitle = "https://bt.rozetka.com.ua/refrigerators/c80125/filter/producer=bosch/";
		
		assertEquals(expectTitle, testTitle);
		driver.quit();
	}

	@Test
	public void testNavigatorPages() {
		driver = parser.selectListGoods("Смартфоны", "Телефоны", "xiaomi");	
		goods = parser.navigatorPages(driver);
		driver.quit();
		
		// 65 - current count goods of this category
		assertEquals(65, goods.size());
		}
}
