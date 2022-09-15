package page.marmelab;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.MessageFormat;

import static org.junit.Assert.assertTrue;


public class CustomersPage {
    protected static WebDriver driver;

    public CustomersPage (WebDriver driver) {CustomersPage.driver = driver;}

    private final By byCustomers = By.xpath("//a[text() = 'Customers']");

    public boolean isCustomersPage () {return driver.findElement(By.xpath("//div[@class = 'MuiCardContent-root css-14x6a5n']")).isDisplayed();}

    public CustomersPage navigateCustomers () {
        driver.findElement(byCustomers).click();
        assertTrue("Перешли на старницу Customers", isCustomersPage());
        return this;
    }

    public String getLastName (String[] listOfFields) {
        String name = listOfFields[2];
        int ind = name.indexOf(' ');
        name = name.substring(ind).trim();
        return name;
    }

    public CustomersPage search (String lastName, String[] listOfFields) throws InterruptedException {
        By bySearch = By.id("q");
        driver.findElement(bySearch).sendKeys(Keys.CONTROL + "a" + Keys.DELETE);
        driver.findElement(bySearch).sendKeys(lastName);
        driver.findElement(bySearch).sendKeys(Keys.ENTER);


        Thread.sleep(3000);
        String countOfCustomers = driver.findElement(By.xpath("//p[@class = 'MuiTablePagination-displayedRows css-1chpzqh']")).getText();
        String count = countOfCustomers.substring(countOfCustomers.lastIndexOf(' ')).trim();
        int max = Integer.parseInt(count);

        for (int i = 1; i <= max; i++)
        {
            String path = MessageFormat.format("//tbody[@class = ''MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e'']/tr[{0}]//div[@class = ''MuiTypography-root MuiTypography-body2 css-1wk3cmv'']", Integer.toString(i));
            String verName = driver.findElement(By.xpath(path)).getText();
            if (verName.equals(listOfFields[2])) {
                WebElement targetCustomer = driver.findElement(By.xpath(path));
                targetCustomer.click();
                break;
            }
            if (i == max) {
                throw new RuntimeException("Пользователь " + verName + " не найден");
            }
        }


        return this;
    }

    public void clearField (WebElement element) throws InterruptedException {
        element.sendKeys(Keys.CONTROL + "a");
        element.sendKeys(Keys.DELETE);
    }

    public void fillField (WebElement element, String value) {
        element.sendKeys(value);
    }

    public void saveChanges () {
        WebElement saveButton = driver.findElement(By.xpath("//button[@aria-label = 'Save']"));
        saveButton.click();
    }



}
