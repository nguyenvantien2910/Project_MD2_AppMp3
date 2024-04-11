package business.entity;

import business.utils.InputMethods;
import business.utils.Messages;

import java.io.Serializable;
import java.util.Comparator;

import static business.designIplm.IAlbumIplm.albumList;
import static business.designIplm.IAuthenticationIplm.userList;
import static business.designIplm.ISingerIplm.singerList;

public class Album implements Serializable {
    private Integer id;
    private Integer singerId;
    private String name;
    private String description;
    private String image;

    public Album() {
    }

    public Album(Integer id, Integer singerId, String name, String description, String image) {
        this.id = id;
        this.singerId = singerId;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSingerId() {
        return singerId;
    }

    public void setSingerId(Integer singerId) {
        this.singerId = singerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void displayData() {
        System.out.printf("|ID : %-5d | SingerID : %-15s | Name : %-20s | Description : %-25s | Image : %-20s\n",
                this.getId(), (this.getSingerId() == -1) ? "Không có ca sĩ" : this.singerId, this.getName(), this.getDescription(), this.getImage());
    }

    public void inputDate() {
        this.id = findMaxId();
        this.singerId = inputSingerId();
        this.name = inputName();
        this.description = inputDescription();
        this.image = inputImage();
    }

    private String inputImage() {
        System.out.println("Nhập link ảnh cho album :");
        return InputMethods.getString();
    }

    private String inputDescription() {
        System.out.println("Nhập mô tả cho album :");
        return InputMethods.getString();
    }

    private String inputName() {
        System.out.println("Nhập tên cho album :");
        return InputMethods.getString();
    }

    private int inputSingerId() {
        do {
            System.out.println("Bạn có muốn nhập ca sĩ cho album không ? ");
            System.out.println("1. Có ");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn : ");

            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    if (singerList.isEmpty()) {
                        System.err.println(Messages.EMTY_LIST);
                        return -1;
                    } else {
                        for (int j = 0; j < singerList.size(); j++) {
                            if (singerList.get(j).isStatus()) {
                                System.out.printf("%d.%s\n", j + 1, singerList.get(j).getSingerName());
                            }
                        }
                        System.out.print("Lựa chọn của bạn: ");
                        choice = InputMethods.getByte();
                        return singerList.get(choice - 1).getSingerId();
                    }
                case 2:
                    return -1;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (true);
    }


    private int findMaxId() {
        if (albumList.isEmpty()) {
            return 1;
        } else {
            int maxAlbumId = albumList.stream()
                    .map(Album::getId)
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            return maxAlbumId + 1;
        }
    }
}