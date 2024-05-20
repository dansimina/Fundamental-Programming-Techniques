package business.logic;

import data.access.BillDAO;
import model.Bill;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class implements validation and database operations for the "client" data type.
 */
public class BillBLL {
    private final BillDAO billDAO;

    public BillBLL() {
        billDAO = new BillDAO();
    }

    /**
     * Retrieve all records from the database.
     *
     * @return a List with all the found records.
     */
    public List<Bill> findBillAll() {
        List<Bill> bill = billDAO.findAll();
        if (bill == null) {
            throw new NoSuchElementException("The bill table is empty");
        }
        return bill;
    }

    /**
     * Apply the validation methods to the received object, and if the object meets all conditions, insert it into the database.
     *
     * @return the object if it has been successfully inserted; otherwise, return null.
     */
    public Bill insertBill(Bill bill) {
        return billDAO.insert(bill);
    }
}
