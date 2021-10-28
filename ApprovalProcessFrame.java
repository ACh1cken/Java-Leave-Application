
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class ApprovalProcessFrame extends BaseWorkFrame  {
	private JScrollPane scrollPanel;
	private JPanel opPanel;
	private JLabel selectIdLabel;
	private JLabel selectIdText;
	private ButtonGroup processButton ;
	private JLabel remarkLabel;
	private JTextField remarkText;
	private JCheckBox endorse;
	private JCheckBox decline;
	private JTable table;
	private DefaultTableModel tmd;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CommanAction.actionPerformed(this, this.approvalProcessMenuItem, (JMenuItem)arg0.getSource());
	}

	@Override
	public void init() {
		this.scrollPanel = new JScrollPane();
		this.opPanel = new JPanel();
		this.opPanel.setLayout(new GridLayout(1,7));
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.scrollPanel, BorderLayout.CENTER);
		this.panel.add(this.opPanel,BorderLayout.SOUTH);
	
		String[][] rowdata  = getData();
		String[] attribute = { "Id","staff", "Detail", "StartDate", "EndDate","CreateTime"};
		
		tmd = new DefaultTableModel(rowdata, attribute);
		this.table = new JTable(tmd);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPanel.setViewportView(this.table);
		
		this.table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int selectedRow = table.getSelectedRow(); 
				Object oa = tmd.getValueAt(selectedRow, 0);
				selectIdText.setText(oa.toString()); 
				endorse.setSelected(true);
				remarkText.setText("");
			}
		});
			
		endorse =  new JCheckBox("Approve");
		decline =  new JCheckBox("Denied");		
		processButton = new ButtonGroup();
		processButton.add(endorse);
		processButton.add(decline);
		
		this.selectIdLabel = new JLabel("Selected ID:");
		this.selectIdText = new JLabel();
		this.remarkLabel  = new JLabel("Remark");
		this.remarkText = new JTextField();
		this.opPanel.add(this.selectIdLabel);
		this.opPanel.add(this.selectIdText);
		this.opPanel.add(this.endorse);
		this.opPanel.add(this.decline);
		this.opPanel.add(this.remarkLabel);
		this.opPanel.add(this.remarkText);
		
		final JButton LogInButton = new JButton("Log In");
		LogInButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1) 
						{
							LogIn(selectedRow);
						}else{					JOptionPane.showMessageDialog(null,"Please select at least one Leave Application!");
						}
					}
				});
		this.opPanel.add(LogInButton);
	}

	private void LogIn(int selectedRow){
		Staff staff = UserLoginHandler.getLoginStaff();
		List<ApprovalProcess> list = DataCenter.getApprovalProcessBySupervisorId(staff.getId());
		for(ApprovalProcess p:list){
			if(p.getApplicationId().equals(selectIdText.getText())){
				p.setCreateTime(DateUtil.getDateStr());
				if(this.endorse.isSelected()){
					p.setStatus(Status.ENDORSE);
				}else{
					p.setStatus(Status.DECLINE);
				}
				p.setMark(this.remarkText.getText().trim());
				if(DataCenter.processApproval(p)){
					tmd.removeRow(selectedRow); 
				}
				return;}}}
	
	private String[][]  getData(){
		Staff staff = UserLoginHandler.getLoginStaff();
		List<ApprovalProcess> list = DataCenter.getApprovalProcessBySupervisorId(staff.getId());
		
		String[][] rowdata  = null;
		if(null == list || list.isEmpty()){
			rowdata = new String[1][6];
		}else{
			rowdata = new String[list.size()][6];
			String[] singleRowdata = null;
			int index =0 ;
			for(ApprovalProcess process:list){
				LeaveApplication application = DataCenter.getApplicationById(process.getApplicationId());
				if(null == application){
					continue;
				}
				singleRowdata = new String[6] ;
				singleRowdata[0] = application.getId();
				Staff leaveStaff = DataCenter.getStaffById(application.getProposerId());
				if(null != leaveStaff){
					singleRowdata[1] = leaveStaff.getUsername();
				}
				singleRowdata[2] = application.getReason();
				singleRowdata[3] = application.getFromDate();
				singleRowdata[4] = application.getToDate();
				singleRowdata[5] = application.getCreateTime();
				rowdata[index ++] = singleRowdata;
			}
		}
		return rowdata;}}
