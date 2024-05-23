package Library;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBook extends JFrame {

    JLabel titleLabel, bookNoLabel, studentIdLabel;
    JTextField bookNoField, studentIdField;
    JButton returnButton, cancelButton;
    Font f, f1;

    ReturnBook() {
        super("Return Book");
        setLocation(450, 400);
        setSize(650, 300);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 20);

        titleLabel = new JLabel("Return Book");
        titleLabel.setFont(f);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        bookNoLabel = new JLabel("Book No:");
        bookNoLabel.setFont(f1);
        bookNoField = new JTextField();
        bookNoField.setFont(f1);

        studentIdLabel = new JLabel("Student ID:");
        studentIdLabel.setFont(f1);
        studentIdField = new JTextField();
        studentIdField.setFont(f1);

        returnButton = new JButton("Return Book");
        cancelButton = new JButton("Cancel");
        returnButton.setFont(f1);
        cancelButton.setFont(f1);

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 0, 0));
        p1.add(titleLabel);
        p1.setBackground(Color.RED);

        JPanel p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2, 10, 10));

        p2.add(bookNoLabel);
        p2.add(bookNoField);
        p2.add(studentIdLabel);
        p2.add(studentIdField);
        p2.add(returnButton);
        p2.add(cancelButton);

        setLayout(new BorderLayout(10, 10));
        add(p1, BorderLayout.NORTH);
        add(p2, BorderLayout.CENTER);

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnBook();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the ReturnBook window
                dispose();
            }
        });
    }

    private void returnBook() {
        // Get the input values
        String bookNo = bookNoField.getText();
        String studentId = studentIdField.getText();

        try {
            ConnectionClass obj = new ConnectionClass();

            // Check if the book is issued to the specified student
            String checkQuery = "SELECT * FROM issuebook WHERE bookNo = ? AND studentId = ?";
            PreparedStatement pstmtCheck = obj.con.prepareStatement(checkQuery);
            pstmtCheck.setString(1, bookNo);
            pstmtCheck.setString(2, studentId);
            ResultSet rs = pstmtCheck.executeQuery();

            if (rs.next()) {
                // Book is issued to the student, proceed with returning the book
                // Get the book quantity to be returned
                int bookQuantity = rs.getInt("bookQuantity");

                // Delete the record from the "issuebook" table
                String deleteQuery = "DELETE FROM issuebook WHERE bookNo = ? AND studentId = ?";
                PreparedStatement pstmtDelete = obj.con.prepareStatement(deleteQuery);
                pstmtDelete.setString(1, bookNo);
                pstmtDelete.setString(2, studentId);
                pstmtDelete.executeUpdate();

                // Update the book quantity in the "addbooktable" table
                String updateQuery = "UPDATE addbooktable SET quantity = quantity + ? WHERE bookno = ?";
                PreparedStatement pstmtUpdate = obj.con.prepareStatement(updateQuery);
                pstmtUpdate.setInt(1, bookQuantity);
                pstmtUpdate.setString(2, bookNo);
                pstmtUpdate.executeUpdate();

                JOptionPane.showMessageDialog(null, "Book returned successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Book not issued to the specified student.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Close the ReturnBook window
        dispose();
    }

    public static void main(String[] args) {
        new ReturnBook().setVisible(true);
    }
}