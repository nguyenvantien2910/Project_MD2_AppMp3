package business.entity;

import business.designIplm.DisplayData;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static business.designIplm.IAuthenticationIplm.userList;
import static business.designIplm.IHistoryIplm.historyList;
import static business.designIplm.ISingerIplm.singerList;

public class History implements Serializable, DisplayData {
    private int historyId;
    private int songId;
    private int userId;
    private LocalDate orderAt;
    private Double totalPrice;
    private Integer typePackage;
    private LocalDate createAt;
    private LocalDate expriedAt;

    public History() {
    }

    public History(int historyId, int songId, int userId, LocalDate orderAt, Double totalPrice, Integer typePackage, LocalDate createAt, LocalDate expriedAt) {
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

    public LocalDate getOrderAt() {
        return orderAt;
    }

    public void setOrderAt(LocalDate orderAt) {
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

    public void displayData() {
        System.out.printf("|History ID: %-5d | Song ID : %-5d | UserId : %-5d | OrderAt : %-10s | TotalPrice : %-12.2f | TypePackage : %-15s | CreateAt : %-12s | ExpriedAt : %-12s\n",
                this.historyId, this.songId, this.userId, this.orderAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), this.totalPrice, (this.typePackage == 1) ? "Gói 30 ngày" : "Vĩnh Viễn", this.createAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                (this.expriedAt == null) ? "Unlimited" : this.expriedAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void inputData() {
        this.historyId = findIdMax();
    }

    public int findIdMax() {
        if (historyList.isEmpty()) {
            return 1;
        } else {
            int max = historyList.stream().map(History::getHistoryId).max(Comparator.naturalOrder()).orElse(0);
            return max + 1;
        }
    }
}