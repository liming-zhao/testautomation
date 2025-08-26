// CheckboxPage.java - Page Object Class
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class CheckboxPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page URL
    private static final String PAGE_URL = "https://the-internet.herokuapp.com/checkboxes";

    // Page Elements using PageFactory
    @FindBy(tagName = "h3")
    private WebElement pageHeading;

    @FindBy(css = "input[type='checkbox']")
    private List<WebElement> checkboxes;

    // Constructor
    public CheckboxPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // Navigation method
    public CheckboxPage navigateToPage() {
        driver.get(PAGE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='checkbox']")));
        return this;
    }

    // Page verification methods
    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getPageHeading() {
        wait.until(ExpectedConditions.visibilityOf(pageHeading));
        return pageHeading.getText();
    }

    public boolean isPageLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOf(pageHeading));
            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[type='checkbox']")));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Checkbox count methods
    public int getCheckboxCount() {
        return checkboxes.size();
    }

    public boolean hasExpectedCheckboxCount(int expectedCount) {
        return getCheckboxCount() == expectedCount;
    }

    // Individual checkbox access methods
    public WebElement getCheckboxByIndex(int index) {
        if (index < 0 || index >= checkboxes.size()) {
            throw new IllegalArgumentException("Checkbox index out of bounds: " + index + ". Available indexes: 0-" + (checkboxes.size() - 1));
        }
        return checkboxes.get(index);
    }

    public WebElement getFirstCheckbox() {
        return getCheckboxByIndex(0);
    }

    public WebElement getSecondCheckbox() {
        return getCheckboxByIndex(1);
    }

    // Checkbox state checking methods
    public boolean isCheckboxSelected(int index) {
        return getCheckboxByIndex(index).isSelected();
    }

    public boolean isFirstCheckboxSelected() {
        return isCheckboxSelected(0);
    }

    public boolean isSecondCheckboxSelected() {
        return isCheckboxSelected(1);
    }

    public boolean isCheckboxEnabled(int index) {
        return getCheckboxByIndex(index).isEnabled();
    }

    public boolean isCheckboxVisible(int index) {
        return getCheckboxByIndex(index).isDisplayed();
    }

    public boolean areAllCheckboxesEnabled() {
        return checkboxes.stream().allMatch(WebElement::isEnabled);
    }

    public boolean areAllCheckboxesVisible() {
        return checkboxes.stream().allMatch(WebElement::isDisplayed);
    }

    // Checkbox interaction methods
    public CheckboxPage clickCheckbox(int index) {
        getCheckboxByIndex(index).click();
        return this;
    }

    public CheckboxPage clickFirstCheckbox() {
        return clickCheckbox(0);
    }

    public CheckboxPage clickSecondCheckbox() {
        return clickCheckbox(1);
    }

    public CheckboxPage checkCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public CheckboxPage uncheckCheckbox(int index) {
        WebElement checkbox = getCheckboxByIndex(index);
        if (checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }

    public CheckboxPage toggleCheckbox(int index) {
        return clickCheckbox(index);
    }

    public CheckboxPage checkAllCheckboxes() {
        for (int i = 0; i < checkboxes.size(); i++) {
            checkCheckbox(i);
        }
        return this;
    }

    public CheckboxPage uncheckAllCheckboxes() {
        for (int i = 0; i < checkboxes.size(); i++) {
            uncheckCheckbox(i);
        }
        return this;
    }

    // Multiple click operations for testing
    public CheckboxPage clickCheckboxMultipleTimes(int index, int times) {
        for (int i = 0; i < times; i++) {
            clickCheckbox(index);
        }
        return this;
    }

    // State verification helper methods
    public boolean verifyInitialStates() {
        return !isFirstCheckboxSelected() && isSecondCheckboxSelected();
    }

    public boolean[] getAllCheckboxStates() {
        boolean[] states = new boolean[checkboxes.size()];
        for (int i = 0; i < checkboxes.size(); i++) {
            states[i] = isCheckboxSelected(i);
        }
        return states;
    }

    public CheckboxPage waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOf(pageHeading));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("input[type='checkbox']")));
        return this;
    }
}
