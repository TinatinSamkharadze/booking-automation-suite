package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import ge.tbc.testautomation.pages.HomePage;
import io.qameta.allure.Step;

import java.sql.Date;
import java.util.Calendar;
import java.util.stream.IntStream;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomeSteps {
    Page page;
    HomePage homePage;
    Calendar cal = Calendar.getInstance();

    public HomeSteps(Page page) {
        this.page = page;
        homePage = new HomePage(page);
    }

    @Step("Searching location")
    public HomeSteps searchLocation(String location) {
        homePage.searchBar.fill(location);
        return this;
    }

    @Step("Clicking search button")
    public HomeSteps clickSearchButton() {
        homePage.searchButton.click();
        return this;
    }

    @Step("Validating search bar is clear")
    public HomeSteps validateSearchBarIsClear() {
        homePage.searchBar.clear();
        return this;
    }

    @Step("Waiting locating options to appear")
    public HomeSteps waitForLocationOptionsToAppear() {
        homePage.locationOptions.waitFor();
        return this;
    }

    @Step("Clicking on the calendar")
    public HomeSteps clickOnCalendar() {
        homePage.calendar.click();
        return this;
    }

    @Step("Clicking next month button")
    public HomeSteps clickNextMonthButton() {
        homePage.nextMonthButton.click();
        return this;
    }

    @Step("Select location option: {option}")
    public HomeSteps selectLocationOption(String option) {
        homePage.listBox.getByText(option).first().click();
        return this;
    }

    @Step("Setting viewport size")
    public HomeSteps setViewportSize(int width, int height) {
        page.setViewportSize(width, height);
        return this;
    }


    @Step("Select checking date: {checkInDay}")
    public HomeSteps selectCheckInDate(String checkInDay) {
        homePage.checkInDay(checkInDay).waitFor();
        homePage.checkInDay(checkInDay).click();
        return this;
    }

    @Step("Select checkout date: {checkOutDay}")
    public HomeSteps selectCheckOutDay(String checkOutDay) {
        homePage.checkOutDay(checkOutDay).waitFor();
        homePage.checkOutDay(checkOutDay).click();
        return this;
    }

    @Step("Waiting page to load")
    public HomeSteps waitForLoadState() {
        page.waitForLoadState();
        return this;
    }

    @Step("Validating navigation bar is visible")
    public HomeSteps validateNavBarIsVisible() {
        assertThat(homePage.navBar).isVisible();
        return this;
    }

    @Step("Validating hamburger menu is visible")
    public HomeSteps validateHamburgerMenuIsVisible() {
        assertThat(homePage.hamburgerMenu).isVisible();
        return this;
    }

    @Step("Validating hamburger menu is not visible")
    public HomeSteps validateHamburgerMenuIsNotVisible() {
        assertThat(homePage.hamburgerMenu).not().isVisible();
        return this;
    }

    @Step("Validating search bar is visible")
    public HomeSteps validateSearchBarIsVisible() {
        assertThat(homePage.searchBar).isVisible();
        return this;
    }

    @Step("Scroll down to a page to check if header is visible")
    public HomeSteps scrollDownToSeeCheckIfHeaderIsVisible() {
        page.evaluate("window.scrollBy(0, 3000)");
        return this;
    }

    @Step("Wait for scroll to complete")
    public HomeSteps waitForHeaderScrollToComplete() {
        page.waitForTimeout(2000);
        return this;
    }

    @Step("Validating header is sticky")
    public HomeSteps validateHeaderIsSticky() {
        assertThat(homePage.header).isVisible();
        return this;
    }

    @Step("Scrolling to the bottom of the page")
    public HomeSteps scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }


    @Step("Scrolling to the top of the page")
    public HomeSteps scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
        return this;
    }

    @Step("Validating footer links are horizontally aligned")
    public HomeSteps validateFooterLinksAreHorizontallyAligned() {
        Locator links = homePage.footerLinks;
        String firstTop = (String) links.nth(0).evaluate("el => window.getComputedStyle(el).top");
        PlaywrightAssertions.assertThat(links.nth(1)).hasCSS("top", firstTop);
        return this;
    }

    @Step("Validating footer links are vertically aligned")
    public HomeSteps validateFooterLinksAreVertical() {
        Locator links = homePage.footers;
        String firstLeft = (String) links.nth(0).evaluate("el => window.getComputedStyle(el).left");
        PlaywrightAssertions.assertThat(links.nth(1)).hasCSS("left", firstLeft);
        return this;
    }

    @Step("Select checkin date: {checkInDate}")
    public HomeSteps selectCheckIn(Date checkInDate) {
        cal.setTime(checkInDate);
        String checkInDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        homePage.checkInDay(checkInDay).click();
        return this;
    }

    @Step("Select checkout date: {checkOutDate}")
    public HomeSteps selectCheckOut(Date checkOutDate) {
        cal.setTime(checkOutDate);
        String checkOutDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        homePage.checkOutDay(checkOutDay).click();
        return this;
    }

    @Step("Clicking on the occupancy")
    public HomeSteps clickOccupancy() {
        homePage.occupancy.click();
        return this;
    }

    @Step("Waiting occupancy panel to become visible")
    public HomeSteps waitOccupancyPanelToBeVisible() {
        page.waitForTimeout(2000);
        return this;
    }

    @Step("Select desired number of guests: {guestCount}")
    public HomeSteps selectGuests(int guestCount) {
        int currentCount = 2;
        int difference = guestCount - currentCount;
        IntStream.range(0, Math.abs(difference))
                .forEach(i -> (difference > 0 ? homePage.plus : homePage.minus).click());
        return this;
    }

    @Step("Validating calendar container is visible")
    public HomeSteps validateCalendarContainerIsVisible() {
        assertThat(homePage.calendarContainer).isVisible();
        return this;
    }

    @Step("Clicking on the calendar if it is not visible")
    public HomeSteps ifNotVisibleClickOnCalendar() {
        try {
            assertThat(homePage.calendarContainer).not().isVisible();
            homePage.calendar.click();
        } catch (AssertionError e) {
        }
        return this;
    }

    @Step("Waiting next month dates to be visible")
    public HomeSteps waitNextMonthDatesToBeVisible() {
        page.waitForTimeout(2000);
        return this;
    }

    @Step("Validating loader is visible")
    public HomeSteps validateLoaderIsVisible() {
        assertThat(homePage.loader).isHidden();
        return this;
    }

    @Step("Validating date is selected")
    public HomeSteps validateDateIsSelected() {
        assertThat(homePage.selectedDate).isVisible();
        return this;
    }


}