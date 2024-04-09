package presentation;

import business.entity.User;
import org.mindrot.jbcrypt.BCrypt;
import business.utils.IOFile;

import java.io.FileNotFoundException;
import java.util.List;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {
        List<User> users = IOFile.readFromFile(IOFile.USER_PATH);
        User admin = new User();
        admin.setUserId(1);
        admin.setUsername("admin123");
        admin.setPassword(BCrypt.hashpw("admin123",BCrypt.gensalt(5)));
        admin.setRole(true);
        users.add(admin);
        IOFile.writeToFile(IOFile.USER_PATH,users);
    }
}
