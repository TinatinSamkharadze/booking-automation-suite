package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.KYOTO;
import static ge.tbc.testautomation.data.Constants.OSAKA;


public class BookingMockTests extends BaseTest {

    @Test
    public void testEmptyHotelListResponse() {
        homeSteps
                .interceptSearchResultsApi()
                .validateSearchBarIsClear()
                .searchLocation("Kutaisi")
                .waitForLocationOptionsToAppear()
                .selectLocationOption("Kutaisi")
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate("2")
                .selectCheckOutDay("8")
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear()
                .validateResultsAfterApplyingRating(PropertyRating.FIVE_STARS)
                .waitForResultsToAppear()
                .validateErrorMessage()
                .validateFiltersAreVisible()
                .validateErrorMessage();
    }

    @Test
    public void testDelayedApiResponse() {
        homeSteps
                .simulateSlowSearchResultsAPI()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate("5")
                .selectCheckOutDay("8")
                .clickSearchButton()
                .validateLoaderIsVisible();
        listingSteps
                .validateLoaderDisappear()
                .validateResultsAppear()
                .validateFiltersAreVisible();

    }

    @Test
    public void testServerError()
    {
        homeSteps
                .simulateHotelListServerError()
                .searchLocation(OSAKA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(OSAKA)
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate("5")
                .selectCheckOutDay("8")
                .clickSearchButton()
                .validateLoaderIsVisible();
        listingSteps
                .validateToastAlert()
                .validateRetryButton()
                .validateProperLogging();
    }
}
