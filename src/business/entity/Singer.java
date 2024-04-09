package business.entity;

import java.io.Serializable;

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

    public String displayData() {
        return "Singer{" +
                "singerId=" + singerId +
                ", singerName='" + singerName + '\'' +
                ", description='" + description + '\'' +
                ", status=" + (status ? "Đang hoạt động" : "Ngừng hoạt động") +
                '}';
    }

    public void inputData() {
    }
}
