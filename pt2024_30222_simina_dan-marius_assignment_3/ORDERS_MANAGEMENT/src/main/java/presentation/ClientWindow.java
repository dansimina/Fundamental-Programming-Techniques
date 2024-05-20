package presentation;

import model.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Implement the window for viewing, inserting, updating, and deleting clients.
 */
public class ClientWindow extends JFrame {
    private JTable tableClients;
    private JTextField textFieldName;
    private JTextField textFieldAge;
    private JTextField textFieldEmail;
    private JTextField textFieldAddress;
    private JButton buttonInsert;
    private JButton buttonBack;
    private JPanel contentPane;
    private JScrollPane scrollPane;
    private JButton buttonUpdate;
    private JButton buttonDelete;

    ActionListener actionListener;

    public ClientWindow(ActionListener actionListener) throws HeadlessException {
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

    public void updateTable(List<Client> clients) {
        PopulateTable.populateTable(clients, tableClients);
    }

    public JTextField getTextFieldName() {
        return textFieldName;
    }

    public JTextField getTextFieldAge() {
        return textFieldAge;
    }

    public JTextField getTextFieldEmail() {
        return textFieldEmail;
    }

    public JTextField getTextFieldAddress() {
        return textFieldAddress;
    }

    public JTable getTableClients() {
        return tableClients;
    }
}
