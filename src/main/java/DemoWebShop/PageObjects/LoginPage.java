package DemoWebShop.PageObjects;

import WebShop.AbstractComponents.AbstractComponent;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class LoginPage extends AbstractComponent {
	WebDriver driver;

	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}
	@FindBy(xpath = "//input[@id='Email']")
	WebElement userEmail;
	@FindBy(xpath = "//input[@id='Password']")
	WebElement userPassword;
	@FindBy(xpath = "(//a[@class='account'])[1]")
	WebElement registerID;
	@FindBy(xpath = "//input[@value='Log in']")
	WebElement logInButton;
	@FindBy(xpath = "(//span[contains(text(),'Login was unsuccessful. Please correct the errors ')])[1]")
	WebElement errorMessage;




	public void loginApplication(String email, String password) {
		Allure.step("Login with email:"+ email +" and password:"+password);
		userEmail.sendKeys(email);
		userPassword.sendKeys(password);
		logInButton.click();

	}
	public void logOut() {
		waitForElementToAppear(By.xpath("(//a[normalize-space()='Log out'])[1]"));
		driver.findElement(By.xpath("(//a[normalize-space()='Log out'])[1]")).click();

	}

	public String getID() {
		waitForWebElementToAppear(registerID);

		String id = registerID.getText();
		return id;

	}
	public String getErrorMessage() {
		waitForWebElementToAppear(errorMessage);
		String id = errorMessage.getText();
		return id;
	}



}
