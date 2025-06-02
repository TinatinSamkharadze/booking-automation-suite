package ge.tbc.testautomation.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class DetailsPage {
    public Locator title,
            reviewScore,
            location,
            reserveButton,
            overview;

    public DetailsPage(Page page) {
        this.title = page.locator("//h2").first();
        this.reserveButton = page.locator("//span[@class='bui-button__text' and normalize-space(text())='Reserve']").first();
        this.overview = page.locator("#overview-tab-trigger");
    }

    public String reviewScoreSelector = "[data-testid='review-score-right-component']";
    public String titleSelector = "#hp_hotel_name";
    public String locationSelector = ".b99b6ef58f.cb4b7a25d9";
    public String roomType = ".hprt-roomtype-icon-link";
}

