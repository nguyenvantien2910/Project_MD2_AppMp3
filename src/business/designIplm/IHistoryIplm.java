package business.designIplm;

import business.design.IGenericDesign;
import business.entity.History;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import presentation.Login;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static business.designIplm.ISongIplm.songList;

public class IHistoryIplm implements IGenericDesign {
    public static List<History> historyList;

    static {
        File historyFile = new File(IOFile.HISTORY_PATH);
        if (historyFile.length() == 0) {
            historyList = new ArrayList<>();
            IOFile.writeDataToFile(IOFile.HISTORY_PATH, historyList);
        } else {
            historyList = IOFile.getDataFormFile(IOFile.ALBUM_PATH);
        }
    }

    @Override
    public Integer findIndexById(Object id) {
        return 0;
    }

    @Override
    public void handleAdd() {
        User user = Login.user;
        //Hiển thị danh sách bài hát cho người dùng chọn
        System.out.println("==========ALL SONG==========");
        for (int i = 0; i < songList.size(); i++) {
            songList.get(i).displayData();
        }
        System.out.println("Nhập ID bài hát muốn mua : ");
        byte selectSongID = InputMethods.getByte();
        int selectSongIndex = 0;
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongId() == selectSongID) {
                selectSongIndex = i;
                break;
            }
        }

        System.out.println("Chọn gói bạn muốn mua : ");
        System.out.println("1. Gói 30 ngày ");
        System.out.println("2. Gói vĩnh viễn");
        System.out.println("Nhập lựa chọn của bạn : ");
        int selectTypePackage = InputMethods.getInteger();

        //Thêm lịch sửa mua hàng
        History newHistory = new History();
        newHistory.setHistoryId(newHistory.findIdMax());
        newHistory.setSongId(songList.get(selectSongID).getSongId());
        newHistory.setCreateAt(LocalDate.now());
        newHistory.setOrderAt(LocalDate.now());
        newHistory.setTypePackage(selectTypePackage);
        if (selectTypePackage == 1) {
            newHistory.setExpriedAt(LocalDate.now().minusDays(30));
            newHistory.setTotalPrice(songList.get(selectSongIndex).getPrice());
        } else {
            newHistory.setExpriedAt(null);
            newHistory.setTotalPrice(songList.get(selectSongIndex).getPrice() * 10);
        }
        newHistory.setUserId(user.getUserId());

        historyList.add(newHistory);
        //Logic trừ tiền người dùng


        // sau khi thêm mới lưu lại nó vào file
        IOFile.writeDataToFile(IOFile.HISTORY_PATH, historyList);
        System.out.println(Messages.BUY_SUCESS);
    }

    @Override
    public void handleShow() {

    }

    @Override
    public void handleEdit() {

    }

    @Override
    public void handleDelete() {

    }

    @Override
    public void handleFindByName() {

    }
}
