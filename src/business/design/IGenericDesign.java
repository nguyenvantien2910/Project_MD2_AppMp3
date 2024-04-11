package business.design;

public interface IGenericDesign<E> {
    Integer findIndexById(E id);

    void handleAdd();

    void handleShow();

    void handleEdit();

    void handleDelete();
    void handleFindByName();
}