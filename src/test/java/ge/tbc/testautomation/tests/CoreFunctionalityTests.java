package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.data.enums.ReservationPolicy;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.utils.Retry;
import ge.tbc.testautomation.utils.RetryAnalyzer;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Epic("Booking Core Functionality")
public class CoreFunctionalityTests extends BaseTest {

    //თუ BaseTest-ში დავწერდი page.navigate()-ს,
    // ყველა ტესტისთვის ავტომატურად შესრულდებოდა ნავიგაცია, მათ შორის mock ტესტებისთვისაც
    //რადგან mock-ები უნდა იყოს კონფიგურირებული ნავიგაციამდე ამიტომ აქ დავწერე
    @BeforeClass
    public void navigateToApplication() {
        page.navigate(BOOKING_BASE_URL);
    }

    @Feature("Basic Search")
    @Story("Search for accommodations by location")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that the user can search for accommodations " +
            "in a specific location and see correct search results.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 1)
    public void searchTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_DESKTOP, HEIGHT_FOR_DESKTOP)
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .searchLocation(KYOTO)
                .selectLocationOption(KYOTO)
                .clickOnCalendar()
                .clickSearchButton();
        listingSteps
                .hideDialog()
                .waitElementToBeStable()
                .hideGoogleOneTap()
                .waitForResultsToAppear()
                .validateResultsAppear()
                .validateSearchHeaderContainsCorrectText(KYOTO)
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateResultsLocationIsCorrect(KYOTO);

    }

    @Feature("Date Selection")
    @Story("User can select check-in and check-out dates")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Ensures that the user is able to select check-in " +
            "and check-out dates from the calendar and view results.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    @Retry(maxRetries = 1)
    public void dateSelectionTest() {
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate(FIRST_DAY_OF_MONTH)
                .selectCheckOutDay(LAST_DAY_OF_MONTH)
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .waitForResultsToAppear()
                .validateCheckInDate(FIRST_DAY_OF_MONTH)
                .validateCheckOutDate(LAST_DAY_OF_MONTH)
                .validateResultsAppear();

    }

    @Feature("Filter Application")
    @Story("Apply filters like property type, rating, and reservation policy")
    @Severity(SeverityLevel.NORMAL)
    @Description("Tests the filter functionality to ensure users" +
            " can refine results by hotel type, rating, and payment policy.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    @Retry(maxRetries = 1)
    public void filterApplicationTest() {
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .clickOccupancy()
                .waitOccupancyPanelToBeVisible()
                .selectGuests(GUESTS)
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear()
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .selectPropertyType(PropertyType.HOTELS)
                .waitForResultsToAppear()
                .waitElementToBeStable()
                .selectPropertyRating(PropertyRating.FOUR_STARS)
                .waitForResultsToAppear()
                .waitElementToBeStable()
                .selectReservationPolicy(ReservationPolicy.NO_PREPAYMENT)
                .waitForResultsToAppear()
                .scrollThroughPropertyCards()
                .validateResultsAfterApplyingPropertyType(PropertyType.HOTELS)
                .scrollThroughPropertyCards()
                .validateResultsAfterApplyingRating(PropertyRating.FOUR_STARS)
                .waitForResultsToAppear()
                .scrollThroughPropertyCards()
                .validateResultsAfterApplyingReservationPolicy(ReservationPolicy.NO_PREPAYMENT);
    }

    @Feature("Sort by Review Score")
    @Story("Sort listings by top-reviewed properties")
    @Severity(SeverityLevel.MINOR)
    @Description("Verifies that the user can sort the listing results " +
            "based on review scores and that the sorting is correct.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 4)
    public void sortByReviewScoreTest() {
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .waitForResultsToAppear()
                .clickSortButton()
                .clickOnPropertyRatingTopReviewed()
                .waitForResultsToAppear()
                .scrollThroughPropertyCards()
                .validatePropertyCardsAreCorrectlyFiltered()
                .waitForResultsToAppear()
                .validateOffersAreTopReviewed();
    }

    @Feature("Consistency between listing and detail pages")
    @Story("Validate that property details are consistent across pages")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checks that the property details shown in the listing" +
            " match those shown on the property detail page.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(dependsOnMethods = {"sortByReviewScoreTest"}, priority = 5)
    public void propertyDetailsConsistencyTest() {
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear()
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .captureFirstPropertyDetails()
                .clickOnFirstPropertyCard();
        detailsSteps
                .waitPageToLoad()
                .goToDetailsPage()
                .scrollToTop()
                .validateWeAreOnDetailsPage()
                .captureActualPropertyDetails()
                .assertLocationMatchesListing()
                .assertReviewScoreMatchesListing()
                .assertTitleMatchesListing()
                .assertRoomTypeMatchesListing();
    }
}
