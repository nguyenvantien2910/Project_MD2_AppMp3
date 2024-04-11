package presentation.user;

import business.designIplm.IUserIplm;
import business.utils.InputMethods;
import business.utils.Messages;

import static presentation.Login.*;

public class UserMenu {
    private static UserMenu userMenu = new UserMenu();

    public static UserMenu getInstance() {
        return userMenu;
    }

    private UserMenu() {

    }

    private static byte choice;
    private static boolean isExit = false;

    public void displayUserMenu() {
        do {
            System.out.println("==========WELCOME TO USER MENU==========");
            System.out.println("1. Trang chủ");
            System.out.println("2. Trang tìm kiếm bài hát");
            System.out.println("3. Trang bài hát yêu thích");
            System.out.println("4. Trang thông tin cá nhân");
            System.out.println("5. Lịch sử mua hàng");
            System.out.println("6. Thoát");
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
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private void displayHistorypage() {
        do {
            System.out.println("==========HISTORY MENU==========");
            System.out.println("1. Hiển thị dữ liệu");
            System.out.println("2. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    IUserIplm.displayBuyHistory();
                    break;
                case 2:
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private void displayInfoPage() {
        do {
            System.out.println("==========INFOMATION MENU==========");
            System.out.println("1. Hiển thị thông tin cá nhân");
            System.out.println("2. Chỉnh sửa thông tin cá nhân");
            System.out.println("3. Đổi mật khẩu");
            System.out.println("4. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    IUserIplm.showInfoUserLogin();
                    break;
                case 2:
                    IUserIplm.updateInfomation();
                    break;
                case 3:
                    IUserIplm.updatePassword();
                    break;
                case 4:
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private void displayFavoritesList() {
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
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private void displaySearchMenu() {
        do {
            System.out.println("==========SEARCH MENU==========");
            System.out.println("1. Tìm kiếm ca sĩ");
            System.out.println("2. Tìm kiếm album");
            System.out.println("3. Tìm kiếm danh sách bài hát");
            System.out.println("4. Mua bài hát");
            System.out.println("5. Thêm vào yêu thích");
            System.out.println("6. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iUserIplm.searchSingerByName();
                    break;
                case 2:
                    iAlbumIplm.searchAlbumByName();
                    break;
                case 3:
                    iUserIplm.searchSongByName();
                    break;
                case 4:
                    iHistoryIplm.addNewHistory();
                    break;
                case 5:
                    iSongIplm.bookmarkSongToFavoriteList();
                    break;
                case 6:
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private void displayHomePageMenu() {
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
                    break;
                case 3:
                    iSingerIplm.showTrendingSinger();
                    break;
                case 4:
                    iAlbumIplm.showTrendingAlbum();
                    break;
                case 5:
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }
}
