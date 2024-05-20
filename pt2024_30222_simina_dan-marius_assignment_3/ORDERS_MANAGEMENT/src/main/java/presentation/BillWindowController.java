package presentation;

import business.logic.BillBLL;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The controller for the bills viewing window.
 */
public class BillWindowController implements ActionListener {
    MainWindowController mainWindowController;
    BillWindow billWindow;
    BillBLL billBLL;

    public BillWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
        billWindow = new BillWindow(this);
        billBLL = new BillBLL();
        updateTable();
    }

    public void updateTable() {
        billWindow.updateTable(billBLL.findBillAll());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Back" -> {
                mainWindowController.setVisible(true);
                billWindow.setVisible(false);
            }
        }
    }

    public void setVisible(boolean visible) {
        billWindow.setVisible(visible);
    }
}
