package ge.tbc.testautomation.runners;

import com.microsoft.playwright.Route;
import ge.tbc.testautomation.utils.FileUtil;

public class BrowserInjection extends BaseTest {
    String mockResponse = FileUtil.loadJson("src/main/resources/data.json");
    String serverErrorResponse = FileUtil.loadJson("src/main/resources/error_handling_test.json");


    public void interceptSearchResultsApi() {
        page.route(
                "**/searchresults*", route -> {
                    route.fulfill(new Route.FulfillOptions()
                            .setStatus(200)
                            .setBody(mockResponse)
                            .setContentType("application/json"));
                });
    }

    public void simulateSlowSearchResultsAPI() {
        page.route("**/searchresults*", route -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            route.resume();
        });
    }

    public void simulateHotelListServerError() {
        page.route("**/searchresults*", route -> {
            route.fulfill(new Route.FulfillOptions()
                    .setStatus(500)
                    .setBody(serverErrorResponse));
        });
    }
}
