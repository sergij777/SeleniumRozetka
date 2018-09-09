package org.mycomp;

import java.util.ArrayList;
import java.util.List;

import org.mycomp.model.Good;
import org.mycomp.service.ParserItems;
import org.openqa.selenium.WebDriver;

public class Runner {

	public static void main(String[] args) {
		ParserItems parser = new ParserItems();
		WebDriver driver = parser.selectListGoods("Смартфоны", "Телефоны", "apple");
		
		List<Good> goodsByCategory = new ArrayList();
		goodsByCategory = parser.navigatorPages(driver);
		
		driver.quit();

	}

}
