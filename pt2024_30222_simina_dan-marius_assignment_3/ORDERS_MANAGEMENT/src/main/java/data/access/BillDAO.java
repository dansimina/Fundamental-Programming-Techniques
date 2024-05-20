package data.access;

import model.Bill;

import java.util.List;

public class BillDAO extends AbstractDAO<Bill> {
    public BillDAO() {
        super();
    }

    @Override
    public List<Bill> findAll() {
        return super.findAll();
    }

    @Override
    public Bill insert(Bill bill) {
        return super.insert(bill);
    }
}
