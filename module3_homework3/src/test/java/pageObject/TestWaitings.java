package pageObject;

import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.marmelab.LoginPage;

import java.time.Duration;

public class TestWaitings {

    @Test
    public void Test() {
        System.setProperty("webdriver.chrome.driver", "/Users/Andriyan.Trapeznikov/Documents/chromedriver/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.manage().window().maximize();

        driver.get("https://marmelab.com/react-admin-demo/#/invoices?displayedFilters=%7B%7D&filter=%7B%7D&order=DESC&page=1&perPage=25&sort=customer_id");

        LoginPage page = new LoginPage(driver);
        page.login("demo", "demo");

        WebElement selectDateSince = driver.findElement(By.id("date_gte"));
        selectDateSince.click();
        selectDateSince.sendKeys(Keys.ARROW_LEFT);
        selectDateSince.sendKeys(Keys.ARROW_LEFT);
        selectDateSince.sendKeys("01012021");
        WebElement refreshIcon = driver.findElement(By.xpath("//button[@class = 'MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeLarge RaLoadingIndicator-loadedIcon css-1l1167e']"));
        //new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(refreshIcon));

        WebElement selectDateBefore = driver.findElement(By.id("date_lte"));
        selectDateBefore.click();
        selectDateBefore.sendKeys(Keys.ARROW_LEFT);
        selectDateBefore.sendKeys(Keys.ARROW_LEFT);
        selectDateBefore.sendKeys("01082022");


        //new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(refreshIcon));

        //new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(refreshIcon));

        //new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOf(refreshIcon));
        WebElement selectCustomer = driver.findElement(By.xpath("//tbody[@class = 'MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e']/tr[1]"));
        selectCustomer.click();

        
    }
}
