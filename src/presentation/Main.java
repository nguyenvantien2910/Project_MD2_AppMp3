package presentation;

import business.designIplm.*;
import business.entity.*;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    //Khai báo biến dùng chung
    public static List<User> userList;
    public static List<Song> songList;
    public static List<Singer> singerList;
    public static List<Album> albumList;
    public static List<History> historyList;
    public static User userLoginToUsed;

    public static ISongIplm iSongIplm = new ISongIplm();
    public static ISingerIplm iSingerIplm = new ISingerIplm();
    public static IUserIplm iUserIplm = new IUserIplm();
    public static IAlbumIplm iAlbumIplm = new IAlbumIplm();
    public static IHistoryListIplm iHistoryIplm = new IHistoryListIplm();

    static {
        File userFile = new File(IOFile.USER_PATH);
        File songFile = new File(IOFile.SONG_PATH);
        File singerFile = new File(IOFile.SINGER_PATH);
        File historyFile = new File(IOFile.HISTORY_PATH);
        File albumFile = new File(IOFile.ALBUM_PATH);
        File loginUserFile = new File(IOFile.LOGIN_USER_PATH);

        if (userFile.length() == 0) {
            userList = new ArrayList<>();
            try {
                IOFile.writeToFile(IOFile.USER_PATH, userList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            userList = IOFile.readFromFile(IOFile.USER_PATH);
        }

        if (songFile.length() == 0) {
            songList = new ArrayList<>();
            try {
                IOFile.writeToFile(IOFile.SONG_PATH, songList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            songList = IOFile.readFromFile(IOFile.SONG_PATH);
        }

        if (singerFile.length() == 0) {
            singerList = new ArrayList<>();
            try {
                IOFile.writeToFile(IOFile.SINGER_PATH, singerList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            singerList = IOFile.readFromFile(IOFile.SINGER_PATH);
        }

        if (albumFile.length() == 0) {
            albumList = new ArrayList<>();
            try {
                IOFile.writeToFile(IOFile.ALBUM_PATH, albumList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            albumList = IOFile.readFromFile(IOFile.ALBUM_PATH);
        }

        if (historyFile.length() == 0) {
            historyList = new ArrayList<>();
            try {
                IOFile.writeToFile(IOFile.HISTORY_PATH, historyList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            historyList = IOFile.readFromFile(IOFile.HISTORY_PATH);
        }

    }

    public static void main(String[] args) {
        IAuthenticationIplm iAuthenticationIplm = new IAuthenticationIplm();
        do {
            System.out.println("===============WELCOME TO SPOTIFY =============");
            System.out.println("1. Đăng nhập");
            System.out.println("2. Đăng kí");
            System.out.println("3. Thoát chương trình");

            System.out.println("Nhập lựa chọn của bạn : ");

            byte choice = InputMethods.getByte();

            switch (choice) {
                case 1:
                    iAuthenticationIplm.login();
                    break;
                case 2:
                    iAuthenticationIplm.register();
                    break;
                case 3:
                    System.out.println(Messages.EXIT_SUCESS);
                    System.exit(0);
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (true);
    }
}
