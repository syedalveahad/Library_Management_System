package Library;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;


public class Librarian extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JButton bt1, bt2;
    JPanel p1, p2;
    Font f, f1;
    JTextField tf1;
    JPasswordField pf1;

    Librarian() {
        super("Librarian Login Page");
        setLocation(450, 400);
        setSize(500, 200);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 15);

        l1 = new JLabel("Librarian Login");
        l2 = new JLabel("Name");
        l3 = new JLabel("Password");

        bt1 = new JButton("Login");
        bt2 = new JButton("Cancel");

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        tf1 = new JTextField();
        pf1 = new JPasswordField();

        l1.setFont(f);
        l2.setFont(f1);
        l3.setFont(f1);
        bt1.setFont(f1);
        bt2.setFont(f1);
        tf1.setFont(f1);
        pf1.setFont(f1);

        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setForeground(Color.WHITE);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 10, 10));
        p1.add(l1);
        p1.setBackground(Color.RED);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(3, 2, 10, 10));
        p2.add(l2);
        p2.add(tf1);
        p2.add(l3);
        p2.add(pf1);
        p2.add(bt1);
        p2.add(bt2);

        setLayout(new BorderLayout(10, 10));
        add(p1, "North");
        add(p2, "Center");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            String name = tf1.getText();
            char[] password = pf1.getPassword();
            String pass = new String(password);

            try {
                ConnectionClass obj = new ConnectionClass();
                String query = "SELECT name, password FROM librarian WHERE name = ? AND password = ?";
                PreparedStatement preparedStatement = obj.con.prepareStatement(query);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, pass);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    new LibrarianSection().setVisible(true);
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Your Name and Password are incorrect.");
                    tf1.setText("");
                    pf1.setText("");
                }
            } catch (Exception ee) {
                ee.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred during login.");
            }
        } else if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Librarian().setVisible(true);
    }
}
