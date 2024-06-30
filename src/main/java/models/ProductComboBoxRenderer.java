package models;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import models.Product; // Ensure this import matches your Product class package

public class ProductComboBoxRenderer extends JLabel implements ListCellRenderer<Product> {
    public ProductComboBoxRenderer() {
        setOpaque(true);
        setHorizontalAlignment(CENTER);
        setVerticalAlignment(CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Product> list, Product value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        if (value != null) {
            // Format the display string as "category -> name"
            setText(value.getCategory() + " -> " + value.getName());
        } else {
            setText("");
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
