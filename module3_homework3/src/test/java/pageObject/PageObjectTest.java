package pageObject;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.marmelab.CustomersPage;
import page.marmelab.InvoicesPage;
import page.marmelab.LoginPage;
import page.marmelab.OrderPage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

public class PageObjectTest {
    final String url = "https://marmelab.com/react-admin-demo/#/login";

    @Test
    public void demoTest() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "/Users/Andriyan.Trapeznikov/Documents/chromedriver/chromedriver.exe");

        WebDriver driver = new ChromeDriver();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        driver.manage().window().maximize();

        driver.get(url);
        //Авторизация
        LoginPage page = new LoginPage(driver);
        page.login("demo", "demo");
        //Проверка перехода на главную страницу
        assertTrue("Мы попали на главную страницу", page.isMainPage());

        //Переход на страницу Order
        OrderPage order = new OrderPage(driver);
        order.navigateDelivered();
        //Выбор трех первых элементов списка
        order.selectItems();
        //Проверка, что элементы выбраны
        assertTrue("Выбраны 3 элемента", order.isSelected());
        //Создание экземпляра класса
        InvoicesPage invoice = new InvoicesPage(driver);

        //Переход на страницу с инвойсами
        invoice.isInvoicesPage();
        //Установка даты в филтры
        invoice.setDate("01012021", "01082022");
        //Проверка, что первый Customer не Korey
        assertFalse("Проверка пройдена", invoice.isNotKorey());
        //Выбор первого кастомера
        invoice.selectCustomer();
        //Значения полей кастомера из списка
        String[] listOfFields = invoice.listOfFields();
        //Значение полей кастомера из открывшейся формы
        String[] listOfFieldsFromForm = invoice.listOfFieldsFromForm();
        //Проверка что значения в списке и открывшейся форме совпадают
        assertTrue("Значение полей совпадают", invoice.fieldsAreEquals(listOfFields, listOfFieldsFromForm));

        //Создание объекта класса страницы кастомерс
        CustomersPage customers = new CustomersPage(driver);
        //Переход на страницу кастомерс
        customers.navigateCustomers();
        //Сохраняем Фамилию первого кастомера из списка
        String lastName = customers.getLastName(listOfFields);
        //Ищем кастомера по фамилии и переходим в редактирование
        customers.search(lastName, listOfFields);

        //Изменяем значение поля адресс на новое
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(By.id("address")));
        WebElement address = driver.findElement(By.id("address"));
        String addressBefore = address.getText();
        customers.clearField(address);
        String addressAfter = "Minisota";

        customers.fillField(address, addressAfter);
        customers.saveChanges();

        invoice.isInvoicesPage();

        assertTrue("Значение полей совпадают", invoice.fieldsAreEquals(invoice.listOfFields(), invoice.listOfFieldsFromForm()));

        customers.navigateCustomers();
        customers.search(lastName, listOfFields);
        customers.clearField(driver.findElement(By.id("address")));
        customers.fillField(driver.findElement(By.id("address")), addressBefore);
        customers.saveChanges();


    }
}
