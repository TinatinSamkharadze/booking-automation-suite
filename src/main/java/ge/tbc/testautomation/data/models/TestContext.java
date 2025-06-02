package ge.tbc.testautomation.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestContext {
    private OfferModel expectedOffer;
    private OfferModel actualOffer;

}
