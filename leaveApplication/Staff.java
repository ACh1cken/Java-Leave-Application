
package leaveApplication;

import java.util.Date;

public class Staff
{
    private String id;
    private String username;
    private String password;
    private String realname;
    private String teleNo;
    private Date birthday;
    private String supervisorId;
    private Role role;
    private boolean deleted;
    
    public Staff() {
        this.deleted = false;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getRealname() {
        return this.realname;
    }
    
    public void setRealname(final String realname) {
        this.realname = realname;
    }
    
    public String getTeleNo() {
        return this.teleNo;
    }
    
    public void setTeleNo(final String teleNo) {
        this.teleNo = teleNo;
    }
    
    public Date getBirthday() {
        return this.birthday;
    }
    
    public void setBirthday(final Date birthday) {
        this.birthday = birthday;
    }
    
    public String getsupervisorId() {
        return this.supervisorId;
    }
    
    public void setsupervisorId(final String supervisorId) {
        this.supervisorId = supervisorId;
    }
    
    public Role getRole() {
        return this.role;
    }
    
    public void setRole(final Role role) {
        this.role = role;
    }
    
    public boolean isDeleted() {
        return this.deleted;
    }
    
    public void setDeleted(final boolean deleted) {
        this.deleted = deleted;
    }
}
