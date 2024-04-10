package business.designIplm;

import business.design.ISingerDesign;
import business.entity.Singer;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

import static presentation.Main.singerList;

public class ISingerIplm implements ISingerDesign {
    private static byte choice;
    private static boolean isExit = false;

    @Override
    public void searchSingerByName() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên ca sĩ muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString().toLowerCase();

            List<Singer> singerListFilterBySearchKey = singerList.stream().filter(singer -> singer.getSingerName().toLowerCase().contains(inputSearchName)).toList();
            if (singerListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                singerListFilterBySearchKey.forEach(Singer::displayData);
            }
        }
    }


    @Override
    public Integer findIndexByName(String name) {
        for (int i = 0; i < singerList.size(); i++) {
            if (singerList.get(i).getSingerName().toLowerCase().contains(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng ca sĩ muốn thêm :");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            Singer singer = new Singer();
            System.out.printf("Nhập thông tin cho ca sĩ thứ %d \n", i + 1);
            singer.inputData();
            singerList.add(singer);
        }
        // sau khi add lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH, singerList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);
    }

    @Override
    public void handleShow() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("========== SINGER LIST ==========");
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 3;
            int page = 1;
            int numberOfPage;
            if (singerList.size() % elementPerPage == 0) {
                numberOfPage = singerList.size() / elementPerPage;
            } else {
                numberOfPage = singerList.size() / elementPerPage + 1;
            }
            do {
                for (int i = 0; i < singerList.size(); i++) {
                    if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                        singerList.get(i).displayData();
                    }
                }

                System.out.println();
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
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID ca sĩ muốn cập nhật thông tin :");
            int inputSearchID = InputMethods.getInteger();
            for (int i = 0; i < singerList.size(); i++) {
                if (singerList.get(i).getSingerId() == inputSearchID) {
                    do {
                        System.out.println("========= EDIT SINGER INFORMATION =======\n");
                        System.out.println("1. Chỉnh sửa tên");
                        System.out.println("2. Chỉnh sửa mô tả");
                        System.out.println("3. Chỉnh sửa trạng thái");
                        System.out.println("4. Thoát");
                        System.out.println("Nhập lựa chọn của bạn : ");

                        choice = InputMethods.getByte();
                        switch (choice) {
                            case 1:
                                System.out.println("Nhập tên cho ca sĩ :");
                                String inputSingerName = InputMethods.getString();
                                singerList.get(i).setSingerName(inputSingerName);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            case 2:
                                System.out.println("Nhập mô tả cho ca sĩ :");
                                String inputSingerDescription = InputMethods.getString();
                                singerList.get(i).setDescription(inputSingerDescription);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            case 3:
                                singerList.get(i).setStatus(!singerList.get(i).isStatus());
                                System.out.println(Messages.UPDATE_STATUS_SUCESS);
                                break;
                            case 4:
                                isExit = true;
                                break;
                        }
                    } while (!isExit);
                } else {
                    System.err.println(Messages.ID_NOT_FOUND);
                }
            }
            // sau khi delete lưu lại nó vào file
            try {
                IOFile.writeToFile(IOFile.SINGER_PATH, singerList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleDelete() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID ca sĩ muốn thực hiện xóa :");
            int inputSearchID = InputMethods.getInteger();
            boolean isExits = false;
            for (int i = 0; i < singerList.size(); i++) {
                if (singerList.get(i).getSingerId() == inputSearchID) {
                    isExits = true;
                    singerList.remove(i);
                    // sau khi delete lưu lại nó vào file
                    try {
                        IOFile.writeToFile(IOFile.SINGER_PATH, singerList);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Messages.DELETE_SUCESS);
                }
            }
            if (!isExits) {
                System.err.println(Messages.NAME_NOT_FOUND);
            }
        }
    }
}
