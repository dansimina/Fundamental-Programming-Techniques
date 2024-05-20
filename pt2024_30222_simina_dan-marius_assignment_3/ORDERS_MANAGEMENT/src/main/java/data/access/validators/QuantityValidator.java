package data.access.validators;

import model.Product;

/**
 * Implement the validation method for quantity.
 * If the quantity is not within the range of 1 to 100, throw an exception.
 */
public class QuantityValidator implements Validator<Product> {
    private static final int MIN_QUANTITY = 0;
    private static final int MAX_QUANTITY = 100;

    public void validate(Product t) {

        if (t.getQuantity() < MIN_QUANTITY || t.getQuantity() > MAX_QUANTITY) {
            throw new IllegalArgumentException("The Product Quantity limit is not respected!");
        }
    }
}
