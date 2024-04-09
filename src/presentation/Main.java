package presentation;

import business.designIplm.*;
import business.entity.Album;
import business.entity.Singer;
import business.entity.Song;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import presentation.admin.AdminMenu;
import presentation.user.UserMenu;

import java.io.FileNotFoundException;
import java.util.List;

public class Main {
    //Khai báo biến dùng chung
    public static List<User> userList;
    public static List<Song> songList;
    public static List<Singer> singerList;
    public static List<Album> albumList;
    public static ISongIplm iSongIplm = new ISongIplm();
    public static ISingerIplm iSingerIplm = new ISingerIplm();
    public static IUserIplm iUserIplm = new IUserIplm();
    public static IAlbumIplm iAlbumIplm = new IAlbumIplm();

    static {
        userList = IOFile.readFromFile(IOFile.USER_PATH);
        songList = IOFile.readFromFile(IOFile.SONG_PATH);
        singerList = IOFile.readFromFile(IOFile.SINGER_PATH);
        albumList = IOFile.readFromFile(IOFile.ALBUM_PATH);
    }

    private static IAuthenticationIplm iAuthenticationIplm = new IAuthenticationIplm();
    private static User user = null;
    private static byte choice;

    public static void main(String[] args) {
        boolean isExit = false;

        while (!isExit) {
            System.out.println("===============WELCOME TO SPOTIFY =============");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.println("3. Thoát chương trình");

            System.out.println("Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();

            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    register();
                    break;
                case 3:
                    isExit = true;
                    System.out.println(Messages.EXIT_SUCESS);
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
            if (choice == 3) {
                break;
            }
        }
    }

    private static void login() {
        System.out.println("=========== LOGIN ===========");
        System.out.print("Nhập tên đăng nhập : \n");
        String inputUserName = InputMethods.getString();
        inputPasswordWithUserLogin(inputUserName);

    }
    private static void inputPasswordWithUserLogin( String inputUserName) {
        System.out.print("Nhập mật khẩu : \n");
        String inputPassword = InputMethods.getString();

        User userLogin = iAuthenticationIplm.login(inputUserName, inputPassword);
        if (userLogin == null) {
            System.err.println(Messages.LOGIN_INVALID);
            System.out.println("========= LOGIN FAILED ===========");
            System.out.printf("1. Tiếp tục đăng nhập với tài khoản: %s ?\n", inputUserName);
            System.out.println("2. Tiếp tục đăng nhập với tài khoản khác");
            System.out.println("3. Chưa có tài khoản, đăng kí ngay ");
            System.out.println("4. Quay lại ");

            System.out.print("Nhâp lựa chọn của bạn : \n");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    inputPasswordWithUserLogin(inputUserName);
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    register();
                    break;
                case 4:
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } else {
            if (userLogin.isRole()) {
                user = userLogin;
                AdminMenu.getInstance().displayAdminMenu();
            } else {
                if (!userLogin.isStatus()) {
                    System.err.println(Messages.BLOCK_USER_ERROR);
                } else {
                    user = userLogin;
                    UserMenu.getInstance().displayUserMenu();
                }
            }
        }
    }

    private static void register() {
        System.out.println("=========== REGISTER ===========");
        User user = new User();
        user.inputData(false);
        IUserIplm.getUserList().add(user);
        try {
            IOFile.writeToFile(IOFile.USER_PATH, IUserIplm.getUserList());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);
        login();
    }
}
