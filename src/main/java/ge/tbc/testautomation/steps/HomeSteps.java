package ge.tbc.testautomation.steps;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import ge.tbc.testautomation.pages.HomePage;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.sql.Date;
import java.util.Calendar;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomeSteps {
    Page page;
    HomePage homePage;
    SoftAssert softAssert;
    Calendar cal = Calendar.getInstance();

    public HomeSteps(Page page) {
        this.page = page;
        homePage = new HomePage(page);
        this.softAssert = new SoftAssert();
    }

    public HomeSteps searchLocation(String location) {
        homePage.searchBar.fill(location);
        return this;
    }

    public HomeSteps clickSearchButton() {
        homePage.searchButton.click();
        return this;
    }

    public HomeSteps validateSearchBarIsClear() {
        homePage.searchBar.clear();
        return this;
    }

    public HomeSteps waitForLocationOptionsToAppear() {
        homePage.locationOptions.waitFor();
        return this;
    }

    public HomeSteps clickOnCalendar() {
        homePage.calendar.click();
        return this;
    }

    public HomeSteps validateCalendarIsVisible() {
        boolean isCalendarVisible = homePage.calendarContainer.isVisible();

        if (!isCalendarVisible) {
            page.waitForTimeout(2000);
            clickOnCalendar();
        }
        return this;
    }

    public HomeSteps clickNextMonthButton() {
        homePage.nextMonthButton.click();
        return this;
    }

    public HomeSteps selectLocationOption(String option) {
        homePage.listBox.getByText(option).first().click();
        return this;
    }

    public HomeSteps setViewportSize(int width, int height) {
        page.setViewportSize(width, height);
        return this;
    }


    public HomeSteps selectCheckInDate(String checkInDay) {
        homePage.checkInDay(checkInDay).click();
        return this;
    }

    public HomeSteps selectCheckOutDay(String checkOutDay) {
        homePage.checkOutDay(checkOutDay).click();
        return this;
    }

    public HomeSteps waitForLoadState() {
        page.waitForLoadState();
        return this;
    }

    public HomeSteps validateNavBarIsVisible() {
        assertThat(homePage.navBar).isVisible();
        return this;
    }

    public HomeSteps validateHamburgerMenuIsVisible() {
        assertThat(homePage.hamburgerMenu).isVisible();
        return this;
    }

    public HomeSteps validateHamburgerMenuIsNotVisible() {
        assertThat(homePage.hamburgerMenu).isHidden();
        return this;
    }

    public HomeSteps validateSearchBarIsVisible() {
        assertThat(homePage.searchBar).isVisible();
        return this;
    }

    public HomeSteps scrollDownToSeeCheckIfHeaderIsVisible() {
        page.evaluate("window.scrollBy(0, 3000)");
        return this;
    }

    public HomeSteps waitForHeaderScrollToComplete() {
        page.waitForTimeout(2000);
        return this;
    }

    public HomeSteps validateHeaderIsSticky() {
        assertThat(homePage.header).isVisible();
        return this;
    }

    public HomeSteps scrollToBottom() {
        page.evaluate("() => window.scrollTo(0, document.body.scrollHeight)");
        return this;
    }


    public HomeSteps scrollToTop() {
        page.evaluate("() => window.scrollTo(0, 0)");
        return this;
    }

    public HomeSteps validateFooterLinksAreHorizontallyAligned() {
        Locator links = homePage.footerLinks;
        double firstY = links.nth(0).boundingBox().y;
        double secondY = links.nth(1).boundingBox().y;
        softAssert.assertTrue(Math.abs(secondY - firstY) <= 10);
        return this;
    }

    public HomeSteps validateFooterLinksAreVertical() {
        Locator links = homePage.footers;
        double firstX = links.nth(0).boundingBox().x;
        double secondX = links.nth(1).boundingBox().x;
        Assert.assertTrue(Math.abs(secondX - firstX) <= 10);
        return this;
    }

    public HomeSteps selectCheckIn(Date checkInDate) {
        cal.setTime(checkInDate);
        String checkInDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        homePage.checkInDay(checkInDay).click();
        return this;
    }

    public HomeSteps selectCheckOut(Date checkOutDate) {
        cal.setTime(checkOutDate);
        String checkOutDay = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
        homePage.checkOutDay(checkOutDay).click();
        return this;
    }

    public HomeSteps clickOccupancy() {
        homePage.occupancy.click();
        return this;
    }

    public HomeSteps waitOccupancyPanelToBeVisible() {
        page.waitForTimeout(2000);
        return this;
    }

    public HomeSteps selectGuests(int guestCount) {
        int currentCount = 2;
        if (guestCount > currentCount) {
            int clicksNeeded = guestCount - currentCount;
            for (int i = 0; i < clicksNeeded; i++) {
                homePage.plus.click();
            }
        } else if (guestCount < currentCount) {
            int clicksNeeded = currentCount - guestCount;
            for (int i = 0; i < clicksNeeded; i++) {
                homePage.minus.click();
            }
        }
        return this;
    }
}