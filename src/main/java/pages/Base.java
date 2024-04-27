package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class Base {
    public static WebDriver driver;

    @BeforeSuite
    public void launchBrowser() {
        driver = new ChromeDriver();
    }

    @BeforeTest
    public void launchApplication() {
        driver.get("https://www.poundland.co.uk/");
    }

    @AfterSuite
    public void killSession() {
        driver.quit();
    }
}
