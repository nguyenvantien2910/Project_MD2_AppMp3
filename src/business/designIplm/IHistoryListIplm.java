package business.designIplm;

import business.entity.History;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.time.LocalDate;

import static presentation.Main.*;

public class IHistoryListIplm {

    public void addNewHistory() {
        //Hiển thị danh sách bài hát cho người dùng chọn
        System.out.println("==========ALL SONG==========");
        for (int i = 0; i < songList.size(); i++) {
            songList.get(i).displayDataForUser();
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
        newHistory.setUserId(userLoginToUsed.getUserId());

        historyList.add(newHistory);

        //Logic trừ tiền người dùng


        // sau khi thêm mới lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.HISTORY_PATH, historyList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.BUY_SUCESS);
    }
}
