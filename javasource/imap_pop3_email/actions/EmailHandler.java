package imap_pop3_email.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderNotFoundException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;

import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.logging.ILogNode;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

import imap_pop3_email.proxies.Attachment;
import imap_pop3_email.proxies.AttachmentPosition;
import imap_pop3_email.proxies.EmailAccount;
import imap_pop3_email.proxies.EmailMessage;
import imap_pop3_email.proxies.Protocol;

public class EmailHandler
{
	protected static ILogNode log = Core.getLogger("Email_IMAPPOP3");
	private final Store store;
	private Folder folder = null;
	private final EmailAccount account;
	private final IContext context;

	public EmailHandler(EmailAccount account, IContext context) throws MessagingException, CoreException
			
	{
		if (account.getServerProtocol() != null)
		{
			this.account = account;
			this.context = context;
			// Initialize session to server.
			Properties props = new Properties();
			props.put("mail.user", account.getUsername());

			switch (account.getServerProtocol())
			{
				case IMAPS:
					props.put("mail.store.protocol", "imaps");
					props.put("mail.imaps.host", account.getServerHost());
					props.put("mail.imaps.port", account.getServerPort());
					props.put("mail.imaps.connectionpoolsize", 20);
					props.put("mail.imaps.partialfetch", false);
					if(account.getTimeout()>0) {
						props.put("mail.imaps.timeout", account.getTimeout());
					}
					
					break;
				case IMAP:
					props.put("mail.store.protocol", "imap");
					props.put("mail.imap.host", account.getServerHost());
					props.put("mail.imap.port", account.getServerPort());
					props.put("mail.imap.connectionpoolsize", 20);
					props.put("mail.imap.partialfetch", false);
					if(account.getTimeout()>0) {
						props.put("mail.imap.timeout", account.getTimeout());
					}
					break;
				case POP3S:
					props.put("mail.store.protocol", "pop3s");
					props.put("mail.pop3s.host", account.getServerHost());
					props.put("mail.pop3s.port", account.getServerPort());
					if(account.getTimeout()>0) {
						props.put("mail.pop3s.timeout", account.getTimeout());
					}
					
					break;
				case POP3:
					props.put("mail.store.protocol", "pop3");
					props.put("mail.pop3.host", account.getServerHost());
					props.put("mail.pop3.port", account.getServerPort());
					if(account.getTimeout()>0) {
						props.put("mail.pop3.timeout", account.getTimeout());
					}
					break;
			}

			Session session = Session.getInstance(props, null);
			store = session.getStore();
			store.connect(account.getUsername(), account.getPassword());
		} else
		{
			throw new CoreException("Server protocol is not specified.");
		}
	}

	public boolean isConnected()
	{
		return store.isConnected();
	}
	
	public void closeConnection() throws MessagingException
	{
		store.close();
	}
	
