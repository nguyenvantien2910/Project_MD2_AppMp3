package presentation;

import business.designIplm.*;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import presentation.admin.AdminMenu;
import presentation.user.UserMenu;

import java.io.File;

public class Login {
    private static IAuthenticationIplm iAuthenticationIplm = new IAuthenticationIplm();
    public static User user = null;
    public static byte choice;
    public static boolean isExit = false;

    public static IUserIplm iUserIplm = new IUserIplm();
    public static ISingerIplm iSingerIplm = new ISingerIplm();
    public static ISongIplm iSongIplm = new ISongIplm();
    public static IAlbumIplm iAlbumIplm = new IAlbumIplm();
    public static IHistoryIplm iHistoryIplm = new IHistoryIplm();

    //tự động đăng nhập
    static {
        File loginUser = new File(IOFile.LOGIN_USER_PATH);
        // nếu có một đối tượng user đã đăng nhập từ trước thì gắn user đó vào biến user
        if (loginUser.length() != 0) {
            user = IOFile.getUserLogin();
        }
        // tự động đăng nhập nếu user khác null
        if (user != null) {
            {
                if (user.isRole()) {
                    AdminMenu.displayAdminMenu();
                } else {
                    UserMenu.displayUserMenu();
                }
            }
        }
    }

    public static void main(String[] args) {

        do {
            System.out.println("===============WELCOME TO SPOTIFY 偽物=============");
            System.out.println("=================MADE BY ティエンさん===============");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.println("3. Thoát chương trình");

            System.out.println("Nhập lựa chọn của bạn : ");

            byte choice = InputMethods.getByte();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    System.out.println(Messages.EXIT_SUCESS);
                    System.exit(0);
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (true);
    }

    private static void register() {
        System.out.println("=========== REGISTER ===========");
        User user = new User();
        user.inputData();
        iAuthenticationIplm.register(user);
        System.out.println(Messages.ADD_NEW_SUCESS);
        login();
    }

    private static void login() {
        System.out.println("=========== LOGIN ===========");
        //Nhập tên đăng nhập
        System.out.println("Nhập tên đăng nhập :");
        String inputUserName = InputMethods.getString();
        //Nhập mật khẩu
        System.out.println("Nhập mật khẩu :");
        String inputPassword = InputMethods.getString();
        // kiểm tra tài khoản nhập vào
        User userLogin = iAuthenticationIplm.login(inputUserName, inputPassword);
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
            //Cập nhập thông tin user đang login
            IOFile.updateUserLogin(userLogin);
            //check quyền có phải admin hay không ?
            if (userLogin.isRole()) {
                user = userLogin;
                AdminMenu.displayAdminMenu();
            } else {
                //Nếu là user thường thì sẽ cần check xem có phải là user đang bị block hay không ?
                if (!userLogin.isStatus()) {
                    System.out.println(Messages.BLOCK_USER_ERROR);
                } else {
                    user = userLogin;
                    UserMenu.displayUserMenu();
                }
            }
        }
    }
}
