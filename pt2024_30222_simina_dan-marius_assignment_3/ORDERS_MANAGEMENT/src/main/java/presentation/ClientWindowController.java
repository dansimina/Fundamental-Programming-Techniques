package presentation;

import business.logic.ClientBLL;
import model.Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the clients viewing window.
 */
public class ClientWindowController implements ActionListener {
    MainWindowController mainWindowController;
    ClientWindow clientWindow;
    ClientBLL clientBLL;

    public ClientWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        clientWindow = new ClientWindow(this);
        clientBLL = new ClientBLL();
        updateTable();
    }

    public void updateTable() {
        clientWindow.updateTable(clientBLL.findClientAll());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Back" -> {
                mainWindowController.setVisible(true);
                clientWindow.setVisible(false);
            }
            case "Update" -> update();
            case "Insert" -> insert();
            case "Delete" -> delete();
        }
    }

    private void update() {
        JTable table = clientWindow.getTableClients();
        int row = -1;
        try {
            for (int i = 0; i < table.getRowCount(); i++) {
                row = Integer.parseInt(table.getValueAt(i, 0).toString());
                Client client = new Client(Integer.parseInt(table.getValueAt(i, 0).toString()), table.getValueAt(i, 1).toString(), table.getValueAt(i, 2).toString(), table.getValueAt(i, 3).toString(), Integer.parseInt(table.getValueAt(i, 4).toString()));
                clientBLL.updateClient(client);
            }
            clientWindow.updateTable(clientBLL.findClientAll());
        } catch (Exception ignored) {
            message(row);
        }
    }

    private void insert() {
        try {
            Client client = new Client(clientWindow.getTextFieldName().getText(), clientWindow.getTextFieldAddress().getText(), clientWindow.getTextFieldEmail().getText(), Integer.parseInt(clientWindow.getTextFieldAge().getText()));
            client = clientBLL.insertClient(client);
            clientWindow.updateTable(clientBLL.findClientAll());
        } catch (Exception ignored) {
            message(-1);
        }
    }

    private void delete() {
        JTable table = clientWindow.getTableClients();
        int[] rows = table.getSelectedRows();
        for (int row : rows) {
            Client client = new Client(Integer.parseInt(table.getValueAt(row, 0).toString()), table.getValueAt(row, 1).toString(), table.getValueAt(row, 2).toString(), table.getValueAt(row, 3).toString(), Integer.parseInt(table.getValueAt(row, 4).toString()));
            clientBLL.deleteClient(client);
        }
        clientWindow.updateTable(clientBLL.findClientAll());
    }


    private void message(int row) {
        if (row != -1) {
            JOptionPane.showMessageDialog(null, "Invalid data!\n(Age must be between 14 and 100)\n(Email address must look like example@example.example)\nrow: " + row);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid data!\n(Age must be between 14 and 100)\n(Email address must look like example@example.example)\n");
        }
    }

    public void setVisible(boolean visible) {
        clientWindow.setVisible(visible);
    }
}
