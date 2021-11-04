// 
// Decompiled by Procyon v0.5.36
// 

package frame;

import java.util.Iterator;
import java.util.List;
import leaveApplication.Staff;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import leaveApplication.LeaveApplication;
import data.DataCenter;
import loginHandler.UserLoginHandler;
import java.awt.Component;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MyApplicationFrame extends BaseWorkFrame
{
    private static final long serialVersionUID = 4521650094578795274L;
    private JScrollPane scrollPanel;
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        CommanAction.actionPerformed(this, this.myApplicationMenuItem, (JMenuItem)arg0.getSource());
    }
    
    @Override
    public void init() {
        this.scrollPanel = new JScrollPane();
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.scrollPanel, "Center");
        final Staff staff = UserLoginHandler.getLoginStaff();
        final List<LeaveApplication> list = DataCenter.getApplicationByProposerId(staff.getId());
        String[][] rowdata = null;
        if (null == list || list.isEmpty()) {
            rowdata = new String[1][6];
        }
        else {
            rowdata = new String[list.size()][6];
            String[] singleRowdata = null;
            int index = 0;
            for (final LeaveApplication application : list) {
                singleRowdata = new String[] { application.getId(), application.getReason(), application.getFromDate(), application.getToDate(), application.getCreateTime(), null };
                if (null != application.getStatus()) {
                    singleRowdata[5] = application.getStatus().name();
                }
                rowdata[index++] = singleRowdata;
            }
        }
        final String[] attribute = { "id", "reason", "fromDate", "toDate", "createTime", "status" };
        final DefaultTableModel tmd = new DefaultTableModel(rowdata, attribute);
        final JTable table = new JTable(tmd);
        this.scrollPanel.setViewportView(table);
    }
}
