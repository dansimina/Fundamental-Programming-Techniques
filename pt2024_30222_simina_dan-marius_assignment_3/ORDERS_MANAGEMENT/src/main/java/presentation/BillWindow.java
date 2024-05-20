package presentation;

import model.Bill;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Implement the window for viewing bills.
 */
public class BillWindow extends JFrame {
    private JPanel contentPane;
    private JTable tableBills;
    private JButton buttonBack;

    ActionListener actionListener;

    public BillWindow(ActionListener actionListener) {
        this.actionListener = actionListener;

        tableBills.setDefaultEditor(Object.class, null);
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
    }

    public void updateTable(List<Bill> bills) {
        PopulateTable.populateTable(bills, tableBills);
    }
}
