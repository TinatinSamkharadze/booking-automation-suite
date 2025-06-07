package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyReviewRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.data.enums.ReservationPolicy;
import ge.tbc.testautomation.data.models.OfferModel;
import ge.tbc.testautomation.data.models.TestContext;
import ge.tbc.testautomation.pages.HomePage;
import ge.tbc.testautomation.pages.ListingPage;
import ge.tbc.testautomation.utils.OfferElement;
import io.qameta.allure.Step;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ListingSteps {
    public Page page;
    public HomePage homePage;
    public ListingPage listingPage;
    private TestContext testContext;
    private Map<Integer, List<Integer>> cardRowGroups = new TreeMap<>();
    public Calendar cal = Calendar.getInstance();

    public ListingSteps(Page page, TestContext testContext) {
        this.page = page;
        this.listingPage = new ListingPage(page);
        this.homePage = new HomePage(page);
        this.testContext = testContext;
    }

    @Step("Validating results appear")
    public ListingSteps validateResultsAppear() {
        listingPage.propertyCards.first().isVisible();
        return this;
    }

    @Step("Validating search header contains correct text: {searchText}")
    public ListingSteps validateSearchHeaderContainsCorrectText(String searchText) {
        assertThat(listingPage.searchHeader).containsText(searchText);
        return this;
    }

    @Step("Scrolling through property cards")
    public ListingSteps scrollThroughPropertyCards() {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(propertyCards::nth)
                .forEach(Locator::scrollIntoViewIfNeeded);
        return this;
    }

    private OfferModel extractOfferDetails(Locator card) {
        return new OfferModel(
                new OfferElement(card.locator(listingPage.titleSelector)),
                new OfferElement(card.locator(listingPage.reviewScoreSelector)),
                new OfferElement(card.locator(listingPage.locationSelector)),
                new OfferElement(card.locator(listingPage.roomType).first())
        );
    }

    @Step("Validating results location is correct: {searchText}")
    public ListingSteps validateResultsLocationIsCorrect(String searchText) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(searchText));
        return this;
    }

    @Step("Validating checkin date: {expectedCheckInDate}")
    public ListingSteps validateCheckInDate(String expectedCheckInDate) {
        assertThat(homePage.calendar).containsText(expectedCheckInDate);
        return this;
    }

    @Step("Validating checkout date: {expectedCheckoutDate}")
    public ListingSteps validateCheckOutDate(String expectedCheckOutDate) {
        assertThat(homePage.calendar).containsText(expectedCheckOutDate);
        return this;
    }


    @Step("Select property type {propertyType}")
    public ListingSteps selectPropertyType(PropertyType propertyType) {
        Locator checkbox = listingPage.getPropertyTypeCheckbox(propertyType);
        checkbox.check();
        return this;
    }

    @Step("Select property rating: {propertyRating}")
    public ListingSteps selectPropertyRating(PropertyRating propertyRating) {
        Locator checkbox = listingPage.getPropertyRatingCheckbox(propertyRating);
        checkbox.check();
        return this;
    }

    @Step("Select reservation policy: {reservationPolicy}")
    public ListingSteps selectReservationPolicy(ReservationPolicy reservationPolicy) {
        Locator checkbox = listingPage.getReservationPolicyCheckbox(reservationPolicy);
        checkbox.check();
        return this;
    }

    @Step("Waiting element to become stable")
    public ListingSteps waitElementToBeStable() {
        page.waitForTimeout(4000);
        return this;
    }

    @Step("Waiting results to appear")
    public ListingSteps waitForResultsToAppear() {
        page.waitForLoadState();
        return this;
    }

    @Step("Validating results after applying rating: {propertyRating}")
    public ListingSteps validateResultsAfterApplyingRating(PropertyRating propertyRating) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(propertyRating.getLabel()));
        return this;
    }

    @Step("Validating results after applying propertyType: {propertyType}")
    public ListingSteps validateResultsAfterApplyingPropertyType(PropertyType propertyType) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(propertyType.getLabel()));
        return this;
    }

    @Step("Validating results after applying reservation policy: {reservationPolicy}")
    public ListingSteps validateResultsAfterApplyingReservationPolicy(ReservationPolicy reservationPolicy) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(reservationPolicy.getLabel()));
        return this;
    }

    @Step("Clicking sort button")
    public ListingSteps clickSortButton() {
        listingPage.sortButton.first().waitFor();
        listingPage.sortButton.first().click();
        return this;
    }

    @Step("Clicking on top reviewed sort button")
    public ListingSteps clickOnPropertyRatingTopReviewed() {
        listingPage.propertyRating.waitFor();
        listingPage.propertyRating.click();
        return this;
    }

    @Step("Validating property cards are correctly filtered")
    public ListingSteps validatePropertyCardsAreCorrectlyFiltered() {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count).mapToObj(i -> propertyCards.nth(i))
                .forEach(this::validateCardRating);
        return this;
    }

    private void validateCardRating(Locator card) {
        String cardText = card.textContent();
        String expectedRating = determineExpectedRating(cardText);
        assertThat(card).containsText(expectedRating);
    }

    private String determineExpectedRating(String cardText) {
        return PropertyReviewRating.fromText(cardText).getLabel();
    }

    @Step("Clicking on first property card")
    public ListingSteps clickOnFirstPropertyCard() {
        listingPage.propertyCards.first().waitFor();
        listingPage.propertyCards.first().click();
        return this;
    }

    @Step("Capturing details about first property card")
    public ListingSteps captureFirstPropertyDetails() {
        Locator firstCard = listingPage.propertyCards.first();
        OfferModel offerDetails = extractOfferDetails(firstCard);
        testContext.setExpectedOffer(offerDetails);
        return this;
    }


    @Step("Validating offers really are top reviewed")
    public ListingSteps validateOffersAreTopReviewed() {
        Locator reviewScoreLocatorGroup = listingPage.propertyCards.locator(listingPage.reviewScoreSelector);
        int count = reviewScoreLocatorGroup.count();
        for (int i = 0; i < count; i++) {
            Locator scoreLocator = reviewScoreLocatorGroup.nth(i);
            assertThat(scoreLocator).containsText(Pattern.compile("9\\.|8\\."));
        }
        return this;
    }

    @Step("Validating checkin date {expectedCheckInDate}")
    public ListingSteps validateCheckIn(Date expectedCheckInDate) {
        cal.setTime(expectedCheckInDate);
        String checkInDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        assertThat(homePage.calendar).containsText(checkInDay);
        return this;
    }

    @Step("Validating checkout date {expectedCheckoutDate}")
    public ListingSteps validateCheckOut(Date expectedCheckOutDate) {
        cal.setTime(expectedCheckOutDate);
        String checkOutDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        assertThat(homePage.calendar).containsText(checkOutDay);
        return this;
    }

    @Step("Changing to grid")
    public ListingSteps changeToGrid() {
        listingPage.grid.click();
        return this;
    }

    @Step("Validating error message")
    public ListingSteps validateErrorMessage() {
        listingPage.errorMessage.waitFor();
        assertThat(listingPage.errorMessage).isVisible();
        return this;
    }

    @Step("Validating filters are visible")
    public ListingSteps validateFiltersAreVisible() {
        assertThat(listingPage.filters).isVisible();
        return this;
    }

    @Step("Validating page did not crash")
    public ListingSteps validatePageDidNotCrash() {
        assertThat(page.locator("body")).isVisible();
        return this;
    }

    @Step("Validating loader disappears")
    public ListingSteps validateLoaderDisappear() {
        assertThat(homePage.loader).not().isVisible();
        return this;
    }

    @Step("Validating toast alert")
    public ListingSteps validateToastAlert() {
        assertThat(listingPage.toastAlert).isVisible();
        return this;
    }

    @Step("Validating retry button is visible")
    public ListingSteps validateRetryButton() {
        assertThat(listingPage.retryButton).isVisible();
        return this;
    }

    @Step("Validating proper logging")
    public ListingSteps validateProperLogging() {
        assertThat(listingPage.logging).isVisible();
        return this;
    }

    @Step("Validating guests number: {guests}")
    public ListingSteps validateGuests(int guests) {
        assertThat(listingPage.occupancy).containsText(String.valueOf(guests));
        return this;
    }


    @Step("Validating grid layout: {expectedPerRow}")
    public ListingSteps validateGridLayout(int expectedPerRow) {
        int totalCards = listingPage.propertyCards.count();
        IntStream.iterate(0, i -> i < totalCards, i -> i + expectedPerRow)
                .forEach(firstCardIndex -> {
                    Locator firstCardInRow = listingPage.propertyCards.nth(firstCardIndex);
                    assertThat(firstCardInRow).isVisible();
                });
        return this;
    }

    @Step("Navigating back to home page")
    public ListingSteps navigateToHomePage() {
        listingPage.bookingLogo.click();
        return this;
    }

    @Step("Scrolling to the top of the page")
    public ListingSteps scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
        return this;
    }

    @Step("Hide Google One Tap popup")
    public ListingSteps hideGoogleOneTap()
    {
        listingPage.page.evaluate(
                "const el = document.querySelector('.google-one-tap-wrapper'); if (el) el.style.display = 'none';"
        );
        return this;
    }

    @Step("Hide dialog")
    public ListingSteps hideDialog()
    {
        homePage.page.evaluate(
                "const el = document.querySelector('.c1cb99b7ca'); if (el) el.style.display = 'none';"
        );
        return this;
    }
}