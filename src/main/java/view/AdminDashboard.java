package view;

import dao.LoanDAO;
import dao.MemberDAO;
import dao.NoticeDAO;
import dao.SavingDAO;
import dao.RepaymentDAO;
import model.Loan;
import model.Member;
import model.Notice;
import model.Saving;
import model.Repayment;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTabbedPane tabbedPane;
    private JTable memberTable, loanTable, savingsTable, repaymentTable, noticeTable;
    private JButton approveButton, denyButton, addNoticeButton;
    private MemberDAO memberDAO;
    private LoanDAO loanDAO;
    private SavingDAO savingDAO;
    private RepaymentDAO repaymentDAO;
    private NoticeDAO noticeDAO;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        memberDAO = new MemberDAO();
        loanDAO = new LoanDAO();
        savingDAO = new SavingDAO();
        repaymentDAO = new RepaymentDAO();
        noticeDAO = new NoticeDAO();

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Members Tab
        JPanel memberPanel = new JPanel(new BorderLayout());
        memberTable = new JTable();
        loadMembers();
        memberPanel.add(new JScrollPane(memberTable), BorderLayout.CENTER);
        JButton refreshMembersButton = new JButton("Refresh Members");
        refreshMembersButton.addActionListener(e -> loadMembers());
        memberPanel.add(refreshMembersButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Members", memberPanel);

        // Loans Tab
        JPanel loanPanel = new JPanel(new BorderLayout());
        loanTable = new JTable();
        loadPendingLoans();
        loanPanel.add(new JScrollPane(loanTable), BorderLayout.CENTER);
        JPanel loanActions = new JPanel();
        approveButton = new JButton("Approve Loan");
        denyButton = new JButton("Deny Loan");
        loanActions.add(approveButton);
        loanActions.add(denyButton);
        loanPanel.add(loanActions, BorderLayout.SOUTH);
        tabbedPane.addTab("Loans", loanPanel);

        // Savings Tab
        JPanel savingsPanel = new JPanel(new BorderLayout());
        savingsTable = new JTable();
        loadAllSavings();
        savingsPanel.add(new JScrollPane(savingsTable), BorderLayout.CENTER);
        tabbedPane.addTab("Savings", savingsPanel);

        // Repayments Tab
        JPanel repaymentPanel = new JPanel(new BorderLayout());
        repaymentTable = new JTable();
        loadAllRepayments();
        repaymentPanel.add(new JScrollPane(repaymentTable), BorderLayout.CENTER);
        tabbedPane.addTab("Repayments", repaymentPanel);

        // Notices Tab
        JPanel noticePanel = new JPanel(new BorderLayout());
        noticeTable = new JTable();
        loadNotices();
        noticePanel.add(new JScrollPane(noticeTable), BorderLayout.CENTER);
        JPanel noticeActions = new JPanel();
        addNoticeButton = new JButton("Add Notice");
        noticeActions.add(addNoticeButton);
        noticePanel.add(noticeActions, BorderLayout.SOUTH);
        tabbedPane.addTab("Notices", noticePanel);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        mainPanel.add(logoutButton, BorderLayout.SOUTH);
        add(mainPanel);
    }

    public void loadMembers() {
        try {
            List<Member> members = memberDAO.getAllMembers();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Surname", "First Name", "Email", "Join Date", "Status"}, 0);
            for (Member m : members) {
                model.addRow(new Object[]{m.getMemberId(), m.getSurname(), m.getFirstName(),
                    m.getEmail(), m.getJoinDate(), m.getStatus()});
            }
            memberTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading members: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadPendingLoans() {
        try {
            List<Loan> loans = loanDAO.getPendingLoans();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Loan ID", "Member ID", "Amount Requested", "Repayment Months", "Status", "Application Date"}, 0);
            for (Loan l : loans) {
                model.addRow(new Object[]{l.getLoanId(), l.getMemberId(), l.getAmountRequested(),
                    l.getRepaymentMonths(), l.getStatus(), l.getApplicationDate()});
            }
            loanTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading loans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadAllSavings() {
        try {
            List<Saving> savings = savingDAO.getAllSavings();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Saving ID", "Member ID", "Amount", "Date", "Description"}, 0);
            for (Saving s : savings) {
                model.addRow(new Object[]{s.getSavingId(), s.getMemberId(), s.getAmount(),
                    s.getDate(), s.getDescription()});
            }
            savingsTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading savings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadAllRepayments() {
        try {
            List<Repayment> repayments = repaymentDAO.getAllRepayments();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Repayment ID", "Loan ID", "Amount", "Date"}, 0);
            for (Repayment r : repayments) {
                model.addRow(new Object[]{r.getRepaymentId(), r.getLoanId(), r.getAmount(), r.getDate()});
            }
            repaymentTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading repayments: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadNotices() {
        try {
            List<Notice> notices = noticeDAO.getAllNotices();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Notice ID", "Title", "Description", "Date"}, 0);
            for (Notice n : notices) {
                model.addRow(new Object[]{n.getNoticeId(), n.getTitle(), n.getDescription(), n.getDate()});
            }
            noticeTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading notices: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Getter methods for controller
    public JButton getApproveLoanButton() {
        return approveButton;
    }

    public JButton getDenyLoanButton() {
        return denyButton;
    }

    public JButton getAddNoticeButton() {
        return addNoticeButton;
    }

    public JTable getLoanTable() {
        return loanTable;
    }
}