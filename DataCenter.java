
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class DataCenter {
	private final static Map<String,Staff> idStaffMap = new HashMap<String,Staff>();
	private final static Map<String,Staff> usernameStaffMap = new HashMap<String,Staff>();
	private final static Map<String,List<Staff>> supervisorStaffMap =  new HashMap<String,List<Staff>>();
	
	private final static Map<String,LeaveApplication> idApplicationMap = new HashMap<String,LeaveApplication>();
	
	private final static Map<String,List<LeaveApplication>> proposerIdApplicationMap = new HashMap<String,List<LeaveApplication>>();
	
	private final static Map<String,ApprovalProcess> idProcessMap = new HashMap<String,ApprovalProcess>();

	private final static Map<String,List<ApprovalProcess>> applicationIdProcessMap = new HashMap<String,List<ApprovalProcess>>();

	private final static Map<String,List<ApprovalProcess>> supervisorIdProcessMap = new HashMap<String,List<ApprovalProcess>>();

	public static Staff getStaffById(String id){
		return idStaffMap.get(id);
	}
	public static Staff getStaffByUsername(String username){
		return usernameStaffMap.get(username);
	}
	public static List<Staff> getStaffBySupervisorId(String supervisorId){
		return supervisorStaffMap.get(supervisorId);
	}
	public static LeaveApplication getApplicationById(String id){
		return idApplicationMap.get(id);
	}
	public static List<LeaveApplication> getApplicationByProposerId(String proposerId){
		return proposerIdApplicationMap.get(proposerId);
	}
	public static ApprovalProcess getApprovalProcessById(String id){
		return idProcessMap.get(id);
	}
	public static List<ApprovalProcess> getApprovalProcessByApplicationId(String applicationId){
		return applicationIdProcessMap.get(applicationId);
	}
	public static List<ApprovalProcess> getApprovalProcessBySupervisorId(String supervisorId){
		return supervisorIdProcessMap.get(supervisorId);
	}
	static{
		Staff staff = null;	
		String directorId = null;
		String staff1Id = null;
		staff = new Staff();
		staff.setUsername("admin");
		staff.setPassword("admin");
		staff.setRealname("director");
		staff.setRole(Role.DIRECTOR);
		addStaff(staff);
		directorId = staff.getId();
		
		staff = new Staff();
		staff.setUsername("staff");
		staff.setPassword("staff");
		staff.setRealname("Oliver");
		staff.setRole(Role.STAFF);
		staff.setsupervisorId(directorId);
		addStaff(staff);
		staff1Id = staff.getId();}
	public static List<Staff> getAllStaff(){
		List<Staff> list =  new ArrayList<Staff>(idStaffMap.values());
		List<Staff> normalStaffList = new  ArrayList<Staff>();
		for(Staff staff:list){
			if(!staff.isDeleted()){
				normalStaffList.add(staff);
			}
		}
		return normalStaffList;} 
	
	public static boolean addStaff(Staff staff){
		if(null == staff ||StringUtil.isBlank(staff.getUsername()) || StringUtil.isBlank(staff.getPassword())){
			return false;
		}
		if(null == staff.getsupervisorId() && staff.getRole() != Role.DIRECTOR){
			return false;
		}
		staff.setId(IdGenerator.getGenerateId(IdType.ST.name()));
		if(idStaffMap.containsKey(staff.getId()) || usernameStaffMap.containsKey(staff.getUsername())){
			return false;
		}		
		
		boolean hasLoop = false;
		Staff supervisor = getStaffById(staff.getsupervisorId());
		Set<String> supervisorIds = new HashSet<String>();
		
		while(null != supervisor && null != supervisor.getsupervisorId()){
			if(supervisorIds.contains(supervisor.getId())){
				hasLoop = true;
				break;
			}
			supervisorIds.add(supervisor.getId());
			supervisor = getStaffById(supervisor.getsupervisorId());
		}
		if(hasLoop){
			return false;
		}
		
		idStaffMap.put(staff.getId(), staff);
		usernameStaffMap.put(staff.getUsername(), staff);
		List<Staff> staffs = supervisorStaffMap.get(staff.getsupervisorId());
		if(null == staffs){
			staffs = new ArrayList<Staff>();
			supervisorStaffMap.put(staff.getsupervisorId(), staffs);
		}
		staffs.add(staff);
		
		return true;
	}
	public static boolean deleteStaff(String id){
		if(StringUtil.isBlank(id)){
			return false;
		}	
		Staff staff = idStaffMap.get(id);
		if(null == staff || staff.isDeleted()){
			return false;
		}	
		List<Staff> subordinates = supervisorStaffMap.get(id);
		if(null != subordinates){
			for(Staff subordinate:subordinates){
				subordinate.setsupervisorId(staff.getsupervisorId());
			}}

		List<ApprovalProcess> processes = supervisorIdProcessMap.get(staff.getId());
		List<ApprovalProcess> supervisorProcesses = supervisorIdProcessMap.get(staff.getsupervisorId());
		if(null == supervisorProcesses ){
			supervisorProcesses = new ArrayList<ApprovalProcess>();
			supervisorIdProcessMap.put(staff.getsupervisorId(), supervisorProcesses);
		}
		if(null != processes && !processes.isEmpty()){
			for(ApprovalProcess process: processes){
				if(process.getStatus() == Status.UNPROCESSED){
					process.setsupervisorId(staff.getsupervisorId());
					supervisorProcesses.add(process);
				}}}
		staff.setDeleted(true);
		return true;
	}
	
	public static boolean addLeaveApplication(LeaveApplication application){
		if(null == application){
			return false;
		}
		application.setId(IdGenerator.getGenerateId(IdType.LA.name()));
		idApplicationMap.put(application.getId(), application);
		List<LeaveApplication> proposerApplications =  proposerIdApplicationMap.get(application.getProposerId());
		if(null == proposerApplications){
			proposerApplications = new ArrayList<LeaveApplication>();
			proposerIdApplicationMap.put(application.getProposerId(), proposerApplications);}
		proposerApplications.add(application);
		ApprovalProcess process = new ApprovalProcess();
		process.setId(IdGenerator.getGenerateId(IdType.AP.name()));
		process.setApplicationId(application.getId());
		process.setsupervisorId(idStaffMap.get(application.getProposerId()).getsupervisorId());
		addApprovalProcess(process);
		return true;
	}
	
	public static boolean processApproval(ApprovalProcess process){
		Staff staff = idStaffMap.get(process.getSupervisorId());
		LeaveApplication application = idApplicationMap.get(process.getApplicationId());
		supervisorIdProcessMap.remove(process.getSupervisorId());
		if(process.getStatus() == Status.ENDORSE && staff.getRole() == Role.DIRECTOR){
			application.setStatus(ApplicationStatus.SUCCESS);
		}else if(process.getStatus() == Status.DECLINE ){
			application.setStatus(ApplicationStatus.FAILED);
		}else{
			ApprovalProcess nextProcess = new ApprovalProcess();
			nextProcess.setId(IdGenerator.getGenerateId(IdType.AP.name()));
			nextProcess.setApplicationId(process.getApplicationId());
			nextProcess.setsupervisorId(idStaffMap.get(process.getSupervisorId()).getsupervisorId());
			
			addApprovalProcess(nextProcess);
		}		
		return true;}
	
	public static boolean addApprovalProcess(ApprovalProcess process){
		if(StringUtil.isBlank(process.getId())||StringUtil.isBlank(process.getApplicationId()) || StringUtil.isBlank(process.getSupervisorId())){
			return false;}
		idProcessMap.put(process.getId(), process);	
		List<ApprovalProcess> processes = new ArrayList<ApprovalProcess>();
		processes.add(process);
		applicationIdProcessMap.put(process.getApplicationId(), processes);
		List<ApprovalProcess> supervisorProcesses = new ArrayList<ApprovalProcess>();
		supervisorProcesses.add(process);
		supervisorIdProcessMap.put(process.getSupervisorId(), processes);
		return true;}}
