package org.mycomp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.mycomp.model.Good;
import org.mycomp.service.Timer;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**Class of goods parser by parameters 
 * @author Serhii Savchuk
 * @version 1.0
 */
public class ParserItems {	
	
	 private static final String SEPARATOR = System.getProperty("file.separator");
	 
	 private static final String USER_DIR = System.getProperty("user.dir");
	 
	 private static final String DRIVER_PATH = USER_DIR + SEPARATOR + "lib" + SEPARATOR + "chromedriver.exe";
	 	 
	 private static final String BASE_URL = "https://rozetka.com.ua/";

	 public static WebDriver getWebDriver() {		 	  
		 System.setProperty("webdriver.chrome.driver", DRIVER_PATH);	  
		 WebDriver driver = new ChromeDriver();	  	  
		 return driver;
	 }
	 
	 /**The method outs the first search page by the parameters
	  * 
	  * @param partOfType part of name category
	  * @param partOfName part of name subcategory
	  * @param brandName brand manufacturer
	  * @return driver
	  */
	 public WebDriver selectListGoods(String partOfType, String partOfName, String brandName) {
		 WebDriver driver = getWebDriver();		  
		 Timer.waitSeconds(5);
		 driver.get(BASE_URL);
		 Timer.waitSeconds(5);
		 
		 WebElement categoryType = driver.findElement(By.partialLinkText(partOfType));
		 String categoryTypeLink = categoryType.getAttribute("href");
		 driver.get(categoryTypeLink);		 
		 Timer.waitSeconds(10);
		 WebElement categoryName = driver.findElement(By.partialLinkText(partOfName)); 
		 String categoryNameLink = categoryName.getAttribute("href");
		 driver.get(categoryNameLink);
		 Timer.waitSeconds(10);
		 
		 List<WebElement> listBrands = driver.findElements(By.className("pab-img"));
		 for (WebElement brandBlock : listBrands) {
			WebElement brandTagLink = brandBlock.findElement(By.tagName("a"));
			String brandLink = brandTagLink.getAttribute("href");			
			
			if (brandLink.endsWith(brandName + "/")) {				
				driver.get(brandLink);
				break;
			}
		}
		 Timer.waitSeconds(10);
		 
		 return driver;
	 }
	
	/**The method finds all the goods on the first page and on the others, if they exist
	 * 
	 * @param driver
	 * @return ArrayList of objects Class Good
	 */
	public List<Good> navigatorPages(WebDriver driver) {
		List<Good> goods = new ArrayList();
		
		boolean nextPageExist = true;		
		while (nextPageExist) {		 
		 
		 WebElement goodsBlock = driver.findElement(By.id("catalog_goods_block"));		 
		 List<WebElement> goodsCatalog = goodsBlock.findElements(By.className("g-i-tile-i-box-desc"));
		 
		 for (WebElement goodElement : goodsCatalog) {
			 Good good = new Good();
			 WebElement goodLinkTag = goodElement.findElement(By.tagName("a"));			 
			 String goodLink = goodLinkTag.getAttribute("href");
			 good.setGoodUrl(goodLink);			 
			 
			 WebElement goodPriceTag = null;			 
			 try {
			 goodPriceTag = goodElement.findElement(By.className("g-price-uah"));
			 } catch (NoSuchElementException e) {				
			 }
			 String goodPrice;
			 if (goodPriceTag != null ) { 
				 goodPrice = goodPriceTag.getText();
				 good.setPrice(goodPrice);
			 }
			 else {
				 goodPrice = "Not available";				 
				 good.setPrice(goodPrice);
			 }			 
			 goods.add(good);
		}  
		 
		 // Navigator to next pages
		 WebElement navigationBlock = null;
		 try {
		 navigationBlock = driver.findElement(By.id("navigation_block"));
		 } catch (NoSuchElementException e) {				
		 }
		 if (navigationBlock != null) {
		 List<WebElement> blockPages = navigationBlock.findElements(By.tagName("li"));
		 boolean active = false;
		 WebElement nextPage = null;
		 for (WebElement numberPage : blockPages) {
			 if (active) {
				 nextPage = numberPage;				 
				 break;
			 }			 
			 if (numberPage.getAttribute("class").contains("active")) {
				 active = true;				 
			 }						 			 
		}
		 if (nextPage != null) {
			 nextPageExist = true;
			 nextPage.click();	
			 Timer.waitSeconds(10);			 
		 }
		 else {
			 nextPageExist = false;
			 System.out.println("All pages passed");
		 }
		 }
		 else {
			 nextPageExist = false;
			 System.out.println("Only first page has been found");
		 }
		 
		} // end while
		 
		 return goods;
	}
}
