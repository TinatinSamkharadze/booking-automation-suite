package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.runners.BrowserInjection;
import io.qameta.allure.*;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Epic("Mock API Testing")
public class BookingMockTests extends BrowserInjection {

    @Feature("Empty Search Results")
    @Story("Validate system behavior when hotel list API returns an empty response")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Simulates a situation where the hotel list API returns no results" +
            " and verifies the UI handles it gracefully with proper messaging" +
            " and visible filters.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 1)
    public void testEmptyHotelListResponse() {
        interceptSearchResultsApi();
        page.navigate(BOOKING_BASE_URL);
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .validateSearchBarIsClear()
                .searchLocation(NARA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(NARA)
                .waitForLoadState()
                .clickNextMonthButton()
                .selectCheckInDate(CHECKIN_DAY_ONE)
                .selectCheckOutDay(CHECKOUT_DAY_1)
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .waitForResultsToAppear()
                .validateResultsAfterApplyingRating(PropertyRating.FIVE_STARS)
                .waitForResultsToAppear()
                .validateErrorMessage()
                .validateFiltersAreVisible()
                .validateErrorMessage()
                .validatePageDidNotCrash();
    }

    @Feature("Delayed API Response")
    @Story("Verify loading behavior and UI stability when API response is delayed")
    @Severity(SeverityLevel.NORMAL)
    @Description("Simulates a delayed response from the hotel search API " +
            "to ensure the loader appears and disappears correctly " +
            "and results are shown.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 2)
    public void testDelayedApiResponse() {
        simulateSlowSearchResultsAPI();
        page.navigate(BOOKING_BASE_URL);
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .waitForLoadState()
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .waitNextMonthDatesToBeVisible()
                .selectCheckInDate(CHECKIN_DAY_TWO)
                .selectCheckOutDay(CHECKOUT_DAY_2)
                .clickSearchButton()
                .validateLoaderIsVisible();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .validateLoaderDisappear()
                .validateResultsAppear()
                .validateFiltersAreVisible()
                .validatePageDidNotCrash();

    }

    @Feature("Server Error Handling")
    @Story("Ensure the application responds properly to server-side errors")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Simulates a server error during hotel search " +
            "and checks for proper error display, retry option," +
            " and logging behavior.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Test(priority = 3)
    public void testServerError() {
        simulateHotelListServerError();
        page.navigate(BOOKING_BASE_URL);
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .validateSearchBarIsClear()
                .searchLocation(OSAKA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(OSAKA)
                .waitForLoadState()
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .waitNextMonthDatesToBeVisible()
                .selectCheckInDate(CHECKING_DAY_THREE)
                .selectCheckOutDay(CHECKOUT_DAY_3)
                .clickSearchButton()
                .validateLoaderIsVisible();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .validateToastAlert()
                .validateRetryButton()
                .validateProperLogging()
                .validatePageDidNotCrash();
    }
}
