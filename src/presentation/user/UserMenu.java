package presentation.user;

import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import presentation.Login;

import java.io.File;

import static business.utils.CSS.*;
import static presentation.Login.*;

public class UserMenu {
    public static void displayUserMenu() {
        do {
            System.out.println("==========WELCOME TO USER MENU==========");
            System.out.println("1. Trang chủ");
            System.out.println("2. Trang tìm kiếm bài hát/Ca sĩ / Album");
            System.out.println("3. Trang bài hát yêu thích");
            System.out.println("4. Trang thông tin cá nhân");
            System.out.println("5. Lịch sử mua hàng");
            System.out.println("6. Logout");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    displayHomePageMenu();
                    break;
                case 2:
                    displaySearchMenu();
                    break;
                case 3:
                    displayFavoritesList();
                    break;
                case 4:
                    displayInfoPage();
                    break;
                case 5:
                    displayHistorypage();
                    break;
                case 6:
                    Login.user = null;
                    IOFile.updateUserLogin(user);
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private static void displayHistorypage() {
        do {
            System.out.println("==========HISTORY MENU==========");
            System.out.println("1. Hiển thị dữ liệu");
            System.out.println("2. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iHistoryIplm.displayHistoryForUser();
                    break;
                case 2:
                    displayUserMenu();
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private static void displayInfoPage() {
        do {
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════════╗");
            System.out.println("║                      INFORMATION MENU                      ║");
            System.out.println("╠════════════════════════════════════════════════════════════╣");
            System.out.println("║ " + ANSI_YELLOW + "1. Hiển thị thông tin cá nhân" + ANSI_CYAN + "                              ║");
            System.out.println("║ " + ANSI_YELLOW + "2. Chỉnh sửa thông tin cá nhân" + ANSI_CYAN + "                             ║");
            System.out.println("║ " + ANSI_YELLOW + "3. Đổi mật khẩu" + ANSI_CYAN + "                                            ║");
            System.out.println("║ " + ANSI_YELLOW + "4. Thoát" + ANSI_CYAN + "                                                   ║");
            System.out.println("╚════════════════════════════════════════════════════════════╝");
            System.out.print(ANSI_RESET + "Nhập lựa chọn của bạn: ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iUserIplm.displayInformation();
                    displayInfoPage();
                    break;
                case 2:
                    iUserIplm.updateInfomation();
                    displayInfoPage();
                    break;
                case 3:
                    iUserIplm.updatePassword();
                    break;
                case 4:
                    displayUserMenu();
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private static void displayFavoritesList() {
        do {
            System.out.println("==========FAVORITES LIST MENU==========");
            System.out.println("1. Danh sách bài hát yêu thích");
            System.out.println("2. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iUserIplm.showAllBookMarkSong();
                    break;
                case 2:
                    displayUserMenu();
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void displaySearchMenu() {
        do {
            System.out.println("==========SEARCH MENU==========");
            System.out.println("1. Tìm kiếm ca sĩ");
            System.out.println("2. Tìm kiếm album");
            System.out.println("3. Tìm kiếm bài hát");
            System.out.println("4. Mua bài hát");
            System.out.println("5. Thêm vào yêu thích");
            System.out.println("6. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iSingerIplm.handleFindByName();
                    displaySearchMenu();
                    break;
                case 2:
                    iAlbumIplm.handleFindByName();
                    displaySearchMenu();
                    break;
                case 3:
                    iSongIplm.handleFindByName();
                    displaySearchMenu();
                    break;
                case 4:
                    iHistoryIplm.handleAdd();
                    break;
                case 5:
                    iSongIplm.bookmarkSongToFavoriteList();
                    break;
                case 6:
                    displayUserMenu();
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void displayHomePageMenu() {
        do {
            System.out.println("==========HOME PAGE==========");
            System.out.println("1. Hiển thị bài hát");
            System.out.println("2. Hiển thị bài hát trending");
            System.out.println("3. Hiển thị ca sĩ trending");
            System.out.println("4. Hiển thị album trending");
            System.out.println("5. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iSongIplm.showAllSongForUser();
                    break;
                case 2:
                    iSongIplm.showTrendingSong();
                    displayHomePageMenu();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    displayUserMenu();
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }
}