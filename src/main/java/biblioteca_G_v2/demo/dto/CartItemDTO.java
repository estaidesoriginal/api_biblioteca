package biblioteca_G_v2.demo.dto;

import biblioteca_G_v2.demo.model.Product;

public class CartItemDTO {
    private Product product;
    private int quantity;

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}