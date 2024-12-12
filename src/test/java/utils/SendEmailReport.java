package utils;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import tests.ReportData;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.List;
import java.util.Properties;

public class SendEmailReport {
    public static void sendReport(String filePath, List<ReportData> scenarioTest) throws MessagingException {
        Properties props = new Properties();
        Dotenv dotenv = Dotenv.load();
        String username = dotenv.get("mail.username");
        String password = dotenv.get("mail.password");

        for (DotenvEntry e : dotenv.entries()) {
            if (e.getKey().contains("mail.smtp.") || e.getKey().contains("mail.")) {
                props.put(e.getKey(), e.getValue());
            }
        }

        // Authenticate the email account
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Compose the email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(dotenv.get("mail.from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(dotenv.get("mail.to")));
        message.setSubject(dotenv.get("mail.subject"));

        // Email body text
        String emailBodyContent = AutomationReport.createEmailBody(scenarioTest);
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailBodyContent, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // Attach the report
        messageBodyPart = new MimeBodyPart();
        String filename = dotenv.get("mail.attachmentPath");
        DataSource source = new FileDataSource(filePath);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filePath);
        multipart.addBodyPart(messageBodyPart);

        // Combine message parts
        message.setContent(multipart);
        // Send the email
        Transport.send(message);
    }
}
