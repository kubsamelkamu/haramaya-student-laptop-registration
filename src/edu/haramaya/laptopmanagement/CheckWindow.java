package edu.haramaya.laptopmanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CheckWindow extends RegistrationWindow{

    // checkWindow Buttons
    protected JButton enterButton;
    protected JButton formatButton;

    // constructor
    public CheckWindow(){
        enterButton= new JButton("Search");
        formatButton=new JButton("Format");
    }
    public static void openRegistrationCheckWindow() {

        // object creation
        RegistrationWindow registrationWindow = new CheckWindow();
        CheckWindow checkWindow = new CheckWindow();

        JFrame thirdWindow = new JFrame("laptop management system");
        thirdWindow.getContentPane().setBackground(new Color(0x2ecc71));
        thirdWindow.setSize(1000, 700);

        JLabel inputCheck = new JLabel("ENTER SERIAL NUMBER");
        inputCheck.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
        thirdWindow.getContentPane().setLayout(null);
        inputCheck.setForeground(new Color(0xffffff));
        inputCheck.setBounds(382, 230, 310, 30);
        thirdWindow.add(inputCheck);

        JTextField serialField = new JTextField();
        serialField.setBounds(403, 290, 200, 30);
        thirdWindow.add(serialField);

        checkWindow.enterButton.setBounds(380, 340, 100, 30);
        registrationWindow.exitButton.setBounds(520, 400, 100, 30);
        registrationWindow.exitButton.setFocusable(false);
        registrationWindow.backButton.setBounds(10, 15, 80, 30);
        registrationWindow.backButton.setFocusable(false);
        registrationWindow.deleteButton.setBounds(380, 400, 100, 30);
        checkWindow.formatButton.setBounds(520, 340, 100, 30);

        thirdWindow.add(checkWindow.enterButton);
        thirdWindow.add(registrationWindow.exitButton);
        thirdWindow.add(registrationWindow.backButton);
        thirdWindow.add(registrationWindow.deleteButton);
        thirdWindow.add(checkWindow.formatButton);


       JLabel deleted = new JLabel("SUCCESSFULLY DELETED !!");
        deleted.setBounds(390, 550, 400, 50);

        deleted.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        deleted.setForeground(new Color(0Xffffff));
        deleted.setVisible(false);
        thirdWindow.add(deleted);

        JLabel format = new JLabel("SUCCESSFULLY FORMAT !!");
        deleted.setBounds(390, 550, 400, 50);
        format.setFont(new Font("SANS_SERIF", Font.BOLD, 20));
        format.setForeground(new Color(0Xffffff));
        format.setVisible(false);
        thirdWindow.add(format);

        registrationWindow.backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaptopManagement.openRegistrationFirstWindow();
                thirdWindow.dispose();
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pcSerial = Pc_serialField.getText();
                String url = "jdbc:sqlite:student.db";

                try (Connection conn = DriverManager.getConnection(url);
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM student WHERE Pc_serial = ?")) {

                    stmt.setString(1, pcSerial);
                    ResultSet rs = stmt.executeQuery();

                    if (rs.next()) {
                        String department = rs.getString("Department");
                        String laptop = rs.getString("Laptop");
                        int contact = rs.getInt("Contact");
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String studentId = rs.getString("student_id");
                        int age = rs.getInt("age");
                        char gender = rs.getString("Gender").charAt(0);

                        // Display student information using GUI
                        JOptionPane.showMessageDialog(null,
                                "Student Information:\n\n" +
                                        "First Name: " + firstName + "\n" +
                                        "Last Name: " + lastName + "\n" +
                                        "Student ID: " + studentId + "\n" +
                                        "Laptop: " + laptop + "\n" +
                                        "Department: " + department + "\n" +
                                        "Contact: " + contact + "\n" +
                                        "Age: " + age + "\n" +
                                        "Gender: " + gender);
                    } else {
                        JOptionPane.showMessageDialog(null, "No student found with the specified serial number.");
                    }

                } catch (SQLException s) {
                    System.err.format("An error occurred: %s", s.getMessage());
                }
            }
        });

            deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String URL = "jdbc:sqlite:student.db";
                String pcSerial = Pc_serialField.getText();

                try (Connection conn = DriverManager.getConnection(URL);
                     Statement stmt = conn.createStatement()) {
                    int rowCount = stmt.executeUpdate("DELETE FROM student WHERE Pc_serial = " + pcSerial);

                    if (rowCount > 0) {
                        System.out.println("Student deleted successfully.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No student found with the given serial number.");

                    }
                } catch (SQLException f) {
                    JOptionPane.showMessageDialog(null, "An error occurred while deleting student data.");
                    f.printStackTrace();
                }
            }
        });

        formatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String URL = "jdbc:sqlite:student.db";


                String sql = "DROP TABLE student";

                try {
                    Connection connection = DriverManager.getConnection(URL);

                    Statement statement = connection.createStatement();

                    statement.executeUpdate(sql);

                    statement.close();
                    connection.close();

                    JOptionPane.showMessageDialog(null, "successfully Formated\n" +
                            "           \uD83D\uDE2D.");

                } catch (SQLException u) {
                    System.out.println("Error dropping table: " + u.getMessage());
                }
            }
        });

        registrationWindow.exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        thirdWindow.setVisible(true);
    }
}
