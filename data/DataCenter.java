// 
// Decompiled by Procyon v0.5.36
// 

package data;

import java.util.HashMap;
import leaveApplication.ApplicationStatus;
import leaveApplication.Status;
import java.util.Set;
import java.util.HashSet;
import utils.IdGenerator;
import leaveApplication.IdType;
import leaveApplication.Role;
import utils.StringUtil;
import java.util.Iterator;
import java.util.Collection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import leaveApplication.ApprovalProcess;
import leaveApplication.LeaveApplication;
import java.util.List;
import leaveApplication.Staff;
import java.util.Map;

public final class DataCenter 
{
    private static final Map<String, Staff> idStaffMap;
    private static final Map<String, Staff> usernameStaffMap;
    private static final Map<String, List<Staff>> supervisorStaffMap;
    private static final Map<String, LeaveApplication> idApplicationMap;
    private static final Map<String, List<LeaveApplication>> proposerIdApplicationMap;
    private static final Map<String, ApprovalProcess> idProcessMap;
    private static final Map<String, List<ApprovalProcess>> applicationIdProcessMap;
    private static final Map<String, List<ApprovalProcess>> supervisorIdProcessMap;
    
    public static Staff getStaffById(final String id) {
        return DataCenter.idStaffMap.get(id);
    }
    
    public static Staff getStaffByUsername(final String username) {
        return DataCenter.usernameStaffMap.get(username);
    }
    
    public static List<Staff> getStaffBySupervisorId(final String supervisorId) {
        return DataCenter.supervisorStaffMap.get(supervisorId);
    }
    
    public static LeaveApplication getApplicationById(final String id) {
        return DataCenter.idApplicationMap.get(id);
    }
    
    public static List<LeaveApplication> getApplicationByProposerId(final String proposerId) {
        return DataCenter.proposerIdApplicationMap.get(proposerId);
    }
    
    public static ApprovalProcess getApprovalProcessById(final String id) {
        return DataCenter.idProcessMap.get(id);
    }
    
    public static List<ApprovalProcess> getApprovalProcessByApplicationId(final String applicationId) {
        return DataCenter.applicationIdProcessMap.get(applicationId);
    }
    
    public static List<ApprovalProcess> getApprovalProcessBySupervisorId(final String supervisorId) {
        return DataCenter.supervisorIdProcessMap.get(supervisorId);
    }
    
    public static List<Staff> getAllStaff() {
        final List<Staff> list = new ArrayList<Staff>(DataCenter.idStaffMap.values());
        final List<Staff> normalStaffList = new ArrayList<Staff>();
        for (final Staff staff : list) {
            if (!staff.isDeleted()) {
                normalStaffList.add(staff);
            }
        }
        return normalStaffList;
    }
    
    public static boolean addStaff(final Staff staff) {
        if (null == staff || StringUtil.isBlank(staff.getUsername()) || StringUtil.isBlank(staff.getPassword())) {
            return false;
        }
        if (null == staff.getsupervisorId() && staff.getRole() != Role.DIRECTOR) {
            return false;
        }
        staff.setId(IdGenerator.getGenerateId(IdType.ST.name()));
        if (DataCenter.idStaffMap.containsKey(staff.getId()) || DataCenter.usernameStaffMap.containsKey(staff.getUsername())) {
            return false;
        }
        boolean hasLoop = false;
        Staff supervisor = getStaffById(staff.getsupervisorId());
        final Set<String> supervisorIds = new HashSet<String>();
        while (null != supervisor && null != supervisor.getsupervisorId()) {
            if (supervisorIds.contains(supervisor.getId())) {
                hasLoop = true;
                break;
            }
            supervisorIds.add(supervisor.getId());
            supervisor = getStaffById(supervisor.getsupervisorId());
        }
        if (hasLoop) {
            return false;
        }
        DataCenter.idStaffMap.put(staff.getId(), staff);
        DataCenter.usernameStaffMap.put(staff.getUsername(), staff);
        List<Staff> staffs = DataCenter.supervisorStaffMap.get(staff.getsupervisorId());
        if (null == staffs) {
            staffs = new ArrayList<Staff>();
            DataCenter.supervisorStaffMap.put(staff.getsupervisorId(), staffs);
        }
        staffs.add(staff);
        return true;
    }
    
    public static void saveData(){
        try {
            File file1 = new File("Tmp");
            FileOutputStream fos = new FileOutputStream(file1);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeChars("ID Staff Map: ");
            oos.writeObject(usernameStaffMap);
            oos.flush();
            oos.close();
            fos.close();

        } catch (Exception e) {
            System.out.println("Failed to save!");
        }


    }




