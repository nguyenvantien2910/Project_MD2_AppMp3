package business.designIplm;

import business.design.IGenericDesign;
import business.entity.Album;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import business.utils.Pagination;
import presentation.Login;
import presentation.admin.AdminMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static business.designIplm.ISingerIplm.singerList;

public class IAlbumIplm implements IGenericDesign {
    private static byte choice;
    private boolean isExit = false;
    public static List<Album> albumList;

    static {
        File albumFile = new File(IOFile.ALBUM_PATH);
        if (albumFile.length() == 0) {
            albumList = new ArrayList<>();
            IOFile.writeDataToFile(IOFile.ALBUM_PATH, albumList);
        } else {
            albumList = IOFile.getDataFormFile(IOFile.ALBUM_PATH);
        }
    }

    public void askAdminToAddNewAlbum() {
        if (Login.user.isRole()){
            System.out.println("Bạn có muốn thêm mới album không ?");
            System.out.println("1. Có ");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn : ");
            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    handleAdd();
                    break;
                case 2:
                    AdminMenu.albumManagement();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        }
    }

    @Override
    public Integer findIndexById(Object id) {
        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getSingerId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng album muốn thêm :");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            Album album = new Album();
            System.out.printf("Nhập thông tin cho album thứ %d \n", i + 1);
            album.inputDate();
            albumList.add(album);
            IOFile.writeDataToFile(IOFile.ALBUM_PATH, albumList);
            System.out.println(Messages.ADD_NEW_SUCESS);
        }
    }

    @Override
    public void handleShow() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewAlbum();
        } else {
            System.out.println("========== ALBUM LIST ==========");
            Pagination.paginateAndDisplay(albumList, Pagination.ELEMENT_PER_PAGE);
        }
    }

    @Override
    public void handleEdit() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewAlbum();
        } else {
            System.out.println("Nhập ID album muốn cập nhật thông tin :");
            int inputID = InputMethods.getInteger();
            int editIndex = findIndexById(inputID);

            if (editIndex != -1) {
                do {
                    System.out.println("========= EDIT ALBUM INFORMATION =======");
                    System.out.println("1. Chỉnh sửa tên");
                    System.out.println("2. Chỉnh sửa mô tả");
                    System.out.println("3. Chỉnh sửa danh sách ca sĩ");
                    System.out.println("4. Chỉnh sửa link ảnh");
                    System.out.println("5. Thoát");
                    System.out.println("Nhập lựa chọn của bạn : ");

                    choice = InputMethods.getByte();
                    switch (choice) {
                        case 1:
                            System.out.println("Nhập tên cho album :");
                            String inputAlbumName = InputMethods.getString();
                            albumList.get(editIndex).setName(inputAlbumName);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;
                        case 2:
                            System.out.println("Nhập mô tả cho album :");
                            String inputAlbumDescription = InputMethods.getString();
                            albumList.get(editIndex).setDescription(inputAlbumDescription);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;
                        case 3:
                            System.out.println("Bạn có muốn nhập ca sĩ cho album không ? ");
                            System.out.println("1. Có ");
                            System.out.println("2. Không ");
                            System.out.println("Nhập lựa chọn của bạn : ");

                            choice = InputMethods.getByte();
                            switch (choice) {
                                case 1:
                                    if (singerList.isEmpty()) {
                                        System.err.println(Messages.EMTY_LIST);
                                        break;
                                    } else {
                                        for (int j = 0; j < singerList.size(); j++) {
                                            if (singerList.get(j).isStatus()) {
                                                System.out.printf("%d. %s\n", j + 1, singerList.get(j).getSingerName());
                                            }
                                        }
                                        System.out.print("Lựa chọn của bạn: ");
                                        choice = InputMethods.getByte();
                                        albumList.get(editIndex).setSingerId(singerList.get(choice - 1).getSingerId());
                                        System.out.println(Messages.UPDATE_INFO_SUCESS);
                                        isExit = true;
                                        break;
                                    }
                                case 2:
                                    albumList.get(editIndex).setSingerId(-1);
                                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                                    break;

                                default:
                                    System.err.println(Messages.SELECT_INVALID);
                                    break;
                            }
                            break;
                        case 4:
                            System.out.println("Nhập link ảnh cho album : ");
                            albumList.get(editIndex).setImage(InputMethods.getString());
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;

                        case 5:
                            isExit = true;
                            break;
                    }
                } while (!isExit);
            }
            // sau khi edit lưu lại nó vào file
            IOFile.writeDataToFile(IOFile.ALBUM_PATH, albumList);
        }
    }

    @Override
    public void handleDelete() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewAlbum();
        } else {
            System.out.println("Nhập ID album muốn thực hiện xóa :");
            int inputID = InputMethods.getInteger();
            int deleteIndex = findIndexById(inputID);
            if (deleteIndex != -1) {
                albumList.remove(deleteIndex);
                IOFile.writeDataToFile(IOFile.ALBUM_PATH, albumList);
                System.out.println(Messages.DELETE_SUCESS);
            } else {
                System.err.println(Messages.ID_NOT_FOUND);
            }
        }
    }

    @Override
    public void handleFindByName() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewAlbum();
        } else {
            System.out.println("Nhập tên album muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString();

            List<Album> albumListFilterBySearchKey = albumList.stream().filter(album -> album.getName().toLowerCase().contains(inputSearchName.toLowerCase())).toList();
            if (albumListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                albumListFilterBySearchKey.forEach(Album::displayData);
                System.out.println();
            }
        }
    }
}
