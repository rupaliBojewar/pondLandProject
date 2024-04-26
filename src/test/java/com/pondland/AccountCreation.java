package com.pondland;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class AccountCreation {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void launchApplication() {
        driver = new ChromeDriver();
        driver.get("https://www.poundland.co.uk/");
        driver.manage().window().maximize();
    }

    @AfterTest
    public void quitBrowser(){
        driver.quit();
    }

    @Test
    public void verifyNavigateToSignUpPage() {

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        //WebElement sighInBtn = driver.findElement(By.cssSelector(".top-nav__link"));

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Accept All Cookies']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".top-nav__item.customer-account"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Create an Account']"))).click();

        WebElement closeBtn=driver.findElement(By.xpath("//div[@data-testid='om-overlays-close']"));

        if (closeBtn.isDisplayed())
        {
            closeBtn.click();
        }

        String expectedPageTitle="Create New Customer Account";

        String actualPageTitle=driver.getTitle();

        System.out.println(actualPageTitle);

        Assert.assertEquals(expectedPageTitle,actualPageTitle);
    }

    @Test
    public void verifyAllElementsArePresent(){

    }
}
