package imap_pop3_email.actions;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import imap_pop3_email.proxies.*;

import javax.mail.Folder;
import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import static imap_pop3_email.proxies.MessageHandling.DeleteMessage;
import static imap_pop3_email.proxies.MessageHandling.MoveMessage;

public class EmailHandler {
  protected static ILogNode log = Core.getLogger("Email_IMAPPOP3");
  private final Store store;
  private final EmailAccount account;
  private final IContext context;

  EmailHandler(EmailAccount account, IContext context) throws MessagingException, CoreException {
    if (account.getServerProtocol() == null)
      throw new CoreException("Server protocol is not specified.");

    this.account = account;
    this.context = context;
    // Initialize session to server.
    Properties props = new Properties();
    props.put("mail.user", account.getUsername());

    switch (account.getServerProtocol()) {
      case IMAPS:
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imaps.host", account.getServerHost());
        props.put("mail.imaps.port", account.getServerPort());
        props.put("mail.imaps.connectionpoolsize", 20);
        props.put("mail.imaps.partialfetch", false);
        if (account.getTimeout() > 0) {
          props.put("mail.imaps.timeout", account.getTimeout());
        }

        break;
      case IMAP:
        props.put("mail.store.protocol", "imap");
        props.put("mail.imap.host", account.getServerHost());
        props.put("mail.imap.port", account.getServerPort());
        props.put("mail.imap.connectionpoolsize", 20);
        props.put("mail.imap.partialfetch", false);
        if (account.getTimeout() > 0) {
          props.put("mail.imap.timeout", account.getTimeout());
        }
        break;
      case POP3S:
        props.put("mail.store.protocol", "pop3s");
        props.put("mail.pop3s.host", account.getServerHost());
        props.put("mail.pop3s.port", account.getServerPort());
        if (account.getTimeout() > 0) {
          props.put("mail.pop3s.timeout", account.getTimeout());
        }

        break;
      case POP3:
        props.put("mail.store.protocol", "pop3");
        props.put("mail.pop3.host", account.getServerHost());
        props.put("mail.pop3.port", account.getServerPort());
        if (account.getTimeout() > 0) {
          props.put("mail.pop3.timeout", account.getTimeout());
        }
        break;
    }

    Session session = Session.getInstance(props, null);
    store = session.getStore();
    store.connect(account.getUsername(), account.getPassword());
  }

  public boolean isConnected() {
    return store.isConnected();
  }

  void closeConnection() throws MessagingException {
    store.close();
  }

  public Folder[] getFolders() throws MessagingException {
    return store.getDefaultFolder().list();
  }

  private Folder getFolder() throws MessagingException {
    Folder folder = store.getFolder(account.getFolder());
    if (!folder.exists())
      throw new FolderNotFoundException(folder);

    return folder;
  }

  private void openFolder(Folder folder) throws MessagingException {
    switch (account.getHandling()) {
      case DeleteMessage:
      case MoveMessage:
        log.debug("Open source folder: " + this.account.getFolder() + " with READ/WRITE rights to move/delete emails.");
        folder.open(Folder.READ_WRITE);
        break;
      default:
        log.debug("Open source folder: " + this.account.getFolder() + " with READ rights to pick up emails.");
        folder.open(Folder.READ_ONLY);
        break;
    }
  }

  private int emailAmounts(Folder folder) throws MessagingException {
    int messageCount = folder.getMessageCount();
    if (account.getUseBatchImport() && messageCount > account.getBatchSize()) {
      log.debug("There are more emails on the server than the used batch size. " +
              "The amount that is too much will be imported with the next round. " +
              "Original amount: " + messageCount + " New amount: " + account.getBatchSize());
    }

    if (!account.getUseBatchImport() && messageCount > account.getBatchSize()) {
      return account.getBatchSize();
    }

    return messageCount;
  }

