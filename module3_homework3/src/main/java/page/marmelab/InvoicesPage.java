package page.marmelab;

import org.checkerframework.checker.units.qual.C;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class InvoicesPage {
    protected static WebDriver driver;

    protected final By byInvoice = By.xpath("//a[text() = 'Invoices']");
    protected final By byCheckInvoice = By.xpath("//span[@data-field = 'date']/span");
    protected final By byDateSince = By.id("date_gte");
    protected final By byDateBefore = By.id("date_lte");


    public InvoicesPage (WebDriver driver) {InvoicesPage.driver = driver; }

    public InvoicesPage isInvoicesPage () {
        driver.findElement(byInvoice).click();
        assertTrue("Мы перешли на страницу Invoices", isInvoices());
        return this;
    }

    public InvoicesPage setDate (String dateSince, String dateBefore) {
        driver.findElement(byDateSince).click();
        driver.findElement(byDateSince).sendKeys(Keys.ARROW_LEFT);
        driver.findElement(byDateSince).sendKeys(Keys.ARROW_LEFT);
        driver.findElement(byDateSince).sendKeys(dateSince);

        driver.findElement(byDateBefore).click();
        driver.findElement(byDateBefore).sendKeys(Keys.ARROW_LEFT);
        driver.findElement(byDateBefore).sendKeys(Keys.ARROW_LEFT);
        driver.findElement(byDateBefore).sendKeys(dateBefore);


        return this;
    }
    public boolean isInvoices () {return driver.findElement(byCheckInvoice).getText().equals("Invoice date");}


    public boolean isNotKorey () throws InterruptedException {
        Thread.sleep(5000);
        String nameFirst = driver.findElement(By.xpath("//tbody[@class = 'MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e']/tr[1]/td[5]//div[@class = 'MuiTypography-root MuiTypography-body2 css-1wk3cmv']")).getText();
        return nameFirst.equals("Korey  Mohr");
    }

    public WebElement selectAnyCustomer (String numberOfCustomer) throws InterruptedException {
        //WebElement el = driver.findElement(By.xpath("//tbody[@class = 'MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e']/*"));
        //ClassExpectations wait = new ClassExpectations(driver);
        //wait.waitVisibilityOfAllElements(el);
        Thread.sleep(5000);
        String path = MessageFormat.format("//tbody[@class = ''MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e'']/tr[{0}]", numberOfCustomer);
        WebElement selectCustomer = driver.findElement(By.xpath(path));
        return selectCustomer;
    }
    public InvoicesPage selectCustomer () throws InterruptedException {
        selectAnyCustomer("1").click();
        assertTrue("Заказчик выбран", isCustomerSelected());
        return this;
    }

    public boolean isCustomerSelected () {return driver.findElement(By.xpath("//div[@class = 'MuiCardContent-root css-1qw96cp']")).isDisplayed();}

    public String[] listOfFields () throws InterruptedException {
        WebElement selectCustomer = selectAnyCustomer("1");
        String numberInvoice = selectCustomer.findElement(By.xpath("//td[3]/span")).getText();

        String date = selectCustomer.findElement(By.xpath("//td[4]/span")).getText();

        String name = selectCustomer.findElement(By.xpath("//td[5]/span//div[@class = 'MuiTypography-root MuiTypography-body2 css-1wk3cmv']")).getText();

        String address = selectCustomer.findElement(By.xpath("//td[6]/span")).getText();
        int i = address.indexOf(',');
        address = address.substring(0, i);

        String numOrder = selectCustomer.findElement(By.xpath("//td[7]//span[@class = 'MuiTypography-root MuiTypography-body2 css-68o8xu']")).getText();

        String[] list  = new String[] {numberInvoice, date, name, address, numOrder};
        return  list;
    }

    public String[] listOfFieldsFromForm () {
        WebElement webForm = driver.findElement(By.xpath("//div[@class = 'MuiCardContent-root css-1qw96cp']"));
        String nameFromWeb = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 css-9l3uo3']")).getText();
        int tmp = nameFromWeb.indexOf('\n');
        nameFromWeb = nameFromWeb.substring(0, tmp);

        String numberFromInvocie = webForm.findElement(By.xpath("//h6[@class = 'MuiTypography-root MuiTypography-h6 MuiTypography-alignRight MuiTypography-gutterBottom css-d54f2v']")).getText();
        numberFromInvocie = numberFromInvocie.substring(8);

        String numFromOrder = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body2 MuiTypography-alignCenter MuiTypography-gutterBottom css-1kggr9']")).getText();

        String addressForm = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 css-9l3uo3']")).getText();
        int i = addressForm.indexOf("\n");
        int j = addressForm.lastIndexOf("\n");
        addressForm = addressForm.substring(i + 1, j);

        String formDate = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 MuiTypography-alignCenter MuiTypography-gutterBottom css-zjt4oc']")).getText();

        String[] listFrom  = new String[] {numberFromInvocie, formDate, nameFromWeb, addressForm, numFromOrder};
        return listFrom;
    }

    public boolean fieldsAreEquals (String[] listOfFields, String[] listOfFieldsFromForm) {
        for (int i = 0; i < listOfFields.length; i++) {
            if (!listOfFields[i].equals(listOfFieldsFromForm[i])) {
                throw new RuntimeException("Значение " + listOfFields[i] + " не совпадает со значением " + listOfFieldsFromForm[i]);
            }
        }
        return true;
    }
}
