package model;

import java.util.Date;

public class Member {
    private int memberId;
    private String surname;
    private String firstName;
    private String middleName;
    private String email;
    private String phone;
    private String password;
    private Date joinDate;
    private String status;

    public Member(int memberId, String surname, String firstName, String middleName, String email,
                 String phone, String password, Date joinDate, String status) {
        this.memberId = memberId;
        this.surname = surname;
        this.firstName = firstName;
        this.middleName = middleName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.joinDate = joinDate;
        this.status = status;
    }

    // Getters and setters
    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) { this.middleName = middleName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public Date getJoinDate() { return joinDate; }
    public void setJoinDate(Date joinDate) { this.joinDate = joinDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

