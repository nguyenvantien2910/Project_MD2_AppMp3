package business.designIplm;

import business.design.IAlbumDesign;
import business.entity.Album;
import business.utils.InputMethods;
import business.utils.Messages;

import java.util.List;

import static presentation.Main.albumList;

public class IAlbumIplm implements IAlbumDesign {
    @Override
    public Integer findIndexById(Album id) {
        return 0;
    }

    @Override
    public void handleAdd() {

    }

    @Override
    public void handleShow() {

    }

    @Override
    public void handleEdit() {

    }

    @Override
    public void handleDelete() {

    }

    @Override
    public void searchAlbumByName() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên album muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString().toLowerCase();

            List<Album> albumListFilterBySearchKey = albumList.stream().filter(singer -> singer.getName().toLowerCase().contains(inputSearchName)).toList();
            if (albumListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa &s là :\n", inputSearchName);
                albumListFilterBySearchKey.forEach(Album::displayData);
            }
        }
    }
}
