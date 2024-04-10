package presentation;

import business.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import business.utils.IOFile;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        List<User> users = IOFile.readFromFile(IOFile.USER_PATH);
        User admin = new User();
        admin.setUserId(1);
        admin.setUsername("admin123");
        admin.setPassword(BCrypt.hashpw("admin123",BCrypt.gensalt(5)));
        admin.setEmail("nguyenvantien2910@gmail.com");
        admin.setStatus(true);
        admin.setFullName("Nguyen Van Tien");
        admin.setRole(true);
        admin.setAvatar("【IP】売上 hoặc 【IP】見積.");
        admin.setPhone("0987654321");
        admin.setAccountType(2);
        admin.setCreateAt(LocalDate.now());
        admin.setUpdateAt(LocalDate.now());
        admin.setWallet(999999.0);
        users.add(admin);
        IOFile.writeToFile(IOFile.USER_PATH,users);
    }
}
