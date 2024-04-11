package business.designIplm;

import business.design.IUserDesign;
import business.entity.*;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileNotFoundException;
import java.util.List;

import static presentation.Login.*;
import static presentation.Login.singerList;

public class IUserIplm implements IUserDesign {
    private static byte choice;
    //Danh sách các ca sĩ đang hoạt động
    private static List<Singer> singerListAvailable = singerList.stream().filter(Singer::isStatus).toList();

    public static void showInfoUserLogin() {
        System.out.println("========= LOGIN USER INFORMATION ========");
        userLoginToUsed.displayDataForUser();
    }

    public static void updateInfomation() {
        System.out.println("========= UPDATE INFORMATION ========");
        System.out.println("1. Chỉnh sửa họ và tên ");
        System.out.println("2. Chỉnh sửa hình ảnh đại diện ");
        System.out.println("3. Chỉnh sửa số điện thoại ");
        System.out.println("4. Thoát ");

        System.out.println("Nhập lựa chọn của bạn ");
        choice = InputMethods.getByte();

        switch (choice) {
            case 1:
                System.out.println("Nhập họ và tên mới");
                userLoginToUsed.setFullName(InputMethods.getString());
                break;

            case 2:
                System.out.println("Nhập link ảnh đại diện : ");
                userLoginToUsed.setAvatar(InputMethods.getString());
                break;

            case 3:
                System.out.println("Nhập số điện thoại mới : ");
                userLoginToUsed.setPhone(InputMethods.getString());
                break;

            case 4:
                return;

            default:
                System.err.println(Messages.SELECT_INVALID);
        }

        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == userLoginToUsed.getUserId()) {
                userList.set(i, userLoginToUsed);
                break;
            }
        }
        // sau khi thêm lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
            System.out.println(Messages.UPDATE_INFO_SUCESS);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updatePassword() {
        String inputPW;
        String inputConfirmPW;
        do {
            System.out.println("Nhập mật khẩu mới :");
            inputPW = InputMethods.getString();

            System.out.println("Nhập mật khẩu xác nhận: ");
            inputConfirmPW = InputMethods.getString();

        } while (!inputConfirmPW.equals(inputPW));

        userLoginToUsed.setPassword(BCrypt.hashpw(inputPW, BCrypt.gensalt(5)));
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == userLoginToUsed.getUserId()) {
                userList.set(i, userLoginToUsed);
                break;
            }
        }
        // sau khi thêm lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
            System.out.println(Messages.UPDATE_PASSWORD_SUCESS);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void displayBuyHistory() {
        System.out.println("========= BUY HISTORY ========");
        historyList.stream().filter(History -> History.getUserId() == userLoginToUsed.getUserId()).forEach(History::displayData);
    }


    public void showAllBookMarkSong() {
        //get danh sách bài hát yêu thích của user đang login
        List<Song> bookmarkSongListOfLoginUser = userLoginToUsed.getFavoriteSongs();

        System.out.println("========== DANH SÁCH BÀI HÁT YÊU THÍCH=========");
        bookmarkSongListOfLoginUser.forEach(Song::displayDataForUser);
    }

    public void searchSingerByName() {
        if (singerList == null || singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên ca sĩ muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString().toLowerCase();

            List<Singer> singerListFilterBySearchKey = singerListAvailable.stream().filter(singer -> singer.getSingerName().toLowerCase().contains(inputSearchName)).toList();
            if (singerListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                singerListFilterBySearchKey.forEach(Singer::displayDataForUser);
            }
        }
    }

    public void searchSongByName() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên bài hát muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString().toLowerCase();

            List<Song> songListFilterBySearchKey = songList.stream().filter(song -> song.getSongName().toLowerCase().contains(inputSearchName)).toList();
            if (songListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                songListFilterBySearchKey.forEach(Song::displayDataForUser);
            }
        }
    }


    public void showTrendingAlbum() {
    }

    public void showTrendingArtist() {
    }

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