	public boolean customFolderExists () throws MessagingException
	{
		this.folder = store.getFolder(this.account.getFolder());
	
		return folder.exists();

	}
	
	
	/**
	 * - Open the folder to read the emails from. - Process the emails with the
	 * attachments - Execute post handling (Delete or remove emails)
	 * @return
	 * @throws Exception
	 */
	public List<IMendixObject> readEmailMessages() throws Exception
	{
		
		if(!this.customFolderExists())
		{
			throw new FolderNotFoundException(folder);
		}
		
		// Open the emailbox with the needed rights
		List<Message> moveList = null;
		switch (this.account.getHandling())
		{			
			case DeleteMessage:
			case MoveMessage:
				log.debug("Open source folder: "+this.account.getFolder()+" with READ/WRITE rights to move/delete emails.");
				folder.open(Folder.READ_WRITE);
				moveList = new ArrayList<Message>();
				break;
			default:
				log.debug("Open source folder: "+this.account.getFolder()+" with READ rights to pick up emails.");
				folder.open(Folder.READ_ONLY);
				break;
		}

		// Store the retrieved message in a certain cases
		
		int amountEmails = folder.getMessageCount();
		int offset = 1;
		int nrToFetch = 0;
		if (this.account.getUseBatchImport() && amountEmails > this.account.getBatchSize())
		{
			log.debug("There are more emails on the server than the used batch size. The amount that is too much will be imported with the next round. Orginal amount: "
					+ amountEmails
					+ " New amount: "
					+ this.account.getBatchSize());
			
		}		

		if(!this.account.getUseBatchImport() && folder.getMessageCount() > this.account.getBatchSize())
		{
			amountEmails = this.account.getBatchSize() -1;
		}
		
		List<IMendixObject> outputList = new ArrayList<IMendixObject>();
		List<IMendixObject> commitList = new ArrayList<IMendixObject>();
		// Read the mesages from the server
		log.debug("START - Processing a list of incoming emails with a size: " + amountEmails);
		//MimeMessage[] messages = (MimeMessage[]) folder.search(new FlagTerm(new Flags(Flags.Flag.USER), false));
		
		Message[] messages;
		
		Folder moveFolder =null; 
		
		if(this.account.getHandling() ==  imap_pop3_email.proxies.MessageHandling.MoveMessage ) {
			moveFolder= store.getFolder(this.account.getMoveFolder());
			if (!moveFolder.exists())
			{
				log.debug("Create the target folder: " + this.account.getMoveFolder() + ", because it doesn't exist.");
				if(!moveFolder.create(Folder.HOLDS_MESSAGES))
				{
					throw new CoreException("Failed to create the target folder: " + this.account.getMoveFolder());
				}
			}
		}
		
		while(amountEmails > 0){

			nrToFetch = amountEmails < this.account.getBatchSize() ? amountEmails : this.account.getBatchSize();
			messages = folder.getMessages(offset, offset + nrToFetch-1 );

			if (account.getServerProtocol() == Protocol.IMAP || account.getServerProtocol() == Protocol.IMAPS) {
				FetchProfile profile = new FetchProfile();
				profile.add(FetchProfile.Item.ENVELOPE);
				profile.add(FetchProfile.Item.CONTENT_INFO);
				folder.fetch(messages, profile);
			}
			
			for (int i = 0; i <= nrToFetch; i++)
			{
				amountEmails--;
				
				Message email = null;
				try
				{
					email = folder.getMessage(offset);				
					offset++;
					EmailMessage message = new EmailMessage(this.context);
					message.setSize(email.getSize());
					// Save all relevant emailaddresses
					message.setFrom(getEmailAddressList(email.getFrom()));
					message.setTo(getEmailAddressList(email.getRecipients(Message.RecipientType.TO)));
					message.setCC(getEmailAddressList(email.getRecipients(Message.RecipientType.CC)));
					message.setBCC(getEmailAddressList(email.getRecipients(Message.RecipientType.BCC)));
					// Set the senddate from the email
					message.setSenddate(email.getSentDate());
					// Set the subject from the email
					message.setSubject(email.getSubject() != null ? MimeUtility.decodeText(email.getSubject()) : "");
	
					log.debug("Process message nr: " + i + " with subject: " + email.getSubject());
	
					// PROCESS CONTENT AND ATTACHMENTS
					processEmailContent(email, message);
	
					// Add the message to the commitlist
					outputList.add(message.getMendixObject());
					commitList.add(message.getMendixObject());
	
					if (moveList != null)
					{
						moveList.add(email);
					}
				} catch (Exception ex)
				{
					String subject = email != null ? email.getSubject() : "Unknown";
					log.error("Error has occured while processing incoming email: "
							+ subject + ". The email will be hold in the "
							+ this.account.getFolder()
							+ " folder and will processed with the next import.", ex);
				}
	
				// Commit emails in a batch size of 300
				if (commitList.size() > 300)
				{
					Core.commit(this.context, commitList);
					commitList.clear();
				}
			}
			// Commit the retrieved messages
			Core.commit(this.context, outputList);
			commitList.clear();
	
			try
			{
				if(moveList != null && moveList.size() > 0 )
				{
					// Check if any post email processing actions are required.
					switch (this.account.getHandling())
					{
						case MoveMessage:
							// Open the folder to move the retrieved messages to. If not
							// exist: create
							if( moveFolder!=null) {
								if (!moveFolder.isOpen())
								{
									log.debug("Open the target folder: " + this.account.getMoveFolder() + ", because it's closed.");
									moveFolder.open(Folder.READ_WRITE);
								}
								// Parse ArrayList to Message Array
								Message[] messageList = new Message[moveList.size()];
								for(int i = 0; i < messageList.length; i++)
								{
									messageList[i] = moveList.get(i);
								}
								log.debug("START - Moving " + messageList.length + " emails to folder: " + this.account.getMoveFolder() + " with the source folder: " + this.account.getFolder());
		
								if(folder != null && messageList != null && moveFolder != null)
								{
									folder.copyMessages(messageList, moveFolder);
								} else
								{
									String text = (folder != null ? "" : " source folder is empty ") + (messageList != null ? "" : " list of emails is empty ") + (moveFolder != null ? "" : " move folder is empty ");
									throw new CoreException("Failed to move emails to folder: " + this.account.getMoveFolder() + ", because " + text);
								}
							}
							
						case DeleteMessage:
							log.debug("START - Deleting " + moveList.size() + " emails from folder: " + this.account.getFolder());
							for (Message email : moveList)
							{
								log.debug("START - Deleting " + email.getSubject());
								email.setFlag(Flags.Flag.DELETED, true);
							}
							break;
						case NoHandling:
							break;
					}
				}
			} catch (Exception ex)
			{
				log.error("Failed processing the post handling " + this.account.getHandling().toString(), ex);
			}
			
			
			if(moveList != null) moveList.clear();
			if(outputList != null) outputList.clear();
			if(commitList != null) commitList.clear();
		}
		
		if(this.account.getHandling() ==  imap_pop3_email.proxies.MessageHandling.MoveMessage ) {
			if(moveFolder.isOpen()) {
				moveFolder.close(true);
			}
		}
		// Close the connections
		folder.close(true);
		store.close();
		log.debug("END - Finished Processing a list of " + amountEmails + "incoming emails");
		return outputList;
	}

