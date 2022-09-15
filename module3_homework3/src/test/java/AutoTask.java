import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.marmelab.LoginPage;
import page.marmelab.OrderPage;


import java.sql.SQLOutput;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class AutoTask {

    String url = "https://marmelab.com/react-admin-demo/#/login";
    @Test
    public void test() {
        System.setProperty("webdriver.chrome.driver", "/Users/Andriyan.Trapeznikov/Documents/chromedriver/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();

        driver.get(url);

        LoginPage page = new LoginPage(driver);
        page.login("demo", "demo");

        assertTrue("Мы попали на главную страницу", page.isMainPage());

        OrderPage order = new OrderPage(driver);
        order.navigateDelivered();

        order.selectItems();

        assertTrue("Выбраны 3 элемента", order.isSelected());

        WebElement navigateInvoices = driver.findElement(By.xpath("//a[text() = 'Invoices']"));
        navigateInvoices.click();

        WebElement selectDateSince = driver.findElement(By.id("date_gte"));
        selectDateSince.click();
        selectDateSince.sendKeys(Keys.ARROW_LEFT);
        selectDateSince.sendKeys(Keys.ARROW_LEFT);
        selectDateSince.sendKeys("01012021");

        WebElement selectDateBefore = driver.findElement(By.id("date_lte"));
        selectDateBefore.click();
        selectDateBefore.sendKeys(Keys.ARROW_LEFT);
        selectDateBefore.sendKeys(Keys.ARROW_LEFT);
        selectDateBefore.sendKeys("01082022");
        //new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//tr[contains(@class, 'MuiTableRow-root MuiTableRow-hover RaDatagrid-row')]"))));

        WebElement refreshIcon = driver.findElement(By.xpath("//button[@class = 'MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeLarge RaLoadingIndicator-loadedIcon css-1l1167e']"));
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(refreshIcon));

        String nameFirst = driver.findElement(By.xpath("//tbody[@class = 'MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e']/tr[1]/td[5]//div[@class = 'MuiTypography-root MuiTypography-body2 css-1wk3cmv']")).getText();
        System.out.println(nameFirst);
        if (nameFirst.equals("Korey Mohr")) {
            throw new RuntimeException("Проверка не пройдена");
        }

        WebElement selectCustomer = driver.findElement(By.xpath("//tbody[@class = 'MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e']/tr[1]"));
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfAllElements(selectCustomer));
        selectCustomer.click();

        String numberInvoice = selectCustomer.findElement(By.xpath("//td[3]/span")).getText();
        System.out.println(numberInvoice);

        String date = selectCustomer.findElement(By.xpath("//td[4]/span")).getText();
        System.out.println(date);

        String name = selectCustomer.findElement(By.xpath("//td[5]/span//div[@class = 'MuiTypography-root MuiTypography-body2 css-1wk3cmv']")).getText();
        System.out.println(name);

        String numOrder = selectCustomer.findElement(By.xpath("//td[7]//span[@class = 'MuiTypography-root MuiTypography-body2 css-68o8xu']")).getText();
        System.out.println(numOrder);

        String[] list  = new String[] {numberInvoice, date, name, numOrder};

        WebElement webForm = driver.findElement(By.xpath("//div[@class = 'MuiCardContent-root css-1qw96cp']"));
        String nameFromWeb = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 css-9l3uo3']")).getText();
        int tmp = nameFromWeb.indexOf('\n');
        nameFromWeb = nameFromWeb.substring(0, tmp);
        System.out.println(nameFromWeb);

        String numberFromInvocie = webForm.findElement(By.xpath("//h6[@class = 'MuiTypography-root MuiTypography-h6 MuiTypography-alignRight MuiTypography-gutterBottom css-d54f2v']")).getText();
        numberFromInvocie = numberFromInvocie.substring(8);
        System.out.println(numberFromInvocie);

        String numFromOrder = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body2 MuiTypography-alignCenter MuiTypography-gutterBottom css-1kggr9']")).getText();
        System.out.println(numFromOrder);

        String formDate = webForm.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 MuiTypography-alignCenter MuiTypography-gutterBottom css-zjt4oc']")).getText();
        System.out.println(formDate);

        String[] listFrom  = new String[] {numberFromInvocie, formDate, nameFromWeb, numFromOrder};

        for (int i = 0; i < list.length; i++) {
            if (!list[i].equals(listFrom[i])) {
                throw new RuntimeException("Значение " + list[i] + " не совпадает со значением " + listFrom[i]);
            }
        }

        WebElement customers = driver.findElement(By.xpath("//a[text() = 'Customers']"));
        customers.click();

        WebElement search = driver.findElement(By.id("q"));
        search.click();
        int ind = name.indexOf(' ');
        search.sendKeys(name.substring(ind).trim());
        search.sendKeys(Keys.ENTER);

        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class = 'MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeLarge RaLoadingIndicator-loadedIcon css-1l1167e']")));

        String countOfCustomers = driver.findElement(By.xpath("//p[@class = 'MuiTablePagination-displayedRows css-1chpzqh']")).getText();
        String count = countOfCustomers.substring(countOfCustomers.lastIndexOf(' ')).trim();
        int max = Integer.parseInt(count);

        for (int i = 1; i <= max; i++)
        {
            String path = MessageFormat.format("//tbody[@class = ''MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e'']/tr[{0}]//div[@class = ''MuiTypography-root MuiTypography-body2 css-1wk3cmv'']", Integer.toString(i));
            String verName = driver.findElement(By.xpath(path)).getText();
            if (verName.equals(nameFromWeb)) {
                WebElement targetCustomer = driver.findElement(By.xpath(path));
                targetCustomer.click();
                break;
            }
            if (i == max) {
                throw new RuntimeException("Пользователь " + verName + " не найден");
            }
        }

        WebElement address = driver.findElement(By.id("address"));
        String addressBefore = address.getText();
        address.click();
        String addressAfter = " Minisota";
        address.sendKeys(addressAfter);

        WebElement saveButton = driver.findElement(By.xpath("//button[@aria-label = 'Save']"));
        saveButton.click();

        navigateInvoices.click();

        WebElement addressForm = driver.findElement(By.xpath("//p[@class = 'MuiTypography-root MuiTypography-body1 css-9l3uo3']"));
        String addForm = addressForm.getText();
        int indf = addForm.lastIndexOf('\n');
        if(!addressAfter.equals(addForm.substring(indf-addressAfter.length(), indf))) {
            throw new RuntimeException("Адресс не изменился");
        }


        customers.click();
        for (int i = 1; i <= max; i++)
        {
            String path = MessageFormat.format("//tbody[@class = ''MuiTableBody-root datagrid-body RaDatagrid-tbody css-1xnox0e'']/tr[{0}]//div[@class = ''MuiTypography-root MuiTypography-body2 css-1wk3cmv'']", Integer.toString(i));
            String verName = driver.findElement(By.xpath(path)).getText();
            if (verName.equals(nameFromWeb)) {
                WebElement targetCustomer = driver.findElement(By.xpath(path));
                targetCustomer.click();
                break;
            }
            if (i == max) {
                throw new RuntimeException("Пользователь " + verName + " не найден");
            }
        }
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.id("address")));
        driver.findElement(By.id("address")).click();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.id("address")));
        driver.findElement(By.id("address")).clear();
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.id("address")));
        driver.findElement(By.id("address")).sendKeys(addressBefore);
        saveButton.click();




        //driver.quit();
    }
}
