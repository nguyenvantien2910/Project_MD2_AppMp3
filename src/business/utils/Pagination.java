package business.utils;

import java.util.List;

public class Pagination {
    private byte choice;
    public static <E> void paginate(List<E> list) {
        if (list == null || list.isEmpty()) {
            System.err.println(Messages.EMTY_LIST);
        } else {
            int firstIndexOfPage = 0;
            int lastIndexOfPage = 2;
            int elementPerPage = 10;
            int page = 1;
            int numberOfPage;
            if (list.size() % elementPerPage == 0) {
                numberOfPage = list.size() / elementPerPage;
            } else {
                numberOfPage = list.size() / elementPerPage + 1;
            }
            do {
                for (int i = 0; i < list.size(); i++) {
                    if (i >= firstIndexOfPage && i <= lastIndexOfPage) {
                        list.get(i).displayDataForUser();
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
}

