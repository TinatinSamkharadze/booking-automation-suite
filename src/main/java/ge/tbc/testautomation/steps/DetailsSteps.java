package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Page;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.data.models.TestContext;
import ge.tbc.testautomation.pages.DetailsPage;
import ge.tbc.testautomation.pages.ListingPage;
import ge.tbc.testautomation.utils.OfferElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class DetailsSteps {
    Page page;
    DetailsPage detailsPage;
    ListingPage listingPage;
    private OfferModel currentOfferDetails;
    private TestContext testContext;

    public DetailsSteps(Page page, TestContext testContext) {
        this.page = page;
        this.detailsPage = new DetailsPage(page);
        this.listingPage = new ListingPage(page);
        this.testContext = testContext;
    }

    public DetailsSteps validateHeaderContainsPropertyType(PropertyType propertyType) {
        assertThat(detailsPage.title).containsText(propertyType.getLabel());
        return this;
    }

    public DetailsSteps validateWeAreOnDetailsPage() {
        detailsPage.overview.waitFor();
        return this;
    }

    public DetailsSteps waitPageToLoad() {
        page.waitForTimeout(3000);
        return this;
    }

    public DetailsSteps goToDetailsPage() {
        waitPageToLoad();
        Page newPage = page.context().pages().get(page.context().pages().size() - 1);
        newPage.bringToFront();
        return new DetailsSteps(newPage, testContext);
    }

    public DetailsSteps scrollToTop() {
        page.evaluate("window.scrollTo(0, 0);");
        return this;
    }

    private OfferModel extractOfferDetails() {
        return new OfferModel(
                new OfferElement(page.locator(detailsPage.titleSelector)),
                new OfferElement(page.locator(detailsPage.reviewScoreSelector)),
                new OfferElement(page.locator(detailsPage.locationSelector))
        );
    }

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

    public DetailsSteps assertLocationMatchesListing() {
        String expectedCity = extractCityFromLocation(testContext.getExpectedOffer().location.getContent());
        assertThat(testContext.getActualOffer().location.getLocator().first()).containsText(expectedCity);
        return this;
    }

    public DetailsSteps assertReviewScoreMatchesListing() {
        String expectedScore = extractScore(testContext.getExpectedOffer().reviewScore.getContent());
        assertThat(testContext.getActualOffer().reviewScore.getLocator().first()).containsText(expectedScore);
        return this;
    }

    public DetailsSteps assertTitleMatchesListing() {
        assertThat(testContext.getActualOffer().title.getLocator().first()).containsText(testContext.getExpectedOffer().title.getContent());
        return this;
    }

    private String extractCityFromLocation(String fullLocation) {
        String[] parts = fullLocation.split(",");
        return parts[parts.length - 1].trim();

    }
}
