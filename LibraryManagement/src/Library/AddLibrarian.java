package Library;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class AddLibrarian extends JFrame implements ActionListener {

    JLabel l1, l2, l3, l4, l5, l6;
    JButton bt1, bt2;
    JPanel p1, p2;
    JTextField tf1, tf2, tf3, tf4;
    JPasswordField pf1;
    Font f, f1;

    AddLibrarian() {
        super("Add Librarian");
        setLocation(450, 400);
        setSize(650, 400);

        f = new Font("Arial", Font.BOLD, 25);
        f1 = new Font("Arial", Font.BOLD, 20);

        l1 = new JLabel("Add Librarian");
        l2 = new JLabel("Name");
        l3 = new JLabel("Password");
        l4 = new JLabel("Email");
        l5 = new JLabel("Contact");
        l6 = new JLabel("Address");

        l1.setForeground(Color.WHITE);
        l1.setHorizontalAlignment(JLabel.CENTER);

        tf1 = new JTextField();
        tf2 = new JTextField();
        tf3 = new JTextField();
        tf4 = new JTextField();

        pf1 = new JPasswordField();

        l1.setFont(f);
        l2.setFont(f1);
        l3.setFont(f1);
        l4.setFont(f1);
        l5.setFont(f1);
        l6.setFont(f1);

        tf1.setFont(f1);
        tf2.setFont(f1);
        tf3.setFont(f1);
        tf4.setFont(f1);

        pf1.setFont(f1);

        bt1 = new JButton("Add Librarian");
        bt2 = new JButton("Cancel");

        bt1.setFont(f1);
        bt2.setFont(f1);

        bt1.addActionListener(this);
        bt2.addActionListener(this);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(1, 1, 0, 0));
        p1.add(l1);
        p1.setBackground(Color.RED);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(7, 2, 10, 10));

        p2.add(l2);
        p2.add(tf1);
        p2.add(l3);
        p2.add(pf1);
        p2.add(l4);
        p2.add(tf2);
        p2.add(l5);
        p2.add(tf3);
        p2.add(l6);
        p2.add(tf4);
        p2.add(bt1);
        p2.add(bt2);

        setLayout(new BorderLayout(10, 10));
        add(p1, "North");
        add(p2, "Center");
    }

    public void actionPerformed(ActionEvent e) 
    {
        String name = tf1.getText();
        String pass = new String(pf1.getPassword());
        String email = tf2.getText();
        String contact = tf3.getText();
        String address = tf4.getText();

        if (e.getSource() == bt1) {
            try {
                ConnectionClass obj = new ConnectionClass();
                String query = "INSERT INTO librarian (name, password, email, contact, address) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pstmt = obj.con.prepareStatement(query);
                pstmt.setString(1, name);
                pstmt.setString(2, pass);
                pstmt.setString(3, email);
                pstmt.setString(4, contact);
                pstmt.setString(5, address);
                int aa = pstmt.executeUpdate();

                if (aa == 1) {
                    JOptionPane.showMessageDialog(null, "Your data was successfully inserted.");
                    this.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Please fill in all details carefully.");
                }
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        if (e.getSource() == bt2) {
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new AddLibrarian().setVisible(true);
    }
}
