package ge.tbc.testautomation.utils;

import com.microsoft.playwright.Locator;

public class OfferElement {
    private final String content;
    private final Locator locator;

    public OfferElement(Locator locator) {
        this.locator = locator;
        this.content = locator.textContent();
    }

    public String getContent() {
        return content;
    }

    public Locator getLocator() {
        return locator;
    }
}
