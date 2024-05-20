package data.access.validators;

import business.logic.ClientBLL;
import business.logic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

/**
 * Implement the validation method for an order.
 * If the client or product is invalid, or if the entered quantity is invalid, throw an exception.
 */
public class OrderValidator implements Validator<Order> {
    private static final int MIN_QUANTITY = 1;
    private final ClientBLL clientBLL;
    private final ProductBLL productBLL;

    public OrderValidator() {
        clientBLL = new ClientBLL();
        productBLL = new ProductBLL();
    }


    @Override
    public void validate(Order order) {
        Client client = clientBLL.findClientById(order.getClientId());
        Product product = productBLL.findProductById(order.getProductId());
        if (client == null && product == null) {
            throw new IllegalArgumentException("The client or product is null!");
        }
        if (order.getQuantity() < MIN_QUANTITY || order.getQuantity() > product.getQuantity()) {
            throw new IllegalArgumentException("The orderBill quantity is invalid!");
        }
    }
}
