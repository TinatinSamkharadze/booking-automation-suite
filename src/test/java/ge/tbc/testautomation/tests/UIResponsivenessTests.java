package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

public class UIResponsivenessTests extends BaseTest {

    @Test(priority = 1)
    public void desktopResponsiveTest() {
        homeSteps
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .scrollToBottom()
                .validateFooterLinksAreHorizontallyAligned()
                .validateHamburgerMenuIsNotVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(KYOTO)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(KYOTO)
                .ifNotVisibleClickOnCalendar()
                .selectCheckInDate(FIRST_DAY_OF_MONTH)
                .selectCheckOutDay(LAST_DAY_OF_MONTH)
                .clickOnCalendar()
                .clickSearchButton();
        listingSteps
                .changeToGrid()
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(DESKTOP_GRID_LAYOUT);
    }

    @Test(priority = 2)
    public void tabletResponsiveTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_TABLET, HEIGHT_FOR_TABLET)
                .clickOnCalendar()
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky()
                .scrollToBottom()
                .validateFooterLinksAreHorizontallyAligned()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(TOKYO_CITY)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(TOKYO_CITY)
                .ifNotVisibleClickOnCalendar()
                .selectCheckInDate(CHECKIN_DAY_ONE)
                .selectCheckOutDay(CHECKOUT_DAY_1)
                .clickOnCalendar()
                .clickSearchButton();
        listingSteps
                .changeToGrid()
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(TABLET_GRID_LAYOUT);
    }

    @Test(priority = 3)
    public void mobileResponsivenessTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_MOBILE, HEIGHT_FOR_MOBILE)
                .clickOnCalendar()
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .validateFooterLinksAreVertical()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky()
                .scrollToTop()
                .validateSearchBarIsClear()
                .searchLocation(OSAKA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(OSAKA)
                .ifNotVisibleClickOnCalendar()
                .selectCheckInDate(CHECKIN_DAY_TWO)
                .selectCheckOutDay(CHECKOUT_DAY_2)
                .clickOnCalendar()
                .clickSearchButton();
        listingSteps
                .changeToGrid()
                .scrollThroughPropertyCards()
                .validateEachRowHasExpectedCardCount(MOBILE_GRID_LAYOUT);
    }
}
