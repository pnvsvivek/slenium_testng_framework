package fb_pageObjects;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentTest;

import reusability.Components;

public class FB_LoginPage {

	public static final String userName = "id;email";
	public static final String passWord = "id;pass";
	public static final String sign_In = "xpath;//input[contains(@data-testid,'royal_login_button')]";
	
	public static void Login(WebDriver driver,String username, String password, ExtentTest test)
	{
		Components.setText(driver, userName, test, "usernameBox", username);
		Components.setText(driver, passWord, test, "passwordBox", password);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Components.clickButton(driver, sign_In, test, "signIn button");
	}
}
