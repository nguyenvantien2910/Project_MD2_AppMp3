package business.designIplm;

import business.design.IGenericDesign;
import business.entity.Singer;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import business.utils.Pagination;
import presentation.Login;
import presentation.admin.AdminMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ISingerIplm implements IGenericDesign {
    private static byte choice;
    private boolean isExit = false;
    public static List<Singer> singerList;

    static {
        File singerFile = new File(IOFile.SINGER_PATH);
        if (singerFile.length() == 0) {
            singerList = new ArrayList<>();
            IOFile.writeDataToFile(IOFile.SINGER_PATH, singerList);
        } else {
            singerList = IOFile.getDataFormFile(IOFile.SINGER_PATH);
        }
    }

    //////////////////////// ADMIN /////////////////////

    private void askAdminToAddNewSinger() {
        if (Login.user.isRole()){
            System.out.println("Bạn có muốn thêm mới ca sĩ không ?");
            System.out.println("1. Có ");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn : ");
            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    handleAdd();
                    break;
                case 2:
                    AdminMenu.singerManagement();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        }
    }

    @Override
    public Integer findIndexById(Object id) {
        for (int i = 0; i < singerList.size(); i++) {
            if (singerList.get(i).getSingerId() == id) {
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
            System.out.println(Messages.ADD_NEW_SUCESS);
            System.out.println();
        }
        // sau khi add lưu lại nó vào file
        IOFile.writeDataToFile(IOFile.SINGER_PATH, singerList);
        System.out.println(Messages.ADD_NEW_SUCESS);
    }

    @Override
    public void handleShow() {
        if (singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewSinger();
        } else {
            System.out.println("==========SINGER LIST==========");
            Pagination.paginateAndDisplay(singerList, Pagination.ELEMENT_PER_PAGE);
        }
    }

    @Override
    public void handleEdit() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewSinger();
        } else {
            System.out.println("Nhập ID ca sĩ muốn cập nhật thông tin :");
            int inputSearchID = InputMethods.getInteger();
            int editIndex = findIndexById(inputSearchID);
            if (editIndex != -1) {
                do {
                    System.out.println("========= EDIT SINGER INFORMATION =======");
                    System.out.println("1. Chỉnh sửa tên");
                    System.out.println("2. Chỉnh sửa mô tả");
                    System.out.println("3. Chỉnh sửa trạng thái");
                    System.out.println("4. Thoát");
                    System.out.println("Nhập lựa chọn của bạn : ");

                    choice = InputMethods.getByte();
                    switch (choice) {
                        case 1:
                            System.out.printf("Tên cũ của ca sĩ : %s\n",singerList.get(editIndex).getSingerName());
                            System.out.println();
                            System.out.println("Nhập tên mới cho ca sĩ :");
                            String inputSingerName = InputMethods.getString();
                            singerList.get(editIndex).setSingerName(inputSingerName);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;

                        case 2:
                            System.out.printf("Mô tả cũ của ca sĩ : %s\n",singerList.get(editIndex).getDescription());
                            System.out.println();
                            System.out.println("Nhập mô tả cho ca sĩ :");
                            String inputSingerDescription = InputMethods.getString();
                            singerList.get(editIndex).setDescription(inputSingerDescription);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;

                        case 3:
                            System.out.printf("Trạng thái cũ của ca sĩ : %s\n",(singerList.get(editIndex).isStatus()) ? "Đang hoạt động" : "Ngừng hoạt động" );
                            System.out.println();
                            singerList.get(editIndex).setStatus(!singerList.get(editIndex).isStatus());
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
        IOFile.writeDataToFile(IOFile.SINGER_PATH, singerList);
    }

    @Override
    public void handleDelete() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewSinger();
        } else {
            System.out.println("Nhập ID ca sĩ muốn thực hiện xóa :");
            int inputSearchID = InputMethods.getInteger();
            int deleteIndex = findIndexById(inputSearchID);
            if (deleteIndex != -1) {
                singerList.remove(deleteIndex);
                IOFile.writeDataToFile(IOFile.SINGER_PATH, singerList);
                System.out.println(Messages.DELETE_SUCESS);
            } else {
                System.err.println(Messages.ID_NOT_FOUND);
            }
        }
    }

    @Override
    public void handleFindByName() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewSinger();
        } else {
            System.out.println("Nhập tên ca sĩ muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString();

            List<Singer> singerListFilterBySearchKey = singerList.stream().filter(singer -> singer.getSingerName().toLowerCase().contains(inputSearchName.toLowerCase())).toList();
            if (singerListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                singerListFilterBySearchKey.forEach(Singer::displayData);
                System.out.println();
            }
        }
    }
}