  private Folder getMoveFolder() throws MessagingException, CoreException {
    if (account.getHandling() != MoveMessage)
      return null;

    Folder moveFolder = store.getFolder(account.getMoveFolder());
    if (!moveFolder.exists()) {
      log.debug("Create the target folder: " + account.getMoveFolder() + ", because it doesn't exist.");
      if (!moveFolder.create(Folder.HOLDS_MESSAGES)) {
        throw new CoreException("Failed to create the target folder: " + account.getMoveFolder());
      }
    }

    return moveFolder;
  }

  private void fetchMessages(Folder folder, int offset, int numberToFetch) throws MessagingException {
    Message[] messages = folder.getMessages(offset, offset + numberToFetch - 1);

    if (account.getServerProtocol() == Protocol.IMAP || account.getServerProtocol() == Protocol.IMAPS) {
      FetchProfile profile = new FetchProfile();
      profile.add(FetchProfile.Item.ENVELOPE);
      profile.add(FetchProfile.Item.CONTENT_INFO);
      folder.fetch(messages, profile);
    }
  }

  private EmailMessage toEmailMessage(Message email) throws MessagingException, UnsupportedEncodingException {
    EmailMessage message = new EmailMessage(context);
    message.setSize(email.getSize());
    message.setFrom(toCommaSeparated(email.getFrom()));
    message.setTo(toCommaSeparated(email.getRecipients(Message.RecipientType.TO)));
    message.setCC(toCommaSeparated(email.getRecipients(Message.RecipientType.CC)));
    message.setBCC(toCommaSeparated(email.getRecipients(Message.RecipientType.BCC)));
    message.setSenddate(email.getSentDate());
    message.setSubject(email.getSubject() != null ? MimeUtility.decodeText(email.getSubject()) : "");

    return message;
  }

  private void moveMessages(List<Message> messages, Folder source, Folder target) throws MessagingException {
    if (!target.isOpen()) {
      log.debug("Open the target folder: " + target.getFullName() + ", because it's closed.");
      target.open(Folder.READ_WRITE);
    }

    log.debug("START - Moving " + messages.size() + " emails to folder: " + target.getFullName() +
            " with the source folder: " + source.getFullName());
    source.copyMessages(messages.toArray(new Message[0]), target);

    target.close(true);
  }

  private void deleteMessages(List<Message> messages) throws MessagingException {
    log.debug("START - Deleting " + messages.size() + " emails from folder: " + account.getFolder());
    for (Message message : messages) {
      log.debug("START - Deleting " + message.getSubject());
      message.setFlag(Flags.Flag.DELETED, true);
    }
  }

  /**
   * - Open the folder to read the emails from. - Process the emails with the
   * attachments - Execute post handling (Delete or remove emails)
   *
   * @return A list of Email Messages
   */
  List<IMendixObject> readEmailMessages() throws MessagingException, CoreException {
    // Open the emailbox with the needed rights
    Folder folder = getFolder();
    openFolder(folder);

    int amountEmails = emailAmounts(folder);
    List<Message> moveList = new ArrayList<>();
    List<IMendixObject> commitList = new ArrayList<>();
    List<IMendixObject> outputList = new ArrayList<>();
    Folder moveFolder = getMoveFolder();

    log.debug("START - Processing a list of incoming emails with a size: " + amountEmails);

    int offset = 1;
    while (amountEmails > 0) {
      int nrToFetch = amountEmails < account.getBatchSize() ? amountEmails : account.getBatchSize();
      fetchMessages(folder, offset, nrToFetch);

      for (int i = 0; i < nrToFetch; i++, amountEmails--) {
        Message email = null;
        try {
          email = folder.getMessage(offset++);
          EmailMessage message = toEmailMessage(email);

          log.debug("Process message nr: " + i + " with subject: " + email.getSubject());
          processEmailContent(email, message);

          if (account.getHandling() == DeleteMessage || account.getHandling() == MoveMessage)
            moveList.add(email);
          commitList.add(message.getMendixObject());
          outputList.add(message.getMendixObject());
        } catch (Exception ex) {
          String subject = email != null ? email.getSubject() : "Unknown";
          log.error("Error has occurred while processing incoming email: "
                  + subject + ". The email will be hold in the "
                  + account.getFolder()
                  + " folder and will processed with the next import.", ex);
        }

        // Commit emails in a batch size of 300
        if (commitList.size() > 300) {
          Core.commit(context, commitList);
          commitList.clear();
        }
      }

      // Commit the remainder messages
      Core.commit(context, commitList);
      commitList.clear();

      try {
        if (moveList.size() > 0) {
          // Check if any post email processing actions are required.
          switch (account.getHandling()) {
            case MoveMessage:
              if (moveFolder != null)
                moveMessages(moveList, folder, moveFolder);
              break;
            case DeleteMessage:
              deleteMessages(moveList);
              break;
            case NoHandling:
              break;
          }
        }
      } catch (Exception ex) {
        log.error("Failed processing the post handling " + account.getHandling().toString(), ex);
      }

      moveList.clear();
      commitList.clear();
    }

    folder.close(true);
    store.close();

    log.debug("END - Finished Processing a list of " + amountEmails + " incoming emails");

    return outputList;
  }

