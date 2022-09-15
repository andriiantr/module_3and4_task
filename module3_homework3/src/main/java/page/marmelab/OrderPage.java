package page.marmelab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class OrderPage {
    protected static WebDriver driver;

    private final By byOrder = By.xpath("//a[text() = 'Orders']");
    private final By byDelivered = By.xpath("//button[contains(text(), 'delivered')]");
    private final String byPath = "//tbody[@class = ''MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e'']/tr[{0}]//span[contains(@class, ''MuiCheckbox-root MuiCheckbox-colorPrimary MuiButtonBase'')]";
    private final By byTitle = By.xpath("//span[@data-field = 'returned']/span");

    private final By bySelected = By.xpath("//h6[@class = 'MuiTypography-root MuiTypography-subtitle1 css-1uk7wdo']");

    //WebDriverWait waitSelectCheckBoxes = new WebDriverWait(driver, Duration.ofSeconds(5));





    public OrderPage(WebDriver driver) {
        OrderPage.driver = driver;
    }

    public OrderPage navigateDelivered () {
        driver.findElement(byOrder).click();
        driver.findElement(byDelivered).click();
        assertTrue("Перешли на вкладку Delivered", isDelivered());
        return this;
    }

    public boolean isDelivered () {return driver.findElement(byTitle).getText().equals("Returned");}

    public void selectItems() {
        for (int i = 1; i <= 3; i++)
        {
            String tmp = Integer.toString(i);
            String path = MessageFormat.format(byPath, tmp);
            WebElement checkBox = driver.findElement(By.xpath(path));
            checkBox.click();
        }
    }

    public boolean isSelected () {
        //waitSelectCheckBoxes.until(ExpectedConditions.textToBePresentInElementValue(bySelected, "3 items selected"));
        return driver.findElement(bySelected).getText().equals("3 items selected");}
 }
