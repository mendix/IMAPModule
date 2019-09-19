// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package encryption.proxies;

public class PasswordData
{
	private final com.mendix.systemwideinterfaces.core.IMendixObject passwordDataMendixObject;

	private final com.mendix.systemwideinterfaces.core.IContext context;

	/**
	 * Internal name of this entity
	 */
	public static final java.lang.String entityName = "Encryption.PasswordData";

	/**
	 * Enum describing members of this entity
	 */
	public enum MemberNames
	{
		NewPassword("NewPassword"),
		ConfirmPassword("ConfirmPassword"),
		PasswordData_ExampleConfiguration("Encryption.PasswordData_ExampleConfiguration");

		private java.lang.String metaName;

		MemberNames(java.lang.String s)
		{
			metaName = s;
		}

		@java.lang.Override
		public java.lang.String toString()
		{
			return metaName;
		}
	}

	public PasswordData(com.mendix.systemwideinterfaces.core.IContext context)
	{
		this(context, com.mendix.core.Core.instantiate(context, "Encryption.PasswordData"));
	}

	protected PasswordData(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject passwordDataMendixObject)
	{
		if (passwordDataMendixObject == null)
			throw new java.lang.IllegalArgumentException("The given object cannot be null.");
		if (!com.mendix.core.Core.isSubClassOf("Encryption.PasswordData", passwordDataMendixObject.getType()))
			throw new java.lang.IllegalArgumentException("The given object is not a Encryption.PasswordData");

		this.passwordDataMendixObject = passwordDataMendixObject;
		this.context = context;
	}

