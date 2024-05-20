package model;

/**
 * Model the data type "order".
 */
public class Order {
    Integer id;
    Integer quantity;
    Integer price;
    Integer clientId;
    Integer productId;

    public Order() {
    }

    public Order(Integer id, Integer quantity, Integer price, Integer clientId, Integer productId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.clientId = clientId;
        this.productId = productId;
    }

    public Order(Integer quantity, Integer price, Integer clientId, Integer productId) {
        this.quantity = quantity;
        this.price = price;
        this.clientId = clientId;
        this.productId = productId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", clientId=" + clientId +
                ", productId=" + productId +
                '}';
    }
}
