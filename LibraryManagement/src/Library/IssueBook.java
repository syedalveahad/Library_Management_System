package Library;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IssueBook extends JFrame {

    JLabel titleLabel, bookNameLabel, bookNoLabel, studentIdLabel, studentNameLabel, studentContactLabel, bookQuantityLabel;
    JTextField bookNameField, bookNoField, studentIdField, studentNameField, studentContactField, bookQuantityField;
    JButton issueButton, cancelButton;
    Font f, f1;

    IssueBook() {
        super("Issue Book");
        setLocation(450, 400);
        setSize(650, 400);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 20);

        titleLabel = new JLabel("Issue Book");
        titleLabel.setFont(f);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        bookNameLabel = new JLabel("Book Name");
        bookNameLabel.setFont(f1);
        bookNameField = new JTextField();
        bookNameField.setFont(f1);

        bookNoLabel = new JLabel("Book No");
        bookNoLabel.setFont(f1);
        bookNoField = new JTextField();
        bookNoField.setFont(f1);

        studentIdLabel = new JLabel("Student ID");
        studentIdLabel.setFont(f1);
        studentIdField = new JTextField();
        studentIdField.setFont(f1);

        studentNameLabel = new JLabel("Student Name");
        studentNameLabel.setFont(f1);
        studentNameField = new JTextField();
        studentNameField.setFont(f1);

        studentContactLabel = new JLabel("Student Contact");
        studentContactLabel.setFont(f1);
        studentContactField = new JTextField();
        studentContactField.setFont(f1);

        bookQuantityLabel = new JLabel("Book Quantity");
        bookQuantityLabel.setFont(f1);
        bookQuantityField = new JTextField();
        bookQuantityField.setFont(f1);

        issueButton = new JButton("Issue Book");
        cancelButton = new JButton("Cancel");
        issueButton.setFont(f1);
        cancelButton.setFont(f1);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 0, 0));
        p1.add(titleLabel);
        p1.setBackground(Color.RED);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(7, 2, 10, 10));

        p2.add(bookNameLabel);
        p2.add(bookNameField);
        p2.add(bookNoLabel);
        p2.add(bookNoField);
        p2.add(studentIdLabel);
        p2.add(studentIdField);
        p2.add(studentNameLabel);
        p2.add(studentNameField);
        p2.add(studentContactLabel);
        p2.add(studentContactField);
        p2.add(bookQuantityLabel);
        p2.add(bookQuantityField);

        p2.add(issueButton);
        p2.add(cancelButton);

        setLayout(new BorderLayout(10, 10));
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);

        

        issueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                issueBook();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the IssueBook window
                dispose();
            }
        });
    }

    private void issueBook() {
        // Get the input values
        String bookName = bookNameField.getText();
        String bookNo = bookNoField.getText();
        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String studentContact = studentContactField.getText();
        int bookQuantity = Integer.parseInt(bookQuantityField.getText());

        try {
            ConnectionClass obj = new ConnectionClass();

            // Check if the book exists in the "addbooktable" database
            String checkQuery = "SELECT quantity FROM addbooktable WHERE name = ? AND bookno = ?";
            PreparedStatement pstmtCheck = obj.con.prepareStatement(checkQuery);
            pstmtCheck.setString(1, bookName);
            pstmtCheck.setString(2, bookNo);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                int availableQuantity = rs.getInt("quantity");
                if (availableQuantity >= bookQuantity) {
                    // Book is available in sufficient quantity, proceed with issuing the book
                    // Insert issued book data into the "issuebook" database
                    String insertQuery = "INSERT INTO issuebook (bookName, bookNo, studentId, studentName, studentContact, bookQuantity) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement pstmtInsert = obj.con.prepareStatement(insertQuery);
                    pstmtInsert.setString(1, bookName);
                    pstmtInsert.setString(2, bookNo);
                    pstmtInsert.setString(3, studentId);
                    pstmtInsert.setString(4, studentName);
                    pstmtInsert.setString(5, studentContact);
                    pstmtInsert.setInt(6, bookQuantity);
                    pstmtInsert.executeUpdate();

                    // Update the book quantity in the "addbooktable" database
                    String updateQuery = "UPDATE addbooktable SET quantity = quantity - ? WHERE name = ? AND bookno = ?";
                    PreparedStatement pstmtUpdate = obj.con.prepareStatement(updateQuery);
                    pstmtUpdate.setInt(1, bookQuantity);
                    pstmtUpdate.setString(2, bookName);
                    pstmtUpdate.setString(3, bookNo);
                    pstmtUpdate.executeUpdate();

                    JOptionPane.showMessageDialog(null, "Book Issued Successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "The requested book is not available in sufficient quantity.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "The requested book does not exist in the library.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred during issuing the book.");
        }

        // Close the IssueBook window
        dispose();
    }

    public static void main(String[] args) {
        new IssueBook().setVisible(true);
    }
}