package business.designIplm;

import business.design.IGenericDesign;
import business.entity.History;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import business.utils.Pagination;
import presentation.Login;
import presentation.user.UserMenu;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static business.designIplm.IAuthenticationIplm.userList;
import static business.designIplm.ISongIplm.songList;

public class IHistoryIplm implements IGenericDesign {
    public static List<History> historyList;
    private static int indexOfUserLogin;

    static {
        File historyFile = new File(IOFile.HISTORY_PATH);
        if (historyFile.length() == 0) {
            historyList = new ArrayList<>();
            IOFile.writeDataToFile(IOFile.HISTORY_PATH, historyList);
        } else {
            historyList = IOFile.getDataFormFile(IOFile.HISTORY_PATH);
        }
    }

    @Override
    public Integer findIndexById(Object id) {
        return 0;
    }

    @Override
    public void handleAdd() {
        if (songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            //Khởi tạo biến lưu thông tin người đang đăng nhập
            User user = Login.user;
            // tìm index tương ứng của user login trong userList
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUserId() == Login.user.getUserId()) {
                    indexOfUserLogin = i;
                    break;
                }
            }

            // Hiển thị danh sách bài hát cho người dùng chọn
            System.out.println("========== ALL SONGS ==========");
            for (int i = 0; i < songList.size(); i++) {
                songList.get(i).displayData();
            }
            System.out.println();
            System.out.println("Nhập ID bài hát muốn mua : ");
            byte selectSongID = InputMethods.getByte();
            int selectSongIndex = 0;
            for (int i = 0; i < songList.size(); i++) {
                if (songList.get(i).getSongId() == selectSongID) {
                    selectSongIndex = i;
                    break;
                }
            }

            // Hiển thị danh sách menu
            System.out.println();
            System.out.println("Chọn gói bạn muốn mua : ");
            System.out.println("1. Gói 30 ngày ");
            System.out.println("2. Gói vĩnh viễn");
            System.out.println("Nhập lựa chọn của bạn : ");
            int selectTypePackage = InputMethods.getInteger();

            // Tạo bản ghi mới cho việc mua hàng
            History newHistory = new History();
            newHistory.setHistoryId(newHistory.findIdMax());
            newHistory.setUserId(user.getUserId());
            newHistory.setSongId(songList.get(selectSongIndex).getSongId());
            newHistory.setCreateAt(LocalDate.now());
            newHistory.setOrderAt(LocalDate.now());
            newHistory.setTypePackage(selectTypePackage);

            //Logic check xem gói user chọn là gói nào ?
            if (selectTypePackage == 1) {
                // Logic gói 30 ngày
                // Check xem user có đủ tiển để nạp hay không
                double totalPrice = songList.get(selectSongIndex).getPrice();
                if (user.getWallet() < totalPrice) {
                    // Thực hiện thanh toán
                    handleInsufficientFunds(user, totalPrice);
                    return;
                }

                // add thông tin ngày hết hạn và giá triền cho gói 30 ngày
                newHistory.setExpriedAt(LocalDate.now().plusDays(30));
                newHistory.setTotalPrice(totalPrice);
                handlePurchase(user, newHistory, indexOfUserLogin);
            } else if (selectTypePackage == 2) {
                // Logic gói mua vĩnh viễn
                // Tính tổng tiền cần thanh toán
                double totalPrice = songList.get(selectSongIndex).getPrice() * 10;
                if (user.getWallet() < totalPrice) {
                    // Xử lí thanh toán tiền, trừ tiền user
                    handleInsufficientFunds(user, totalPrice);
                    return;
                }

                // set thông tin ngày hết hạn và giá tiền khi thanh toán vĩnh viễn
                newHistory.setExpriedAt(null);
                newHistory.setTotalPrice(totalPrice);
                handlePurchase(user, newHistory, indexOfUserLogin);
            } else {
                System.err.println(Messages.SELECT_INVALID);
            }
        }
    }

    // Method thanh toán
    private void handlePurchase(User user, History newHistory, int indexOfUserLogin) {
        user.setWallet(user.getWallet() - newHistory.getTotalPrice());
        IHistoryIplm.historyList.add(newHistory); // Thêm lịch sử chỉnh sửa mới
        // Cập nhật lại thông tin của user đang login vào list user
        userList.set(indexOfUserLogin, user);
        IOFile.writeDataToFile(IOFile.USER_PATH, IAuthenticationIplm.userList);
        //Ghi lích sử mua hàng vào file
        IOFile.writeDataToFile(IOFile.HISTORY_PATH, IHistoryIplm.historyList);
        System.out.println(Messages.BUY_SUCESS);
    }

    // Method nạp tiền + tiếp tục thanh toán
    private void handleInsufficientFunds(User user, double requiredAmount) {
        System.err.println(Messages.WALLET_NOT_ENOUGH);
        System.out.println("Bạn có muốn nạp tiền hay không ?");
        System.out.println("1. Có");
        System.out.println("2. Không");
        System.out.println("Nhập lựa chọn của bạn : ");
        int choice = InputMethods.getByte();
        switch (choice) {
            case 1:
                System.out.println("Nhập số tiền bạn muốn nạp thêm : ");
                double amount = InputMethods.getDouble();
                user.setWallet(user.getWallet() + amount);
                System.out.println(Messages.WALLET_CHARGE_SUCESS);
                // Tiếp tục thanh toán hay bakc về menu chính
                System.out.println("Tiếp tục mua bài hát ? \n1. Có\n2. Không");
                int continueChoice = InputMethods.getByte();
                if (continueChoice == 1) {
                    handleAdd(); // Tiếp tục thanh toán
                } else {
                    UserMenu.displaySearchMenu();
                }
                break;
            case 2:
                UserMenu.displaySearchMenu();
                break;
            default:
                System.err.println(Messages.SELECT_INVALID);
        }
    }


    @Override
    public void handleShow() {
        if (historyList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("=============== HISTORY LIST ==============");
            Pagination.paginateAndDisplay(historyList, Pagination.ELEMENT_PER_PAGE);
            System.out.println();
        }
    }

    @Override
    public void handleEdit() {

    }

    @Override
    public void handleDelete() {

    }

    @Override
    public void handleFindByName() {
        if (historyList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID của lịch sử mua hàng muốn tìm kiếm  : ");
            int inputId = InputMethods.getInteger();

            // Tìm lịch sử chỉnh sửa theo ID đã nhập
            History history = historyList.stream()
                    .filter(h -> h.getHistoryId() == inputId)
                    .findFirst()
                    .orElse(null);

            if (history != null) {
                System.out.println("=============== HISTORY ==============");
                history.displayData();
                System.out.println();
            } else {
                System.err.println(Messages.ID_NOT_FOUND);
            }
        }
    }


    ////////////////////// USSER ////////////
    public void displayHistoryForUser() {
        User user = Login.user;

        List<History> historyListOfLoginUser = historyList.stream().filter(history -> history.getUserId() == user.getUserId()).toList();

        if (historyListOfLoginUser.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("============ HISTORY FOR USER ===========");
            historyListOfLoginUser.forEach(History::displayData);
        }
    }
}
