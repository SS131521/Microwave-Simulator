package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import database.DatabaseHandler;
import models.Product;
import java.util.List;
import java.util.ArrayList;

public class MicrowaveSimulator extends JFrame {
    private JTextField timeField;
    private JTextArea outputArea;
    private JComboBox<Product> productComboBox;
    private JComboBox<String> categoryComboBox;

    private DatabaseHandler dbHandler;

    public MicrowaveSimulator() {
        dbHandler = new DatabaseHandler();
        dbHandler.connect();

        setTitle("Kuchenka Mikrofalowa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JLabel timeLabel = new JLabel("Czas (s):");
        JLabel productLabel = new JLabel("Produkt:");
        JLabel categoryLabel = new JLabel("Kategoria:");

        timeField = new JTextField(10);
            categoryComboBox = new JComboBox<>(new DefaultComboBoxModel<>(dbHandler.getAllCategories().toArray(new String[0])));
            productComboBox = new JComboBox<>(new DefaultComboBoxModel<>());

        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryComboBox.getSelectedItem();
                List<Product> products = dbHandler.getProductsByCategory(selectedCategory);
                productComboBox.setModel(new DefaultComboBoxModel<>(products.toArray(new Product[0])));
            }
        });

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new StartButtonListener());

        JButton addButton = new JButton("Dodaj produkt");
        addButton.addActionListener(e -> {
            AddProductWindow addProductWindow = new AddProductWindow(dbHandler);
            addProductWindow.addWindowListener(new RefreshDataWindowListener());
        });

        JButton removeButton = new JButton("Usuń produkt");
        removeButton.addActionListener(e -> {
            RemoveProductWindow removeProductWindow = new RemoveProductWindow(dbHandler);
            removeProductWindow.addWindowListener(new RefreshDataWindowListener());
        });


        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(categoryLabel);
        panel.add(categoryComboBox);
        panel.add(productLabel);
        panel.add(productComboBox);
        panel.add(timeLabel);
        panel.add(timeField);
        panel.add(new JLabel(""));
        panel.add(startButton);
        panel.add(addButton);
        panel.add(removeButton);
        panel.add(new JScrollPane(outputArea));

        add(panel, BorderLayout.CENTER);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int time = Integer.parseInt(timeField.getText());
                Product product = (Product) productComboBox.getSelectedItem();

                if (product != null) {
                    if (time < product.getCookTime()) {
                        outputArea.setText("Produkt jest niedogotowany.");
                    } else if (time < product.getBurnTime()) {
                        outputArea.setText("Produkt jest gotowy do spożycia.");
                    } else {
                        outputArea.setText("Produkt się spalił!");
                    }
                }
            } catch (NumberFormatException ex) {
                outputArea.setText("Błędne dane wejściowe.");
            }
        }
    }

    public void refreshData() {
        List<String> categories = new ArrayList<>(dbHandler.getAllCategories());
        categories.add(0, "");
        categoryComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new String[0])));
        productComboBox.setModel(new DefaultComboBoxModel<>());
    }

    private class RefreshDataWindowListener extends WindowAdapter {
        @Override
        public void windowClosed(WindowEvent e) {
            refreshData();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MicrowaveSimulator().setVisible(true);
        });
    }
}