	/**
	 * Process a incoming message. Because Mendix uses OSGi the code must parse
	 * the inpustream to a bodypart to find what type of content it's about.
	 * @param email The email from the IMAP mail server
	 * @param mxMessage The Mendix object to save the email content
	 * @throws MessagingException Exceptoin
	 * @throws IOException Exceptoin
	 */
	private void processEmailContent(Message email, EmailMessage mxMessage) throws MessagingException, IOException
	{
		log.trace("Process email, content type: " + email.getContentType()+" - " + email.getSubject());
		List<IMendixObject> attachmentList = new ArrayList<IMendixObject>();		
		boolean hasHTML = false;
		if(email.isMimeType("text/plain") || email.isMimeType("text/html"))
		{
			// The email plain, plain, flat plain
			try {
				if (email.getSize() > 0) {
					setEmailContent(mxMessage, email.getContent(), email.getContentType());
				}
			} catch (IOException e) {
				// no content
			}

			hasHTML = email.isMimeType("text/html");
		} else
		{							
			if (email.getContentType().contains("multipart")) {
				Multipart multiPart = (Multipart) email.getContent();
				hasHTML = processMultiPart(mxMessage, attachmentList, multiPart);
			} else {
				log.error("Email (" + email.getSubject() + ") contains an invalid content type: " + email.getContentType());
			}
		}

		// Combine the attachments with the email message.
		mxMessage.setisHTML(hasHTML);
		if(this.account.getUseInlineImages() && hasHTML && attachmentList.size() > 0)
		{
			//http://localhost:8080/file?target=window&fileID=126
			String HTML = mxMessage.getContent();
			log.debug("Check for attachments that are inline of the HTML to make it visible in the browser. List size: " + attachmentList.size());
			String URL = "/file?target=window&fileID=";
			for(IMendixObject obj : attachmentList)
			{
				Attachment attach = Attachment.initialize(context, obj);
				if(attach.getPosition() == AttachmentPosition.Inline && attach.getContentID() != null)
				{
					String source = "cid:"+attach.getContentID();
					String target = URL+attach.getFileID();
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
	
	private boolean processMultiPart(EmailMessage mxMessage, List<IMendixObject> attachmentList, Multipart multiPart ) throws MessagingException, IOException
	{
		boolean hasHTML = false;
		log.trace("Process multi part email content type: " + multiPart.getContentType());
		// Process multipart			
		for (int i = 0; i < multiPart.getCount(); i++)
		{
			BodyPart bodyPart = multiPart.getBodyPart(i);
			
			if (bodyPart.getSize() == 0) {
				continue; // skip empty parts
			}
			
			log.trace("Processing first line body part. Content type: " + bodyPart.getContentType());
			// Check what type of content the part is: HTML, PLAIN, ATTACHMENT 
			if (bodyPart.isMimeType("multipart/alternative") || bodyPart.isMimeType("multipart/related"))
			{
				log.trace("-> Process message sub multi part");
				// The content is a variant of HTML
				Multipart subpart = (Multipart) bodyPart.getContent();
				hasHTML = processMultiPart(mxMessage, attachmentList, subpart);
			} else if (bodyPart.isMimeType("text/html") && bodyPart.getFileName()==null)
			{
				setEmailContent(mxMessage, bodyPart.getContent(), bodyPart.getContentType());
				hasHTML = true;
			} else if (bodyPart.isMimeType("text/plain") && hasHTML == false)
			{
				setEmailContent(mxMessage, bodyPart.getContent(), bodyPart.getContentType());
				hasHTML = true;
			} else
			{
				log.trace("Process body part as ATTACHMENT: " + bodyPart.getFileName() + " with size: " + bodyPart.getSize() + " with position: " + bodyPart.getDisposition());
								
				// The content is an attachment
				Attachment attach = new Attachment(this.context);
				attach.setAttachment_EmailMessage(mxMessage);
				attach.setSize(bodyPart.getSize());
				attach.setName(bodyPart.getFileName());				
				Core.storeFileDocumentContent(this.context, attach.getMendixObject(), bodyPart.getInputStream());
				
				if(bodyPart instanceof MimeBodyPart && "inline".equalsIgnoreCase(bodyPart.getDisposition()))
				{
					MimeBodyPart mime = (MimeBodyPart) bodyPart;
					String contentId = mime.getContentID();
					if(contentId != null)
					{
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

	/**
	 * Parse the email content to string to save to the Mendix object. The
	 * Apache IOUtils is used to save the content from an InputStream to String.
	 * @param message The Mendix object to save the email content
	 * @param content Object of the content, can be an InputStream or String
	 * @throws IOException
	 */
	private static void setEmailContent(EmailMessage message, Object content, String contentType) throws IOException
	{
		if (content instanceof InputStream)
		{			
			message.setContent(IOUtils.toString((InputStream) content, getCharSet(contentType)));
		} else if (content instanceof String)
		{
			message.setContent((String) content);
		} else
		{
			log.warn("Retrieved content from an email, but is wasn't possible to determine what type of content it was: " + content.toString() + " with contenttype: " + contentType);
		}
	}
	
	private static String getCharSet(String contentType)
	{
		String charSet = "UTF-8";
		try
		{		
			if(contentType.indexOf("UTF-8") > -1)
			{
				charSet = "UTF-8";
			} else if(contentType.indexOf("ISO-8859-1") > -1)
			{
				charSet = "ISO-8859-1";
			} else if (contentType.indexOf("charset") > -1)
			{
				for(String set : Charset.availableCharsets().keySet())
				{
					if(contentType.indexOf(set) > -1)
					{
						charSet = set;
						break;
					}
				}
			}
		} catch (Exception ex)
		{
			charSet = "UTF-8";
			log.warn("Failed to determine the charset from the email part: " + contentType, ex);
		}
		return charSet;
	}

	/**
	 * Creates a sum of a list of EmailAddresses with a comma separated
	 * @param addresslist
	 * @return A combined string of all EmailAddresses
	 * @throws UnsupportedEncodingException 
	 */
	private static String getEmailAddressList(Address[] addresslist) throws UnsupportedEncodingException
	{
		String output = "";
		if (addresslist != null)
		{
			for (int i = 0; i < addresslist.length; i++)
			{
				String address = addresslist[i].toString();
				if (output.length() + address.length() + 2 < 200)
				{
					output += MimeUtility.decodeText((addresslist[i].toString()));
					if (i < (addresslist.length - 1))
					{
						output += ", ";
					}
				} else
				{
					break;
				}
			}
		}
		return output;
	}

	public Folder[] getFolders() throws MessagingException {
		
		return store.getDefaultFolder().list();
		
	}
}
