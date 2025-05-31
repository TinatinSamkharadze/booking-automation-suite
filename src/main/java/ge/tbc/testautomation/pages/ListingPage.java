package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import ge.tbc.testautomation.data.enums.ReservationPolicy;
import ge.tbc.testautomation.data.enums.PropertyRating;
import ge.tbc.testautomation.data.enums.PropertyType;

public class ListingPage {
    private final Page page;
    public Locator propertyCards,
            searchHeader,
            dismissButton,
            sortButton,
            propertyRating,
            propertyCardTitle,
    reviewScore,
    searchBoxForCalendar;

    public ListingPage(Page page)
    {
        this.page = page;
        this.propertyCards = page.locator("div[data-testid='property-card'] ");
        this.searchHeader = page.locator(".b87c397a13.cacb5ff522");
        this.dismissButton = page.locator("#b2searchresultsPage");
        this.sortButton = page.locator(".cd46a6a263");
        this.propertyRating = page.getByRole(AriaRole.OPTION, new Page.GetByRoleOptions().setName("Top reviewed"));
        this.propertyCardTitle = page.locator(".b87c397a13.a3e0b4ffd1").first();
        this.reviewScore = page.locator("[data-testid='review-score']");
        this.searchBoxForCalendar = page.getByTestId("searchbox-dates-container");

    }

    public String locationSelector = "[data-testid='address']";
    public String titleSelector = "[data-testid='title']";
    public String reviewScoreSelector = "[data-testid='review-score']";




    public Locator getPropertyTypeCheckbox(PropertyType propertyType) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                propertyType.getLabel());
        return page.locator(xpath).first();
    }

    public Locator getPropertyRatingCheckbox(PropertyRating rating) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                rating.getLabel());
        return page.locator(xpath).first();
    }

    public Locator getReservationPolicyCheckbox(ReservationPolicy reservationPolicy) {
        String xpath = String.format("//div[contains(text(), '%s')]/ancestor::*/span[contains(@class, 'c850687b9b')]",
                reservationPolicy.getLabel());
        return page.locator(xpath).first();
    }

}