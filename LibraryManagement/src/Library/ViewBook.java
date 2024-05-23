package Library;

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

public class ViewBook extends JFrame {

    String x[] = {"Name", "Book No", "Author", "Publisher", "Quantity"};
    JButton bt;
    String y[][] = new String[20][5];
    int i = 0, j = 0;
    JTable t;
    Font f, f1;
    JTextField searchField;
    JButton searchButton;
    CustomTableModel model; // Custom table model

    ViewBook() {
        super("Book Information");
        setLocation(1, 1);
        setSize(1000, 400);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 15);

        // Initialize your custom table model with an empty data array
        model = new CustomTableModel(new Object[0][5], x);

        t = new JTable(model); // Set the custom table model
        t.setFont(f1);
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
        searchPanel.add(new JLabel("Search Book by Name or Book No:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.SOUTH);
        
        // Load all books by default
        loadAllBooks();
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

    // Search for books
    private void searchBook() {
        String searchText = searchField.getText().trim().toLowerCase();
        model.setRowCount(0); // Clear the table

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM addbooktable";
            ResultSet rest = obj.stm.executeQuery(q);

            while (rest.next()) {
                String name = rest.getString("name").toLowerCase();
                String bookno = rest.getString("bookno").toLowerCase();
                if (name.contains(searchText) || bookno.contains(searchText)) {
                    String[] rowData = {
                        rest.getString("name"),
                        rest.getString("bookno"),
                        rest.getString("author"),
                        rest.getString("publisher"),
                        rest.getString("quantity")
                    };
                    model.addRow(rowData);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    // Load all books
    private void loadAllBooks() {
        model.setRowCount(0); // Clear the table

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM addbooktable";
            ResultSet rest = obj.stm.executeQuery(q);

            while (rest.next()) {
                String[] rowData = {
                    rest.getString("name"),
                    rest.getString("bookno"),
                    rest.getString("author"),
                    rest.getString("publisher"),
                    rest.getString("quantity")
                };
                model.addRow(rowData);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ViewBook().setVisible(true);
    }
}
