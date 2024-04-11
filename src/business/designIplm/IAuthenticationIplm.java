package business.designIplm;

import business.design.IAuthentication;
import business.entity.User;
import business.utils.IOFile;
import org.mindrot.jbcrypt.BCrypt;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class IAuthenticationIplm implements IAuthentication {
    public static List<User> userList;
    static {
        File userFile = new File(IOFile.USER_PATH);
        if (userFile.length() == 0) {
            userList = new ArrayList<>();
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
            userList.add(admin);
            IOFile.writeDataToFile(IOFile.USER_PATH, userList);
        } else {
            userList = IOFile.getDataFormFile(IOFile.USER_PATH);
            if (userList.stream().noneMatch(user -> user.getUsername().equals("Admin"))){
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
                userList.add(admin);
                IOFile.writeDataToFile(IOFile.USER_PATH, userList);
            }
        }
    }


    private User getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }


    @Override
    public User login(String username, String password) {
        User userLogin = getUserFromUsername(username);
        if (userLogin == null) {
            return null;
        }
        boolean checkLogin = BCrypt.checkpw(password, userLogin.getPassword());
        if (checkLogin) {
            return userLogin;
        }
        return null;
    }

    @Override
    public void register(User user) {
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
        userList.add(user);
        IOFile.writeDataToFile(IOFile.USER_PATH, userList);
    }
}
