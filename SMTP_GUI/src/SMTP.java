import java.awt.EventQueue;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JTextField;

import org.omg.CORBA.TCKind;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;


public class SMTP 
{

    private JFrame frame;
    private JTextField txt_to;
    private JTextField txt_subject;
    private JTextField txt_mail;

    /**
     * Launch the application.
     */
    static final String username = "Your Email Address";
    static final String password = "Password";
    static int count = 0;
    static boolean result = true;
    public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					smtp window = new smtp();
					window.frame.setVisible(true);
				}
				catch(Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
    }

    /**
     * Create the application.
     */
    public smtp() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 488, 413);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	JButton btnSend = new JButton("Send");
	btnSend.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent e) 
		{
		    Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");
			Session session = Session.getInstance(props,
			new javax.mail.Authenticator()
			{
				protected PasswordAuthentication getPasswordAuthentication()
				{
					return new PasswordAuthentication(username, password);
				}
			 });
			try
			{
			    String message_GUI = txt_mail.getText();
				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(txt_to.getText()));
				message.setSubject(txt_subject.getText());
				message.setText(message_GUI);
				isValidEmailAddress(username,txt_to.getText());
				if(result == true)
				{
				  //  Thread.sleep(2000);
				    JOptionPane.showMessageDialog(null,"Message Sent","Succesful",JOptionPane.INFORMATION_MESSAGE);
				    Transport.send(message);
				    clean();
				}
			}
			catch (MessagingException e1)
			{
				JOptionPane.showMessageDialog(null,"Error","Unsuccesful",JOptionPane.ERROR_MESSAGE);
				clean();
				throw new RuntimeException(e1);
			}
		}
		public void  clean()
		{
		 txt_mail.setText(" ");   
		 txt_subject.setText(" ");   
		 txt_to.setText(" ");   
		}
		public boolean isValidEmailAddress(String from,String recipient)
		{
			try
			{
			      InternetAddress emailAddr = new InternetAddress(from);
			      emailAddr.validate();
			      if(result == true)
			      {
				      InternetAddress emailAddr1 = new InternetAddress(recipient);
				      emailAddr1.validate(); 
				      clean();
			      }
			   }
			   catch (AddressException ex)
			   {
			       result = false;
			       JOptionPane.showMessageDialog(null,"@ || another character is not existent","Unsuccesful",JOptionPane.ERROR_MESSAGE);
			       clean();
			   }
			   return result;
		    }
	});
	btnSend.setBounds(37, 241, 89, 23);
	frame.getContentPane().add(btnSend);
	
	JButton btnCancel = new JButton("Cancel");
	btnCancel.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) 
		{
		    System.exit(0);
		}
	});
	
	btnCancel.setBounds(332, 241, 89, 23);
	frame.getContentPane().add(btnCancel);
	
	JLabel lblMailThruSmtp = new JLabel("Mail Thru SMTP Transfer\r\n");
	lblMailThruSmtp.setFont(new Font("Tahoma", Font.BOLD, 15));
	lblMailThruSmtp.setBounds(113, 11, 184, 14);
	frame.getContentPane().add(lblMailThruSmtp);
	
	JLabel label = new JLabel("To");
	label.setFont(new Font("Tahoma", Font.BOLD, 15));
	label.setBounds(37, 64, 80, 14);
	frame.getContentPane().add(label);
	
	JLabel lblSubject = new JLabel("Subject");
	lblSubject.setFont(new Font("Tahoma", Font.BOLD, 15));
	lblSubject.setBounds(37, 118, 80, 14);
	frame.getContentPane().add(lblSubject);
	
	JLabel lblMailContnet = new JLabel("Mail Content");
	lblMailContnet.setFont(new Font("Tahoma", Font.BOLD, 15));
	lblMailContnet.setBounds(37, 166, 101, 14);
	frame.getContentPane().add(lblMailContnet);
	
	txt_to = new JTextField();
	txt_to.setColumns(10);
	txt_to.setBounds(284, 61, 137, 24);
	frame.getContentPane().add(txt_to);
	
	txt_subject = new JTextField();
	txt_subject.setColumns(10);
	txt_subject.setBounds(284, 115, 137, 24);
	frame.getContentPane().add(txt_subject);
	
	txt_mail = new JTextField();
	txt_mail.setColumns(10);
	txt_mail.setBounds(284, 163, 137, 24);
	frame.getContentPane().add(txt_mail);
    } 
}
