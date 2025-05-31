package ge.tbc.testautomation.data.enums;

public enum ReservationPolicy {
    FREE_CANCELLATION("Free cancellation"),
    NO_PREPAYMENT("No prepayment");

    private final String label;

    ReservationPolicy(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
