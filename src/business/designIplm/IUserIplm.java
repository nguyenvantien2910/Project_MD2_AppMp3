package business.designIplm;

import business.design.IUserDesign;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import business.utils.Pagination;
import org.mindrot.jbcrypt.BCrypt;
import presentation.Login;
import presentation.admin.AdminMenu;
import presentation.user.UserMenu;

import java.util.List;

import static business.designIplm.IAuthenticationIplm.userList;

public class IUserIplm implements IUserDesign {
    private static byte choice;
    private boolean isExit = false;


    ////////////////////////////////////// ADMIN /////////////////////////////////////

    @Override
    public void updateUserStatus() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewUser();
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
                                System.out.printf("Trạng thái cũ của người dùng : %s\n", (userList.get(i).isStatus()) ? "Active" : "Block");
                                System.out.println();
                                userList.get(i).setStatus(!userList.get(i).isStatus());
                                IOFile.writeDataToFile(IOFile.USER_PATH, userList);
                                System.out.println(Messages.UPDATE_STATUS_SUCESS);
                                userList.get(i).displayData();
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

    private void askAdminToAddNewUser() {
        if (Login.user.isRole()) {
            System.out.println("Bạn có muốn thêm mới người dùng không ?");
            System.out.println("1. Có ");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn : ");
            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    handleAdd();
                    break;
                case 2:
                    AdminMenu.userManagement();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        }
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng người dùng muốn thêm mới : ");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            User user = new User();
            System.out.printf("Nhập thông tin cho người dùng thứ %d \n", i + 1);
            System.out.println();
            user.inputData();
            //Mã hóa password cho user
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
            //Thêm user mới vào list
            userList.add(user);
            // sau khi thêm lưu lại nó vào file
            IOFile.writeDataToFile(IOFile.USER_PATH, userList);
            System.out.println(Messages.ADD_NEW_SUCESS);
            System.out.println();
        }
    }

    @Override
    public void handleShow() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewUser();
        } else {
            System.out.println("==========USER LIST==========");
            Pagination.paginateAndDisplay(userList, Pagination.ELEMENT_PER_PAGE);
        }
    }


    @Override
    public void handleFindByName() {
        if (userList == null || userList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddNewUser();
        } else {
            System.out.println("Nhập tên người dùng muốn tìm kiếm :");
            String inputName = InputMethods.getString();
            List<User> userListFilterByName = userList.stream().filter(user -> user.getFullName().contains(inputName)).toList();
            System.out.printf("Danh sách người dùng tìm kiếm theo từ khóa %s \n", inputName);
            userListFilterByName.forEach(User::displayData);
        }
    }

    //////////////////////////////////// USER //////////////////////////
    public void displayInformation() {
        User user = Login.user;
        System.out.println("=========== User Information ==========");
        user.displayData();
        System.out.println();
    }

    @Override
    public void updatePassword() {
        User user = Login.user;
        String currentPassword = user.getPassword();
        while (true) {
            System.out.println("Mời bạn nhập mật khẩu mới : ");
            String inputPassword = InputMethods.getString();
            if (BCrypt.checkpw(inputPassword, currentPassword)) {
                System.err.println(Messages.PASSWORD_USED_ERROR);
            } else {
                user.setPassword(BCrypt.hashpw(inputPassword, BCrypt.gensalt(5)));
                IOFile.writeDataToFile(IOFile.USER_PATH, userList);
                System.out.println(Messages.UPDATE_PASSWORD_SUCESS);
                break;
            }
        }
    }

    @Override
    public void updateInfomation() {
        User user = Login.user;
        do {
            System.out.println("========= UPDATE INFORMATION ========");
            System.out.println("1. Chỉnh sửa họ và tên ");
            System.out.println("2. Chỉnh sửa hình ảnh đại diện ");
            System.out.println("3. Chỉnh sửa số điện thoại ");
            System.out.println("4. Thoát ");

            System.out.println("Nhập lựa chọn của bạn ");
            choice = InputMethods.getByte();

            switch (choice) {
                case 1:
                    System.out.printf("Tên hiện tại : %s\n", user.getFullName());
                    System.out.println();
                    user.setFullName(user.inputFullName());
                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                    System.out.println();
                    user.displayData();
                    System.out.println();
                    break;

                case 2:
                    System.out.printf("Link avatar hiện tại : %s\n", user.getAvatar());
                    System.out.println();
                    user.setAvatar(user.inputAvatar());
                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                    System.out.println();
                    user.displayData();
                    System.out.println();
                    break;

                case 3:
                    System.out.printf("Số điện thoạt hiện tại : %s\n", user.getPhone());
                    user.setPhone(user.inputPhone());
                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                    System.out.println();
                    user.displayData();
                    System.out.println();
                    break;

                case 4:
                    isExit = true;
                    return;

                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);

        // sau khi thêm lưu lại nó vào file
        IOFile.writeDataToFile(IOFile.USER_PATH, userList);
    }

    ////////////////// WAITTING /////////////////

    @Override
    public void handleEdit() {

    }

    @Override
    public void handleDelete() {

    }


    @Override
    public Integer findIndexById(Object id) {
        return 0;
    }


    @Override
    public void displayBuyHistory() {

    }

    @Override
    public void showAllBookMarkSong() {

    }

}

