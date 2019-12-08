package BankingApp;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class EmployeeAccount {

    // holds a temporary EmployeeAccount for the current log in session
    private String userName;
    private String password;
    private String type; // T for teller M for manager
    private LocalDateTime logInTime;


    public EmployeeAccount(String userName){
        this.userName=userName;
        setLogInTime();
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

    public LocalDateTime getLogInTime() {
        return logInTime;
    }

    public void setLogInTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("login time is: "+now.toString());
        this.logInTime = now;
    }

    public long getTimeLoggedIn(){
        LocalDateTime logginTime = getLogInTime();
        LocalDateTime now = LocalDateTime.now();

        long secondsLoggedIn = ChronoUnit.SECONDS.between(logginTime,now);

        return secondsLoggedIn;
    }

    @Override
    public String toString() {
        return "EmployeeAccount{" +
                "userName='" + userName + '\'' +
                '}';
    }
}
