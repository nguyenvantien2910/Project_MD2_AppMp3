package business.designIplm;

import business.design.ISongDesign;
import business.entity.Song;
import business.utils.IOFile;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

public class ISongIplm implements ISongDesign {
    public static List<Song> songList;

    public static List<Song> getSongList() {
        if (songList == null) {
            songList = IOFile.readFromFile(IOFile.USER_PATH);
        }
        return songList;
    }
    private static byte choice;
    @Override
    public Integer findIndexById(Song id) {
        return 0;
    }

    @Override
    public void handleAdd() {

        // sau khi add lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH,songList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleShow() {
        if (songList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("==========ALL SONG==========");
            songList.forEach(Song::displayData);
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
    public void searchByName() {

    }
}
