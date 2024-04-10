package business.entity;

import business.utils.InputMethods;
import business.utils.Messages;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import static presentation.Main.*;

public class Song implements Serializable {
    private int songId;
    private int singerId;
    private String songName;
    private String description;
    private String source;
    private double price;
    private Integer albumId;
    private String image;
    private LocalDate createAt;
    private LocalDate updateAt;
    private int playCount;

    public Song() {
    }

    public Song(int songId, int singerId, String songName, String description, String source, double price, Integer albumId, String image, LocalDate createAt, LocalDate updateAt, int playCount) {
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

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
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

    public void displayData() {
        System.out.printf("| Song ID : %-5d |Singer ID : %-5d | Song Name : %-20s | Description : %-25s | Source : %-20s | Price : %-12.2f | Album ID : %-5s | Image : %-20s | CreateAt : %-15s | UpdateAt : %-15s\n",
                this.songId, this.singerId, this.songName, this.description, this.source, this.price, this.albumId, this.image, this.createAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), this.updateAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
    }

    public void inputData() {
        this.songId = findMaxId();
        this.singerId = selectSinger();
        this.songName = inputSongName();
        this.description = inputDescription();
        this.source = inputSource();
        this.price = inputPrice();
        inputAlbumId();
        this.image = inputImage();
        this.createAt = LocalDate.from(LocalDateTime.now());
        this.updateAt = LocalDate.from(LocalDateTime.now());
        this.playCount = 0;
    }

    private String inputImage() {
        System.out.println("Nhập link hình ảnh bài hát : ");
        return InputMethods.getString();
    }

    private void inputAlbumId() {
        System.out.println("Bạn có muốn nhập album cho bài hát không ? ");
        System.out.println("1. Có");
        System.out.println("2. Không");
        System.out.println("Nhập lựa chọn của bạn ");

        byte choice = InputMethods.getByte();

        switch (choice) {
            case 1:
                if (albumList.isEmpty()) {
                    System.err.println(Messages.EMTY_LIST);
                    this.albumId = null;
                } else {
                    System.out.println("Danh sách album");
                    for (int i = 0; i < albumList.size(); i++) {
                        System.out.printf("%d.%s\n", i + 1, albumList.get(i).getName());
                    }
                    System.out.print("Lựa chọn của bạn: ");
                    choice = InputMethods.getByte();
                    this.albumId = albumList.get(choice - 1).getId();
                }
                break;
            case 2:
                this.albumId = null;
                break;
            default:
                System.err.println(Messages.SELECT_INVALID);
        }
    }

    private double inputPrice() {
        System.out.println("Nhập giá cho bài hát :");
        return InputMethods.getDouble();
    }

    private String inputSource() {
        System.out.println("Nhập link nguồn cho bài hát :");
        return InputMethods.getString();
    }

    private String inputDescription() {
        System.out.println("Nhập mô tả cho bài hát : ");
        return InputMethods.getString();
    }

    private String inputSongName() {
        System.out.println("Nhập tên cho bài hát : ");
        String inputName = InputMethods.getString().toLowerCase();
        if (songList == null || songList.isEmpty()) {
            return inputName;
        } else {
            // Kiểm tra xem có bài hát nào trong songList có tên chứa inputName hay không
            boolean isExisting = songList.stream()
                    .anyMatch(song -> song.getSongName().toLowerCase().contains(inputName));
            if (isExisting) {
                System.err.println(Messages.IS_EXITS_ERROR);
            } else {
                return inputName;
            }

            // Nếu không có bài hát nào chứa inputName hoặc inputName là trống/null, trả về null
            return null;
        }
    }

    private int selectSinger() {
        if (!singerList.isEmpty()) {
            System.out.println("Chọn ca sĩ cho bài hát:");
            for (int i = 0; i < singerList.size(); i++) {
                if (singerList.get(i).isStatus()) {
                    System.out.printf("%d.%s\n", i + 1, singerList.get(i).getSingerName());
                }
            }
            System.out.print("Lựa chọn của bạn: ");
            byte choice = InputMethods.getByte();
            return singerList.get(choice - 1).getSingerId();
        }
        return -1;
    }

    private int findMaxId() {
        if (songList == null || songList.isEmpty()) {
            return 1;
        } else {
            int maxSongId = songList.stream()
                    .map(Song::getSongId)
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            return maxSongId + 1;
        }
    }
}
