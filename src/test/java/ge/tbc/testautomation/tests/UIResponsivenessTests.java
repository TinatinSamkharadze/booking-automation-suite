package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Epic("UI Responsiveness")
public class UIResponsivenessTests extends BaseTest {
    @BeforeClass
    public void navigateToApplication() {
        page.navigate(BOOKING_BASE_URL);
    }


    @Story("Verify layout and elements on desktop devices")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that the application layout, header, footer," +
            " and elements are correctly displayed" +
            " and functional on desktop viewports.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Feature("Desktop Responsiveness")
    @Test(priority = 1)
    public void desktopResponsiveTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_DESKTOP, HEIGHT_FOR_DESKTOP)
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
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
                .searchLocation(OSAKA)
                .waitForLocationOptionsToAppear()
                .selectLocationOption(OSAKA)
                .clickNextMonthButton()
                .selectCheckInDate(FIRST_DAY_OF_MONTH)
                .selectCheckOutDay(LAST_DAY_OF_MONTH)
                .validateDateIsSelected()
                .clickOccupancy()
                .waitOccupancyPanelToBeVisible()
                .selectGuests(GUESTS)
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .changeToGrid()
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateGridLayout(DESKTOP_GRID_LAYOUT)
                .scrollToTop()
                .navigateToHomePage()
                .waitForResultsToAppear();
    }

    @Story("Verify layout and elements on tablet devices")
    @Severity(SeverityLevel.NORMAL)
    @Description("Validates the correct rendering and behavior of UI components" +
            " on tablet screen resolutions.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Feature("Tablet Responsiveness")
    @Test(priority = 2)
    public void tabletResponsiveTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_TABLET, HEIGHT_FOR_TABLET)
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky()
                .scrollToBottom()
                .validateFooterLinksAreHorizontallyAligned()
                .scrollToTop()
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .changeToGrid()
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateGridLayout(TABLET_GRID_LAYOUT)
                .scrollToTop()
                .navigateToHomePage()
                .waitForResultsToAppear();
    }

    @Story("Verify layout and elements on mobile devices")
    @Severity(SeverityLevel.NORMAL)
    @Description("Ensures the UI adapts properly to smaller mobile devices" +
            " and critical elements are accessible.")
    @Link(name = "Booking App", url = "https://booking.com")
    @Feature("Mobile Responsiveness")
    @Test(priority = 3)
    public void mobileResponsivenessTest() {
        homeSteps
                .setViewportSize(WIDTH_FOR_MOBILE, HEIGHT_FOR_MOBILE)
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .validateNavBarIsVisible()
                .validateSearchBarIsVisible()
                .validateFooterLinksAreVertical()
                .validateHamburgerMenuIsVisible()
                .scrollDownToSeeCheckIfHeaderIsVisible()
                .waitForHeaderScrollToComplete()
                .validateHeaderIsSticky()
                .scrollToTop()
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .changeToGrid()
                .waitElementToBeStable()
                .scrollThroughPropertyCards()
                .validateGridLayout(MOBILE_GRID_LAYOUT);
    }
}
