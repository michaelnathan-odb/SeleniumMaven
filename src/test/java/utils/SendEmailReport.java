package utils;

import com.aventstack.extentreports.ExtentTest;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SendEmailReport {
    public static void sendReport(String filePath, List<ExtentTest> scenarioTest) throws MessagingException, IOException {
        // Load properties from config.properties
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            props.load(input);
        }

        // Setup mail server properties
        props.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
        props.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
        props.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
        props.put("mail.smtp.starttls.enable", props.getProperty("mail.smtp.starttls.enable"));

        // Authenticate the email account
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        props.getProperty("mail.username"),
                        props.getProperty("mail.password")
                );
            }
        });

        // Compose the email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(props.getProperty("mail.from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(props.getProperty("mail.to")));
        message.setSubject(props.getProperty("mail.subject"));


        // Email body text
        String emailBodyContent = AutomationReport.createEmailBody(scenarioTest);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailBodyContent, "text/html");
        //TODO: Customize the email body using string layout for reporting string to email

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Attach the report
        messageBodyPart = new MimeBodyPart();
        String filename = props.getProperty("mail.attachmentPath");
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        // Combine message parts
        message.setContent(multipart);

        // Send the email
        Transport.send(message);

        System.out.println("Email sent successfully!");
    }
}
