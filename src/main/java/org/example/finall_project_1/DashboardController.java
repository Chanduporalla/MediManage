package org.example.finall_project_1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {

    @FXML private Label dailySales;
    @FXML private Label totalProfit;
    @FXML private Label pendingRx;
    @FXML private Label lowStock;

    @FXML private TableView<Medicine> inventoryTable;
    @FXML private TableColumn<Medicine, String> colMedicine;
    @FXML private TableColumn<Medicine, String> colBatch;
    @FXML private TableColumn<Medicine, String> colExpiry;
    @FXML private TableColumn<Medicine, String> colHSN;
    @FXML private TableColumn<Medicine, Integer> colStock;

    @FXML private TextField searchMedicine;
    @FXML private TableView<BillItem> billingTable;
    @FXML private TableColumn<BillItem, String> billColName;
    @FXML private TableColumn<BillItem, Integer> billColQty;
    @FXML private TableColumn<BillItem, Double> billColPrice;
    @FXML private TableColumn<BillItem, Double> billColGST;
    @FXML private TableColumn<BillItem, Double> billColTotal;
    @FXML private Label lblTotal;

    @FXML private Button btnAddBill, btnGenerateInvoice;

    private ObservableList<Medicine> inventoryList = FXCollections.observableArrayList();
    private ObservableList<BillItem> billList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        DBUtil.initDB();
        setupInventoryTable();
        setupBillingTable();
        loadKPIs();
        loadInventory();
        setupBillingButtons();
    }

    private void setupInventoryTable() {
        colMedicine.setCellValueFactory(data -> javafx.beans.property.SimpleStringProperty.stringExpression(data.getValue().getName()));
        colBatch.setCellValueFactory(data -> javafx.beans.property.SimpleStringProperty.stringExpression(data.getValue().getBatch()));
        colExpiry.setCellValueFactory(data -> javafx.beans.property.SimpleStringProperty.stringExpression(data.getValue().getExpiry()));
        colHSN.setCellValueFactory(data -> javafx.beans.property.SimpleStringProperty.stringExpression(data.getValue().getHsn()));
        colStock.setCellValueFactory(data -> javafx.beans.property.SimpleIntegerProperty.integerExpression(data.getValue().getStock()));
        inventoryTable.setItems(inventoryList);
    }

    private void setupBillingTable() {
        billColName.setCellValueFactory(data -> javafx.beans.property.SimpleStringProperty.stringExpression(data.getValue().getName()));
        billColQty.setCellValueFactory(data -> javafx.beans.property.SimpleIntegerProperty.integerExpression(data.getValue().getQty()));
        billColPrice.setCellValueFactory(data -> javafx.beans.property.SimpleDoubleProperty.doubleExpression(data.getValue().getPrice()));
        billColGST.setCellValueFactory(data -> javafx.beans.property.SimpleDoubleProperty.doubleExpression(data.getValue().getGst()));
        billColTotal.setCellValueFactory(data -> javafx.beans.property.SimpleDoubleProperty.doubleExpression(data.getValue().getTotal()));
        billingTable.setItems(billList);
    }

    private void loadKPIs() {
        try (Connection conn = DBUtil.getConnection()) {
            // Daily Sales
            ResultSet rs = conn.createStatement().executeQuery("SELECT IFNULL(SUM(total),0) AS daily_sales FROM bills WHERE date = DATE('now')");
            if (rs.next()) dailySales.setText("₹" + rs.getDouble("daily_sales"));

            // Total Profit
            rs = conn.createStatement().executeQuery("SELECT IFNULL(SUM(profit),0) AS total_profit FROM bills");
            if (rs.next()) totalProfit.setText("₹" + rs.getDouble("total_profit"));

            // Pending Prescriptions
            rs = conn.createStatement().executeQuery("SELECT COUNT(*) AS pending FROM prescriptions WHERE status='pending'");
            if (rs.next()) pendingRx.setText(rs.getString("pending"));

            // Low Stock
            rs = conn.createStatement().executeQuery("SELECT COUNT(*) AS low_stock FROM inventory WHERE stock < 10");
            if (rs.next()) lowStock.setText(rs.getString("low_stock"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadInventory() {
        try (Connection conn = DBUtil.getConnection()) {
            ResultSet rs = conn.createStatement().executeQuery("SELECT name, batch, expiry, hsn, stock FROM inventory");
            while (rs.next()) {
                inventoryList.add(new Medicine(
                        rs.getString("name"),
                        rs.getString("batch"),
                        rs.getString("expiry"),
                        rs.getString("hsn"),
                        rs.getInt("stock")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupBillingButtons() {
        btnAddBill.setOnAction(e -> addToBill());
        btnGenerateInvoice.setOnAction(e -> generateInvoice());
    }

    private void addToBill() {
        Medicine selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        BillItem item = new BillItem(selected.getName(), 1, 50.0, 5.0); // Example: qty=1, price=50, GST=5%
        billList.add(item);
        updateTotal();
    }

    private void generateInvoice() {
        // Save bill in DB or generate PDF
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Invoice");
        alert.setHeaderText("Invoice Generated ✅");
        alert.setContentText("Total: " + lblTotal.getText());
        alert.showAndWait();
        billList.clear();
        updateTotal();
    }

    private void updateTotal() {
        double total = billList.stream().mapToDouble(BillItem::getTotal).sum();
        lblTotal.setText("₹" + total);
    }

    // ---------------- Inner Classes ----------------
    public static class Medicine {
        private final String name, batch, expiry, hsn;
        private final int stock;

        public Medicine(String name, String batch, String expiry, String hsn, int stock) {
            this.name = name;
            this.batch = batch;
            this.expiry = expiry;
            this.hsn = hsn;
            this.stock = stock;
        }
        public javafx.beans.property.SimpleStringProperty getName() { return new javafx.beans.property.SimpleStringProperty(name); }
        public javafx.beans.property.SimpleStringProperty getBatch() { return new javafx.beans.property.SimpleStringProperty(batch); }
        public javafx.beans.property.SimpleStringProperty getExpiry() { return new javafx.beans.property.SimpleStringProperty(expiry); }
        public javafx.beans.property.SimpleStringProperty getHsn() { return new javafx.beans.property.SimpleStringProperty(hsn); }
        public javafx.beans.property.SimpleIntegerProperty getStock() { return new javafx.beans.property.SimpleIntegerProperty(stock); }
    }

    public static class BillItem {
        private final String name;
        private final int qty;
        private final double price;
        private final double gst;
        private final double total;

        public BillItem(String name, int qty, double price, double gst) {
            this.name = name;
            this.qty = qty;
            this.price = price;
            this.gst = gst;
            this.total = price + gst;
        }
        public javafx.beans.property.SimpleStringProperty getName() { return new javafx.beans.property.SimpleStringProperty(name); }
        public javafx.beans.property.SimpleIntegerProperty getQty() { return new javafx.beans.property.SimpleIntegerProperty(qty); }
        public javafx.beans.property.SimpleDoubleProperty getPrice() { return new javafx.beans.property.SimpleDoubleProperty(price); }
        public javafx.beans.property.SimpleDoubleProperty getGst() { return new javafx.beans.property.SimpleDoubleProperty(gst); }
        public javafx.beans.property.SimpleDoubleProperty getTotal() { return new javafx.beans.property.SimpleDoubleProperty(total); }
    }
}
