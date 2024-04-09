package business.designIplm;

import business.design.IAuthentication;
import business.entity.User;
import business.utils.IOFile;
import org.mindrot.jbcrypt.BCrypt;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.List;

public class IAuthenticationIplm implements IAuthentication {
    //Khai báo biến dùng chung
    private static List<User> userList;

    static {
        userList = IOFile.readFromFile(IOFile.USER_PATH);
    }

    //Tim kiếm user theo tên
    private User getUserFromUsername(String username) {
        return userList.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    //Id tự tăng
    private int getNewId() {
        int IdMax = 0;
        if (userList != null) {
            IdMax = userList.stream()
                    .map(User::getUserId)
                    .max(Comparator.naturalOrder())
                    .orElse(0);
            return IdMax + 1;
        } else {
            return 1;
        }
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
        user.setUserId(getNewId());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(5)));
        userList.add(user);
        try {
            IOFile.writeToFile(IOFile.USER_PATH, userList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
