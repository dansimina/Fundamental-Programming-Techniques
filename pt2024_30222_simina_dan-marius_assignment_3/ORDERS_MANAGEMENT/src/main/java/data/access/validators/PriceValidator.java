package data.access.validators;

import model.Product;

/**
 * Implement the validation method for price.
 * If the price is not within the range of 0 to 1000000, throw an exception.
 */
public class PriceValidator implements Validator<Product> {
    private static final int MIN_PRICE = 0;
    private static final int MAX_PRICE = 1000000;

    public void validate(Product t) {

        if (t.getPrice() < MIN_PRICE || t.getPrice() > MAX_PRICE) {
            throw new IllegalArgumentException("The Product Price limit is not respected!");
        }
    }
}
