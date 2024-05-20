package presentation;

import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Implement the window for viewing, inserting, updating, and deleting products.
 */
public class ProductWindow extends JFrame {
    private JPanel contentPane;
    private JTable tableProducts;
    private JButton buttonBack;
    private JButton buttonUpdate;
    private JButton buttonInsert;
    private JButton buttonDelete;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JTextField textFieldQuantity;

    ActionListener actionListener;

    public ProductWindow(ActionListener actionListener) throws HeadlessException {
        this.actionListener = actionListener;

        this.add(contentPane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(false);

        initializeButtons();
    }

    private void initializeButtons() {
        buttonBack.setActionCommand("Back");
        buttonBack.addActionListener(actionListener);
        buttonUpdate.setActionCommand("Update");
        buttonUpdate.addActionListener(actionListener);
        buttonInsert.setActionCommand("Insert");
        buttonInsert.addActionListener(actionListener);
        buttonDelete.setActionCommand("Delete");
        buttonDelete.addActionListener(actionListener);
    }

    public void updateTable(List<Product> products) {
        PopulateTable.populateTable(products, tableProducts);
    }

    public JTextField getTextFieldName() {
        return textFieldName;
    }

    public JTextField getTextFieldPrice() {
        return textFieldPrice;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }
}
