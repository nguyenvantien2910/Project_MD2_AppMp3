package business.utils;

import java.util.List;

public class Pagination {
    public static final int ELEMENT_PER_PAGE = 2;
    public static void paginateAndDisplay(List<?> dataList, int elementPerPage) {
        int numberOfPages = (int) Math.ceil((double) dataList.size() / elementPerPage);
        int page = 1;

        int startIndex;
        int endIndex;

        do {
            startIndex = (page - 1) * elementPerPage;
            endIndex = Math.min(startIndex + elementPerPage, dataList.size());

            for (int i = startIndex; i < endIndex; i++) {
                System.out.println(dataList.get(i));
                //dataList.get(i);
            }

            System.out.println();
            System.out.println("Trang: " + page + "/" + numberOfPages);

            if (page == 1 && page < numberOfPages) {
                System.out.println("2. Trang sau");
            } else if (page > 1 && page < numberOfPages) {
                System.out.println("1. Trang trước || 2. Trang sau");
            } else if (page > 1 && page == numberOfPages) {
                System.out.println("1. Trang trước");
            }

            System.out.println("3. Thoát");
            System.out.println("Mời nhập lựa chọn: ");

            int choice = InputMethods.getByte();
            switch (choice) {
                case 1:
                    if (page > 1) {
                        page--;
                    }
                    break;
                case 2:
                    if (page < numberOfPages) {
                        page++;
                    }
                    break;
                case 3:
                    return;
                default:
                    System.err.println(Messages.SELECT_INVALID);
            }
        } while (true);
    }
}
