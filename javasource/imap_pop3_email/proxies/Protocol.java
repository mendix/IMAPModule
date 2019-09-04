// This file was generated by Mendix Modeler.
//
// WARNING: Code you write here will be lost the next time you deploy the project.

package imap_pop3_email.proxies;

public enum Protocol
{
	IMAP(new java.lang.String[][] { new java.lang.String[] { "nl_NL", "IMAP" }, new java.lang.String[] { "en_ZA", "IMAP" }, new java.lang.String[] { "en_GB", "IMAP" }, new java.lang.String[] { "en_US", "IMAP" } }),
	IMAPS(new java.lang.String[][] { new java.lang.String[] { "nl_NL", "IMAPS" }, new java.lang.String[] { "en_ZA", "IMAPS" }, new java.lang.String[] { "en_GB", "IMAPS" }, new java.lang.String[] { "en_US", "IMAPS" } }),
	POP3(new java.lang.String[][] { new java.lang.String[] { "nl_NL", "POP3" }, new java.lang.String[] { "en_ZA", "POP3" }, new java.lang.String[] { "en_GB", "POP3" }, new java.lang.String[] { "en_US", "POP3" } }),
	POP3S(new java.lang.String[][] { new java.lang.String[] { "en_US", "POP3S" }, new java.lang.String[] { "en_ZA", "POP3S" }, new java.lang.String[] { "en_GB", "POP3S" } });

	private java.util.Map<java.lang.String, java.lang.String> captions;

	private Protocol(java.lang.String[][] captionStrings)
	{
		this.captions = new java.util.HashMap<java.lang.String, java.lang.String>();
		for (java.lang.String[] captionString : captionStrings)
			captions.put(captionString[0], captionString[1]);
	}

	public java.lang.String getCaption(java.lang.String languageCode)
	{
		if (captions.containsKey(languageCode))
			return captions.get(languageCode);
		return captions.get("en_US");
	}

	public java.lang.String getCaption()
	{
		return captions.get("en_US");
	}
}
