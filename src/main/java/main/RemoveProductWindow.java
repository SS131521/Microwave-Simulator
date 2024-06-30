package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import database.DatabaseHandler;
import models.Product;
import models.ProductComboBoxRenderer;

public class RemoveProductWindow extends JFrame {
    private JComboBox<Product> productComboBox;

    private DatabaseHandler dbHandler;

    public RemoveProductWindow(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;

        setTitle("Usuń produkt");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel productLabel = new JLabel("Produkt:");

        productComboBox = new JComboBox<>(dbHandler.getAllProducts().toArray(new Product[0]));
        productComboBox.setRenderer(new ProductComboBoxRenderer());

        JButton removeButton = new JButton("Usuń");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Product product = (Product) productComboBox.getSelectedItem();
                if (product != null) {
                    dbHandler.removeProduct(product.getId());
                    JOptionPane.showMessageDialog(null, "Produkt usunięty.");
                    dispose();
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(productLabel);
        panel.add(productComboBox);
        panel.add(new JLabel(""));
        panel.add(removeButton);

        add(panel, BorderLayout.CENTER);
    }
}
