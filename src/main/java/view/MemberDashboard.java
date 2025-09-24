package view;

import dao.LoanDAO;
import dao.SavingDAO;
import dao.RepaymentDAO;
import dao.NoticeDAO;
import model.Member;
import model.Loan;
import model.Saving;
import model.Repayment;
import model.Notice;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MemberDashboard extends JFrame {
    private Member member;
    private JTabbedPane tabbedPane;
    private JTable savingsTable, loanTable, repaymentTable, noticeTable;
    private JButton addSavingsButton, applyLoanButton, addRepaymentButton;
    private SavingDAO savingDAO;
    private LoanDAO loanDAO;
    private RepaymentDAO repaymentDAO;
    private NoticeDAO noticeDAO;

    public MemberDashboard(Member member) {
        this.member = member;
        setTitle("Member Dashboard - " + member.getFirstName() + " " + member.getSurname());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        savingDAO = new SavingDAO();
        loanDAO = new LoanDAO();
        repaymentDAO = new RepaymentDAO();
        noticeDAO = new NoticeDAO();

        initComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Profile Tab
        JPanel profilePanel = new JPanel(new GridLayout(5, 2));
        profilePanel.add(new JLabel("Member ID:"));
        profilePanel.add(new JLabel(String.valueOf(member.getMemberId())));
        profilePanel.add(new JLabel("Name:"));
        profilePanel.add(new JLabel(member.getFirstName() + " " + member.getSurname()));
        profilePanel.add(new JLabel("Email:"));
        profilePanel.add(new JLabel(member.getEmail()));
        profilePanel.add(new JLabel("Join Date:"));
        profilePanel.add(new JLabel(String.valueOf(member.getJoinDate())));
        profilePanel.add(new JLabel("Status:"));
        profilePanel.add(new JLabel(member.getStatus()));
        tabbedPane.addTab("Profile", profilePanel);

        // Savings Tab
        JPanel savingsPanel = new JPanel(new BorderLayout());
        savingsTable = new JTable();
        loadSavings();
        savingsPanel.add(new JScrollPane(savingsTable), BorderLayout.CENTER);
        addSavingsButton = new JButton("Add Savings");
        savingsPanel.add(addSavingsButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Savings", savingsPanel);

        // Loans Tab
        JPanel loanPanel = new JPanel(new BorderLayout());
        loanTable = new JTable();
        loadLoans();
        loanPanel.add(new JScrollPane(loanTable), BorderLayout.CENTER);
        applyLoanButton = new JButton("Apply for Loan");
        loanPanel.add(applyLoanButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Loans", loanPanel);

        // Repayments Tab
        JPanel repaymentPanel = new JPanel(new BorderLayout());
        repaymentTable = new JTable();
        loadRepayments();
        repaymentPanel.add(new JScrollPane(repaymentTable), BorderLayout.CENTER);
        addRepaymentButton = new JButton("Make Repayment");
        repaymentPanel.add(addRepaymentButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Repayments", repaymentPanel);

        // Notices Tab
        JPanel noticePanel = new JPanel(new BorderLayout());
        noticeTable = new JTable();
        loadNotices();
        noticePanel.add(new JScrollPane(noticeTable), BorderLayout.CENTER);
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

    public void loadSavings() {
        try {
            List<Saving> savings = savingDAO.getSavingsByMember(member.getMemberId());
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Saving ID", "Amount", "Date", "Description"}, 0);
            for (Saving s : savings) {
                model.addRow(new Object[]{s.getSavingId(), s.getAmount(), s.getDate(), s.getDescription()});
            }
            savingsTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading savings: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadLoans() {
        try {
            List<Loan> loans = loanDAO.getLoansByMember(member.getMemberId());
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"Loan ID", "Amount Requested", "Amount Approved", "Repayment Months", "Status", "Application Date"}, 0);
            for (Loan l : loans) {
                model.addRow(new Object[]{l.getLoanId(), l.getAmountRequested(), l.getAmountApproved(),
                    l.getRepaymentMonths(), l.getStatus(), l.getApplicationDate()});
            }
            loanTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading loans: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadRepayments() {
        try {
            List<Repayment> repayments = repaymentDAO.getRepaymentsByMember(member.getMemberId());
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
    public JButton getAddSavingsButton() {
        return addSavingsButton;
    }

    public JButton getApplyLoanButton() {
        return applyLoanButton;
    }

    public JButton getAddRepaymentButton() {
        return addRepaymentButton;
    }

    public Member getMember() {
        return member;
    }
}