package mailerUtility;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Mailer {
	private String EMAIL_SUBJECT = "Test Send Email via SMTP";
	private String EMAIL_TEXT = "Hello Java Mail \n ABC123";
	// for example, smtp.mailgun.org
	private String SMTP_SERVER = "";
	private String USERNAME = "";
	private String PASSWORD = "";
	private int PORT;
	private String EMAIL_FROM = "From@gmail.com";
	private String EMAIL_TO = "subbug12@gmail.com";
	private String EMAIL_TO_CC = "";

	public String getSMTP_SERVER() {
		return SMTP_SERVER;
	}

	public void setSMTP_SERVER(String sMTP_SERVER) {
		SMTP_SERVER = sMTP_SERVER;
	}

	public String getUSERNAME() {
		return USERNAME;
	}

	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public Integer getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public String getEMAIL_FROM() {
		return EMAIL_FROM;
	}

	public void setEMAIL_FROM(String eMAIL_FROM) {
		EMAIL_FROM = eMAIL_FROM;
	}

	public String getEMAIL_TO() {
		return EMAIL_TO;
	}

	public void setEMAIL_TO(String eMAIL_TO) {
		EMAIL_TO = eMAIL_TO;
	}

	public String getEMAIL_TO_CC() {
		return EMAIL_TO_CC;
	}

	public void setEMAIL_TO_CC(String eMAIL_TO_CC) {
		EMAIL_TO_CC = eMAIL_TO_CC;
	}

	public String getEMAIL_SUBJECT() {
		return EMAIL_SUBJECT;
	}

	public void setEMAIL_SUBJECT(String eMAIL_SUBJECT) {
		EMAIL_SUBJECT = eMAIL_SUBJECT;
	}

	public String getEMAIL_TEXT() {
		return EMAIL_TEXT;
	}

	public void setEMAIL_TEXT(String eMAIL_TEXT) {
		EMAIL_TEXT = eMAIL_TEXT;
	}

	public Mailer() {// default gmail
		this.SMTP_SERVER = "smtp.gmail.com";
		this.USERNAME = "gopalasubrahmanyamyedida0789@gmail.com";
		this.PASSWORD = "dob06121997*Y";
		this.PORT = 587;
	}

	public Mailer(String server, String user, String passw, Integer port) {
		this.SMTP_SERVER = server;
		this.USERNAME = user;
		this.PASSWORD = passw;
		this.PORT = port;
	}

	public Session configure() {
		Mailer m = new Mailer();
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", m.getSMTP_SERVER()); // optional, defined in
		prop.put("mail.smtp.user", m.getUSERNAME());
		prop.put("mail.smtp.password", m.getPASSWORD());
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.port", m.getPORT().toString()); // default port 25
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(m.getUSERNAME(), m.getPASSWORD());
			}
		});

		System.out.println(session);
		return session;
	}

	public void sendMEssage(Session session, String subject, String message) {
		Message msg = new MimeMessage(session);

		try {
			msg.setFrom(new InternetAddress(EMAIL_FROM));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(EMAIL_TO, false));
			// msg.setRecipients(Message.RecipientType.CC,
			// InternetAddress.parse(EMAIL_TO_CC, false));
			msg.setSubject(subject);
			msg.setText(message);
			msg.setSentDate(new Date());
			SMTPTransport t = (SMTPTransport) session.getTransport("smtp");

			t.connect(SMTP_SERVER, session.getProperty("mail.smtp.user"), session.getProperty("mail.smtp.password"));
			t.sendMessage(msg, msg.getAllRecipients());
			System.out.println("Response: " + t.getLastServerResponse());

			t.close();
			System.out.println(session.getProperties());
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public Session configure(String server, String user, String passw, Integer port) {
		Mailer m = new Mailer(server, user, passw, port);
		Properties prop = System.getProperties();
		prop.put("mail.smtp.host", m.getSMTP_SERVER()); // optional, defined in
														// SMTPTransport
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.port", m.getPORT().toString()); // default port 25

		Session session = Session.getInstance(prop, null);
		return session;
	}

}