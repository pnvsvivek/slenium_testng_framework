package testScripts;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import google_pageObjects.Google_searchPage;
import reusability.Runner;
import reusability.Components;
import reusability.Utilities;

public class Google {

	WebDriver driver;
	ExtentTest test;
	PrintStream ps = Runner.ps;
	HashMap<String, String> hm;
	
	@BeforeMethod
	public void setUp()
	{
		try
		{
		hm = Utilities.readExcel_dataDriven(Components.getProperties("TDMPath"), Components.getProperties("SheetName"));
		driver = Components.selectBrowser(hm.get("Browser"));
		driver.get(Components.getProperties("googleurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		catch(Exception e)
		{
			e.printStackTrace(ps);
		}
	}
	@Test
	public void Google_Search()
	{
		String testcaseName = "Google_Search";
		try
		{
		test = Runner.report.startTest(testcaseName);
		test.assignAuthor("Vivek1");
		test.assignCategory("sprint1");
		Google_searchPage.searchInGoogle(driver, test, hm.get("Search_Keyword"));
		Components.verifyTextExistence(driver, Google_searchPage.links, hm.get("Expected Text"), test, Utilities.screenPrints(driver, testcaseName));
		test.log(LogStatus.INFO, "Expected text is verified"+test.addScreenCapture(Utilities.screenPrints(driver, testcaseName)));
		}
		catch(Exception e)
		{
			e.printStackTrace(ps);
		}
		Runner.report.endTest(test);
	}
	@Test
	public void Google_Search_Compare()
	{
		String testcaseName = "Google_Search_Compare";
		try
		{
		test = Runner.report.startTest(testcaseName);
		test.assignAuthor("Vivek");
		test.assignCategory("sprint1");
		Google_searchPage.searchInGoogle(driver, test, hm.get("Search_Keyword"));
		Components.verifyTextExistence(driver, Google_searchPage.links, hm.get("Expected Text"), test, Utilities.screenPrints(driver, testcaseName));
		test.log(LogStatus.INFO, "Expected text is verified"+test.addScreenCapture(Utilities.screenPrints(driver, testcaseName)));
		List<WebElement> li = Components.findElementSelenium(driver, Google_searchPage.links);
		System.out.println("the size of links displayed "+li.size());
		for(int i=0; i<li.size();i++)
		{
			if(li.get(i).getText().contains(hm.get("Expected Text")))
			{
				li.get(i).click();
				break;
			}
		}
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.INFO, "the title of the page displayed "+driver.getTitle()+test.addScreenCapture(Utilities.screenPrints(driver, testcaseName)));
		}
		catch(Exception e)
		{
			e.printStackTrace(ps);
		}
		Runner.report.endTest(test);
	}
	@AfterMethod
	public void tearDown()
	{
		driver.close();
	}
}
