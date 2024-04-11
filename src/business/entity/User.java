package business.entity;

import business.utils.Messages;
import business.utils.InputMethods;
import presentation.Login;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static business.designIplm.IAuthenticationIplm.userList;

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
    private LocalDate createAt;
    private LocalDate updateAt;
    private double wallet;
    private List<Song> favoriteSongs;

    public User() {
        this.status = true;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.wallet = 0;
        this.favoriteSongs = new ArrayList<>();
    }

    public User(int userId, String username, String email, String fullName, boolean status, String password, boolean role, String avatar, String phone, Integer accountType, LocalDate createAt, LocalDate updateAt, double wallet) {
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
        this.favoriteSongs = new ArrayList<>();
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

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public LocalDate getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDate updateAt) {
        this.updateAt = updateAt;
    }

    public double getWallet() {
        return wallet;
    }

    public void setWallet(double wallet) {
        this.wallet = wallet;
    }

    public List<Song> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<Song> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }

    //inputData()
    public void inputData() {
        this.userId = findMaxId();
        this.username = inputUserName();
        this.fullName = inputFullName();
        this.email = inputEmail();
        this.password = inputPassword();
        this.phone = inputPhone();
        this.avatar = inputAvatar();
        this.status = true;
        this.accountType = 1;
        this.createAt = LocalDate.now();
        this.updateAt = LocalDate.now();
        this.wallet = 100.0f;
        this.favoriteSongs = new ArrayList<>();
    }

    private String inputPassword() {
        do {
            System.out.println("Nhập mật khẩu : ");
            String inputPassword = InputMethods.getString();
            System.out.println("Nhập mật khẩu xác nhận: ");
            String inputConfirmPassword = InputMethods.getString();
            if (inputConfirmPassword.equals(inputPassword)) {
                return inputPassword;
            } else {
                System.err.println(Messages.CONFIRM_PASSWORD_ERROR);
            }
        } while (true);
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
        while (true) {
            System.out.println("Nhập tên đăng nhập : ");
            String inputUserName = InputMethods.getString();
            if (userList.stream().anyMatch(user -> user.getUsername().equals(inputUserName))) {
                System.err.println(Messages.IS_EXITS_ERROR);
            } else {
                return InputMethods.getString();
            }
        }
    }

    public String inputEmail() {
        final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        while (true) {
            System.out.println("Nhập email : ");
            String inputEmail = InputMethods.getString();
            if (inputEmail.matches(REGEX_EMAIL)) {
                if (userList.stream().noneMatch(user -> user.getEmail().equals(email))) {
                    return InputMethods.getString();
                } else {
                    System.err.println(Messages.IS_EXITS_ERROR);
                }
            } else {
                System.err.println(Messages.EMAIL_FORMAT_ERROR);
            }
        }
    }

    public String inputFullName() {
        while (true) {
            System.out.println("Nhập họ và tên : ");
            String inputFullName = InputMethods.getString();
            if (inputFullName.isBlank()) {
                System.err.println(Messages.EMTY_ERROR);
            } else {
                return InputMethods.getString();
            }
        }
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

    private int findMaxId() {
        if (userList == null || userList.isEmpty()) {
            return 1;
        } else {
            int max = userList.stream().map(User::getUserId).max(Comparator.naturalOrder()).orElse(0);
            return max + 1;
        }
    }

    public void displayData() {
        if (!Login.user.isRole()) {
            System.out.printf("| UserName : %-20s | Email : %-25s | FullName : %-25s | Avatar : %-30s | Phone : %-9s | AccountType : %-15s | Wallet : %-12.2f\n",
                    this.username, this.email, this.fullName, this.avatar, this.phone, (this.accountType == 1) ? "Tài khoản thường" : "Premium", this.wallet);
        } else {
            System.out.printf("| ID : %-3d | UserName : %-20s | Email : %-25s | FullName : %-25s | Status : %-10s | Password : %-25s | Role : %-12s | Avatar : %-30s | Phone : %-9s | AccountType : %-15s | Wallet : %-12.2f | CreateAt : %-15s | UpdateAt : %-15s\n",
                    this.userId, this.username, this.email, this.fullName, this.status ? "Active" : "Inactive", this.password, this.role ? "Admin" : "User", this.avatar, this.phone, (this.accountType == 1) ? "Tài khoản thường" : "Premium",
                    this.wallet, this.createAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), this.updateAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        }
    }

    //displayData()
//    public void displayDataForUser() {
//        System.out.printf("| UserName : %-20s | Email : %-25s | FullName : %-25s | Avatar : %-30s | Phone : %-9s | AccountType : %-15s | Wallet : %-12.2f\n",
//                this.username, this.email, this.fullName, this.avatar, this.phone, (this.accountType == 1) ? "Tài khoản thường" : "Premium", this.wallet);
//    }
//
//    public void displayDataForAdmin() {
//        System.out.printf("| ID : %-3d | UserName : %-20s | Email : %-25s | FullName : %-25s | Status : %-10s | Password : %-25s | Role : %-12s | Avatar : %-30s | Phone : %-9s | AccountType : %-15s | Wallet : %-12.2f | CreateAt : %-15s | UpdateAt : %-15s\n",
//                this.userId, this.username, this.email, this.fullName, this.status ? "Active" : "Inactive", this.password, this.role ? "Admin" : "User", this.avatar, this.phone, (this.accountType == 1) ? "Tài khoản thường" : "Premium",
//                this.wallet, this.createAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), this.updateAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
//    }
}