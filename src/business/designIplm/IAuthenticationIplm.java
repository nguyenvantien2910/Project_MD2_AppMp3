package business.designIplm;

import business.design.IAuthentication;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import org.mindrot.jbcrypt.BCrypt;
import presentation.admin.AdminMenu;
import presentation.user.UserMenu;

import java.io.FileNotFoundException;
import java.util.Comparator;

import static presentation.Main.userList;
import static presentation.Main.userLoginToUsed;

public class IAuthenticationIplm implements IAuthentication {
    //Tim kiếm user theo tên
    private User getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    //Id tự tăng
    private int getNewId() {
        int maxUserId = userList.stream()
                .map(User::getUserId)
                .max(Comparator.naturalOrder())
                .orElse(0);
        return maxUserId + 1;
    }

    @Override
    public void login() {
        System.out.println("=========== LOGIN ===========");
        //Nhập tên đăng nhập
        System.out.println("Nhập tên đăng nhập :");
        String inputUserName = InputMethods.getString();
        //Khởi tại tài khoản đang đăng nhập theo username đã nhập
        User userLogin = getUserFromUsername(inputUserName);
        //Kiểm tra tài khoản đang đăng nhập có tồn tại hay không ?
        if (userLogin == null) {
            System.err.println(Messages.USERNAME_INVALID);
            System.out.println("========= LOGIN FAILED ===========");
            System.out.println("1. Tiếp tục đăng nhập với tài khoản khác");
            System.out.println("2. Chưa có tài khoản, đăng kí ngay ");
            System.out.println("3. Quay lại ");

            System.out.print("Nhâp lựa chọn của bạn : \n");

            byte choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } else {
            //Nhập mật khẩu
            System.out.println("Nhập mật khẩu : ");
            String inputPassword = InputMethods.getString();
            //Kiểm tra tính hợp lệ của mật khẩu
            boolean checkLogin = BCrypt.checkpw(inputPassword, userLogin.getPassword());
            //Kiểm tra role của user đăng nhập
            if (checkLogin) {
                userLoginToUsed = userLogin;
                if (userLogin.isRole()) {
                    AdminMenu.getInstance().displayAdminMenu();
                } else {
                    if (!userLogin.isStatus()) {
                        System.err.println(Messages.BLOCK_USER_ERROR);
                    } else {
                        UserMenu.getInstance().displayUserMenu();
                    }
                }
            } else {
                System.err.println(Messages.PASSWORD_INVALID);
            }
        }
    }

    @Override
    public void register() {
        System.out.println("=========== REGISTER ===========");
        User user = new User();
        user.inputData();
        user.setUserId(getNewId());
        userList.add(user);
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);
        login();
    }
}
