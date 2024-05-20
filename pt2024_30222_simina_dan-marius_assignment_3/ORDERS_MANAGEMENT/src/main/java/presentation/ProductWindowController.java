package presentation;

import business.logic.ProductBLL;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the products viewing window.
 */
public class ProductWindowController implements ActionListener {
    MainWindowController mainWindowController;
    ProductWindow productWindow;
    ProductBLL productBLL;

    public ProductWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        productWindow = new ProductWindow(this);
        productBLL = new ProductBLL();
        updateTable();
    }

    public void updateTable() {
        productWindow.updateTable(productBLL.findProductAll());
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Back" -> {
                mainWindowController.setVisible(true);
                productWindow.setVisible(false);
            }
            case "Update" -> update();
            case "Insert" -> insert();
            case "Delete" -> delete();
        }
    }

    private void update() {
        JTable table = productWindow.getTableProducts();
        int row = -1;
        try {
            for(int i = 0; i < table.getRowCount(); i++) {
                row = Integer.parseInt(table.getValueAt(i, 0).toString());
                Product product = new Product(Integer.parseInt(table.getValueAt(i, 0).toString()), table.getValueAt(i, 1).toString(), Integer.parseInt(table.getValueAt(i, 2).toString()), Integer.parseInt(table.getValueAt(i, 3).toString()));
                product = productBLL.updateProduct(product);
            }
            productWindow.updateTable(productBLL.findProductAll());
        } catch (Exception ignored) {
            message(row);
        }
    }

    private void insert() {
        try {
            Product product = new Product(productWindow.getTextFieldName().getText(), Integer.parseInt(productWindow.getTextFieldQuantity().getText()), Integer.parseInt(productWindow.getTextFieldPrice().getText()));
            productBLL.insertProduct(product);
            productWindow.updateTable(productBLL.findProductAll());
        }
        catch (Exception ignored) {
            message(-1);
        }
    }

    private void delete() {
        JTable table = productWindow.getTableProducts();
        int[] rows = table.getSelectedRows();
        for (int row : rows) {
            Product product = new Product(Integer.parseInt(table.getValueAt(row, 0).toString()), table.getValueAt(row, 1).toString(), Integer.parseInt(table.getValueAt(row, 2).toString()), Integer.parseInt(table.getValueAt(row, 3).toString()));
            productBLL.deleteProduct(product);
        }
        productWindow.updateTable(productBLL.findProductAll());
    }

    private void message(int row) {
        if(row != -1) {
            JOptionPane.showMessageDialog(null, "Invalid data!\n(Quantity must be between 1 and 100)\n(Price must be between 0 and 100000)\nrow: " + row);
        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid data!\n(Quantity must be between 1 and 100)\n(Price must be between 0 and 100000)");
        }
    }

    public void setVisible(boolean visible) {
        productWindow.setVisible(visible);
    }
}
