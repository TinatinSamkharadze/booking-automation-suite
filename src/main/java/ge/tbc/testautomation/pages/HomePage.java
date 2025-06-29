package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

public class HomePage {
    public final Page page;
    public Locator searchBar,
            searchButton,
            calendar,
            listBox,
            navBar,
            hamburgerMenu,
            footerLinks,
            header,
            calendarContainer,
            nextMonthButton,
            locationOptions,
            footers,
            occupancy,
            guests,
            minus,
            plus,
            firstOption,
            loader,
            selectedDate,
            loginPopUp,
            registrationWindow,
            dialog,
            singIn;

    public HomePage(Page page) {
        this.page = page;
        this.searchBar = page.getByPlaceholder("Where are you going?");
        this.searchButton = page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Search"));
        this.calendar = page.getByTestId("searchbox-dates-container");
        this.listBox = page.getByRole(AriaRole.LISTBOX).first();
        this.navBar = page.locator(".c4a6e8e871").first();
        this.hamburgerMenu = page.getByTestId("header-mobile-menu-button").first();
        this.footerLinks = page.locator(".e9f7361569.b049f18dec");
        this.header = page.getByTestId("web-shell-header-mfe");
        this.calendarContainer = page.getByTestId("searchbox-datepicker-calendar");
        this.nextMonthButton = page.locator("[aria-label='Next month']");
        this.locationOptions = page.locator("#autocomplete-results");
        this.footers = page.locator(".cec0620c60");
        this.occupancy = page.getByTestId("occupancy-config");
        this.guests = page.locator(".e301a14002");
        this.minus = page.locator(".de576f5064.b46cd7aad7.e26a59bb37.c295306d66.c7a901b0e7.aaf9b6e287.c857f39cb2").first();
        this.plus = page.locator(".de576f5064.b46cd7aad7.e26a59bb37.c295306d66.c7a901b0e7.aaf9b6e287.dc8366caa6").first();
        this.firstOption = page.locator("#autocomplete-result-0");
        this.loader = page.locator("[data-testid='loader-container']");
        this.selectedDate = page.locator("//span[@aria-pressed ='true']").first();
        this.loginPopUp = page.locator(".TvD9Pc-Bz112c.ZYIfFd-aGxpHf-FnSee");
        this.registrationWindow = page.locator(".google-one-tap-wrapper");
        this.dialog = page.getByRole(AriaRole.DIALOG).first();
        this.singIn = page.getByText("Sign in or Register");

    }

    public Locator checkInDay(String day) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(day)).first();
    }

    public Locator checkOutDay(String day) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(day)).last();
    }


}
