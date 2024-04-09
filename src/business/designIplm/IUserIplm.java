package business.designIplm;

import business.design.IUserDesign;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

public class IUserIplm implements IUserDesign {
    public static List<User> userList;

    public static List<User> getUserList() {
        if (userList == null) {
            userList = IOFile.readFromFile(IOFile.USER_PATH);
        }
        return userList;
    }
    private static byte choice;

    @Override
    public Integer findIndexById(Object id) {
        return 0;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng người dùng muốn thêm mới : ");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            User user = new User();
            System.out.printf("Nhập thông tin cho người dùng thứ %d \n", i + 1);
            user.inputData(true);
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
            IOFile.writeToFile(IOFile.USER_PATH,userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleShow() {
        if (userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("==========USER LIST==========");
            userList.forEach(User::displayData);
        }
    }

    @Override
    public void handleEdit() {
        // sau khi edit lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH,userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleDelete() {

        // sau khi delete lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH,userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void searchUserByName() {
        if (userList.isEmpty()){
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên người dùng muốn tìm kiếm :");
            String inputName = InputMethods.getString();
            List<User> userListFilterByName = userList.stream().filter(user -> user.getUsername().contains(inputName)).toList();
            System.out.printf("Danh sách người dùng tìm kiếm theo từ khóa %s \n", inputName);
            userListFilterByName.forEach(User::displayData);
        }
    }

    @Override
    public void updateUserStatus() {
        if (userList.isEmpty()){
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên người dùng muốn thay đổi trạng thái :");
            String inputName = InputMethods.getString();
            User userSearchByName = userList.stream().filter(user -> user.getUsername().contains(inputName)).findFirst().orElse(null);
            if (userSearchByName != null) {
                do {
                    System.out.printf("Bạn có muốn thay đổi trạng thái của người dùng  %s không ? \n", userSearchByName.getUsername());
                    System.out.println("1. Có");
                    System.out.println("2. Không");
                    System.out.println("Nhập lựa chọn của bạn : ");
                    choice = InputMethods.getByte();
                    switch (choice) {
                        case 1:
                            userSearchByName.setStatus(!userSearchByName.isStatus());
                            try {
                                IOFile.writeToFile(IOFile.USER_PATH, userList);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            System.out.println(Messages.UPDATE_STATUS_SUCESS);
                            break;
                        case 2:
                            return;
                        default:
                            System.err.println(Messages.SELECT_INVALID);
                    }
                } while (true);

            } else {
                System.err.println(Messages.NAME_NOT_FOUND);
            }
        }
    }
}
