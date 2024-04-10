package business.designIplm;

import business.design.IUserDesign;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

import static presentation.Main.userList;

public class IUserIplm implements IUserDesign {
    private static byte choice;

    @Override
    public Integer findIndexByName(String name) {
        return 0;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng người dùng muốn thêm mới : ");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            User user = new User();
            System.out.printf("Nhập thông tin cho người dùng thứ %d \n", i + 1);
            user.inputData();
            userList.add(user);
        }
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);

        // sau khi thêm lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleShow() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("==========USER LIST==========");
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 3;
            int page = 1;
            int numberOfPage;
            if (userList.size() % elementPerPage == 0) {
                numberOfPage = userList.size() / elementPerPage;
            } else {
                numberOfPage = userList.size() / elementPerPage + 1;
            }
            do {
                for (int i = 0; i < userList.size(); i++) {
                    if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                        userList.get(i).displayDataForAdmin();
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
        // sau khi edit lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleDelete() {

        // sau khi delete lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchUserByName() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên người dùng muốn tìm kiếm :");
            String inputName = InputMethods.getString();
            List<User> userListFilterByName = userList.stream().filter(user -> user.getFullName().contains(inputName)).toList();
            System.out.printf("Danh sách người dùng tìm kiếm theo từ khóa %s \n", inputName);
            userListFilterByName.forEach(User::displayDataForAdmin);
        }
    }

    @Override
    public void updateUserStatus() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            do {
                System.out.println("Nhập ID người dùng muốn thay đổi trạng thái :");
                int inputID = InputMethods.getInteger();
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).getUserId() == inputID) {
                        System.out.printf("Bạn có muốn thay đổi trạng thái của người dùng  %s không ? \n", userList.get(i).getUsername());
                        System.out.println("1. Có");
                        System.out.println("2. Không");
                        System.out.println("Nhập lựa chọn của bạn : ");
                        choice = InputMethods.getByte();
                        switch (choice) {
                            case 1:
                                userList.get(i).setStatus(!userList.get(i).isStatus());
                                try {
                                    IOFile.writeToFile(IOFile.USER_PATH, userList);
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                                System.out.println(Messages.UPDATE_STATUS_SUCESS);
                                userList.get(i).displayDataForAdmin();
                                break;
                            case 2:
                                return;
                            default:
                                System.err.println(Messages.SELECT_INVALID);
                        }
                    }
                }
                break;
            } while (true);
        }
    }
}
