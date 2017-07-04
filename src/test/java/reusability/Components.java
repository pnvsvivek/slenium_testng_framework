package reusability;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * This class contains all common methods which are not specific to any application
 * 
 * @author p.nvs.vivek
 */
public class Components {
	
	/**
	 * This method is for reading config.properties file
	 * @param key - this is key used to get value from config.properties file
	 * @return
	 */
	public static String getProperties(String key)
	{
		String value = null;
		try
		{
		FileInputStream fis = new FileInputStream("config.properties");
		Properties prop = new Properties();
		prop.load(fis);
		value = prop.getProperty(key);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}	
		return value;
	}
	
	/**
	 * This method is for selecting the browser type
	 * @param browser - browser type for example: chrome, firefox & ie etc.
	 * @return
	 */
	public static WebDriver selectBrowser(String browser)
	{
		WebDriver driver = null;
		if(browser.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", "drivers//IEDriverServer.exe");
			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(cap);
		}
		else if(browser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "drivers//chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if(browser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "drivers//geckodriver.exe");
			/*FirefoxOptions cap = new FirefoxOptions();
			cap.setBinary("C:/Program Files (x86)/Mozilla Firefox/firefox.exe");*/
			driver = new FirefoxDriver();
		}
		return driver;
	}
	
	/**
	 * This method is for printing console text into text file. This works good for recording errors
	 * @param path - path for text file where console text is printed
	 * @return
	 */
	public static PrintStream consloeToText(String path)
	{
		PrintStream ps = null;
		try
		{
		FileOutputStream fos = new FileOutputStream(path);
		ps = new PrintStream(fos);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return ps;
	}
	
	
	/**
	 * This method is for getting web element based on the locator type
	 * @param driver - WebDriver instance
	 * @param element - element that is brought from page object factory
	 * @return
	 */
	public static List<WebElement> findElementSelenium(WebDriver driver, String element)
	{
		List<WebElement> elements = null;
		String splitter[] = element.split(";");
		String attribute = splitter[0];
		String value = splitter[1];
		if(attribute.equalsIgnoreCase("id"))
		{
			elements = driver.findElements(By.id(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.id(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("name"))
		{
			elements = driver.findElements(By.name(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.name(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("classname"))
		{
			elements = driver.findElements(By.className(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.className(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("linktext"))
		{
			elements = driver.findElements(By.linkText(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.linkText(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("partiallinktext"))
		{
			elements = driver.findElements(By.partialLinkText(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.partialLinkText(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("xpath"))
		{
			elements = driver.findElements(By.xpath(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.xpath(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("css"))
		{
			elements = driver.findElements(By.cssSelector(value));
			if(elements.size()==0)
			{
				for(int i=0; i<30; i++)
				{
					driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
					elements = driver.findElements(By.cssSelector(value));
					if(elements.size()>0)
					{
						break;
					}
				}
			}
		}
		else if(attribute.equalsIgnoreCase("tagname"))
		{
				elements = driver.findElements(By.tagName(value));
				if(elements.size()==0)
				{
					for(int i=0; i<30; i++)
					{
						driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
						elements = driver.findElements(By.tagName(value));
						if(elements.size()>0)
						{
							break;
						}
					}
				}
			}
		return elements;
	}
	
	/**
	 * This method is for verifying whether element is present with the given property
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object factory
	 * @param vpMessage - verification point message(this can be anything)
	 * @param test - ExtentTest instance
	 * @param screenShotpath - screen prints path
	 * @return
	 */
	@SuppressWarnings("finally")
	public boolean VerifyExistence(WebDriver driver, String element,
			String vpMessage, ExtentTest test, String screenShotpath) {
		boolean returnValue = false;
		try {
			List<WebElement> elementList = findElementSelenium(driver, element);
			if (elementList != null && elementList.size() > 0) {

				returnValue = true;
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", elementList.get(0));
				Actions action = new Actions(driver);
				action.moveToElement(elementList.get(0)).build().perform();
				JavascriptExecutor js = (JavascriptExecutor) driver;
				js.executeScript(
						"arguments[0].style.border='2px groove green'",
						elementList.get(0));
				test.log(LogStatus.PASS,"Verification Passed: " + vpMessage+test.addScreenCapture(screenShotpath));
			} else {
				test.log(LogStatus.FAIL,"Verification Failed: "	+ vpMessage+test.addScreenCapture(screenShotpath));

			}

		} catch (Exception e) {
			// TODO: handle exception
			test.log(LogStatus.FAIL, "Exception in Validation. Exception is "
					+ e.getMessage()+test.addScreenCapture(screenShotpath));
			e.printStackTrace();
		} finally {
			return returnValue;
			}

	}
	
	/**
	 * This method is for validating whetehr a particular text is present
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param expectedMessage - expected message for comparing with actual message
	 * @param test - ExtentTest instance
	 * @param screenShotpath - screen prints path
	 */
	public static void verifyTextExistence(WebDriver driver, String element, String expectedMessage, ExtentTest test, String screenShotpath )
	{
		try
		{
			List<WebElement> elements = findElementSelenium(driver, element);
			String ActualValue = " ";
			if(elements!=null&&elements.size()>0)
			{
				int flag=0;
				for(int i=0; i<elements.size();i++)
				{
					ActualValue=elements.get(i).getText();
					if(ActualValue.contains(expectedMessage))
					{
						flag=1;
						Actions action = new Actions(driver);
						action.moveToElement(elements.get(i)).build().perform();
						((JavascriptExecutor)driver).executeScript("arguments[0].style.border='2px groove green'", elements.get(i));
						test.log(LogStatus.PASS, "Verification passed: "+expectedMessage +" matches with Actualtext "+ActualValue+test.addScreenCapture(screenShotpath));
						break;
					}
				}
				if(flag==0)
				{
					test.log(LogStatus.FAIL, "Verification failed: "+expectedMessage +" does not match with Actualtext "+ActualValue+test.addScreenCapture(screenShotpath));
				}
			}
			else
			{
				test.log(LogStatus.FAIL, "Unable to find the element with property "+element+test.addScreenCapture(screenShotpath));
			}			
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, "Exception: "+e.getMessage()+" has been set when identifying the element with "+element+test.addScreenCapture(screenShotpath));
		}
	}	
		
	
/**
 * This method is for setting text in a particular text box
 * @param driver - WebDriver instance
 * @param element - element that is from page object repository
 * @param test - ExtentTest instance
 * @param boxname - box name
 * @param value - value to be inserted in box
 */
	public static void setText(WebDriver driver, String element, ExtentTest test, String boxname, String value)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(0).clear();
			elements.get(0).sendKeys(value);
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(0)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(0));
			test.log(LogStatus.INFO, boxname+" has been set with "+value);
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify the text box with "+boxname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying textbox with "+boxname);
		}
	}	
	
	
	/**
	 * This method is for setting text in a particular text box based on index
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param test - ExtentTest instance
	 * @param boxname - box name
	 * @param value - value to be inserted in box
	 * @param index - this is the index when we have multiple elements with same property
	 */
	public static void setText(WebDriver driver, String element, ExtentTest test, String boxname, String value, int index)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(index).clear();
			elements.get(index).sendKeys(value);
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(index)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(index));
			test.log(LogStatus.INFO, boxname+" has been set with "+value);
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify the text box with "+boxname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying textbox with "+boxname);
		}
	}
	
	/**
	 * This method is for clicking button
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param test - ExtentTest instance
	 * @param buttonname - button name
	 */
	public static void clickButton(WebDriver driver, String element, ExtentTest test, String buttonname)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(0).click();
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(0)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(0));
			test.log(LogStatus.INFO, buttonname+" has been clicked");
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify button with "+buttonname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying button with "+buttonname);
		}
	}
	
	/**
	 * This method is for clicking button based on index
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param test - ExtentTest instance
	 * @param buttonname - button name
	 * @param index - this is the index when we have multiple elements with same property
	 */
	public static void clickButton(WebDriver driver, String element, ExtentTest test, String buttonname, int index)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(index).click();
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(index)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(index));
			test.log(LogStatus.INFO, buttonname+" has been clicked");
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify button with "+buttonname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying button with "+buttonname);
		}
	}
	
	/**
	 * This method is for clicking link based on index
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param test - ExtentTest instance
	 * @param link - link name
	 */
	
	public static void clickLink(WebDriver driver, String element, ExtentTest test, String linkname)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(0).click();
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(0)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(0));
			test.log(LogStatus.INFO, linkname+" has been clicked");
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify link with "+linkname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying link with "+linkname);
		}
	}
	
	/**
	 * This method is for clicking link based on index
	 * @param driver - WebDriver instance
	 * @param element - element that is from page object repository
	 * @param test - ExtentTest instance
	 * @param linkname - button name
	 * @param index - this is the index when we have multiple elements with same property
	 */
	public static void clickLink(WebDriver driver, String element, ExtentTest test, String linkname, int index)
	{
		try
		{
		List<WebElement> elements = findElementSelenium(driver, element);
		if(elements.size()!= 0)
		{
			elements.get(index).click();
			Actions action = new Actions(driver);
			action.moveToElement(elements.get(index)).build().perform();
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].style.border='2px groove green'", elements.get(index));
			test.log(LogStatus.INFO, linkname+" has been clicked");
		}
		else
		{
			test.log(LogStatus.FAIL, "Not able to identify link with "+linkname);
		}
		}
		catch(Exception e)
		{
			test.log(LogStatus.FAIL, e.getMessage()+" has been set while identifying link with "+linkname);
		}
	}
	
	
}
