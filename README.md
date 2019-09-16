# IMAPModule
This module enables your project to retrieve emails from POP3/POP3S/IMAP/IMAPS servers.

## Description
A lot of communication is done by email. In order for Mendix to act on incoming email you can implement this module and model all actions around it.

## Typical usage scenario
Retrieve emails and act like an email client. This is the recommended approach when hosting your application in the Mendix cloud.

## Features
* Configuration of multiple accounts.
* Supported protocols:
* * POP3 / POP3S
* * IMAP / IMAPS
* Actions to be performed after receiving emails:
* * Deleted from server
* * Move to a folder (e.g. archive).

## Limitations
* Does not retrieve meeting requests (.ics).

## Installation
This module depends on the following modules:
*Encryption (used to encrypt passwords of the email accounts)

## Configuration
Basic setup and receive of emails can be done using the EmailAccount_Overview. To invoke receiving emails from an account you can call DS_ReceiveEmails.

## Dependencies
This version requires Encryption v1.4.1.