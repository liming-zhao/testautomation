import io.opentelemetry.api.GlobalOpenTelemetry;
import org.openqa.selenium.By;
import org.openqa.selenium.HasAuthentication;
import org.openqa.selenium.UsernameAndPassword;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.devtools.DevTools;


public class LoginPage {



   protected WebDriver driver;


   private String loginUrl = "https://the-internet.herokuapp.com/login";

   private By usernameBy = By.name("username");
   private By passwordBy = By.name("password");

   private By submitBy = By.xpath("//*[@id='login']/button/i");



   public LoginPage(WebDriver driver)
   {
       HasAuthentication authentication= (HasAuthentication) driver;

// You can either register something for all sites
       authentication.register(() -> new UsernameAndPassword("admin", "admin"));
       this.driver = driver;
       this.loginUrl = "https://the-internet.herokuapp.com/basic_auth";
       driver.get(loginUrl);
       /*if(!driver.getTitle().equals("The Internet"))
       {
           throw new IllegalStateException("" + "CurrentPage is :" + driver.getCurrentUrl());
       }

        */
   }

   public HomePage login(String username, String password)
   {
       driver.findElement(usernameBy).sendKeys(username);
       driver.findElement(passwordBy).sendKeys(password);
       driver.findElement(submitBy).click();
       return new HomePage(driver);

   }








}
