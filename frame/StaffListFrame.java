// 
// Decompiled by Procyon v0.5.36
// 

package frame;

import java.util.Iterator;
import java.util.List;
import leaveApplication.Staff;
import leaveApplication.Role;
import data.DataCenter;
import utils.StringUtil;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.GridLayout;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class StaffListFrame extends BaseWorkFrame
{
    private static final long serialVersionUID = 4521650094578795274L;
    private JScrollPane scrollPanel;
    private JPanel opPanel;
    private JLabel selectIdLabel;
    private JLabel selectIdText;
    private JTable table;
    private DefaultTableModel tmd;
    
    @Override
    public void actionPerformed(final ActionEvent arg0) {
        CommanAction.actionPerformed(this, this.staffListMenuItem, (JMenuItem)arg0.getSource());
    }
    
    @Override
    public void init() {
        this.scrollPanel = new JScrollPane();
        (this.opPanel = new JPanel()).setLayout(new GridLayout(1, 3));
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.scrollPanel, "Center");
        this.panel.add(this.opPanel, "South");
        final String[][] rowdata = this.getData();
        final String[] attribute = { "id", "Username", "Realname", "Role", "Supervisior" };
        this.tmd = new DefaultTableModel(rowdata, attribute);
        (this.table = new JTable(this.tmd)).setSelectionMode(0);
        this.scrollPanel.setViewportView(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int selectedRow = StaffListFrame.this.table.getSelectedRow();
                final Object oa = StaffListFrame.this.tmd.getValueAt(selectedRow, 0);
                StaffListFrame.this.selectIdText.setText(oa.toString());
            }
        });
        this.selectIdLabel = new JLabel("Selected ID:");
        this.selectIdText = new JLabel();
        this.opPanel.add(this.selectIdLabel);
        this.opPanel.add(this.selectIdText);
        final JButton delButton = new JButton("Delete");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int selectedRow = StaffListFrame.this.table.getSelectedRow();
                if (selectedRow != -1) {
                    StaffListFrame.this.delStaff(selectedRow);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please select a staff first!!!");
                }
            }
        });
        this.opPanel.add(delButton);
    }
    
    private void delStaff(final int selectedRow) {
        final String staffId = this.selectIdText.getText();
        if (StringUtil.isNotBlank(staffId)) {
            final Staff staff = DataCenter.getStaffById(staffId);
            if (null == staff) {
                return;
            }
            if (Role.DIRECTOR == staff.getRole()) {
                JOptionPane.showMessageDialog(null, "Can not delete DIRECTOR!!!");
                return;
            }
            if (DataCenter.deleteStaff(staffId)) {
                this.tmd.removeRow(selectedRow);
            }
            else {
                JOptionPane.showMessageDialog(null, "Failed to delete user!!!");
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "Please select a staff first!!!");
        }
    }
    
    private String[][] getData() {
        final List<Staff> list = DataCenter.getAllStaff();
        String[][] rowdata = null;
        if (null == list || list.isEmpty()) {
            rowdata = new String[1][5];
        }
        else {
            rowdata = new String[list.size()][5];
            String[] singleRowdata = null;
            int index = 0;
            for (final Staff staff : list) {
                singleRowdata = new String[] { staff.getId(), staff.getUsername(), staff.getRealname(), staff.getRole().name(), null };
                final Staff supervisor = DataCenter.getStaffById(staff.getsupervisorId());
                if (null != supervisor) {
                    singleRowdata[4] = supervisor.getUsername();
                }
                rowdata[index++] = singleRowdata;
            }
        }
        return rowdata;
    }
}
