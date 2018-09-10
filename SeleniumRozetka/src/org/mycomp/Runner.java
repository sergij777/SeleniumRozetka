package org.mycomp;

import java.util.ArrayList;
import java.util.List;

import org.mycomp.model.Good;
import org.mycomp.service.ParserItems;
import org.openqa.selenium.WebDriver;

/** Parser rozetka.ua
 * 
 * @author Serhii Savchuk
 *
 */
public class Runner {

	public static void main(String[] args) {
		ParserItems parser = new ParserItems();
		WebDriver driver = parser.selectListGoods("Смартфоны", "Телефоны", "nokia");
		
		List<Good> goodsByCategory = new ArrayList();
		goodsByCategory = parser.navigatorPages(driver);
		
		driver.quit();

	}

}
