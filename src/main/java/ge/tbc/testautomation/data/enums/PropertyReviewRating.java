package ge.tbc.testautomation.data.enums;

import java.util.Arrays;

public enum PropertyReviewRating {
    EXCEPTIONAL("Exceptional"),
    WONDERFUL("Wonderful"),
    VERY_GOOD("Very Good"),
    LUXURY("Luxury"),
    EXCELLENT("Excellent");

    private final String label;

    PropertyReviewRating(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static PropertyReviewRating fromText(String text) {
        return Arrays.stream(values())
                .filter(rating -> text.contains(rating.getLabel()))
                .findFirst()
                .orElse(EXCELLENT);
    }
}