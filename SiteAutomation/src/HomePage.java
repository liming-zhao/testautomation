import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class HomePage {

    private WebDriver driver;

    private By subjectBy =By.tagName("H1");
    private By logoutBy = By.name("Logout");

    public HomePage(WebDriver driver)
    {
        this.driver = driver;
        if(!driver.getTitle().equals(""))
        {
            throw new IllegalStateException("" + "CurrentPage is :" + driver.getCurrentUrl());
        }
        //checkSubject(driver);

    }

    public String getSubject(WebDriver driver)
    {
        WebElement subject = driver.findElement(subjectBy);
        if(subject == null)// || !subject.getText().equals(""))
        {
            throw new IllegalStateException("" + "subject is Null");
        }
        return subject.getText();


    }

    private LoginPage logout(WebDriver driver)
    {
        driver.findElement(logoutBy).click();
        return new LoginPage(driver);
    }


}
