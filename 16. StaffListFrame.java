# Java-Leave-Application

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class StaffListFrame extends BaseWorkFrame {
	private JScrollPane scrollPanel;
	private JPanel opPanel;
	private JLabel selectIdLabel;
	private JLabel selectIdText;
	private JTable table;
	private DefaultTableModel tmd;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CommanAction.actionPerformed(this, this.staffListMenuItem,
				(JMenuItem) arg0.getSource());
	}

	@Override
	public void init() {
		this.scrollPanel = new JScrollPane();
		this.opPanel = new JPanel();
		this.opPanel.setLayout(new GridLayout(1,3));
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.scrollPanel, BorderLayout.CENTER);
		this.panel.add(this.opPanel,BorderLayout.SOUTH);

		String[][] rowdata = getData();
		String[] attribute = new String[] { "Id", "Username", "Realname", "Role", "Supervisior" };
		tmd = new DefaultTableModel(rowdata, attribute);
		this.table = new JTable(tmd);
		this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.scrollPanel.setViewportView(this.table);

		this.table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int selectedRow = table.getSelectedRow(); 
						Object oa = tmd.getValueAt(selectedRow, 0);
						selectIdText.setText(oa.toString()); 
					}});

		this.selectIdLabel = new JLabel("Selected ID:");
		this.selectIdText = new JLabel();
		this.opPanel.add(this.selectIdLabel);
		this.opPanel.add(this.selectIdText);
		
		final JButton delButton = new JButton("Delete");
		delButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int selectedRow = table.getSelectedRow();
						if (selectedRow != -1)
						{
							delStaff(selectedRow);
						}else{
				JOptionPane.showMessageDialog(null,"Please select at least one staff!");
						}}});
this.opPanel.add(delButton);
	}

	private void delStaff(int selectedRow){
		String staffId = selectIdText.getText();
		
		if(StringUtil.isNotBlank(staffId)){
			Staff staff = DataCenter.getStaffById(staffId);
			if(null == staff ){
				return;
			}
			
			if(Role.DIRECTOR == staff.getRole()){
				JOptionPane.showMessageDialog(null,"Can not delete Admin!");
				return;
			}
			if(DataCenter.deleteStaff(staffId)){
				tmd.removeRow(selectedRow);
			}else{
				JOptionPane.showMessageDialog(null,"Failed to delete user!!!");
			}
		}else{
			JOptionPane.showMessageDialog(null,"Please select at least one staff!");
		}
	}
	
	
	private String[][] getData() {
		List<Staff> list = DataCenter.getAllStaff();

		String[][] rowdata = null;
		if (null == list || list.isEmpty()) {
			rowdata = new String[1][5];
		} else {
			rowdata = new String[list.size()][5];
			String[] singleRowdata = null;
			int index = 0;
			for (Staff staff : list) {
				singleRowdata = new String[5];
				singleRowdata[0] = staff.getId();
				singleRowdata[1] = staff.getUsername();
				singleRowdata[2] = staff.getRealname();
				singleRowdata[3] = staff.getRole().name();
				Staff supervisor = DataCenter.getStaffById(staff
						.getsupervisorId());
				if (null != supervisor) {
					singleRowdata[4] = supervisor.getUsername();
				}

				rowdata[index++] = singleRowdata;
			}
		}
		return rowdata;}}
