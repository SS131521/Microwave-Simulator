package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import database.DatabaseHandler;
import models.Product;

public class AddProductWindow extends JFrame {
    private JTextField categoryField;
    private JTextField nameField;
    private JTextField cookTimeField;
    private JTextField burnTimeField;

    private DatabaseHandler dbHandler;

    public AddProductWindow(DatabaseHandler dbHandler) {
        this.dbHandler = dbHandler;

        setTitle("Dodaj produkt");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel categoryLabel = new JLabel("Kategoria:");
        JLabel nameLabel = new JLabel("Nazwa:");
        JLabel cookTimeLabel = new JLabel("Czas gotowania:");
        JLabel burnTimeLabel = new JLabel("Czas spalenia:");

        categoryField = new JTextField(10);
        nameField = new JTextField(10);
        cookTimeField = new JTextField(10);
        burnTimeField = new JTextField(10);

        JButton addButton = new JButton("Dodaj");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String category = categoryField.getText();
                String name = nameField.getText();
                int cookTime = Integer.parseInt(cookTimeField.getText());
                int burnTime = Integer.parseInt(burnTimeField.getText());

                Product product = new Product(0, category, name, cookTime, burnTime);
                dbHandler.addProduct(product);
                JOptionPane.showMessageDialog(null, "Produkt dodany.");
                dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(cookTimeLabel);
        panel.add(cookTimeField);
        panel.add(burnTimeLabel);
        panel.add(burnTimeField);
        panel.add(new JLabel(""));
        panel.add(addButton);

        add(panel, BorderLayout.CENTER);
    }
}
