package business.entity;

import business.utils.InputMethods;
import business.utils.Messages;

import java.io.Serializable;
import java.util.Comparator;

import static business.designIplm.IAuthenticationIplm.userList;
import static business.designIplm.ISingerIplm.singerList;

public class Singer implements Serializable {
    private Integer singerId;
    private String singerName;
    private String description;
    private boolean status;

    public Singer() {
    }

    public Singer(Integer singerId, String singerName, String description, boolean status) {
        this.singerId = singerId;
        this.singerName = singerName;
        this.description = description;
        this.status = status;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getSingerName() {
        return singerName;
    }

    public void setSingerName(String singerName) {
        this.singerName = singerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void displayData() {
        System.out.printf("|Singer ID : %-5s | Singer Name : %-20s | Description : %-30s | Status : %-15s |\n",
                this.singerId, this.singerName, this.description, this.status ? "Đang hoạt động" : "Ngừng hoạt động");
    }

    public void displayDataForUser() {
        System.out.printf("|Singer ID : %-5s | Singer Name : %-20s | Description : %-30s |\n",
                this.singerId, this.singerName, this.description);
    }

    public void inputData() {
        this.singerId = findMaxId();
        this.singerName = inputSingerName();
        this.description = inputSingerDescription();
        this.status = inputSingerStatus();
    }

    private int findMaxId() {
        if (singerList.isEmpty()) {
            return 1;
        } else {
            int max = singerList.stream().map(Singer::getSingerId).max(Comparator.naturalOrder()).orElse(0);
            return max + 1;
        }
    }

    private boolean inputSingerStatus() {
        System.out.println("Nhập trạng thái cho ca sĩ (true/false) :");
        return InputMethods.getBoolean();
    }

    private String inputSingerDescription() {
        System.out.println("Nhập trạng mô tả cho ca sĩ :");
        return InputMethods.getString();
    }

    private String inputSingerName() {
        do {
            System.out.println("Nhập tên cho ca sĩ : ");
            String inputName = InputMethods.getString();
            if (!inputName.trim().isBlank()) {
                return inputName;
            } else {
                System.err.println(Messages.EMTY_ERROR);
            }
        } while (true);
    }
}