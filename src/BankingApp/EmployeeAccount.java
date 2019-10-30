package BankingApp;

public class EmployeeAccount {

    private String userName;
    private String password;
    private String type; // T for teller M for manager


    public EmployeeAccount(String userName){
        this.userName=userName;
    }

    public EmployeeAccount(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public EmployeeAccount(String userName, String password,String type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
