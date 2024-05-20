package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;

/**
 * It contains the method for inserting a list of objects into a table.
 */
public class PopulateTable {
    /**
     * The method receives a list of objects and a table, then extracts the table's header and populates it.
     *
     * @param objectList
     * @param table
     */
    public static void populateTable(List<?> objectList, JTable table) {
        if (!objectList.isEmpty() && table != null) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            model.setColumnCount(0);

            for (Field field : objectList.getFirst().getClass().getDeclaredFields()) {
                model.addColumn(field.getName());
            }

            for (Object object : objectList) {
                String[] row = new String[table.getColumnCount()];
                int i = 0;
                for (Field field : object.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        if (field.get(object) != null) {
                            row[i] = field.get(object).toString();
                        } else {
                            row[i] = "null";
                        }
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    i++;
                }
                model.addRow(row);
            }
        }
    }
}
