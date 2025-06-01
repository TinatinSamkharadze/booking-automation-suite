package ge.tbc.testautomation.runners;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.models.TestContext;
import ge.tbc.testautomation.steps.DetailsSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.ListingSteps;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.util.Arrays;

import static ge.tbc.testautomation.data.Constants.BOOKING_BASE_URL;

public class BaseTest {
    public SoftAssert softAssert;
    public Playwright playwright;
    public Browser browser;
    public BrowserContext browserContext;
    public Page page;
    public HomeSteps homeSteps;
    public ListingSteps listingSteps;
    public DetailsSteps detailsSteps;
    public TestContext sharedContext;


    @BeforeClass
    @Parameters({"browserType"})
    public void setUp(@Optional("chromium") String browserType){
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--disable-gpu", "--disable-extensions", "--start-maximized"));
        launchOptions.setHeadless(false);

        if (browserType.equalsIgnoreCase("chromium")){
            browser = playwright.chromium().launch(launchOptions);
        } else if (browserType.equalsIgnoreCase("safari")) {
            browser = playwright.webkit().launch(launchOptions);
        }
        browserContext = browser.newContext();
        browserContext.onDialog(dialog -> {
            dialog.dismiss();
        });
        browserContext.route("**/signin**", route -> route.abort());
        browserContext.route("**/login-popup**", route -> route.abort());
        page = browserContext.newPage();

    }

    @BeforeMethod
    public void resetContext() {
        page.navigate(BOOKING_BASE_URL);
        this.homeSteps = new HomeSteps(page);
        browserContext.onDialog(dialog -> {
            dialog.dismiss();
        });
        browserContext.route("**/signin**", route -> route.abort());
        browserContext.route("**/login-popup**", route -> route.abort());
        this.sharedContext = new TestContext();
        this.listingSteps = new ListingSteps(page, sharedContext);
        this.detailsSteps = new DetailsSteps(page, sharedContext);
       this.softAssert = new SoftAssert();
    }


    @AfterClass
    public void tearDown() {
        browserContext.close();
        browser.close();
        playwright.close();
    }

    @AfterMethod
    public void tearDownPerTest()
    {
        softAssert.assertAll();
    }

}