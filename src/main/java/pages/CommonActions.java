package pages;

import org.openqa.selenium.WebElement;

public class CommonActions {

    public void clickOnElement(WebElement element) {
        element.click();
    }

    public void typeInInputBox(WebElement element, String data) {
        element.sendKeys(data);
    }
}
