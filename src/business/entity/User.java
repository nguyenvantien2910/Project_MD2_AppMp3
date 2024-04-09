package business.entity;

import business.utils.Messages;
import org.mindrot.jbcrypt.BCrypt;
import business.utils.InputMethods;
import java.io.Serializable;
import java.time.LocalDateTime;

public class User implements Serializable {
    private int userId;
    private String username;
    private String email;
    private String fullName;
    private boolean status;
    private String password;
    private boolean role;
    private String avatar;
    private String phone;
    private Integer accountType;
    private String createAt;
    private String updateAt;
    private double wallet;

    public User() {
        this.status = true;
    }

    public User(int userId, String username, String email, String fullName, String password, boolean role, String avatar, String phone, Integer accountType, String createAt, String updateAt, double wallet) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.status = true;
        this.password = password;
        this.role = role;
        this.avatar = avatar;
        this.phone = phone;
        this.accountType = accountType;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.wallet = wallet;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRole() {
        return role;
    }

    public void setRole(boolean role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    //inputData()
    public void inputData(boolean isAdminAdd) {
        this.username = inputUserName();
        this.fullName = inputFullName();
        this.email = inputEmail();
        do {
            System.out.println("Nhập mật khẩu : ");
            inputPassword();
            System.out.println("Nhập mật khẩu xác nhận: ");
            String inputConfirmPassword = InputMethods.getString();
            if (BCrypt.checkpw(inputConfirmPassword,this.password)) {
                break;
            } else {
                System.err.println(Messages.CONFIRM_PASSWORD_ERROR);
            }
        } while (true);
        this.phone = inputPhone();
        this.avatar = inputAvatar();
        if (isAdminAdd) {
            this.status = inputStatus();
            this.accountType = inputAccountType();
        } else {
            this.status = true;
            this.accountType = 1;
        }
        setCreateAt(String.valueOf(LocalDateTime.now()));
        setUpdateAt(null);
        setWallet(0.0);
    }

    private Integer inputAccountType() {
        System.out.println("Nhập loại account cho người dùng(mặc định là 1):");
        Integer inputAccountType = InputMethods.getInteger();
        if (inputAccountType.describeConstable().isEmpty()) {
            System.err.println(Messages.EMTY_ERROR);
        } else {
            return inputAccountType;
        }
        return 1;
    }

    private boolean inputStatus() {
        System.out.println("Nhập role cho người dùng (true/false) : ");
        return InputMethods.getBoolean();
    }

    public String inputUserName() {
        System.out.println("Nhập tên đăng nhập : ");
        return InputMethods.getString();
    }

    public String inputEmail() {
        final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        System.out.println("Nhập email : ");
        while (true) {
            String inputEmail = InputMethods.getString();
            if (inputEmail.matches(REGEX_EMAIL)) {
                return InputMethods.getString();
            } else {
                System.err.println(Messages.EMAIL_FORMAT_ERROR);
            }
        }
    }

    public String inputFullName() {
        System.out.println("Nhập họ và tên : ");
        String inputFullName = InputMethods.getString();
        while (true) {
            if (inputFullName.isBlank()) {
                System.err.println(Messages.EMTY_ERROR);
            } else {
                return InputMethods.getString();
            }
        }
    }

    public void inputPassword() {
        this.password = BCrypt.hashpw(InputMethods.getString(),BCrypt.gensalt(5));
    }

    public String inputPhone() {
        final String REGEX_PHONE_VN = "^\\+?(?:0|84)(?:3[2-9]|5[6-9]|7[0|6-9]|8[1-9]|9[0-9])\\d{7}$";
        while (true) {
            System.out.println("Nhập số điện thoại: ");
            String inputPhoneNum = InputMethods.getString();
            if (inputPhoneNum.matches(REGEX_PHONE_VN)) {
                return inputPhoneNum;
            } else {
                System.err.println(Messages.PHONE_FORMAT_ERROR);
            }
        }
    }

    public String inputAvatar() {
        System.out.println("Nhập ảnh đại diện: ");
        return InputMethods.getString();
    }

    //displayData()
    public String displayData() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", status=" + (status ? "Active" : "Block") +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                ", phone='" + phone + '\'' +
                ", accountType=" + accountType +
                ", createAt='" + createAt + '\'' +
                ", updateAt='" + updateAt + '\'' +
                ", wallet=" + wallet +
                '}';
    }
}
