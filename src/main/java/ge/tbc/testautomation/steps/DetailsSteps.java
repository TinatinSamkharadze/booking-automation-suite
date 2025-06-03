package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.data.models.TestContext;
import ge.tbc.testautomation.pages.DetailsPage;
import ge.tbc.testautomation.pages.ListingPage;
import ge.tbc.testautomation.utils.OfferElement;
import io.qameta.allure.Step;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DetailsSteps {
    public Page page;
    public DetailsPage detailsPage;
    public ListingPage listingPage;
    private TestContext testContext;

    public DetailsSteps(Page page, TestContext testContext) {
        this.page = page;
        this.detailsPage = new DetailsPage(page);
        this.listingPage = new ListingPage(page);
        this.testContext = testContext;
    }

    @Step("Validate we are on details page")
    public DetailsSteps validateWeAreOnDetailsPage() {
        detailsPage.overview.waitFor();
        return this;
    }

    @Step("Waiting for page to load")
    public DetailsSteps waitPageToLoad() {
        page.waitForTimeout(3000);
        return this;
    }

    @Step("Going to details page")
    public DetailsSteps goToDetailsPage() {
        Page newPage = page.context().pages().get(page.context().pages().size() - 1);
        newPage.bringToFront();
        return new DetailsSteps(newPage, testContext);
    }

    @Step("Scrolling to the top of the page")
    public DetailsSteps scrollToTop() {
        page.evaluate("window.scrollTo(0, 0);");
        return this;
    }

    private OfferModel extractOfferDetails() {
        return new OfferModel(
                new OfferElement(page.locator(detailsPage.titleSelector).first()),
                new OfferElement(page.locator(detailsPage.reviewScoreSelector).first()),
                new OfferElement(page.locator(detailsPage.locationSelector).first()),
                new OfferElement(page.locator(detailsPage.roomType).first())
        );
    }

    @Step("Capturing details from a property")
    public DetailsSteps captureActualPropertyDetails() {
        OfferModel actualOffer = extractOfferDetails();
        testContext.setActualOffer(actualOffer);
        return this;
    }

    private String extractScore(String content) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(content);
        return matcher.find() ? matcher.group() : "";
    }

    @Step("Validating location is matching listing page's location")
    public DetailsSteps assertLocationMatchesListing() {
        String expectedCity = extractCityFromLocation(testContext.getExpectedOffer().location.getContent());
        assertThat(testContext.getActualOffer().location.getLocator().first()).containsText(expectedCity);
        return this;
    }

    @Step("Validating room type is matching listing page's room type")
    public DetailsSteps assertRoomTypeMatchesListing() {
        String expectedRoomType = extractCityFromLocation(testContext.getExpectedOffer().roomType.getContent());
        assertThat(testContext.getActualOffer().roomType.getLocator().first()).containsText(expectedRoomType);
        return this;
    }

    @Step("Validating review score is matching listing page's review score")
    public DetailsSteps assertReviewScoreMatchesListing() {
        String expectedScore = extractScore(testContext.getExpectedOffer().reviewScore.getContent());
        assertThat(testContext.getActualOffer().reviewScore.getLocator().first()).containsText(expectedScore);
        return this;
    }

    @Step("Validating title is matching listing page's title")
    public DetailsSteps assertTitleMatchesListing() {
        assertThat(testContext.getActualOffer().title.getLocator().first()).containsText(testContext.getExpectedOffer().title.getContent());
        return this;
    }

    private String extractCityFromLocation(String fullLocation) {
        String[] parts = fullLocation.split(",");
        return parts[parts.length - 1].trim();

    }
}
