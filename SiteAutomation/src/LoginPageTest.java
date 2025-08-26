import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoginPageTest {


    WebDriver driver;
    final String expectedSubject = "";

    LoginPageTest() {

        driver = new ChromeDriver();
    }

    @org.junit.jupiter.api.Test
    void login() {
        LoginPage signInPage = new LoginPage(driver);
        HomePage homePage = signInPage.login("tomsmith", "SuperSecretPassword!");
        String subject = homePage.getSubject(driver);
        Assert.assertNotNull(subject);
        Assert.assertEquals(subject, expectedSubject);


    }

    @org.junit.jupiter.api.Test
    void loginBasic() {
        LoginPage signInPage = new LoginPage(driver);
    }


    @Test
    void testLogin() {
    }
}