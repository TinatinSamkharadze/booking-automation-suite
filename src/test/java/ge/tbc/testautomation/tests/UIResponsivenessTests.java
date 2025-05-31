package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class UIResponsivenessTests extends BaseTest {

    @Test(priority = 6)
    public void desktopResponsiveTest() {
        homeSteps
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .scrollToBottom()
                .validateFooterLinksAreHorizontallyAligned()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .clickOnCalendar()
                .validateCalendarIsVisible()
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear();
        homeSteps
                .clickOnCalendar()
                .validateHamburgerMenuIsNotVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky();
        listingSteps
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(DESKTOP_GRID_LAYOUT);
    }

    @Test(priority = 7)
    public void tabletResponsiveTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_TABLET, HEIGHT_FOR_TABLET)
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .scrollToBottom()
                .validateFooterLinksAreHorizontallyAligned()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .validateCalendarIsVisible()
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear();
        homeSteps
                .clickOnCalendar()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky();
        listingSteps
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(TABLET_GRID_LAYOUT);
    }

    @Test(priority = 8)
    public void mobileResponsivenessTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_MOBILE, HEIGHT_FOR_MOBILE)
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .validateFooterLinksAreVertical()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .validateCalendarIsVisible()
                .clickSearchButton();
        listingSteps
                .waitForResultsToAppear();
        homeSteps
                .clickOnCalendar()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky();
        listingSteps
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(MOBILE_GRID_LAYOUT);
    }
}
