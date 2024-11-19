package nl.fontys.indbe.s3cb13_io_room_usage_detection_backend.business.microsoftGraphApi;

import com.microsoft.graph.models.*;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendWarningEarlyVacatedRoom {
    private final GraphServiceClient graphServiceClient;

    public SendWarningEarlyVacatedRoom(GetGraphServiceClient getGraphClient) {
        this.graphServiceClient = getGraphClient.getGraphServiceClient();
    }

    public void sendEmail(String recipientEmail, String recipientName, String senderEmail) {
        String fromEmailId = "Testruimte1.eindhoven@iodigital.com";
        if (senderEmail != null) {
            fromEmailId = senderEmail;
        }

        Message message = new Message() {{
            setSubject("Warning: you left your reserved room early without confirming it.");
            setBody(new ItemBody() {{
                setContentType(BodyType.Html);
                setContent(String.format("""
                    Dear %s,
                    
                    The system has detected you vacated your booked room earlier than the end time you declared when you booked it.
                    Please, next time mark the room as available for others if you vacate the room early.
                    
                    Best regards,
                    
                    iO""", recipientName));
            }});
            setToRecipients(List.of(new Recipient() {{
                setEmailAddress(new EmailAddress() {{
                    setAddress(recipientEmail);
                }});
            }}));
        }};

        com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody sendMailPostRequestBody = new com.microsoft.graph.users.item.sendmail.SendMailPostRequestBody();
        sendMailPostRequestBody.setMessage(message);
        sendMailPostRequestBody.setSaveToSentItems(false);
        graphServiceClient.users().byUserId(fromEmailId).sendMail().post(sendMailPostRequestBody);

        //CC people
//        LinkedList<Recipient> ccRecipientsList = new LinkedList<Recipient>();
//        Recipient ccRecipients = new Recipient();
//        EmailAddress emailAddress1 = new EmailAddress();
//        emailAddress1.address = "def@outlook.com";
//        ccRecipients.emailAddress = emailAddress1;
//        ccRecipientsList.add(ccRecipients);
//        message.ccRecipients = ccRecipientsList;
    }
}
