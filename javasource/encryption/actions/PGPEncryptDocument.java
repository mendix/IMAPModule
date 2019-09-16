// This file was generated by Mendix Modeler.
//
// WARNING: Only the following code will be retained when actions are regenerated:
// - the import list
// - the code between BEGIN USER CODE and END USER CODE
// - the code between BEGIN EXTRA CODE and END EXTRA CODE
// Other code you write will be lost the next time you deploy the project.
// Special characters, e.g., é, ö, à, etc. are supported in comments.

package encryption.actions;

import com.mendix.systemwideinterfaces.core.IContext;
import com.mendix.systemwideinterfaces.core.IMendixObject;
import com.mendix.webui.CustomJavaAction;
import encryption.pgp.PGPFileProcessor;

/**
 * Encrypt the FileDocument using PGP encryption. 
 *  This is allowed to be the same FileDocument instance and the action will just store the encrypted file in the provided entity.
 * 
 * The certificate must be a valid public PGP key provided by the external party. If you want to sign the document you can optionally provide the private key to used for signing. 
 * The external party would have to validate the signature against the pub key from signature key.
 * 
 * This action will either return true or an exception
 */
public class PGPEncryptDocument extends CustomJavaAction<java.lang.Boolean>
{
	private IMendixObject __ExternalPublicKey;
	private system.proxies.FileDocument ExternalPublicKey;
	private IMendixObject __DocumentToEncrypt;
	private system.proxies.FileDocument DocumentToEncrypt;
	private IMendixObject __OutputDocument;
	private system.proxies.FileDocument OutputDocument;
	private IMendixObject __SigningCertificate;
	private encryption.proxies.PGPCertificate SigningCertificate;

	public PGPEncryptDocument(IContext context, IMendixObject ExternalPublicKey, IMendixObject DocumentToEncrypt, IMendixObject OutputDocument, IMendixObject SigningCertificate)
	{
		super(context);
		this.__ExternalPublicKey = ExternalPublicKey;
		this.__DocumentToEncrypt = DocumentToEncrypt;
		this.__OutputDocument = OutputDocument;
		this.__SigningCertificate = SigningCertificate;
	}

	@Override
	public java.lang.Boolean executeAction() throws Exception
	{
		this.ExternalPublicKey = __ExternalPublicKey == null ? null : system.proxies.FileDocument.initialize(getContext(), __ExternalPublicKey);

		this.DocumentToEncrypt = __DocumentToEncrypt == null ? null : system.proxies.FileDocument.initialize(getContext(), __DocumentToEncrypt);

		this.OutputDocument = __OutputDocument == null ? null : system.proxies.FileDocument.initialize(getContext(), __OutputDocument);

		this.SigningCertificate = __SigningCertificate == null ? null : encryption.proxies.PGPCertificate.initialize(getContext(), __SigningCertificate);

		// BEGIN USER CODE

		PGPFileProcessor p = new PGPFileProcessor();
		p.setInputFileDocument(this.DocumentToEncrypt.getMendixObject());
		p.setOutputFileDocument(this.OutputDocument.getMendixObject());
		p.setAsciiArmored(true);
		p.setPublicKeyFileName(this.ExternalPublicKey.getMendixObject());

		// If we are going to validate the signature we also need the private key and passphrase
		// p.setPassphrase( Microflows.decrypt(getContext(), this.SigningCertificate.getPassPhrase_Encrypted()) );

		return p.encrypt(getContext());
		// END USER CODE
	}

	/**
	 * Returns a string representation of this action
	 */
	@Override
	public java.lang.String toString()
	{
		return "PGPEncryptDocument";
	}

	// BEGIN EXTRA CODE
	// END EXTRA CODE
}
