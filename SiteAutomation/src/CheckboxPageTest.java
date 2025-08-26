// RefactoredCheckboxesTest.java - Test Class using Page Object
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class CheckboxPageTest {
    private WebDriver driver;
    private CheckboxPage checkboxPage;

    @BeforeClass
    public void setUp() {
        // Set up Chrome options for better test stability
        ChromeOptions options = new ChromeOptions();
       // options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--window-size=1920,1080");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initialize page object
        checkboxPage = new CheckboxPage(driver);
    }

    @BeforeMethod
    public void navigateToPage() {
        checkboxPage.navigateToPage().waitForPageLoad();
    }

    @Test(priority = 1, description = "Verify page loads correctly with proper title and heading")
    public void testPageLoadAndTitle() {
        Assert.assertTrue(checkboxPage.getPageTitle().contains("The Internet"),
                "Page title should contain 'The Internet'");

        Assert.assertEquals(checkboxPage.getPageHeading(), "Checkboxes",
                "Page heading should be 'Checkboxes'");

        Assert.assertTrue(checkboxPage.isPageLoaded(),
                "Page should be fully loaded");
    }

    @Test(priority = 2, description = "Verify exactly 2 checkboxes are present on the page")
    public void testCheckboxesPresent() {
        Assert.assertTrue(checkboxPage.hasExpectedCheckboxCount(2),
                "There should be exactly 2 checkboxes on the page");
    }

    @Test(priority = 3, description = "Verify initial states: first unchecked, second checked")
    public void testInitialCheckboxStates() {
        Assert.assertFalse(checkboxPage.isFirstCheckboxSelected(),
                "First checkbox should be unchecked initially");

        Assert.assertTrue(checkboxPage.isSecondCheckboxSelected(),
                "Second checkbox should be checked initially");

        Assert.assertTrue(checkboxPage.verifyInitialStates(),
                "Initial states should match expected pattern");
    }

    @Test(priority = 4, description = "Test checking the first checkbox")
    public void testCheckFirstCheckbox() {
        // Verify initial state
        Assert.assertFalse(checkboxPage.isFirstCheckboxSelected(),
                "First checkbox should be unchecked initially");

        // Click to check
        checkboxPage.clickFirstCheckbox();

        // Verify it's now checked
        Assert.assertTrue(checkboxPage.isFirstCheckboxSelected(),
                "First checkbox should be checked after clicking");
    }

    @Test(priority = 5, description = "Test unchecking the second checkbox")
    public void testUncheckSecondCheckbox() {
        // Verify initial state
        Assert.assertTrue(checkboxPage.isSecondCheckboxSelected(),
                "Second checkbox should be checked initially");

        // Click to uncheck
        checkboxPage.clickSecondCheckbox();

        // Verify it's now unchecked
        Assert.assertFalse(checkboxPage.isSecondCheckboxSelected(),
                "Second checkbox should be unchecked after clicking");
    }

    @Test(priority = 6, description = "Test toggle functionality for both checkboxes")
    public void testToggleCheckboxes() {
        // Store initial states
        boolean[] initialStates = checkboxPage.getAllCheckboxStates();

        // Toggle both checkboxes
        checkboxPage.clickFirstCheckbox().clickSecondCheckbox();

        // Verify states are toggled
        Assert.assertNotEquals(checkboxPage.isFirstCheckboxSelected(), initialStates[0],
                "First checkbox state should be toggled");
        Assert.assertNotEquals(checkboxPage.isSecondCheckboxSelected(), initialStates[1],
                "Second checkbox state should be toggled");

        // Toggle back
        checkboxPage.clickFirstCheckbox().clickSecondCheckbox();

        // Verify states are back to original
        boolean[] finalStates = checkboxPage.getAllCheckboxStates();
        Assert.assertEquals(finalStates[0], initialStates[0],
                "First checkbox should be back to original state");
        Assert.assertEquals(finalStates[1], initialStates[1],
                "Second checkbox should be back to original state");
    }

    @Test(priority = 7, description = "Verify all checkboxes are enabled")
    public void testCheckboxesAreEnabled() {
        Assert.assertTrue(checkboxPage.areAllCheckboxesEnabled(),
                "All checkboxes should be enabled");

        // Test individual checkboxes as well
        for (int i = 0; i < checkboxPage.getCheckboxCount(); i++) {
            Assert.assertTrue(checkboxPage.isCheckboxEnabled(i),
                    "Checkbox " + (i + 1) + " should be enabled");
        }
    }

    @Test(priority = 8, description = "Verify all checkboxes are visible")
    public void testCheckboxesAreVisible() {
        Assert.assertTrue(checkboxPage.areAllCheckboxesVisible(),
                "All checkboxes should be visible");

        // Test individual checkboxes as well
        for (int i = 0; i < checkboxPage.getCheckboxCount(); i++) {
            Assert.assertTrue(checkboxPage.isCheckboxVisible(i),
                    "Checkbox " + (i + 1) + " should be visible");
        }
    }

    @Test(priority = 9, description = "Test multiple toggle operations")
    public void testMultipleToggleOperations() {
        // Test with first checkbox
        boolean initialState = checkboxPage.isFirstCheckboxSelected();

        // Click 5 times (odd number - should end up opposite to initial state)
        checkboxPage.clickCheckboxMultipleTimes(0, 5);

        Assert.assertNotEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "After odd number of clicks, checkbox state should be opposite to initial");

        // Click once more (even total clicks - should be back to initial state)
        checkboxPage.clickFirstCheckbox();

        Assert.assertEquals(checkboxPage.isFirstCheckboxSelected(), initialState,
                "After even number of clicks, checkbox should be back to initial state");
    }

    @Test(priority = 10, description = "Test checkboxes work independently")
    public void testCheckboxIndependence() {
        boolean firstInitial = checkboxPage.isFirstCheckboxSelected();
        boolean secondInitial = checkboxPage.isSecondCheckboxSelected();

        // Click first checkbox
        checkboxPage.clickFirstCheckbox();

        // Verify second checkbox state hasn't changed
        Assert.assertEquals(checkboxPage.isSecondCheckboxSelected(), secondInitial,
                "Second checkbox state should not change when first checkbox is clicked");

        // Click second checkbox
        checkboxPage.clickSecondCheckbox();

        // Verify first checkbox state hasn't changed from its clicked state
        Assert.assertNotEquals(checkboxPage.isFirstCheckboxSelected(), firstInitial,
                "First checkbox should maintain its changed state");
    }

    @Test(priority = 11, description = "Test check/uncheck specific methods")
    public void testCheckUncheckMethods() {
        // Ensure first checkbox is checked
        checkboxPage.checkCheckbox(0);
        Assert.assertTrue(checkboxPage.isFirstCheckboxSelected(),
                "First checkbox should be checked after using check method");

        // Ensure second checkbox is unchecked
        checkboxPage.uncheckCheckbox(1);
        Assert.assertFalse(checkboxPage.isSecondCheckboxSelected(),
                "Second checkbox should be unchecked after using uncheck method");

        // Use check on already checked checkbox (should remain checked)
        checkboxPage.checkCheckbox(0);
        Assert.assertTrue(checkboxPage.isFirstCheckboxSelected(),
                "First checkbox should remain checked when check method is called on already checked checkbox");

        // Use uncheck on already unchecked checkbox (should remain unchecked)
        checkboxPage.uncheckCheckbox(1);
        Assert.assertFalse(checkboxPage.isSecondCheckboxSelected(),
                "Second checkbox should remain unchecked when uncheck method is called on already unchecked checkbox");
    }

    @Test(priority = 12, description = "Test check all and uncheck all functionality")
    public void testCheckUncheckAllMethods() {
        // Check all checkboxes
        checkboxPage.checkAllCheckboxes();

        for (int i = 0; i < checkboxPage.getCheckboxCount(); i++) {
            Assert.assertTrue(checkboxPage.isCheckboxSelected(i),
                    "Checkbox " + (i + 1) + " should be checked after checkAll operation");
        }

        // Uncheck all checkboxes
        checkboxPage.uncheckAllCheckboxes();

        for (int i = 0; i < checkboxPage.getCheckboxCount(); i++) {
            Assert.assertFalse(checkboxPage.isCheckboxSelected(i),
                    "Checkbox " + (i + 1) + " should be unchecked after uncheckAll operation");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}