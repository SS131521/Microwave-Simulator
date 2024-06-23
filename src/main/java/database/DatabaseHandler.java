package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.Product;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:sqlite:src/resources/microwave.sqlite";

    public void connect() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Baza danych została połączona.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getAllCategories() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT * FROM products GROUP BY category";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getInt("cook_time"),
                        rs.getInt("burn_time")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY category";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(new Product(rs.getInt("id"),
                        rs.getString("category"),
                        rs.getString("name"),
                        rs.getInt("cook_time"),
                        rs.getInt("burn_time")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }


    public List<String> getCategoryProductString() {
        List<String> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY category";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                products.add(rs.getString("category") + " -> " + rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return products;
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO products (category, name, cook_time, burn_time) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getCategory());
            pstmt.setString(2, product.getName());
            pstmt.setInt(3, product.getCookTime());
            pstmt.setInt(4, product.getBurnTime());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeProduct(int id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
