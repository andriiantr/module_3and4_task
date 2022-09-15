package page.marmelab;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ClassExpectations {
    protected static WebDriver driver;

    public ClassExpectations (WebDriver driver) {ClassExpectations.driver = driver;}

    public void open(String url){
        driver.get(url);
    }

    public WebElement waitElementClickable (WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    public WebElement waitVisibilityOfAllElements (WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfAllElements(element));
        return element;
    }

    public WebElement waitVisibilityOf (WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public WebElement waitElementNotClickable (WebElement element) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until((ExpectedConditions.not(ExpectedConditions.elementToBeClickable(element))));
        return element;
    }



    public ClassExpectations reloadRefreshButton () {
        WebElement refreshIcon = driver.findElement(By.xpath("//button[@class = 'MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeLarge RaLoadingIndicator-loadedIcon css-1l1167e']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(refreshIcon));
        return this;
    }

}


