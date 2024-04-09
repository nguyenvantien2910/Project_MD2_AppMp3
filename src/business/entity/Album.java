package business.entity;

import java.io.Serializable;

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

    public String displayData() {
        return "Album{" +
                "id=" + id +
                ", singerId=" + singerId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
