// This file was generated by Mendix Studio Pro.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package imap_pop3_tests.proxies.microflows;

import java.util.HashMap;
import java.util.Map;
import com.mendix.core.Core;
import com.mendix.core.CoreException;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;

public class Microflows
{
	// These are the microflows for the IMAP_POP3_Tests module
	public static imap_pop3_email.proxies.EmailAccount createAccount(IContext context, imap_pop3_email.proxies.Protocol _protocol, boolean _useInlineImages, imap_pop3_email.proxies.MessageHandling _messageHandling, java.lang.Long _batchSize, java.lang.String _folderName)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("protocol", _protocol == null ? null : _protocol.name());
		params.put("useInlineImages", _useInlineImages);
		params.put("messageHandling", _messageHandling == null ? null : _messageHandling.name());
		params.put("batchSize", _batchSize);
		params.put("folderName", _folderName);
		IMendixObject result = (IMendixObject)Core.microflowCall("IMAP_POP3_Tests.CreateAccount").withParams(params).execute(context);
		return result == null ? null : imap_pop3_email.proxies.EmailAccount.initialize(context, result);
	}
	public static void deleteFolder(IContext context, java.lang.String _folderName)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("folderName", _folderName);
		Core.microflowCall("IMAP_POP3_Tests.DeleteFolder").withParams(params).execute(context);
	}
	public static java.lang.String getSubject(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.GetSubject").withParams(params).execute(context);
	}
	public static void resetData(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		Core.microflowCall("IMAP_POP3_Tests.ResetData").withParams(params).execute(context);
	}
	public static java.lang.String retrieve_Assert(IContext context, imap_pop3_email.proxies.EmailAccount _emailAccount)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("emailAccount", _emailAccount == null ? null : _emailAccount.getMendixObject());
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Retrieve_Assert").withParams(params).execute(context);
	}
	public static java.lang.String retrieve_Per_Protocol(IContext context, imap_pop3_email.proxies.Protocol _protocol, imap_pop3_tests.proxies.CharacterSet _characterSet)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		params.put("protocol", _protocol == null ? null : _protocol.name());
		params.put("characterSet", _characterSet == null ? null : _characterSet.name());
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Retrieve_Per_Protocol").withParams(params).execute(context);
	}
	public static void setup(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		Core.microflowCall("IMAP_POP3_Tests.Setup").withParams(params).execute(context);
	}
	public static java.lang.String test_Multipart(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Multipart").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_IMAP(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_IMAP").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_IMAPS(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_IMAPS").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_POP3(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_POP3").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_POP3S(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_POP3S").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_Then_Delete(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_Then_Delete").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_Then_Move(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_Then_Move").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_Then_Move_Then_Delete(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_Then_Move_Then_Delete").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_Then_Nothing(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_Then_Nothing").withParams(params).execute(context);
	}
	public static java.lang.String test_Retrieve_Then_Nothing_Then_Delete(IContext context)
	{
		Map<java.lang.String, Object> params = new HashMap<>();
		return (java.lang.String) Core.microflowCall("IMAP_POP3_Tests.Test_Retrieve_Then_Nothing_Then_Delete").withParams(params).execute(context);
	}
}