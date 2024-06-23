package models;

public class Product {
    private int id;
    private String category;
    private String name;
    private int cookTime;
    private int burnTime;

    public Product(int id, String category, String name, int cookTime, int burnTime) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.cookTime = cookTime;
        this.burnTime = burnTime;
    }

    public int getId() { return id; }
    public String getCategory() { return category; }
    public String getName() { return name; }
    public int getCookTime() { return cookTime; }
    public int getBurnTime() { return burnTime; }

    @Override
    public String toString() {
        return name;
    }
}
