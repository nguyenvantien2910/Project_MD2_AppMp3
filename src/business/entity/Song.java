package business.entity;

import java.io.Serializable;
import java.time.LocalDate;

public class Song implements Serializable {
    private Integer songId;
    private Integer singerId;
    private String songName;
    private String description;
    private String source;
    private Double price;
    private int albumId;
    private String image;
    private LocalDate createAt;
    private LocalDate updateAt;
    private int playCount;

    public Song() {
    }

    public Song(Integer songId, Integer singerId, String songName, String description, String source, Double price, Integer albumId, String image, LocalDate createAt, LocalDate updateAt, int playCount) {
        this.songId = songId;
        this.singerId = singerId;
        this.songName = songName;
        this.description = description;
        this.source = source;
        this.price = price;
        this.albumId = albumId;
        this.image = image;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.playCount = playCount;
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public int getSingerId() {
        return singerId;
    }

    public void setSingerId(int singerId) {
        this.singerId = singerId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String displayData() {
        return "Singer{" +
                "songId=" + songId +
                ", singerId=" + singerId +
                ", songName='" + songName + '\'' +
                ", description='" + description + '\'' +
                ", source='" + source + '\'' +
                ", price=" + price +
                ", albumId=" + albumId +
                ", image='" + image + '\'' +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", playCount=" + playCount +
                '}';
    }
}
