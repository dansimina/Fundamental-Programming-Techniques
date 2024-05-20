package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Implement the main menu window.
 */
public class MainWindow extends JFrame {
    private JButton buttonClient;
    private JPanel contentPane;
    private JButton buttonProduct;
    private JButton buttonOrder;
    private JButton buttonBill;

    ActionListener actionListener;

    public MainWindow(ActionListener actionListener) throws HeadlessException {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }

        this.actionListener = actionListener;
        this.add(contentPane);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(300, 400);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);

        initializeButtons();
    }

    private void initializeButtons() {
        buttonClient.setText("Client");
        buttonClient.addActionListener(actionListener);
        buttonProduct.setText("Product");
        buttonProduct.addActionListener(actionListener);
        buttonOrder.setText("Order");
        buttonOrder.addActionListener(actionListener);
        buttonBill.setText("Bill");
        buttonBill.addActionListener(actionListener);
    }
}