	/**
	 * @deprecated Use 'PasswordData.load(IContext, IMendixIdentifier)' instead.
	 */
	@java.lang.Deprecated
	public static encryption.proxies.PasswordData initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		return encryption.proxies.PasswordData.load(context, mendixIdentifier);
	}

	/**
	 * Initialize a proxy using context (recommended). This context will be used for security checking when the get- and set-methods without context parameters are called.
	 * The get- and set-methods with context parameter should be used when for instance sudo access is necessary (IContext.createSudoClone() can be used to obtain sudo access).
	 */
	public static encryption.proxies.PasswordData initialize(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixObject mendixObject)
	{
		return new encryption.proxies.PasswordData(context, mendixObject);
	}

	public static encryption.proxies.PasswordData load(com.mendix.systemwideinterfaces.core.IContext context, com.mendix.systemwideinterfaces.core.IMendixIdentifier mendixIdentifier) throws com.mendix.core.CoreException
	{
		com.mendix.systemwideinterfaces.core.IMendixObject mendixObject = com.mendix.core.Core.retrieveId(context, mendixIdentifier);
		return encryption.proxies.PasswordData.initialize(context, mendixObject);
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
	 * @return value of NewPassword
	 */
	public final java.lang.String getNewPassword()
	{
		return getNewPassword(getContext());
	}

	/**
	 * @param context
	 * @return value of NewPassword
	 */
	public final java.lang.String getNewPassword(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.NewPassword.toString());
	}

	/**
	 * Set value of NewPassword
	 * @param newpassword
	 */
	public final void setNewPassword(java.lang.String newpassword)
	{
		setNewPassword(getContext(), newpassword);
	}

	/**
	 * Set value of NewPassword
	 * @param context
	 * @param newpassword
	 */
	public final void setNewPassword(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String newpassword)
	{
		getMendixObject().setValue(context, MemberNames.NewPassword.toString(), newpassword);
	}

	/**
	 * @return value of ConfirmPassword
	 */
	public final java.lang.String getConfirmPassword()
	{
		return getConfirmPassword(getContext());
	}

	/**
	 * @param context
	 * @return value of ConfirmPassword
	 */
	public final java.lang.String getConfirmPassword(com.mendix.systemwideinterfaces.core.IContext context)
	{
		return (java.lang.String) getMendixObject().getValue(context, MemberNames.ConfirmPassword.toString());
	}

	/**
	 * Set value of ConfirmPassword
	 * @param confirmpassword
	 */
	public final void setConfirmPassword(java.lang.String confirmpassword)
	{
		setConfirmPassword(getContext(), confirmpassword);
	}

	/**
	 * Set value of ConfirmPassword
	 * @param context
	 * @param confirmpassword
	 */
	public final void setConfirmPassword(com.mendix.systemwideinterfaces.core.IContext context, java.lang.String confirmpassword)
	{
		getMendixObject().setValue(context, MemberNames.ConfirmPassword.toString(), confirmpassword);
	}

	/**
	 * @return value of PasswordData_ExampleConfiguration
	 */
	public final encryption.proxies.ExampleConfiguration getPasswordData_ExampleConfiguration() throws com.mendix.core.CoreException
	{
		return getPasswordData_ExampleConfiguration(getContext());
	}

	/**
	 * @param context
	 * @return value of PasswordData_ExampleConfiguration
	 */
	public final encryption.proxies.ExampleConfiguration getPasswordData_ExampleConfiguration(com.mendix.systemwideinterfaces.core.IContext context) throws com.mendix.core.CoreException
	{
		encryption.proxies.ExampleConfiguration result = null;
		com.mendix.systemwideinterfaces.core.IMendixIdentifier identifier = getMendixObject().getValue(context, MemberNames.PasswordData_ExampleConfiguration.toString());
		if (identifier != null)
			result = encryption.proxies.ExampleConfiguration.load(context, identifier);
		return result;
	}

	/**
	 * Set value of PasswordData_ExampleConfiguration
	 * @param passworddata_exampleconfiguration
	 */
	public final void setPasswordData_ExampleConfiguration(encryption.proxies.ExampleConfiguration passworddata_exampleconfiguration)
	{
		setPasswordData_ExampleConfiguration(getContext(), passworddata_exampleconfiguration);
	}

	/**
	 * Set value of PasswordData_ExampleConfiguration
	 * @param context
	 * @param passworddata_exampleconfiguration
	 */
	public final void setPasswordData_ExampleConfiguration(com.mendix.systemwideinterfaces.core.IContext context, encryption.proxies.ExampleConfiguration passworddata_exampleconfiguration)
	{
		if (passworddata_exampleconfiguration == null)
			getMendixObject().setValue(context, MemberNames.PasswordData_ExampleConfiguration.toString(), null);
		else
			getMendixObject().setValue(context, MemberNames.PasswordData_ExampleConfiguration.toString(), passworddata_exampleconfiguration.getMendixObject().getId());
	}

	/**
	 * @return the IMendixObject instance of this proxy for use in the Core interface.
	 */
	public final com.mendix.systemwideinterfaces.core.IMendixObject getMendixObject()
	{
		return passwordDataMendixObject;
	}

	/**
	 * @return the IContext instance of this proxy, or null if no IContext instance was specified at initialization.
	 */
	public final com.mendix.systemwideinterfaces.core.IContext getContext()
	{
		return context;
	}

	@java.lang.Override
	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;

		if (obj != null && getClass().equals(obj.getClass()))
		{
			final encryption.proxies.PasswordData that = (encryption.proxies.PasswordData) obj;
			return getMendixObject().equals(that.getMendixObject());
		}
		return false;
	}

	@java.lang.Override
	public int hashCode()
	{
		return getMendixObject().hashCode();
	}

	/**
	 * @return String name of this class
	 */
	public static java.lang.String getType()
	{
		return "Encryption.PasswordData";
	}

	/**
	 * @return String GUID from this object, format: ID_0000000000
	 * @deprecated Use getMendixObject().getId().toLong() to get a unique identifier for this object.
	 */
	@java.lang.Deprecated
	public java.lang.String getGUID()
	{
		return "ID_" + getMendixObject().getId().toLong();
	}
}