  /**
   * Process a incoming message. Because Mendix uses OSGi the code must parse
   * the inpustream to a bodypart to find what type of content it's about.
   *
   * @param email     The email from the IMAP mail server
   * @param mxMessage The Mendix object to save the email content
   */
  private void processEmailContent(Message email, EmailMessage mxMessage) throws MessagingException, IOException {
    log.trace("Process email, content type: " + email.getContentType() + " - " + email.getSubject());
    List<IMendixObject> attachmentList = new ArrayList<>();
    boolean hasHTML = false;
    if (email.isMimeType("text/plain") || email.isMimeType("text/html")) {
      // The email plain, plain, flat plain
      try {
        if (email.getSize() > 0) {
          setEmailContent(mxMessage, email.getContent(), email.getContentType());
        }
      } catch (IOException e) {
        // no content
      }

      hasHTML = email.isMimeType("text/html");
    } else {
      if (email.getContentType().contains("multipart")) {
        Multipart multiPart = (Multipart) email.getContent();
        hasHTML = processMultiPart(mxMessage, attachmentList, multiPart);
      } else {
        log.error("Email (" + email.getSubject() + ") contains an invalid content type: " + email.getContentType());
      }
    }

    // Combine the attachments with the email message.
    mxMessage.setisHTML(hasHTML);
    if (this.account.getUseInlineImages() && hasHTML && attachmentList.size() > 0) {
      //http://localhost:8080/file?target=window&fileID=126
      String HTML = mxMessage.getContent();
      log.debug("Check for attachments that are inline of the HTML to make it visible in the browser. List size: " + attachmentList.size());
      String URL = "/file?target=window&fileID=";
      for (IMendixObject obj : attachmentList) {
        Attachment attach = Attachment.initialize(context, obj);
        if (attach.getPosition() == AttachmentPosition.Inline && attach.getContentID() != null) {
          String source = "cid:" + attach.getContentID();
          String target = URL + attach.getFileID();
          log.trace("Replace value with: " + source + " to: " + target);
          HTML = HTML.replaceAll(source, target);
        }
      }
      if (HTML != null) {
        mxMessage.setContent(MimeUtility.decodeText(HTML));
      }
    }

    mxMessage.setAttachmentAmount(attachmentList.size());
    mxMessage.sethasAttachments(attachmentList.size() > 0);
    mxMessage.setEmailMessage_EmailAccount(account);
    Core.commit(this.context, attachmentList);
  }

