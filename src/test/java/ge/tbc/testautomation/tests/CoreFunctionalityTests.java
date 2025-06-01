package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.data.enums.ReservationPolicy;
import ge.tbc.testautomation.runners.BaseTest;
import ge.tbc.testautomation.utils.Retry;
import ge.tbc.testautomation.utils.RetryAnalyzer;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class CoreFunctionalityTests extends BaseTest {

    @Test(priority = 1)
    public void searchTest() {
        homeSteps
                .searchLocation(KYOTO)
                .selectLocationOption(KYOTO)
                .clickOnCalendar()
                .clickSearchButton();
        listingSteps
                .validateResultsAppear()
                .validateSearchHeaderContainsCorrectText(KYOTO)
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateResultsLocationIsCorrect(KYOTO);
    }

    @Test(priority = 2, retryAnalyzer = RetryAnalyzer.class)
    @Retry(maxRetries = 1)
    public void dateSelectionTest() {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(TOKYO_CITY)
                .selectLocationOption(TOKYO_CITY)
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate(FIRST_DAY_OF_MONTH)
                .selectCheckOutDay(LAST_DAY_OF_MONTH)
                .clickSearchButton();
        listingSteps
                .validateCheckInDate(FIRST_DAY_OF_MONTH)
                .validateCheckOutDate(LAST_DAY_OF_MONTH)
                .validateResultsAppear()
                .validateSearchHeaderContainsCorrectText(TOKYO_CITY)
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateResultsLocationIsCorrect(TOKYO_CITY);

    }

    @Test(priority = 3, retryAnalyzer = RetryAnalyzer.class)
    @Retry(maxRetries = 1)
    public void filterApplicationTest() {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(NEW_YORK)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(NEW_YORK)
                .ifNotVisibleClickOnCalendar();
        homeSteps
                .clickNextMonthButton()
                .selectCheckInDate(CHECKIN_DAY_ONE)
                .selectCheckOutDay(CHECKOUT_DAY_1)
                .clickSearchButton();
        listingSteps
                .validateCheckInDate(CHECKIN_DAY_ONE)
                .validateCheckOutDate(CHECKOUT_DAY_1)
                .changeToGrid()
                .validateResultsAppear()
                .waitElementToBeStable()
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

    @Test(priority = 4)
    public void sortByReviewScoreTest() {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(NARA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(NARA)
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate(CHECKIN_DAY_TWO)
                .selectCheckOutDay(CHECKOUT_DAY_2)
                .clickSearchButton();
        listingSteps
                .validateCheckInDate(CHECKIN_DAY_TWO)
                .validateCheckOutDate(CHECKOUT_DAY_2)
                .validateResultsAppear()
                .waitElementToBeStable()
                .selectPropertyRating(PropertyRating.FIVE_STARS)
                .waitForResultsToAppear()
                .clickSortButton()
                .clickOnPropertyRatingTopReviewed()
                .waitForResultsToAppear()
                .scrollThroughPropertyCards()
                .validatePropertyCardsAreCorrectlyFiltered()
                .waitForResultsToAppear()
                .validateOffersAreTopReviewed();
    }

    @Test(priority = 5)
    public void propertyDetailsConsistencyTest() {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(OSAKA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(OSAKA)
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .selectCheckInDate(CHECKING_DAY_THREE)
                .selectCheckOutDay(CHECKOUT_DAY_3)
                .clickSearchButton();
        listingSteps
                .validateCheckInDate(CHECKING_DAY_THREE)
                .validateCheckOutDate(CHECKOUT_DAY_3)
                .validateResultsAppear()
                .waitElementToBeStable()
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
                .validateResultsAfterApplyingReservationPolicy(ReservationPolicy.NO_PREPAYMENT)
                .waitForResultsToAppear()
                .waitElementToBeStable()
                .captureFirstPropertyDetails()
                .clickOnFirstPropertyCard();
        detailsSteps
                .goToDetailsPage()
                .scrollToTop()
                .validateWeAreOnDetailsPage()
                .captureActualPropertyDetails()
                .assertLocationMatchesListing()
                .assertReviewScoreMatchesListing()
                .assertTitleMatchesListing();
    }
}
