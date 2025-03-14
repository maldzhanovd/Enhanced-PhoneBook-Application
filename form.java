import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class form extends JFrame {
    private JPanel panel;
    private JTextField nameField, phoneField, emailField, addressField;
    private JTextArea contactsArea;
    private JButton addButton, showButton, deleteButton, updateButton;

    public form() {
        // Set up JFrame
        setTitle("Phonebook Application");
        setSize(400, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null); // Manual positioning

        // Labels and Text Fields
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(20, 20, 80, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(100, 20, 250, 25);
        add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(20, 60, 80, 25);
        add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(100, 60, 250, 25);
        add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 80, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(100, 100, 250, 25);
        add(emailField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(20, 140, 80, 25);
        add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(100, 140, 250, 25);
        add(addressField);

        // Buttons
        addButton = new JButton("Add Contact");
        addButton.setBounds(20, 180, 150, 30);
        add(addButton);

        showButton = new JButton("Show Contacts");
        showButton.setBounds(200, 180, 150, 30);
        add(showButton);

        updateButton = new JButton("Update Contact");
        updateButton.setBounds(20, 220, 150, 30);
        add(updateButton);

        deleteButton = new JButton("Delete Contact");
        deleteButton.setBounds(200, 220, 150, 30);
        add(deleteButton);

        // Contacts Display Area
        contactsArea = new JTextArea();
        contactsArea.setBounds(20, 270, 330, 150);
        contactsArea.setEditable(false);
        add(contactsArea);

        // Button Actions
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String address = addressField.getText();

                if (!name.isEmpty() && !phone.isEmpty()) {
                    Connect.addContact(name, phone, email, address);
                    contactsArea.setText("Contact Added!");
                    clearFields();
                } else {
                    JOptionPane.showMessageDialog(null, "Name and Phone are required!");
                }
            }
        });

        showButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<String> contacts = Connect.getContacts();

                if (contacts.isEmpty()) {
                    contactsArea.setText("No contacts found in the database.");
                } else {
                    contactsArea.setText("");
                    for (String contact : contacts) {
                        contactsArea.append(contact + "\n");
                    }
                }
            }
        });


        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = JOptionPane.showInputDialog("Enter Contact ID to Delete:");
                if (idText != null && !idText.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idText);
                        Connect.deleteContact(id);
                        contactsArea.setText("Contact Deleted!");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid ID! Please enter a number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID is required to delete a contact!");
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = JOptionPane.showInputDialog("Enter Contact ID to Update:");
                if (idText != null && !idText.isEmpty()) {
                    try {
                        int id = Integer.parseInt(idText);
                        String newName = JOptionPane.showInputDialog("Enter New Name (Leave blank to keep current):");
                        String newPhone = JOptionPane.showInputDialog("Enter New Phone (Leave blank to keep current):");
                        String newEmail = JOptionPane.showInputDialog("Enter New Email (Leave blank to keep current):");
                        String newAddress = JOptionPane.showInputDialog("Enter New Address (Leave blank to keep current):");

                        Connect.updateContact(id, newName, newPhone, newEmail, newAddress);
                        contactsArea.setText("Contact Updated!");

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Invalid ID! Please enter a number.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID is required to update a contact!");
                }
            }
        });

        setVisible(true);
    }

    private void clearFields() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        addressField.setText("");
    }
}
