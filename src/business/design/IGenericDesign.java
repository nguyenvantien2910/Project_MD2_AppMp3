package business.design;

public interface IGenericDesign {
    Integer findIndexByName(String name);

    void handleAdd();

    void handleShow();

    void handleEdit();

    void handleDelete();
}
