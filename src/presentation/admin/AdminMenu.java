package presentation.admin;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import presentation.Login;

import static presentation.Login.*;

public class AdminMenu {

    public static void displayAdminMenu() {
        do {
            System.out.println("==========WELCOME TO ADMIN MENU==========");
            System.out.println("1. Quàn lí người dùng");
            System.out.println("2. Quản lí ca sĩ");
            System.out.println("3. Quản lí Album");
            System.out.println("4. Quản lí bài hát");
            System.out.println("5. Quản lí đơn hàng");
            System.out.println("6. Thống kê");
            System.out.println("7. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    userManagement();
                    break;
                case 2:
                    singerManagement();
                    break;
                case 3:
                    albumManagement();
                    break;
                case 4:
                    songManagement();
                    break;
                case 5:
                    historyManagement();
                    break;
                case 6:
                    dashboardManagement();
                    break;
                case 7:
                    Login.user = null;
                    IOFile.updateUserLogin(user);
                    isExit = true;
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private static void dashboardManagement() {
        do {
            System.out.println("==========DASHBOARD MANAGEMENT==========");
            System.out.println("1. Thống kê đơn hàng theo tháng/năm/quý");
            System.out.println("2. Thống kê doanh thu theo tháng/năm/quý");
            System.out.println("3. Thống kê bài hát theo ca sĩ/album/ngày phát hành");
            System.out.println("4. Thống kê user theo từng loại tài khoản");
            System.out.println("5. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    private static void historyManagement() {
        do {
            System.out.println("==========HISTORY MANAGEMENT==========");
            System.out.println("1. Hiển thị danh sách lịch sử mua hàng");
            System.out.println("2. Tìm kiếm đơn hàng");
            System.out.println("3. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iHistoryIplm.handleShow();
                    break;
                case 2:
                    iHistoryIplm.handleFindByName();
                    break;
                case 3:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void songManagement() {
        do {
            System.out.println("==========SONG MANAGEMENT==========");
            System.out.println("1. Hiển thị danh sách bài hát");
            System.out.println("2. Thêm mới bài hát");
            System.out.println("3. Sửa bái hát");
            System.out.println("4. Xóa bài hát");
            System.out.println("5. Tìm kiếm bài hát theo tên");
            System.out.println("6. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iSongIplm.handleShow();
                    break;
                case 2:
                    iSongIplm.handleAdd();
                    break;
                case 3:
                    iSongIplm.handleEdit();
                    break;
                case 4:
                    iSongIplm.handleDelete();
                    break;
                case 5:
                    iSongIplm.handleFindByName();
                    break;
                case 6:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void albumManagement() {
        do {
            System.out.println("==========ALBUM MANAGEMENT==========");
            System.out.println("1. Hiển thị danh sách album");
            System.out.println("2. Thêm mới album");
            System.out.println("3. Sửa album");
            System.out.println("4. Xóa album");
            System.out.println("5. Tìm kiếm album theo tên");
            System.out.println("6. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iAlbumIplm.handleShow();
                    break;
                case 2:
                    iAlbumIplm.handleAdd();
                    break;
                case 3:
                    iAlbumIplm.handleEdit();
                    break;
                case 4:
                    iAlbumIplm.handleDelete();
                    break;
                case 5:
                    iAlbumIplm.handleFindByName();
                    break;
                case 6:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void singerManagement() {
        do {
            System.out.println("==========SINGER MANAGEMENT==========");
            System.out.println("1. Hiển thị danh sách ca sĩ");
            System.out.println("2. Thêm mới ca sĩ");
            System.out.println("3. Sửa ca sĩ");
            System.out.println("4. Xóa ca sĩ");
            System.out.println("5. Tìm kiếm ca sĩ theo tên");
            System.out.println("6. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iSingerIplm.handleShow();
                    break;
                case 2:
                    iSingerIplm.handleAdd();
                    break;
                case 3:
                    iSingerIplm.handleEdit();
                    break;
                case 4:
                    iSingerIplm.handleDelete();
                    break;
                case 5:
                    iSingerIplm.handleFindByName();
                    break;
                case 6:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }

    public static void userManagement() {
        do {
            System.out.println("==========USER MANAGEMENT==========");
            System.out.println("1. Hiển thị danh sách người dùng");
            System.out.println("2. Thêm mới người dùng");
            System.out.println("3. Tìm kiếm người dùng theo tên");
            System.out.println("4. Thay đổi trạng thái người dùng");
            System.out.println("5. Thoát");
            System.out.println(" Nhập lựa chọn của bạn : ");

            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    iUserIplm.handleShow();
                    userManagement();
                    break;
                case 2:
                    iUserIplm.handleAdd();
                    break;
                case 3:
                    iUserIplm.handleFindByName();
                    break;
                case 4:
                    iUserIplm.updateUserStatus();
                    userManagement();
                    break;
                case 5:
                    displayAdminMenu();
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (!isExit);
    }
}