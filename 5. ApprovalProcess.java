public class ApprovalProcess {
	private String id;
	private String applicationId;
	private String supervisorId;
	private Status status;
	private String createTime;
	private String mark;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getSupervisorId() {
		return supervisorId;
	}
	public void setsupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}}
