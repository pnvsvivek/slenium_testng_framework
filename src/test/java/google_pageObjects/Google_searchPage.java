package google_pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import reusability.Components;

public class Google_searchPage {
		public static final String search_textBox= "id;lst-ib";
		public static final String seach_Button = "id;_fZl";
		public static final String links = "tagname;a";
		
		public static void searchInGoogle(WebDriver driver, ExtentTest test, String searchValue)
		{
			Components.setText(driver, search_textBox, test, "google_Search_Box", searchValue);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			Components.clickButton(driver, seach_Button, test, "searchButton");
		}
}
