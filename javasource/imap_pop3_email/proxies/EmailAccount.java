// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package imap_pop3_email.proxies;

public class EmailAccount
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject emailAccountMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "IMAP_POP3_email.EmailAccount";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		ServerProtocol("ServerProtocol"),
		ServerHost("ServerHost"),
		ServerPort("ServerPort"),
		Username("Username"),
		Password("Password"),
		UseDefaultFolder("UseDefaultFolder"),
		Folder("Folder"),
		UseBatchImport("UseBatchImport"),
		BatchSize("BatchSize"),
		Handling("Handling"),
		MoveFolder("MoveFolder"),
		UseInlineImages("UseInlineImages"),
		Timeout("Timeout");

		private java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public EmailAccount(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "IMAP_POP3_email.EmailAccount"));
	}

	protected EmailAccount(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject emailAccountMendixObject)
	{
		if (emailAccountMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("IMAP_POP3_email.EmailAccount", emailAccountMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a IMAP_POP3_email.EmailAccount");

		this.emailAccountMendixObject = emailAccountMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'EmailAccount.load(IContext, IMendixIdentifier)' instead.
	 */
	@Deprecated
	public static imap_pop3_email.proxies.EmailAccount initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return imap_pop3_email.proxies.EmailAccount.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static imap_pop3_email.proxies.EmailAccount initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new imap_pop3_email.proxies.EmailAccount(context, mendixObject);
	}

	public static imap_pop3_email.proxies.EmailAccount load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return imap_pop3_email.proxies.EmailAccount.initialize(context, mendixObject);
	}

	public static java.util.List<imap_pop3_email.proxies.EmailAccount> load(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String xpathConstraint) throws com.mendix.core.CoreException
	{
		java.util.List<imap_pop3_email.proxies.EmailAccount> result = new java.util.ArrayList<imap_pop3_email.proxies.EmailAccount>();
		for (com.mendix.systemwideinterfaces.core.IMendixObject obj : com.mendix.core.Core.retrieveXPathQuery(context, "//IMAP_POP3_email.EmailAccount" + xpathConstraint))
			result.add(imap_pop3_email.proxies.EmailAccount.initialize(context, obj));
		return result;
	}

	/**
	 * Commit the changes made on this proxy object.
	 */
	public final void commit() throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Commit the changes made on this proxy object using the specified context.
	 */
	public final void commit(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		com.mendix.core.Core.commit(context, getMendixObject());
	}

	/**
	 * Delete the object.
	 */
	public final void delete()
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}

	/**
	 * Delete the object using the specified context.
	 */
	public final void delete(com.mendix.systemwideinterfaces.core.IContext context)
	{
		com.mendix.core.Core.delete(context, getMendixObject());
	}
	/**
	 * Set value of ServerProtocol
	 * @param serverprotocol
	 */
	public final imap_pop3_email.proxies.Protocol getServerProtocol()
	{
		return getServerProtocol(getContext());
	}

	/**
	 * @param context
	 * @return value of ServerProtocol
	 */
	public final imap_pop3_email.proxies.Protocol getServerProtocol(com.mendix.systemwideinterfaces.core.IContext context)
	{
		Object obj = getMendixObject().getValue(context, MemberNames.ServerProtocol.toString());
		if (obj == null)
			return null;

		return imap_pop3_email.proxies.Protocol.valueOf((java.lang.String) obj);
	}

	/**
	 * Set value of ServerProtocol
	 * @param serverprotocol
	 */
	public final void setServerProtocol(imap_pop3_email.proxies.Protocol serverprotocol)
	{
		setServerProtocol(getContext(), serverprotocol);
	}

	/**
	 * Set value of ServerProtocol
	 * @param context
	 * @param serverprotocol
	 */
	public final void setServerProtocol(com.mendix.systemwideinterfaces.core.IContext context, imap_pop3_email.proxies.Protocol serverprotocol)
	{
		if (serverprotocol != null)
			getMendixObject().setValue(context, MemberNames.ServerProtocol.toString(), serverprotocol.toString());
		else
			getMendixObject().setValue(context, MemberNames.ServerProtocol.toString(), null);
	}

	/**
	 * @return value of ServerHost
	 */
	public final java.lang.String getServerHost()
	{
		return getServerHost(getContext());
	}

	/**
	 * @param context
	 * @return value of ServerHost
	 */
	public final java.lang.String getServerHost(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ServerHost.toString());
	}

	/**
	 * Set value of ServerHost
	 * @param serverhost
	 */
	public final void setServerHost(java.lang.String serverhost)
	{
		setServerHost(getContext(), serverhost);
	}

	/**
	 * Set value of ServerHost
	 * @param context
	 * @param serverhost
	 */
	public final void setServerHost(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String serverhost)
	{
		getMendixObject().setValue(context, MemberNames.ServerHost.toString(), serverhost);
	}

	/**
	 * @return value of ServerPort
	 */
	public final java.lang.Integer getServerPort()
	{
		return getServerPort(getContext());
	}

	/**
	 * @param context
	 * @return value of ServerPort
	 */
	public final java.lang.Integer getServerPort(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.ServerPort.toString());
	}

	/**
	 * Set value of ServerPort
	 * @param serverport
	 */
	public final void setServerPort(java.lang.Integer serverport)
	{
		setServerPort(getContext(), serverport);
	}

	/**
	 * Set value of ServerPort
	 * @param context
	 * @param serverport
	 */
	public final void setServerPort(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer serverport)
	{
		getMendixObject().setValue(context, MemberNames.ServerPort.toString(), serverport);
	}

	/**
	 * @return value of Username
	 */
	public final java.lang.String getUsername()
	{
		return getUsername(getContext());
	}

	/**
	 * @param context
	 * @return value of Username
	 */
	public final java.lang.String getUsername(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Username.toString());
	}

	/**
	 * Set value of Username
	 * @param username
	 */
	public final void setUsername(java.lang.String username)
	{
		setUsername(getContext(), username);
	}

	/**
	 * Set value of Username
	 * @param context
	 * @param username
	 */
	public final void setUsername(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String username)
	{
		getMendixObject().setValue(context, MemberNames.Username.toString(), username);
	}

	/**
	 * @return value of Password
	 */
	public final java.lang.String getPassword()
	{
		return getPassword(getContext());
	}

	/**
	 * @param context
	 * @return value of Password
	 */
	public final java.lang.String getPassword(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Password.toString());
	}

	/**
	 * Set value of Password
	 * @param password
	 */
	public final void setPassword(java.lang.String password)
	{
		setPassword(getContext(), password);
	}

	/**
	 * Set value of Password
	 * @param context
	 * @param password
	 */
	public final void setPassword(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String password)
	{
		getMendixObject().setValue(context, MemberNames.Password.toString(), password);
	}

	/**
	 * @return value of UseDefaultFolder
	 */
	public final java.lang.Boolean getUseDefaultFolder()
	{
		return getUseDefaultFolder(getContext());
	}

	/**
	 * @param context
	 * @return value of UseDefaultFolder
	 */
	public final java.lang.Boolean getUseDefaultFolder(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.UseDefaultFolder.toString());
	}

	/**
	 * Set value of UseDefaultFolder
	 * @param usedefaultfolder
	 */
	public final void setUseDefaultFolder(java.lang.Boolean usedefaultfolder)
	{
		setUseDefaultFolder(getContext(), usedefaultfolder);
	}

	/**
	 * Set value of UseDefaultFolder
	 * @param context
	 * @param usedefaultfolder
	 */
	public final void setUseDefaultFolder(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean usedefaultfolder)
	{
		getMendixObject().setValue(context, MemberNames.UseDefaultFolder.toString(), usedefaultfolder);
	}

	/**
	 * @return value of Folder
	 */
	public final java.lang.String getFolder()
	{
		return getFolder(getContext());
	}

	/**
	 * @param context
	 * @return value of Folder
	 */
	public final java.lang.String getFolder(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.Folder.toString());
	}

	/**
	 * Set value of Folder
	 * @param folder
	 */
	public final void setFolder(java.lang.String folder)
	{
		setFolder(getContext(), folder);
	}

	/**
	 * Set value of Folder
	 * @param context
	 * @param folder
	 */
	public final void setFolder(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String folder)
	{
		getMendixObject().setValue(context, MemberNames.Folder.toString(), folder);
	}

	/**
	 * @return value of UseBatchImport
	 */
	public final java.lang.Boolean getUseBatchImport()
	{
		return getUseBatchImport(getContext());
	}

	/**
	 * @param context
	 * @return value of UseBatchImport
	 */
	public final java.lang.Boolean getUseBatchImport(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.UseBatchImport.toString());
	}

	/**
	 * Set value of UseBatchImport
	 * @param usebatchimport
	 */
	public final void setUseBatchImport(java.lang.Boolean usebatchimport)
	{
		setUseBatchImport(getContext(), usebatchimport);
	}

	/**
	 * Set value of UseBatchImport
	 * @param context
	 * @param usebatchimport
	 */
	public final void setUseBatchImport(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean usebatchimport)
	{
		getMendixObject().setValue(context, MemberNames.UseBatchImport.toString(), usebatchimport);
	}

	/**
	 * @return value of BatchSize
	 */
	public final java.lang.Integer getBatchSize()
	{
		return getBatchSize(getContext());
	}

	/**
	 * @param context
	 * @return value of BatchSize
	 */
	public final java.lang.Integer getBatchSize(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.BatchSize.toString());
	}

	/**
	 * Set value of BatchSize
	 * @param batchsize
	 */
	public final void setBatchSize(java.lang.Integer batchsize)
	{
		setBatchSize(getContext(), batchsize);
	}

	/**
	 * Set value of BatchSize
	 * @param context
	 * @param batchsize
	 */
	public final void setBatchSize(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer batchsize)
	{
		getMendixObject().setValue(context, MemberNames.BatchSize.toString(), batchsize);
	}

	/**
	 * Set value of Handling
	 * @param handling
	 */
	public final imap_pop3_email.proxies.MessageHandling getHandling()
	{
		return getHandling(getContext());
	}

	/**
	 * @param context
	 * @return value of Handling
	 */
	public final imap_pop3_email.proxies.MessageHandling getHandling(com.mendix.systemwideinterfaces.core.IContext context)
	{
		Object obj = getMendixObject().getValue(context, MemberNames.Handling.toString());
		if (obj == null)
			return null;

		return imap_pop3_email.proxies.MessageHandling.valueOf((java.lang.String) obj);
	}

	/**
	 * Set value of Handling
	 * @param handling
	 */
	public final void setHandling(imap_pop3_email.proxies.MessageHandling handling)
	{
		setHandling(getContext(), handling);
	}

	/**
	 * Set value of Handling
	 * @param context
	 * @param handling
	 */
	public final void setHandling(com.mendix.systemwideinterfaces.core.IContext context, imap_pop3_email.proxies.MessageHandling handling)
	{
		if (handling != null)
			getMendixObject().setValue(context, MemberNames.Handling.toString(), handling.toString());
		else
			getMendixObject().setValue(context, MemberNames.Handling.toString(), null);
	}

	/**
	 * @return value of MoveFolder
	 */
	public final java.lang.String getMoveFolder()
	{
		return getMoveFolder(getContext());
	}

	/**
	 * @param context
	 * @return value of MoveFolder
	 */
	public final java.lang.String getMoveFolder(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.MoveFolder.toString());
	}

	/**
	 * Set value of MoveFolder
	 * @param movefolder
	 */
	public final void setMoveFolder(java.lang.String movefolder)
	{
		setMoveFolder(getContext(), movefolder);
	}

	/**
	 * Set value of MoveFolder
	 * @param context
	 * @param movefolder
	 */
	public final void setMoveFolder(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String movefolder)
	{
		getMendixObject().setValue(context, MemberNames.MoveFolder.toString(), movefolder);
	}

	/**
	 * @return value of UseInlineImages
	 */
	public final java.lang.Boolean getUseInlineImages()
	{
		return getUseInlineImages(getContext());
	}

	/**
	 * @param context
	 * @return value of UseInlineImages
	 */
	public final java.lang.Boolean getUseInlineImages(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Boolean) getMendixObject().getValue(context, MemberNames.UseInlineImages.toString());
	}

	/**
	 * Set value of UseInlineImages
	 * @param useinlineimages
	 */
	public final void setUseInlineImages(java.lang.Boolean useinlineimages)
	{
		setUseInlineImages(getContext(), useinlineimages);
	}

	/**
	 * Set value of UseInlineImages
	 * @param context
	 * @param useinlineimages
	 */
	public final void setUseInlineImages(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Boolean useinlineimages)
	{
		getMendixObject().setValue(context, MemberNames.UseInlineImages.toString(), useinlineimages);
	}

	/**
	 * @return value of Timeout
	 */
	public final java.lang.Integer getTimeout()
	{
		return getTimeout(getContext());
	}

	/**
	 * @param context
	 * @return value of Timeout
	 */
	public final java.lang.Integer getTimeout(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.Integer) getMendixObject().getValue(context, MemberNames.Timeout.toString());
	}

	/**
	 * Set value of Timeout
	 * @param timeout
	 */
	public final void setTimeout(java.lang.Integer timeout)
	{
		setTimeout(getContext(), timeout);
	}

	/**
	 * Set value of Timeout
	 * @param context
	 * @param timeout
	 */
	public final void setTimeout(com.mendix.systemwideinterfaces.core.IContext context, java.lang.Integer timeout)
	{
		getMendixObject().setValue(context, MemberNames.Timeout.toString(), timeout);
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return emailAccountMendixObject;
	}

	/**
	 * @return the IContext instance of this proxy, or null if no IContext instance was specified at initialization.
	 */
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final imap_pop3_email.proxies.EmailAccount that = (imap_pop3_email.proxies.EmailAccount) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

	/**
	 * @return String name of this class
	 */
	public static java.lang.String getType()
	{
		return "IMAP_POP3_email.EmailAccount";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}
