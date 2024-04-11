package business.utils;

import business.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    //Khai báo các đường dẫn file
    public static final String USER_PATH = "src/business/data/users.txt";
    public static final String SONG_PATH = "src/business/data/songs.txt";
    public static final String SINGER_PATH = "src/business/data/singers.txt";
    public static final String ALBUM_PATH = "src/business/data/albums.txt";
    public static final String HISTORY_PATH = "src/business/data/histories.txt";
    public static final String LOGIN_USER_PATH = "src/business/data/loginUserInfo.txt";

    //Ghi data vào file
    public static <T> void writeDataToFile(String path, List<T> list) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    //Đọc data từ file
    public static <T> List<T> getDataFormFile(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        List<T> list = new ArrayList<>();
        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    // Lấy thông tin người dùng đang đăng nhập
    public static User getUserLogin() {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        User user = new User();
        try {
            fis = new FileInputStream(LOGIN_USER_PATH);
            ois = new ObjectInputStream(fis);
            user = (User) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }

    // Cập nhật lại thông tin của người đang dăng nhập
    public static void updateUserLogin(User user) {
        File newFile = new File(IOFile.LOGIN_USER_PATH);
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(LOGIN_USER_PATH);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}