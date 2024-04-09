package business.designIplm;

import business.design.ISingerDesign;
import business.entity.Singer;
import business.utils.IOFile;
import business.utils.InputMethods;
import business.utils.Messages;

import java.io.FileNotFoundException;
import java.util.List;

public class ISingerIplm implements ISingerDesign {
    public static List<Singer> singerList;

    public static List<Singer> getSingerList() {
        if (singerList == null) {
            singerList = IOFile.readFromFile(IOFile.USER_PATH);
        }
        return singerList;
    }
    private static byte choice;

    @Override
    public void searchSingerByName() {
        if (singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên ca sĩ muốn tìm kiếm : ");
            String inputSearchName = InputMethods.getString().toLowerCase();

            List<Singer> singerListFilterBySearchKey = singerList.stream().filter(singer -> singer.getSingerName().toLowerCase().contains(inputSearchName)).toList();
            if (singerListFilterBySearchKey.isEmpty()) {
                System.err.println(Messages.NAME_NOT_FOUND);
            } else {
                System.out.printf("Danh sách tìm kiếm theo từ khòa &s là :\n", inputSearchName);
                singerListFilterBySearchKey.forEach(Singer::displayData);
            }
        }
    }

    @Override
    public Integer findIndexById(Singer id) {
        return 0;
    }

    @Override
    public void handleAdd() {
        System.out.println("Nhập số lượng ca sĩ muốn thêm :");
        byte addNum = InputMethods.getByte();

        for (int i = 0; i < addNum; i++) {
            Singer singer = new Singer();
            System.out.printf("Nhập thông tin cho ca sĩ thứ %d \n", i + 1);
            singer.inputData();
            singerList.add(singer);
            try {
                IOFile.writeToFile(IOFile.SINGER_PATH, singerList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Messages.ADD_NEW_SUCESS);
        }
        // sau khi add lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH,singerList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleShow() {
        if (singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("========== SINGER LIST ==========");
            singerList.forEach(Singer::displayData);
        }
    }

    @Override
    public void handleEdit() {
        if (singerList.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            System.out.println("Nhập tên ca sĩ muốn cập nhật thông tin :");
        }
        // sau khi edit lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH,singerList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleDelete() {
        // sau khi delete lưu lại nó vào file
        try {
            IOFile.writeToFile(IOFile.SINGER_PATH,singerList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
