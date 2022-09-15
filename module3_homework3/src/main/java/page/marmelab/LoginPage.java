package page.marmelab;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.junit.Assert.assertTrue;

public class LoginPage {
    protected static WebDriver driver;

    private final By byLogin = By.id("username");
    private final By byPassword = By.id("password");
    private final By byTitle = By.xpath("//div[@class = 'MuiBox-root css-chxp4w']");
    private final By signIn = By.xpath("//div[@class = 'MuiCardActions-root MuiCardActions-spacing css-7roxmj']//button");
    private final By mainPage = By.xpath("//h2[@class = 'MuiTypography-root MuiTypography-h5 MuiTypography-gutterBottom css-t1nuxs']");

    public LoginPage(WebDriver driver) {
        LoginPage.driver = driver;
    }

    public LoginPage login(String login, String password) {
        assertTrue("Мы находимся на странице авторизации", isLoginPage());
        driver.findElement(byLogin).sendKeys(login);
        driver.findElement(byPassword).sendKeys(password);
        driver.findElement(signIn).click();
        return this;
    }

    public boolean isLoginPage() {return driver.findElement(byTitle).getText().equals("Hint: demo / demo");}

    public boolean isMainPage() {return driver.findElement(mainPage).getText().contains("Welcome");}
}