  private boolean processMultiPart(EmailMessage mxMessage, List<IMendixObject> attachmentList, Multipart multiPart) throws MessagingException, IOException {
    boolean hasHTML = false;
    log.trace("Process multi part email content type: " + multiPart.getContentType());
    // Process multipart
    for (int i = 0; i < multiPart.getCount(); i++) {
      BodyPart bodyPart = multiPart.getBodyPart(i);

      if (bodyPart.getSize() == 0) {
        continue; // skip empty parts
      }

      log.trace("Processing first line body part. Content type: " + bodyPart.getContentType());
      // Check what type of content the part is: HTML, PLAIN, ATTACHMENT
      if (bodyPart.isMimeType("multipart/alternative") || bodyPart.isMimeType("multipart/related")) {
        log.trace("-> Process message sub multi part");
        // The content is a variant of HTML
        Multipart subpart = (Multipart) bodyPart.getContent();
        hasHTML = processMultiPart(mxMessage, attachmentList, subpart);
      } else if (bodyPart.isMimeType("text/html") && bodyPart.getFileName() == null) {
        setEmailContent(mxMessage, bodyPart.getContent(), bodyPart.getContentType());
        hasHTML = true;
      } else if (bodyPart.isMimeType("text/plain") && !hasHTML) {
        setEmailContent(mxMessage, bodyPart.getContent(), bodyPart.getContentType());
        hasHTML = true;
      } else {
        log.trace("Process body part as ATTACHMENT: " + bodyPart.getFileName() + " with size: " + bodyPart.getSize() + " with position: " + bodyPart.getDisposition());

        // The content is an attachment
        Attachment attach = new Attachment(this.context);
        attach.setAttachment_EmailMessage(mxMessage);
        attach.setSize((long) bodyPart.getSize());
        attach.setName(bodyPart.getFileName());
        Core.storeFileDocumentContent(this.context, attach.getMendixObject(), bodyPart.getInputStream());

        if (bodyPart instanceof MimeBodyPart && "inline".equalsIgnoreCase(bodyPart.getDisposition())) {
          MimeBodyPart mime = (MimeBodyPart) bodyPart;
          String contentId = mime.getContentID();
          if (contentId != null) {
            contentId = contentId.replaceAll("<", "").replaceAll(">", "");
          }
          attach.setContentID(contentId);
          attach.setPosition(AttachmentPosition.Inline);
        }
        attachmentList.add(attach.getMendixObject());
      }
    }

    return hasHTML;
  }

  private static void setEmailContent(EmailMessage message, Object content, String contentType) {
    if (content instanceof InputStream) {
      message.setContent(mkString((InputStream) content, getCharSet(contentType)));
    } else if (content instanceof String) {
      message.setContent((String) content);
    } else {
      log.warn("Retrieved content from an email, but is wasn't possible to determine what type of content it was: " + content.toString() + " with contenttype: " + contentType);
    }
  }

  private static String mkString(InputStream inputStream, String charset) {
    try (Scanner scanner = new Scanner(inputStream, charset)) {
      return scanner.useDelimiter("\\A").next();
    }
  }

  private static String getCharSet(String contentType) {
    String charSet = "UTF-8";
    try {
      if (contentType.contains("UTF-8")) {
        charSet = "UTF-8";
      } else if (contentType.contains("ISO-8859-1")) {
        charSet = "ISO-8859-1";
      } else if (contentType.contains("charset")) {
        for (String set : Charset.availableCharsets().keySet()) {
          if (contentType.contains(set)) {
            charSet = set;
            break;
          }
        }
      }
    } catch (Exception ex) {
      charSet = "UTF-8";
      log.warn("Failed to determine the charset from the email part: " + contentType, ex);
    }

    return charSet;
  }

  private static String toCommaSeparated(Address[] addresses) throws UnsupportedEncodingException {
    String output = "";
    if (addresses != null) {
      for (int i = 0; i < addresses.length; i++) {
        String address = addresses[i].toString();
        if (output.length() + address.length() + 2 < 200) {
          output += MimeUtility.decodeText((addresses[i].toString()));
          if (i < (addresses.length - 1)) {
            output += ", ";
          }
        } else {
          break;
        }
      }
    }

    return output;
  }
}
