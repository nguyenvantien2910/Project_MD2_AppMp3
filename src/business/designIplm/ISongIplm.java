package business.designIplm;

import business.design.ISongDesign;
import business.entity.Song;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static presentation.Main.*;
import static presentation.Main.albumList;

public class ISongIplm implements ISongDesign {
    private static byte choice;

    public void showTrendingSong() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            //Tìm top 10 bài hát có lượt nghe nhiều nhất
            List<Song> top10TrendingSongByPlayCount = songList.stream().sorted(Comparator.comparing(Song::getPlayCount).reversed()).limit(10).toList();
            System.out.println("==========TRENDING SONG==========");
            top10TrendingSongByPlayCount.forEach(Song::displayDataForUser);
        }
    }

    public void showAllSongForUser() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("==========ALL SONG==========");
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 10;
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
                        songList.get(i).displayDataForUser();
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

    public void bookmarkSongToFavoriteList() {
        //Hiển thị danh sách bài hát cho người dùng chọn
        System.out.println("==========ALL SONG==========");
        for (int i = 0; i < songList.size(); i++) {
            songList.get(i).displayDataForUser();
        }
        System.out.println("Nhập ID bài hát muốn thêm vào danh sách yêu thích : ");

        byte selectSongID = InputMethods.getByte();
        int selectSongIndex = 0;
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongId() == selectSongID) {
                selectSongIndex = i;
                break;
            }
        }

        List<Song> selectSongList = new ArrayList<>();
        selectSongList.add(songList.get(selectSongIndex));
        userLoginToUsed.setFavoriteSongs(selectSongList);
        //Gán lại giá trị bookmark của user đang login cho user có cùng ID trong file để đảm bảo thống nhất data
        for (int j = 0; j < userList.size(); j++) {
            if (userList.get(j).getUserId() == userLoginToUsed.getUserId()) {
                userList.get(j).setFavoriteSongs(userLoginToUsed.getFavoriteSongs());
                break;
            }
        }
        // sau khi thêm lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Messages.BOOKMARK_SUCESS);
    }


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
            IOFile.writeToFile(IOFile.SONG_PATH, songList);
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
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID bài hát muốn cập nhật thông tin :");
            int inputID = InputMethods.getInteger();
            for (int i = 0; i < songList.size(); i++) {
                if (songList.get(i).getSingerId() == inputID) {
                    boolean isExit = false;
                    do {
                        System.out.println("========= EDIT SONG INFORMATION =======");
                        System.out.println("1. Chỉnh sửa tên");
                        System.out.println("2. Chỉnh sửa ID ca sĩ");
                        System.out.println("3. Chỉnh sửa mô tả bài hát");
                        System.out.println("4. Chỉnh sửa source bài hát");
                        System.out.println("5. Chỉnh sửa giá bài hát");
                        System.out.println("6. Chỉnh sửa mã album");
                        System.out.println("7. Chỉnh sửa hình ảnh về bài hát");
                        System.out.println("8. Thoát");
                        System.out.println("Nhập lựa chọn của bạn : ");

                        choice = InputMethods.getByte();
                        switch (choice) {
                            case 1:
                                System.out.println("Nhập tên cho bài hát :");
                                String inputSongName = InputMethods.getString();
                                songList.get(i).setSongName(inputSongName);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;

                            case 2:
                                if (!singerList.isEmpty()) {
                                    System.out.println("Chọn ca sĩ cho bài hát:");
                                    for (int j = 0; j < singerList.size(); j++) {
                                        if (singerList.get(i).isStatus()) {
                                            System.out.printf("%d. %s\n", j + 1, singerList.get(i).getSingerName());
                                        }
                                    }
                                    System.out.print("Lựa chọn của bạn: ");
                                    byte choice = InputMethods.getByte();
                                    songList.get(i).setSingerId(singerList.get(choice - 1).getSingerId());
                                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                                    break;
                                } else {
                                    System.err.println(Messages.EMTY_LIST);
                                    break;
                                }

                            case 3:
                                System.out.println("Nhập mô tả cho bài hát : ");
                                String inputDescription = InputMethods.getString();
                                songList.get(i).setDescription(inputDescription);
                                System.out.println(Messages.UPDATE_STATUS_SUCESS);
                                break;

                            case 4:
                                System.out.println("Nhập source bài hát : ");
                                String inputSource = InputMethods.getString();
                                songList.get(i).setSource(inputSource);
                                System.out.println(Messages.UPDATE_STATUS_SUCESS);
                                break;

                            case 5:
                                System.out.println("Nhập giá bài hát : ");
                                double inputPrice = InputMethods.getDouble();
                                songList.get(i).setPrice(inputPrice);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;

                            case 6:
                                if (albumList.isEmpty()) {
                                    System.err.println(Messages.EMTY_LIST);
                                    songList.get(i).setAlbumId(null);
                                } else {
                                    System.out.println("Danh sách album");
                                    for (int j = 0; j < albumList.size(); j++) {
                                        System.out.printf("%d. %s\n", j + 1, albumList.get(i).getName());
                                    }
                                    System.out.print("Lựa chọn của bạn: ");
                                    choice = InputMethods.getByte();
                                    songList.get(i).setAlbumId(albumList.get(choice - 1).getId());
                                    System.out.println(Messages.UPDATE_INFO_SUCESS);
                                }
                                break;

                            case 7:
                                System.out.println("Nhập nguồn hình ảnh cho bài hát : ");
                                String inputImage = InputMethods.getString();
                                songList.get(i).setImage(inputImage);
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;

                            case 8:
                                isExit = true;
                                break;
                        }
                    } while (!isExit);
                } else {
                    System.err.println(Messages.ID_NOT_FOUND);
                }
            }
            // sau khi edit lưu lại nó vào file
            try {
                IOFile.writeToFile(IOFile.SONG_PATH, songList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void handleDelete() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập ID bài hát muốn thực hiện xóa :");
            int inputID = InputMethods.getInteger();
            boolean isExits = false;
            for (int i = 0; i < songList.size(); i++) {
                if (songList.get(i).getSongId() == inputID) {
                    isExits = true;
                    songList.remove(i);
                    // sau khi delete lưu lại nó vào file
                    try {
                        IOFile.writeToFile(IOFile.SONG_PATH, songList);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Messages.DELETE_SUCESS);
                    break;
                }
            }
            if (!isExits) {
                System.err.println(Messages.ID_NOT_FOUND);
            }
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
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                songListFilterBySearchKey.forEach(Song::displayData);
            }
        }
    }
}
