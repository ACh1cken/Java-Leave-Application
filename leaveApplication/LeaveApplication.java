
package leaveApplication;

public class LeaveApplication
{
    private String id;
    private String proposerId;
    private String reason;
    private String fromDate;
    private String toDate;
    private String createTime;
    private ApplicationStatus status;
    private boolean repeal;
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public String getProposerId() {
        return this.proposerId;
    }
    
    public void setProposerId(final String proposerId) {
        this.proposerId = proposerId;
    }
    
    public String getReason() {
        return this.reason;
    }
    
    public void setReason(final String reason) {
        this.reason = reason;
    }
    
    public String getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(final String fromDate) {
        this.fromDate = fromDate;
    }
    
    public String getToDate() {
        return this.toDate;
    }
    
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }
    
    public String getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(final String createTime) {
        this.createTime = createTime;
    }
    
    public ApplicationStatus getStatus() {
        return this.status;
    }
    
    public void setStatus(final ApplicationStatus status) {
        this.status = status;
    }
    
    public boolean isRepeal() {
        return this.repeal;
    }
    
    public void setRepeal(final boolean repeal) {
        this.repeal = repeal;
    }
}
