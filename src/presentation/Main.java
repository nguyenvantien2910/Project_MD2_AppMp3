package presentation;

import business.designIplm.*;
import business.entity.Album;
import business.entity.Singer;
import business.entity.Song;
import business.entity.User;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;
import java.util.List;

public class Main {
    //Khai báo biến dùng chung
    public static List<User> userList;
    public static List<Song> songList;
    public static List<Singer> singerList;
    public static List<Album> albumList;
    public static ISongIplm iSongIplm = new ISongIplm();
    public static ISingerIplm iSingerIplm = new ISingerIplm();
    public static IUserIplm iUserIplm = new IUserIplm();
    public static IAlbumIplm iAlbumIplm = new IAlbumIplm();

    static {
        userList = IOFile.readFromFile(IOFile.USER_PATH);
        songList = IOFile.readFromFile(IOFile.SONG_PATH);
        singerList = IOFile.readFromFile(IOFile.SINGER_PATH);
        albumList = IOFile.readFromFile(IOFile.ALBUM_PATH);
    }

    public static void main(String[] args) {
        IAuthenticationIplm iAuthenticationIplm = new IAuthenticationIplm();
        boolean isExit = false;

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
                    isExit = true;
                    System.out.println(Messages.EXIT_SUCESS);
                    break;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
            if (choice == 3) {
                break;
            }
        }while (!isExit);
    }
}
