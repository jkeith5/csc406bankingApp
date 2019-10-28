package BankingApp;

public class EmployeeAccount {

    private String userName;
    private String password;


    public EmployeeAccount(String userName){
        this.userName=userName;
    }

    public EmployeeAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EmployeeAccount{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
