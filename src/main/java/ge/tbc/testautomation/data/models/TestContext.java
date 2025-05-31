package ge.tbc.testautomation.data.models;

public class TestContext {
    private OfferModel expectedOffer;
    private OfferModel actualOffer;

    public OfferModel getExpectedOffer() {
        return expectedOffer;
    }

    public void setExpectedOffer(OfferModel expectedOffer) {
        this.expectedOffer = expectedOffer;
    }

    public OfferModel getActualOffer() {
        return actualOffer;
    }

    public void setActualOffer(OfferModel actualOffer) {
        this.actualOffer = actualOffer;
    }
}
