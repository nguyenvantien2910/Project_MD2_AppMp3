package business.designIplm;

import business.design.IGenericDesign;
import business.entity.Song;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import business.utils.Pagination;
import presentation.Login;
import presentation.admin.AdminMenu;
import presentation.user.UserMenu;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static business.designIplm.IAlbumIplm.albumList;
import static business.designIplm.ISingerIplm.singerList;

public class ISongIplm implements IGenericDesign {
    private static byte choice;
    private boolean isExit = false;
    public static List<Song> songList;

    static {
        File songFile = new File(IOFile.SONG_PATH);
        if (songFile.length() == 0) {
            songList = new ArrayList<>();
            IOFile.writeDataToFile(IOFile.SONG_PATH, songList);
        } else {
            songList = IOFile.getDataFormFile(IOFile.SONG_PATH);
        }
    }

    public void showTrendingSong() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            UserMenu.displayHomePageMenu();
        } else {
            //Tìm top 10 bài hát có lượt nghe nhiều nhất
            List<Song> top10TrendingSongByPlayCount = songList.stream().sorted(Comparator.comparing(Song::getPlayCount).reversed()).limit(10).toList();
            System.out.println("==========TRENDING SONG==========");
            top10TrendingSongByPlayCount.forEach(Song::displayData);
        }
    }

    public void showAllSongForUser() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            UserMenu.displayHomePageMenu();
        } else {
            System.out.println("==========ALL SONG==========");
            Pagination.paginateAndDisplay(songList, Pagination.ELEMENT_PER_PAGE);
        }
    }

    public void bookmarkSongToFavoriteList() {
        //Hiển thị danh sách bài hát cho người dùng chọn
        System.out.println("==========ALL SONG==========");
        for (int i = 0; i < songList.size(); i++) {
            songList.get(i).displayData();
        }
        System.out.println("Nhập ID bài hát muốn thêm vào danh sách yêu thích : ");

        byte selectSongID = InputMethods.getByte();
        int selectSongIndex = findIndexById(selectSongID);

//        List<Song> selectSongList = new ArrayList<>();
//        selectSongList.add(songList.get(selectSongIndex));
//        .setFavoriteSongs(selectSongList);
//        //Gán lại giá trị bookmark của user đang login cho user có cùng ID trong file để đảm bảo thống nhất data
//        for (int j = 0; j < userList.size(); j++) {
//            if (userList.get(j).getUserId() == userLoginToUsed.getUserId()) {
//                userList.get(j).setFavoriteSongs(userLoginToUsed.getFavoriteSongs());
//                break;
//            }
//        }
//        // sau khi thêm lưu lại nó vào file
//
//        IOFile.writeToFile(IOFile.USER_PATH, userList);
//        System.out.println(Messages.BOOKMARK_SUCESS);
    }

    @Override
    public Integer findIndexById(Object id) {
        for (int i = 0; i < songList.size(); i++) {
            if (songList.get(i).getSongId() == id) {
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
            // sau khi add lưu lại nó vào file
            IOFile.writeDataToFile(IOFile.SONG_PATH, songList);
            System.out.println(Messages.ADD_NEW_SUCESS);
        }
    }

    @Override
    public void handleShow() {
        if (songList == null || songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddSong();
        } else {
            System.out.println("==========ALL SONG==========");
            Pagination.paginateAndDisplay(songList, 10);
        }
    }

    private void askAdminToAddSong() {
        if (Login.user.isRole()){
            System.out.println("Bạn có muốn thêm mới bài hát không ?");
            System.out.println("1. Có ");
            System.out.println("2. Không ");
            System.out.println("Nhập lựa chọn của bạn : ");
            choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    handleAdd();
                    break;
                    case 2:
                        AdminMenu.songManagement();
                        break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        }
    }

    @Override
    public void handleEdit() {
        if (songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddSong();
        } else {
            System.out.println("Nhập ID bài hát muốn cập nhật thông tin :");
            int inputID = InputMethods.getInteger();
            int editSongIndex = findIndexById(inputID);
            if (editSongIndex != -1) {
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
                            System.out.println("Nhập tên mới cho bài hát :");
                            String inputSongName = InputMethods.getString();
                            songList.get(editSongIndex).setSongName(inputSongName);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;

                        case 2:
                            if (!singerList.isEmpty()) {
                                System.out.println("Chọn ca sĩ cho bài hát:");
                                for (int j = 0; j < singerList.size(); j++) {
                                    if (singerList.get(editSongIndex).isStatus()) {
                                        System.out.printf("%d. %s\n", j + 1, singerList.get(editSongIndex).getSingerName());
                                    }
                                }
                                System.out.print("Lựa chọn của bạn: ");
                                choice = InputMethods.getByte();
                                songList.get(editSongIndex).setSingerId(singerList.get(choice - 1).getSingerId());
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                                break;
                            } else {
                                System.err.println(Messages.EMTY_LIST);
                                break;
                            }

                        case 3:
                            System.out.println("Nhập mô tả cho bài hát : ");
                            String inputDescription = InputMethods.getString();
                            songList.get(editSongIndex).setDescription(inputDescription);
                            System.out.println(Messages.UPDATE_STATUS_SUCESS);
                            break;

                        case 4:
                            System.out.println("Nhập source bài hát : ");
                            String inputSource = InputMethods.getString();
                            songList.get(editSongIndex).setSource(inputSource);
                            System.out.println(Messages.UPDATE_STATUS_SUCESS);
                            break;

                        case 5:
                            System.out.println("Nhập giá bài hát : ");
                            double inputPrice = InputMethods.getDouble();
                            songList.get(editSongIndex).setPrice(inputPrice);
                            System.out.println(Messages.UPDATE_INFO_SUCESS);
                            break;

                        case 6:
                            if (albumList.isEmpty()) {
                                System.err.println(Messages.EMTY_LIST);
                                songList.get(editSongIndex).setAlbumId(null);
                            } else {
                                System.out.println("Danh sách album");
                                for (int j = 0; j < albumList.size(); j++) {
                                    System.out.printf("%d. %s\n", j + 1, albumList.get(editSongIndex).getName());
                                }
                                System.out.print("Lựa chọn của bạn: ");
                                choice = InputMethods.getByte();
                                songList.get(editSongIndex).setAlbumId(albumList.get(choice - 1).getId());
                                System.out.println(Messages.UPDATE_INFO_SUCESS);
                            }
                            break;

                        case 7:
                            System.out.println("Nhập nguồn hình ảnh cho bài hát : ");
                            String inputImage = InputMethods.getString();
                            songList.get(editSongIndex).setImage(inputImage);
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
        IOFile.writeDataToFile(IOFile.SONG_PATH, songList);
    }

    @Override
    public void handleDelete() {
        if (songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddSong();
        } else {
            System.out.println("Nhập ID bài hát muốn thực hiện xóa :");
            int inputID = InputMethods.getInteger();
            int deleteIndex = findIndexById(inputID);
            if (deleteIndex == -1) {
                System.err.println(Messages.ID_NOT_FOUND);
            } else {
                songList.remove(deleteIndex);
                IOFile.writeDataToFile(IOFile.SONG_PATH, songList);
            }
        }
    }

    @Override
    public void handleFindByName() {
        if (songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
            askAdminToAddSong();
        } else {
            System.out.println("Nhập tên bài hát muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString();

            List<Song> songListFilterBySearchKey = songList.stream().filter(song -> song.getSongName().toLowerCase().contains(inputSearchName.toLowerCase())).toList();
            if (songListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa %s là :\n", inputSearchName);
                songListFilterBySearchKey.forEach(Song::displayData);
                System.out.println();
            }
        }
    }
}
