package ge.tbc.testautomation.data.models;

import ge.tbc.testautomation.utils.OfferElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferModel {
    public OfferElement title,
    reviewScore,
    location,
    roomType;
}