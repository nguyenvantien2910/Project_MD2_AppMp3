package business.designIplm;

import business.design.IAlbumDesign;
import business.entity.Album;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static presentation.Main.albumList;
import static presentation.Main.singerList;

public class IAlbumIplm implements IAlbumDesign {
    private static byte choice;
    private static boolean isExit;

    @Override
    public Integer findIndexByName(String name) {
        for (int i = 0; i < albumList.size(); i++) {
            if (albumList.get(i).getName().toLowerCase().contains(name)) {
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
        }
        // sau khi add lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.ALBUM_PATH, albumList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);

    }

    @Override
    public void handleShow() {
        if (albumList.isEmpty() || albumList == null) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("========== ALBUM LIST ==========");
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 3;
            int page = 1;
            int numberOfPage;
            if (albumList.size() % elementPerPage == 0) {
                numberOfPage = albumList.size() / elementPerPage;
            } else {
                numberOfPage = albumList.size() / elementPerPage + 1;
            }
            do {
                for (int i = 0; i < albumList.size(); i++) {
                    if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                        albumList.get(i).displayData();
                    }
                }

                System.out.println("Trang : " + page + "/" + numberOfPage);
                if (page == 1) {
                    System.out.println("2.Trang sau");
                    System.out.println("3.Thoát");
                } else if (page == numberOfPage) {
                    System.out.println("1.Trang Trước");
                    System.out.println("3.Thoát");
                } else {
                    System.out.println("1.Trang trước  ||  2.Trang sau");
                    System.out.println("3.Thoát");
                }

                System.out.println("Mời nhập lựa chọn: ");
                choice = InputMethods.getByte();
                switch (choice) {
                    case 1:
                        if (page <= numberOfPage && page >= 0) {
                            firstIndexOfPage -= elementPerPage;
                            lastIndexOfPage -= elementPerPage;
                            page -= 1;
                            break;
                        }
                    case 2:
                        if (page <= numberOfPage && page >= 0) {
                            firstIndexOfPage += elementPerPage;
                            lastIndexOfPage += elementPerPage;
                            page += 1;
                            break;
                        }
                    case 3:
                        return;
                    default:
                        System.err.print(Messages.SELECT_INVALID);
                        break;
                }
            } while (true);
        }
    }

    @Override
    public void handleEdit() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID album muốn cập nhật thông tin :");
            byte inputID = InputMethods.getByte();
            for (int i = 0; i < albumList.size(); i++) {
                if (albumList.get(i).getId() == inputID) {
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
                                albumList.get(i).setName(inputAlbumName);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            case 2:
                                System.out.println("Nhập mô tả cho album :");
                                String inputAlbumDescription = InputMethods.getString();
                                albumList.get(i).setDescription(inputAlbumDescription);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            case 3:
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
                                                albumList.get(i).setId(-1);
                                            } else {
                                                for (int j = 0; j < singerList.size(); j++) {
                                                    if (singerList.get(j).isStatus()) {
                                                        System.out.printf("%d.%s\n", j + 1, singerList.get(j).getSingerName());
                                                    }
                                                }
                                                System.out.print("Lựa chọn của bạn: ");
                                                choice = InputMethods.getByte();
                                                albumList.get(i).setId(singerList.get(choice - 1).getSingerId());
                                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                                break;
                                            }
                                        case 2:
                                            albumList.get(i).setId(-1);
                                        default:
                                            System.err.println(Messages.SELECT_INVALID);
                                    }
                                } while (true);
                            case 4:
                                System.out.println("Nhập link ảnh cho album : ");
                                String inputImage = InputMethods.getString();
                                albumList.get(i).setImage(inputImage);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            case 5:
                                isExit = true;
                                break;
                        }
                    } while (!isExit);
                } else {
                    System.err.println(Messages.NAME_NOT_FOUND);
                }
            }
            // sau khi edit lưu lại nó vào file
            try {
                IOFile.writeToFile(IOFile.ALBUM_PATH, albumList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public void handleDelete() {
        if (albumList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên album muốn thực hiện xóa :");
            String inputSearchName = InputMethods.getString().toLowerCase();
            int deleteIndex = findIndexByName(inputSearchName);
            if (deleteIndex != -1) {
                albumList.remove(deleteIndex);
                // sau khi delete lưu lại nó vào file
                try {
                    IOFile.writeToFile(IOFile.ALBUM_PATH, albumList);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                System.out.println();
            } else {
                System.err.println(Messages.NAME_NOT_FOUND);
            }
        }
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
