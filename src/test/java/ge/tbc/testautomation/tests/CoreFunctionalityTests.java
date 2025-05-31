package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.enums.ReservationPolicy;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;
import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class CoreFunctionalityTests extends BaseTest {

    @Test(priority = 1)
    public void searchTest() {
        homeSteps
                .searchLocation(KYOTO)
                .selectLocationOption(KYOTO)
                .clickOnCalendar()
                .clickSearchButton()
                .clickOnCalendar();
        listingSteps
                .validateResultsAppear()
                .validateSearchHeaderContainsCorrectText(KYOTO)
                .scrollThroughPropertyCards()
                .validateResultsLocationIsCorrect(KYOTO);
    }

    @Test(priority = 2)
    public void dateSelectionTest()
    {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .selectLocationOption(KYOTO)
                .clickNextMonthButton()
                .selectCheckInDate(FIRST_DAY_OF_MONTH)
                .selectCheckOutDay(LAST_DAY_OF_MONTH)
                .clickSearchButton();
        listingSteps
                .validateCheckInDate(FIRST_DAY_OF_MONTH)
                .validateCheckOutDate(LAST_DAY_OF_MONTH);

    }

    @Test(priority = 3)
    public void filterApplicationTest()
    {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(NEW_YORK)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(NEW_YORK)
                .clickOnCalendar();
        listingSteps
                .waitElementToBeStable();
        homeSteps
                .clickNextMonthButton()
                .selectCheckInDate("2")
                .selectCheckOutDay("29")
                .clickSearchButton();
        listingSteps
                .validateCheckInDate("2")
                .validateCheckOutDate("29")
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
    public void sortByReviewScoreTest()
    {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(NEW_YORK)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(NEW_YORK)
                .clickOnCalendar();
        listingSteps
                .waitElementToBeStable();
        homeSteps
                .clickNextMonthButton()
                .selectCheckInDate("3")
                .selectCheckOutDay("28")
                .clickSearchButton();
        listingSteps
                .validateCheckInDate("3")
                .validateCheckOutDate("28")
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
    public void propertyDetailsConsistencyTest()
    {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .clickOnCalendar();
        listingSteps
                .waitElementToBeStable();
        homeSteps
                .clickNextMonthButton()
                .selectCheckInDate("4")
                .selectCheckOutDay("27")
                .clickSearchButton();
        listingSteps
                .validateCheckInDate("4")
                .validateCheckOutDate("27")
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
