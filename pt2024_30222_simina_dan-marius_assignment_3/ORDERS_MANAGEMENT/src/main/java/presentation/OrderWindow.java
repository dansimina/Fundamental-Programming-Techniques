package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Implement the window for inserting orders.
 */
public class OrderWindow extends JFrame {
    private JButton buttonBack;
    private JButton buttonOrder;
    private JTable tableClients;
    private JTable tableProducts;
    private JTextField textFieldQuantity;
    private JPanel contentPane;
    private JButton viewOrdersButton;
    private JLabel labelPrice;

    ActionListener actionListener;

    public OrderWindow(ActionListener actionListener) {
        this.actionListener = actionListener;

        tableClients.setDefaultEditor(Object.class, null);
        tableProducts.setDefaultEditor(Object.class, null);

        tableClients.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableProducts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        this.add(contentPane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(900, 500);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(false);

        initializeButtons();
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    private void initializeButtons() {
        buttonBack.setActionCommand("Back");
        buttonBack.addActionListener(actionListener);
        buttonOrder.setActionCommand("Insert");
        buttonOrder.addActionListener(actionListener);
        viewOrdersButton.setActionCommand("ViewOrders");
        viewOrdersButton.addActionListener(actionListener);
    }

    public void updateTables(List<Client> clients, List<Product> products) {
        PopulateTable.populateTable(clients, tableClients);
        PopulateTable.populateTable(products, tableProducts);
    }

    public JTable getTableClients() {
        return tableClients;
    }

    public JTable getTableProducts() {
        return tableProducts;
    }

    public void setLabelPrice(String text) {
        this.labelPrice.setText("Price: " + text);
    }
}
