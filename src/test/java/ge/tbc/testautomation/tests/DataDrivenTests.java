package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.data.models.BookingCase;
import ge.tbc.testautomation.runners.BaseTest;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static ge.tbc.testautomation.data.Constants.*;

@Epic("Booking Platform Core Functionality")
public class DataDrivenTests extends BaseTest {
    @BeforeClass
    public void navigateToApplication() {
        page.navigate(BOOKING_BASE_URL);
    }

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    @Story("User Search Form Validation")
    @Feature("Search and Booking Flow")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Validates that users can successfully search for accommodations" +
            " using various search criteria including destination, check-in/out dates," +
            " and guest count. Verifies that search results " +
            "correctly reflect the input parameters.")
    public void searchFormTest(BookingCase bookingCase) {
        homeSteps
                .waitForLoadState()
                .hideDialog()
                .hideGoogleOneTap()
                .setViewportSize(WIDTH_FOR_DESKTOP, HEIGHT_FOR_DESKTOP)
                .validateSearchBarIsClear()
                .searchLocation(bookingCase.getDestination())
                .waitForLocationOptionsToAppear()
                .selectLocationOption(bookingCase.getDestination())
                .ifNotVisibleClickOnCalendar()
                .validateCalendarContainerIsVisible()
                .clickNextMonthButton()
                .waitNextMonthDatesToBeVisible()
                .selectCheckIn(bookingCase.getCheckIn())
                .selectCheckOut(bookingCase.getCheckOut())
                .clickOccupancy()
                .waitOccupancyPanelToBeVisible()
                .selectGuests(bookingCase.getGuests())
                .clickSearchButton();
        listingSteps
                .waitElementToBeStable()
                .hideDialog()
                .hideGoogleOneTap()
                .validateResultsLocationIsCorrect(bookingCase.getDestination())
                .validateCheckIn(bookingCase.getCheckIn())
                .validateCheckOut(bookingCase.getCheckOut())
                .validateGuests(bookingCase.getGuests());

    }
}