    public static boolean deleteStaff(final String id) {
        if (StringUtil.isBlank(id)) {
            return false;
        }
        final Staff staff = DataCenter.idStaffMap.get(id);
        if (null == staff || staff.isDeleted()) {
            return false;
        }
        final List<Staff> subordinates = DataCenter.supervisorStaffMap.get(id);
        if (null != subordinates) {
            for (final Staff subordinate : subordinates) {
                subordinate.setsupervisorId(staff.getsupervisorId());
            }
        }
        final List<ApprovalProcess> processes = DataCenter.supervisorIdProcessMap.get(staff.getId());
        List<ApprovalProcess> supervisorProcesses = DataCenter.supervisorIdProcessMap.get(staff.getsupervisorId());
        if (null == supervisorProcesses) {
            supervisorProcesses = new ArrayList<ApprovalProcess>();
            DataCenter.supervisorIdProcessMap.put(staff.getsupervisorId(), supervisorProcesses);
        }
        if (null != processes && !processes.isEmpty()) {
            for (final ApprovalProcess process : processes) {
                if (process.getStatus() == Status.UNPROCESSED) {
                    process.setsupervisorId(staff.getsupervisorId());
                    supervisorProcesses.add(process);
                }
            }
        }
        staff.setDeleted(true);
        return true;
    }
    
    public static boolean addLeaveApplication(final LeaveApplication application) {
        if (null == application) {
            return false;
        }
        application.setId(IdGenerator.getGenerateId(IdType.LA.name()));
        DataCenter.idApplicationMap.put(application.getId(), application);
        List<LeaveApplication> proposerApplications = DataCenter.proposerIdApplicationMap.get(application.getProposerId());
        if (null == proposerApplications) {
            proposerApplications = new ArrayList<LeaveApplication>();
            DataCenter.proposerIdApplicationMap.put(application.getProposerId(), proposerApplications);
        }
        proposerApplications.add(application);
        final ApprovalProcess process = new ApprovalProcess();
        process.setId(IdGenerator.getGenerateId(IdType.AP.name()));
        process.setApplicationId(application.getId());
        process.setsupervisorId(DataCenter.idStaffMap.get(application.getProposerId()).getsupervisorId());
        addApprovalProcess(process);
        return true;
    }
    
    public static boolean processApproval(final ApprovalProcess process) {
        final Staff staff = DataCenter.idStaffMap.get(process.getSupervisorId());
        final LeaveApplication application = DataCenter.idApplicationMap.get(process.getApplicationId());
        DataCenter.supervisorIdProcessMap.remove(process.getSupervisorId());
        if (process.getStatus() == Status.ENDORSE && staff.getRole() == Role.DIRECTOR) {
            application.setStatus(ApplicationStatus.SUCCESS);
        }
        else if (process.getStatus() == Status.DECLINE) {
            application.setStatus(ApplicationStatus.FAILED);
        }
        else {
            final ApprovalProcess nextProcess = new ApprovalProcess();
            nextProcess.setId(IdGenerator.getGenerateId(IdType.AP.name()));
            nextProcess.setApplicationId(process.getApplicationId());
            nextProcess.setsupervisorId(DataCenter.idStaffMap.get(process.getSupervisorId()).getsupervisorId());
            addApprovalProcess(nextProcess);
        }
        return true;
    }
    
    public static boolean addApprovalProcess(final ApprovalProcess process) {
        if (StringUtil.isBlank(process.getId()) || StringUtil.isBlank(process.getApplicationId()) || StringUtil.isBlank(process.getSupervisorId())) {
            return false;
        }
        DataCenter.idProcessMap.put(process.getId(), process);
        final List<ApprovalProcess> processes = new ArrayList<ApprovalProcess>();
        processes.add(process);
        DataCenter.applicationIdProcessMap.put(process.getApplicationId(), processes);
        final List<ApprovalProcess> supervisorProcesses = new ArrayList<ApprovalProcess>();
        supervisorProcesses.add(process);
        DataCenter.supervisorIdProcessMap.put(process.getSupervisorId(), processes);
        return true;
    }
    
    static {
        idStaffMap = new HashMap<String, Staff>();
        usernameStaffMap = new HashMap<String, Staff>();
        supervisorStaffMap = new HashMap<String, List<Staff>>();
        idApplicationMap = new HashMap<String, LeaveApplication>();
        proposerIdApplicationMap = new HashMap<String, List<LeaveApplication>>();
        idProcessMap = new HashMap<String, ApprovalProcess>();
        applicationIdProcessMap = new HashMap<String, List<ApprovalProcess>>();
        supervisorIdProcessMap = new HashMap<String, List<ApprovalProcess>>();
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
        staff.setUsername("staff1");
        staff.setPassword("staff1");
        staff.setRealname("staff1");
        staff.setRole(Role.STAFF);
        staff.setsupervisorId(directorId);
        addStaff(staff);
        staff1Id = staff.getId();
        staff = new Staff();
        staff.setUsername("staff2");
        staff.setPassword("staff2");
        staff.setRealname("staff2");
        staff.setRole(Role.STAFF);
        staff.setsupervisorId(directorId);
        addStaff(staff);
        staff = new Staff();
        staff.setUsername("staff3");
        staff.setPassword("staff3");
        staff.setRealname("staff3");
        staff.setRole(Role.STAFF);
        staff.setsupervisorId(directorId);
        addStaff(staff);
        staff = new Staff();
        staff.setUsername("test1");
        staff.setPassword("test1");
        staff.setRealname("test1");
        staff.setRole(Role.STAFF);
        staff.setsupervisorId(staff1Id);
        addStaff(staff);
    }
}
