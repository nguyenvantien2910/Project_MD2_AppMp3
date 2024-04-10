package business.designIplm;

import business.design.ISongDesign;
import business.entity.Song;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

import static presentation.Main.songList;

public class ISongIplm implements ISongDesign {
    private static byte choice;


    @Override
    public Integer findIndexByName(String name) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongName().toLowerCase().contains(name)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng bài hát muốn thêm :");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            Song song = new Song();
            System.out.printf("Nhập thông tin cho bài hát thứ %d \n", i + 1);
            song.inputData();
            songList.add(song);
        }
        // sau khi add lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH,songList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.ADD_NEW_SUCESS);
    }

    @Override
    public void handleShow() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("==========ALL SONG==========");
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 3;
            int page = 1;
            int numberOfPage;
            if (songList.size() % elementPerPage == 0) {
                numberOfPage = songList.size() / elementPerPage;
            } else {
                numberOfPage = songList.size() / elementPerPage + 1;
            }
            do {
                for (int i = 0; i < songList.size(); i++) {
                    if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                        songList.get(i).displayData();
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
            IOFile.writeToFile(IOFile.SONG_PATH,songList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleDelete() {
// sau khi delete lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SONG_PATH,songList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    

    @Override
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
                System.out.printf("Danh sách tìm kiếm theo từ khòa &s là :\n", inputSearchName);
                songListFilterBySearchKey.forEach(Song::displayData);
            }
        }
    }
}
