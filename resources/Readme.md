greenmail.p12 was generated as follows:
* extract greenmail.jks from ../userlib/greenmail-standalone-1.5.10.jar
* keytool -importkeystore -srckeystore greenmail.jks -destkeystore greenmail.p12 -deststoretype PKCS12 -deststorepass changeit
* Type password "changeit" and press enter