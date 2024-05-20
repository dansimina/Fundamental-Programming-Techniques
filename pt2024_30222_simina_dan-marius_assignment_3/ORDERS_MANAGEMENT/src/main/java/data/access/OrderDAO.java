package data.access;

import model.Order;

import java.util.List;

public class OrderDAO extends AbstractDAO<Order> {
    public OrderDAO() {
        super();
    }

    @Override
    public List<Order> findAll() {
        return super.findAll();
    }

    @Override
    public Order findById(int id) {
        return super.findById(id);
    }

    @Override
    public Order insert(Order orderBill) {
        return super.insert(orderBill);
    }
}
