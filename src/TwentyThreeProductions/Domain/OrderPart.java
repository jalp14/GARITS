package TwentyThreeProductions.Domain;

public class OrderPart {

    private int orderID;
    private String partID;
    private int stockItem;
    private int orderQty;
    private float value;

    public OrderPart(){}

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        this.partID = partID;
    }

    public int getStockItem() {
        return stockItem;
    }

    public void setStockItem(int stockItem) {
        this.stockItem = stockItem;
    }

    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
