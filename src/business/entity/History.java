package business.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class History implements Serializable {
    private Integer historyId;
    private int songId;
    private Integer userId;
    private String orderAt;
    private Double totalPrice;
    private Integer typePackage;
    private LocalDate createAt;
    private LocalDate expriedAt;

    public History() {
    }

    public History(Integer historyId, int songId, Integer userId, String orderAt, Double totalPrice, Integer typePackage, LocalDate createAt, LocalDate expriedAt) {
        this.historyId = historyId;
        this.songId = songId;
        this.userId = userId;
        this.orderAt = orderAt;
        this.totalPrice = totalPrice;
        this.typePackage = typePackage;
        this.createAt = createAt;
        this.expriedAt = expriedAt;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Integer historyId) {
        this.historyId = historyId;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(String orderAt) {
        this.orderAt = orderAt;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTypePackage() {
        return typePackage;
    }

    public void setTypePackage(Integer typePackage) {
        this.typePackage = typePackage;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getExpriedAt() {
        return expriedAt;
    }

    public void setExpriedAt(LocalDate expriedAt) {
        this.expriedAt = expriedAt;
    }

    public String displayData() {
        return "History{" +
                "historyId=" + historyId +
                ", songId=" + songId +
                ", userId=" + userId +
                ", orderAt='" + orderAt + '\'' +
                ", totalPrice=" + totalPrice +
                ", typePackage=" + typePackage +
                ", createAt=" + createAt +
                ", expriedAt=" + expriedAt +
                '}';
    }
}
