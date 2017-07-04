package testScripts;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import fb_pageObjects.FB_LoginPage;
import reusability.Runner;
import reusability.Components;
import reusability.Utilities;

public class Facebook {
	WebDriver driver;
	ExtentTest test;
	PrintStream ps = Runner.ps;
	HashMap<String, String> hm;
	
	@BeforeMethod
	public void setUp()
	{
		try
		{
		hm=Utilities.readExcel_dataDriven(Components.getProperties("TDMPath"), Components.getProperties("SheetName"));	
		driver = Components.selectBrowser(hm.get("Browser"));
		driver.get(Components.getProperties("appurl"));
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		}
		catch(IOException e)
		{
			e.printStackTrace(ps);
		}
	}
	
	@Test
	public void facebook_Login() throws IOException
	{
		String testcaseName = "facebook_Login";
		try
		{
		test = Runner.report.startTest(testcaseName);
		test.assignAuthor("Vivek");
		test.assignCategory("sprint1");
		FB_LoginPage.Login(driver, hm.get("username"), hm.get("password"), test);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		test.log(LogStatus.INFO, "Facebook is logged in successfuly"+ test.addScreenCapture(Utilities.screenPrints(driver, testcaseName)));
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+e.getMessage()+" has been raised"+test.addScreenCapture(Utilities.screenPrints(driver, testcaseName)));
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
