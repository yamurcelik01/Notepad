package project;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginPage extends JFrame implements ActionListener{
	
	 private JTextField usernameField;
	    private JPasswordField passwordField;
	    private JButton loginButton;
	    

	    public LoginPage() {
	        super("Giriş Yap");

	        // Kullanıcı adı etiketi ve giriş alanı
	        JPanel usernamePanel = new JPanel(new BorderLayout());
	        JLabel usernameLabel = new JLabel("User Name:");
	        usernameField = new JTextField(20);
	        usernamePanel.add(usernameLabel, BorderLayout.WEST);
	        usernamePanel.add(usernameField, BorderLayout.CENTER);

	        // Şifre etiketi ve giriş alanı
	        JPanel passwordPanel = new JPanel(new BorderLayout());
	        JLabel passwordLabel = new JLabel("Password:");
	        passwordField = new JPasswordField(20);
	        passwordPanel.add(passwordLabel, BorderLayout.WEST);
	        passwordPanel.add(passwordField, BorderLayout.CENTER);

	        // Giriş düğmesi
	        loginButton = new JButton("Enter");
	        loginButton.addActionListener(this);

	        // Ana paneli oluşturma ve bileşenleri eklenme
	        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
	        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        mainPanel.add(usernamePanel, BorderLayout.NORTH);
	        mainPanel.add(passwordPanel, BorderLayout.CENTER);
	        mainPanel.add(loginButton, BorderLayout.SOUTH);
	        
	       

	        // Pencereye ana paneli ekleme
	        add(mainPanel);

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        pack();
	        setLocationRelativeTo(null);
	    }

	    public void actionPerformed(ActionEvent e) {
	        String username = usernameField.getText();
	        String password = new String(passwordField.getPassword());

	        if (checkCredentials(username, password)) {
	            // Kullanıcı adı ve şifre doğruysa GUI penceresini aç
	            GUI gui = new GUI();
	            gui.setVisible(true);
	            dispose(); // LoginPage penceresini kapat
	        } else {
	            // Kullanıcı adı veya şifre yanlışsa hata mesajı göster
	            JOptionPane.showMessageDialog(this, "invalid username or password", "error", JOptionPane.ERROR_MESSAGE);
	        }
	    }

	    private boolean checkCredentials(String username, String password) {
	       
	        return username.equals("yby") && password.equals("1234");
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                LoginPage loginPage = new LoginPage();
	                loginPage.setVisible(true);
	            }
	        });
	    }
	}


