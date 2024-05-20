package business.logic;

import data.access.OrderDAO;
import data.access.validators.OrderValidator;
import model.Bill;
import model.Order;
import model.Product;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class implements validation and database operations for the "client" data type.
 */
public class OrderBLL {
    private final OrderDAO orderDAO;
    private final OrderValidator validator;

    private ProductBLL productBll;
    private BillBLL billBll;

    public OrderBLL() {
        orderDAO = new OrderDAO();
        validator = new OrderValidator();
        productBll = new ProductBLL();
        billBll = new BillBLL();
    }

    /**
     * Retrieve all records from the database.
     *
     * @return a List with all the found records.
     */
    public List<Order> findOrderAll() {
        List<Order> orders = orderDAO.findAll();
        if (orders == null) {
            throw new NoSuchElementException("The order table is empty");
        }
        return orders;
    }

    /**
     * Retrieve the record with the ID provided as a parameter from the database.
     *
     * @return the object with the entered ID if it exists, otherwise return null.
     */
    public Order findOrderById(int id) {
        Order order = orderDAO.findById(id);
        if (order == null) {
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        }
        return order;
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, insert it into the database.
     *
     * @return the object if it has been successfully inserted; otherwise, return null.
     */
    public Order insertOrder(Order order) {
        validator.validate(order);
        Product product = productBll.findProductById(order.getProductId());
        product.setQuantity(product.getQuantity() - order.getQuantity());
        productBll.updateProduct(product);

        Order newOrder = orderDAO.insert(order);
        String timeStamp = new SimpleDateFormat("E yyyy.MM.dd 'at' hh:mm:ss a zzz").format(Calendar.getInstance().getTime());
        Bill bill = new Bill(timeStamp, newOrder.getId());
        billBll.insertBill(bill);

        return newOrder;
    }
}
