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
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ListingSteps {
    Page page;
    HomePage homePage;
    ListingPage listingPage;
    private OfferModel currentOfferDetails;
    private TestContext testContext;
    public SoftAssert softAssert;
    private Map<Integer, List<Integer>> cardRowGroups = new TreeMap<>();
    Calendar cal = Calendar.getInstance();

    public ListingSteps(Page page, TestContext testContext) {
        this.page = page;
        listingPage = new ListingPage(page);
        homePage = new HomePage(page);
        this.testContext = testContext;
        this.softAssert = new SoftAssert();
    }

    public ListingSteps validateResultsAppear()
    {
        listingPage.propertyCards.first().isVisible();
        return this;
    }

    public ListingSteps validateSearchHeaderContainsCorrectText(String searchText) {
        assertThat(listingPage.searchHeader).containsText(searchText);

        return this;
    }

    public ListingSteps scrollThroughPropertyCards() {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(propertyCards::nth)
                .forEach(Locator::scrollIntoViewIfNeeded);

        return this;
    }

    private OfferModel extractOfferDetails(Locator card) {
        return new OfferModel(
                new OfferElement(card.locator(listingPage.titleSelector)),
                new OfferElement(card.locator(listingPage.reviewScoreSelector)),
                new OfferElement(card.locator(listingPage.locationSelector))
        );
    }


    public ListingSteps validateResultsLocationIsCorrect(String searchText) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(searchText));
        return this;
    }

    private void validatePropertyCardLocation(Locator card, String searchText) {
        OfferModel offerDetails = extractOfferDetails(card);
        assertThat(offerDetails.getLocation().getLocator().first()).containsText(searchText);
    }

    public ListingSteps validateCheckInDate(String expectedCheckInDate) {
        assertThat(homePage.calendar).containsText(expectedCheckInDate);
        return this;
    }
    public ListingSteps validateCheckOutDate( String expectedCheckOutDate) {
        assertThat(homePage.calendar).containsText(expectedCheckOutDate);
        return this;
    }


    public ListingSteps selectPropertyType(PropertyType propertyType) {
        Locator checkbox = listingPage.getPropertyTypeCheckbox(propertyType);
        checkbox.check();
        return this;
    }
    public ListingSteps selectPropertyRating(PropertyRating propertyRating) {
        Locator checkbox = listingPage.getPropertyRatingCheckbox(propertyRating);
        checkbox.check();
        return this;
    }

    public ListingSteps selectReservationPolicy(ReservationPolicy reservationPolicy) {
        Locator checkbox = listingPage.getReservationPolicyCheckbox(reservationPolicy);
        checkbox.check();
        return this;
    }

    public ListingSteps waitElementToBeStable()
    {
        page.waitForTimeout(2000);
        return this;
    }

    public ListingSteps waitForResultsToAppear()
    {
        page.waitForLoadState();
        return this;
    }

    public ListingSteps validateResultsAfterApplyingRating(PropertyRating propertyRating) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(propertyRating.getLabel()));

        return this;
    }
    public ListingSteps validateResultsAfterApplyingPropertyType(PropertyType propertyType) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(propertyType.getLabel()));
        return this;
    }
    public ListingSteps validateResultsAfterApplyingReservationPolicy(ReservationPolicy reservationPolicy) {
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(i -> propertyCards.nth(i))
                .forEach(card -> assertThat(card).containsText(reservationPolicy.getLabel()));
        return this;
    }

    public ListingSteps clickSortButton()
    {
        listingPage.sortButton.first().waitFor();
        listingPage.sortButton.first().click();
        return this;
    }

    public ListingSteps clickOnPropertyRatingTopReviewed()
    {
        listingPage.propertyRating.waitFor();
        listingPage.propertyRating.click();
        return this;
    }

    public ListingSteps validatePropertyCardsAreCorrectlyFiltered() {
        listingPage.propertyCards.first().waitFor();
        Locator propertyCards = listingPage.propertyCards;
        int count = propertyCards.count();
        IntStream.range(0, count)
                .mapToObj(i -> propertyCards.nth(i))
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

    public ListingSteps clickOnFirstPropertyCard()
    {
        listingPage.propertyCards.first().waitFor();
        listingPage.propertyCards.first().click();
        return this;
    }

    public ListingSteps captureFirstPropertyDetails() {
        Locator firstCard = listingPage.propertyCards.first();
        OfferModel offerDetails = extractOfferDetails(firstCard);
        testContext.setExpectedOffer(offerDetails);
        return this;
    }


    public ListingSteps validateOffersAreTopReviewed() {
        Locator reviewScoreLocatorGroup = listingPage.propertyCards.locator(listingPage.reviewScoreSelector);
        int count = reviewScoreLocatorGroup.count();
        for (int i = 0; i < count; i++) {
            Locator scoreLocator = reviewScoreLocatorGroup.nth(i);
            assertThat(scoreLocator)
                    .containsText(Pattern.compile("9\\.|8\\."));
        }
        return this;
    }

    private double getVerticalCenter(Locator locator) {
        var box = locator.boundingBox();
        return box.y + box.height / 2;
    }

    public ListingSteps scrollToLoadAllCards() {
        while (true) {
            int previousCount = listingPage.propertyCards.count();
            int newCount = listingPage.propertyCards.count();
            if (newCount == previousCount) break;
        }
        return this;
    }

    public ListingSteps clearSearchBoxForDates()
    {
        listingPage.searchBoxForCalendar.clear();
        return this;
    }

    public ListingSteps validateEachRowHasExpectedCardCount(int expectedPerRow) {
        groupCardsByY().values().forEach(cards ->
                Assert.assertEquals(cards.size(), expectedPerRow));
        return this;
    }

    private Map<Integer, List<Integer>> groupCardsByY() {
        Map<Integer, List<Integer>> rows = new TreeMap<>();
        int count = listingPage.propertyCards.count();
        for (int i = 0; i < count; i++) {
            Integer rowY = getCardRowY(i, rows.keySet());
            if (rowY != null) {
                rows.computeIfAbsent(rowY, k -> new ArrayList<>()).add(i);
            }
        }
        return rows;
    }

    private Integer getCardRowY(int cardIndex, Set<Integer> existingRows) {
        var box = listingPage.propertyCards.nth(cardIndex).boundingBox();
        if (box == null) return null;
        int y = (int) box.y;
        return existingRows.stream()
                .filter(key -> Math.abs(key - y) <= 5)
                .findFirst()
                .orElse(y);
    }

    public ListingSteps validateCheckIn(Date expectedCheckInDate) {
        cal.setTime(expectedCheckInDate);
        String checkInDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        assertThat(homePage.calendar).containsText(checkInDay);
        return this;
    }

    public ListingSteps validateCheckOut(Date expectedCheckOutDate) {
        cal.setTime(expectedCheckOutDate);
        String checkOutDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        assertThat(homePage.calendar).containsText(checkOutDay);
        return this;
    }
}
