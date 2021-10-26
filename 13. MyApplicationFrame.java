# Java-Leave-Application

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MyApplicationFrame extends BaseWorkFrame {

	private JScrollPane scrollPanel;
	@Override
	public void actionPerformed(ActionEvent arg0) {
		CommanAction.actionPerformed(this, this.myApplicationMenuItem, (JMenuItem)arg0.getSource());
	}

	@Override
	public void init() {
		this.scrollPanel = new JScrollPane();
		this.panel.setLayout(new BorderLayout());
		this.panel.add(this.scrollPanel,BorderLayout.CENTER);
		
		Staff staff = UserLoginHandler.getLoginStaff();
		List<LeaveApplication> list = DataCenter.getApplicationByProposerId(staff.getId());
		
		String[][] rowdata  = null;
		if(null == list || list.isEmpty()){
			rowdata = new String[1][6];
		}else{
			rowdata = new String[list.size()][6];
			String[] singleRowdata = null;
			int index =0 ;
			for(LeaveApplication application:list){
				singleRowdata = new String[6] ;
				singleRowdata[0] = application.getId();
				singleRowdata[1] = application.getReason();
				singleRowdata[2] = application.getFromDate();
				singleRowdata[3] = application.getToDate();
				singleRowdata[4] = application.getCreateTime();
				if( null != application.getStatus()){
					singleRowdata[5] = application.getStatus().name();
				}
				rowdata[index ++] = singleRowdata;}}
		
		String[] attribute = { "Id", "Detail", "StartDate", "EndDate","CreateTime","Status"};
		DefaultTableModel tmd = new DefaultTableModel(rowdata, attribute);
		JTable table = new JTable(tmd);
		this.scrollPanel.setViewportView(table);}}
