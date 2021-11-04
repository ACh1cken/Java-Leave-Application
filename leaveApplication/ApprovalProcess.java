
package leaveApplication;

public class ApprovalProcess
{
    private String id;
    private String applicationId;
    private String supervisorId;
    private Status status;
    private String createTime;
    private String mark;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getApplicationId() {
        return this.applicationId;
    }
    
    public void setApplicationId(final String applicationId) {
        this.applicationId = applicationId;
    }
    
    public Status getStatus() {
        return this.status;
    }
    
    public void setStatus(final Status status) {
        this.status = status;
    }
    
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final String createTime) {
        this.createTime = createTime;
    }
    
    public String getMark() {
        return this.mark;
    }
    
    public void setMark(final String mark) {
        this.mark = mark;
    }
    
    public String getSupervisorId() {
        return this.supervisorId;
    }
    
    public void setsupervisorId(final String supervisorId) {
        this.supervisorId = supervisorId;
    }
}
