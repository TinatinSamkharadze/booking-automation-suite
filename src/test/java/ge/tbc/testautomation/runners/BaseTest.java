package ge.tbc.testautomation.runners;

import com.microsoft.playwright.*;
import ge.tbc.testautomation.data.models.TestContext;
import ge.tbc.testautomation.steps.DetailsSteps;
import ge.tbc.testautomation.steps.HomeSteps;
import ge.tbc.testautomation.steps.ListingSteps;
import io.qameta.allure.Attachment;
import org.testng.ITestResult;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

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
    private boolean tracingEnabled = true;
    private static final String TRACE_DIR = "traces";

    @BeforeClass
    @Parameters({"browserType"})
    public void setUp(@Optional("chromium") String browserType) {
        playwright = Playwright.create();
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setArgs(Arrays.asList("--disable-gpu", "--disable-extensions", "--start-maximized"));
        launchOptions.setHeadless(false);

        if (browserType.equalsIgnoreCase("chromium")) {
            browser = playwright.chromium().launch(launchOptions);
        } else if (browserType.equalsIgnoreCase("webkit")) {
            browser = playwright.webkit().launch(launchOptions);
        }
        Browser.NewContextOptions contextOptions = new Browser.NewContextOptions();
        browserContext = browser.newContext(contextOptions);
        browserContext.onDialog(dialog -> {
            dialog.dismiss();
        });
        browserContext.route("**/signin**", route -> route.abort());
        browserContext.route("**/login-popup**", route -> route.abort());
        page = browserContext.newPage();

    }

    @BeforeMethod
    public void resetContext() {
        if (tracingEnabled) {
            browserContext.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(true)
                    .setTitle("Test: " + getCurrentTestName()));
        }
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
    public void tearDownPerTest(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        String timestamp = String.valueOf(System.currentTimeMillis());

        try {
            Files.createDirectories(Paths.get(TRACE_DIR));

            if (tracingEnabled) {
                if (result.getStatus() == ITestResult.FAILURE) {
                    String tracePath = TRACE_DIR + "/failed-" + testName + "-" + timestamp + ".zip";
                    browserContext.tracing().stop(new Tracing.StopOptions().setPath(Paths.get(tracePath)));
                    System.out.println("Trace saved for failed test: " + tracePath);

                    Path screenshotPath = Paths.get(TRACE_DIR + "/failure-screenshot-" + testName + "-" + timestamp + ".png");
                    page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath));
                    System.out.println("Screenshot saved at: " + screenshotPath);

                    attachScreenshotToAllure(Files.readAllBytes(screenshotPath));
                } else {
                    browserContext.tracing().stop();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTestName() {
        return Thread.currentThread().getStackTrace()[3].getMethodName();
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] attachScreenshotToAllure(byte[] screenshotBytes) {
        return screenshotBytes;
    }

}