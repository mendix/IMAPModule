// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package encryption.actions;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;
import com.mendix.systemwideinterfaces.MendixRuntimeException;
import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.webui.CustomJavaAction;

public class DecryptString extends CustomJavaAction<java.lang.String>
{
	private java.lang.String value;
	private java.lang.String key;
	private java.lang.String prefix;

	public DecryptString(IContext context, java.lang.String value, java.lang.String key, java.lang.String prefix)
	{
		super(context);
		this.value = value;
		this.key = key;
		this.prefix = prefix;
	}

	@Override
	public java.lang.String executeAction() throws Exception
	{
		// BEGIN USER CODE
		if (value == null || !value.startsWith(prefix))
			return null;
		if (prefix == null || prefix.isEmpty())
			throw new MendixRuntimeException("Prefix should not be empty");
		if (key == null || key.isEmpty())
			throw new MendixRuntimeException("Key should not be empty");
		if (key.length() != 16)
			throw new MendixRuntimeException("Key length should be 16");
		Cipher c = Cipher.getInstance("AES/CBC/PKCS5PADDING");
		SecretKeySpec k = new SecretKeySpec(key.getBytes(), "AES");
		String[] s = value.substring(prefix.length()).split(";");
		if (s.length < 2) //Not an encrypted string, just return the original value.
			return value;

		byte[] iv = Base64.decodeBase64(s[0].getBytes());
		byte[] encryptedData = Base64.decodeBase64(s[1].getBytes());
		c.init(Cipher.DECRYPT_MODE, k, new IvParameterSpec(iv));
		return new String(c.doFinal(encryptedData));
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "DecryptString";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
