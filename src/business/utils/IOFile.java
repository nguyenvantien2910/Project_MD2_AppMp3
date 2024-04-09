package business.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IOFile {
    //Khai báo các đường dẫn file
    public static final String USER_PATH = "src/business/data/users.txt";
    public static final String SONG_PATH = "src/business/data/songs.txt";
    public static final String SINGER_PATH = "src/business/data/singers.txt";
    public static final String ALBUM_PATH = "src/business/data/albums.txt";


    //Ghi file
    public static <T> void writeToFile(String path, List<T> list) throws FileNotFoundException {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {

            fos = new FileOutputStream(path, true);
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


    //Đọc file
    public static <T> List<T> readFromFile(String path) {
        List<T> list = new ArrayList<>();
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(path);
            ois = new ObjectInputStream(fis);
            list = (List<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EOFException e) {

        } catch (IOException e) {
            e.printStackTrace();
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
}
