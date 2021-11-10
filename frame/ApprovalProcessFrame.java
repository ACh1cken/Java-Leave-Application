
package frame;

import leaveApplication.LeaveApplication;
import java.util.Iterator;
import java.util.List;
import leaveApplication.Staff;
import leaveApplication.Status;
import utils.DateUtil;
import leaveApplication.ApprovalProcess;
import data.DataCenter;
import loginHandler.UserLoginHandler;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.AbstractButton;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ApprovalProcessFrame extends BaseWorkFrame {
    private static final long serialVersionUID = 4521650094578795274L;
    private JScrollPane scrollPanel;
    private JPanel opPanel;
    private JLabel selectIdLabel;
    private JLabel selectIdText;
    private ButtonGroup processButton;
    private JLabel remarkLabel;
    private JTextField remarkText;
    private JRadioButton endorse;
    private JRadioButton decline;
    private JTable table;
    private DefaultTableModel tmd;

    @Override
    public void actionPerformed(final ActionEvent arg0) {
        CommanAction.actionPerformed(this, this.approvalProcessMenuItem, (JMenuItem) arg0.getSource());
    }

    @Override
    public void init() {
        this.scrollPanel = new JScrollPane();
        (this.opPanel = new JPanel()).setLayout(new GridLayout(1, 7));
        this.panel.setLayout(new BorderLayout());
        this.panel.add(this.scrollPanel, "Center");
        this.panel.add(this.opPanel, "South");
        final String[][] rowdata = this.getData();
        final String[] attribute = { "id", "staff", "reason", "fromDate", "toDate", "createTime" };
        this.tmd = new DefaultTableModel(rowdata, attribute);
        (this.table = new JTable(this.tmd)).setSelectionMode(0);
        this.scrollPanel.setViewportView(this.table);
        this.table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                final int selectedRow = ApprovalProcessFrame.this.table.getSelectedRow();
                final Object oa = ApprovalProcessFrame.this.tmd.getValueAt(selectedRow, 0);
                ApprovalProcessFrame.this.selectIdText.setText(oa.toString());
                ApprovalProcessFrame.this.endorse.setSelected(true);
                ApprovalProcessFrame.this.remarkText.setText("");
            }
        });
        this.endorse = new JRadioButton("endorse");
        this.decline = new JRadioButton("decline");
        (this.processButton = new ButtonGroup()).add(this.endorse);
        this.processButton.add(this.decline);
        this.selectIdLabel = new JLabel("Selected ID:");
        this.selectIdText = new JLabel();
        this.remarkLabel = new JLabel("Remark");
        this.remarkText = new JTextField();
        this.opPanel.add(this.selectIdLabel);
        this.opPanel.add(this.selectIdText);
        this.opPanel.add(this.endorse);
        this.opPanel.add(this.decline);
        final JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent s) {

                if (tmd.getValueAt(0, 0) != null){
                try {
                    FileWriter writer = new FileWriter("Approval.txt");
                    for (int r = 0; r < tmd.getRowCount(); r++) {
                        for (int c = 0; c < tmd.getColumnCount(); c++) {
                            writer.write(tmd.getValueAt(r, c).toString());
                            writer.write(' ');
                        }
                        writer.write("\n");
                    }
                    writer.flush();
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Successfully saved into Approval.txt!");
                    
                } catch (IOException injarfile) {

                    try {
                        String path = ApprovalProcessFrame.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath() + " Approval.txt";
                        FileWriter write1 = new FileWriter(path);
                        for (int r = 0; r < tmd.getRowCount(); r++) {
                            for (int c = 0; c < tmd.getColumnCount(); c++) {
                                write1.write(tmd.getValueAt(r, c).toString());
                                write1.write(' ');
                            }
                            write1.write("\n");
                        }
                        write1.flush();
                        write1.close();
                        JOptionPane.showMessageDialog(null,"Successfully saved into Approval.txt!" );
    
    
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    injarfile.printStackTrace();
                }
                }else{
                    JOptionPane.showMessageDialog(null, "Table is empty! Unable to save!");
                }


            }
        });

        this.opPanel.add(this.remarkLabel);
        this.opPanel.add(this.remarkText);
        final JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final int selectedRow = ApprovalProcessFrame.this.table.getSelectedRow();
                if (selectedRow != -1) {
                    ApprovalProcessFrame.this.submit(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select one Leave Application first!");
                }
            }
        });
        this.opPanel.add(submitButton);
        this.opPanel.add(saveButton);
    }

    private void submit(final int selectedRow) {
        final Staff staff = UserLoginHandler.getLoginStaff();
        final List<ApprovalProcess> list = DataCenter.getApprovalProcessBySupervisorId(staff.getId());
        for (final ApprovalProcess p : list) {
            if (p.getApplicationId().equals(this.selectIdText.getText())) {
                p.setCreateTime(DateUtil.getDateStr());
                if (this.endorse.isSelected()) {
                    p.setStatus(Status.ENDORSE);
                } else {
                    p.setStatus(Status.DECLINE);
                }
                p.setMark(this.remarkText.getText().trim());
                if (DataCenter.processApproval(p)) {
                    this.tmd.removeRow(selectedRow);
                }
            }
        }
    }

    private String[][] getData() {
        final Staff staff = UserLoginHandler.getLoginStaff();
        final List<ApprovalProcess> list = DataCenter.getApprovalProcessBySupervisorId(staff.getId());
        String[][] rowdata = null;
        if (null == list || list.isEmpty()) {
            rowdata = new String[1][6];
        } else {
            rowdata = new String[list.size()][6];
            String[] singleRowdata = null;
            int index = 0;
            for (final ApprovalProcess process : list) {
                final LeaveApplication application = DataCenter.getApplicationById(process.getApplicationId());
                if (null == application) {
                    continue;
                }
                singleRowdata = new String[6];
                singleRowdata[0] = application.getId();
                final Staff leaveStaff = DataCenter.getStaffById(application.getProposerId());
                if (null != leaveStaff) {
                    singleRowdata[1] = leaveStaff.getUsername();
                }
                singleRowdata[2] = application.getReason();
                singleRowdata[3] = application.getFromDate();
                singleRowdata[4] = application.getToDate();
                singleRowdata[5] = application.getCreateTime();
                rowdata[index++] = singleRowdata;
            }
        }
        return rowdata;
    }
}
