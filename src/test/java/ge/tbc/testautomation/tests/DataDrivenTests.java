package ge.tbc.testautomation.tests;

import ge.tbc.testautomation.data.DataSupplier;
import ge.tbc.testautomation.data.models.BookingCase;
import ge.tbc.testautomation.runners.BaseTest;
import org.testng.annotations.Test;

public class DataDrivenTests extends BaseTest {

    @Test(dataProvider = "bookingTestData", dataProviderClass = DataSupplier.class)
    public void searchFormTest(BookingCase bookingCase)
    {
        homeSteps
                .validateSearchBarIsClear()
                .searchLocation(bookingCase.getDestination())
                .selectLocationOption(bookingCase.getDestination())
                .ifNotVisibleClickOnCalendar()
                .clickNextMonthButton()
                .validateCalendarContainerIsVisible()
                .selectCheckIn(bookingCase.getCheckIn())
                .selectCheckOut(bookingCase.getCheckOut())
                .clickOccupancy()
                .waitOccupancyPanelToBeVisible()
                .selectGuests(bookingCase.getGuests())
                .clickSearchButton();
        listingSteps
                .validateResultsLocationIsCorrect(bookingCase.getDestination())
                .validateCheckIn(bookingCase.getCheckIn())
                .validateCheckOut(bookingCase.getCheckOut());

    }
}
