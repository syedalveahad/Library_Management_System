package Library;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ViewIssueBook extends JFrame {

    String x[] = {"Book No", "Book Name", "Student Id", "Student Name", "Student Contact", "Book Quantity"};
    JButton searchButton;
    CustomTableModel model; // Custom table model

    JTextField searchField;

    ViewIssueBook() {
        super("Issued Books");
        setLocation(1, 1);
        setSize(1000, 400);

        // Create a custom table model
        model = new CustomTableModel(new Object[0][6], x);

        JTable t = new JTable(model);
        t.setFont(new Font("Arial", Font.BOLD, 15));

        JScrollPane sp = new JScrollPane(t);
        add(sp);

        // Add a search section
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBook();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Search Book by Student Id/Name or Book No:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.SOUTH);

        // Load all issued books by default
        loadAllIssuedBooks();
    }

    // Custom table model
    public class CustomTableModel extends DefaultTableModel {
        public CustomTableModel(Object[][] data, Object[] columnNames) {
            super(data, columnNames);
        }

        // This override is necessary to ensure the JTable's model is of your custom type.
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    // Search for issued books
    private void searchBook() {
        String searchText = searchField.getText().trim().toLowerCase();
        model.setRowCount(0); // Clear the table

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM issuebook";
            ResultSet rest = obj.stm.executeQuery(q);

            while (rest.next()) {
                String name = rest.getString("studentName").toLowerCase();
                String bookno = rest.getString("bookNo").toLowerCase();
                String studentid = rest.getString("studentId").toLowerCase();
                if (name.contains(searchText) || bookno.contains(searchText) || studentid.contains(searchText)) {
                    String[] rowData = {
                        rest.getString("bookNo"),
                        rest.getString("bookName"),
                        rest.getString("studentId"),
                        rest.getString("studentName"),
                        rest.getString("studentContact"),
                        rest.getString("bookQuantity")
                    };
                    model.addRow(rowData);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Load all issued books
    private void loadAllIssuedBooks() {
        model.setRowCount(0); // Clear the table

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM issuebook";
            ResultSet rest = obj.stm.executeQuery(q);

            while (rest.next()) {
                String[] rowData = {
                    rest.getString("bookNo"),
                    rest.getString("bookName"),
                    rest.getString("studentId"),
                    rest.getString("studentName"),
                    rest.getString("studentContact"),
                    rest.getString("bookQuantity")
                };
                model.addRow(rowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewIssueBook().setVisible(true);
    }
}
