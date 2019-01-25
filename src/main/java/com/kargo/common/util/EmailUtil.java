package com.kargo.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dannygu on 7/6/15.
 */

public class EmailUtil {
    private static final Logger logger = LoggerFactory.getLogger(EmailUtil.class);

    public static HtmlEmail sendEmailByApacheMail(String user,String password,String host,String port,String charSet,String mailTo, String mailCC, String mailBcc, String mailSubject, String mailContent, String mailAttachmentPath, String isUseAttachment) throws EmailException {
    	logger.info("user:[{}],password:[{}],host:[{}],port:[{}],charSet:[{}],mailTo:[{}], mailCC:[{}], mailBcc:[{}], mailSubject:[{}], mailContent:[{}], mailAttachmentPath:[{}], isUseAttachment:[{}]", user, password, host, port, charSet, mailTo,  mailCC,  mailBcc,  mailSubject,  mailContent,  mailAttachmentPath,  isUseAttachment);
    	if(StringUtils.isBlank(mailTo)){
    		return null;
    	}
        HtmlEmail email = new HtmlEmail();

        email.setHostName(host);
        email.setSmtpPort(Integer.parseInt(port));
        email.setAuthentication(user, password);
        email.setTLS(true);

        email.setFrom(user);
        email.setSubject(mailSubject);
        email.setCharset(charSet);
        email.setMsg(mailContent);

        String[] toArray = mailTo.split(";");
        for (int i = 0; i < toArray.length; i++) {
            email.addTo(toArray[i]);
        }

        if (mailCC != null && mailCC.trim().length() > 0) {
            String[] ccArray = mailCC.split(";");
            for (int i = 0; i < ccArray.length; i++) {
                email.addCc(ccArray[i]);
            }

        }
        if (mailBcc != null && mailBcc.trim().length() > 0) {
            String[] bccArray = mailBcc.split(";");
            for (int i = 0; i < bccArray.length; i++) {
                email.addBcc(bccArray[i]);
            }
        }

        if ("Y".equals(isUseAttachment)) {
            EmailAttachment attachment = new EmailAttachment();
            attachment.setPath(mailAttachmentPath);
            attachment.setDisposition(EmailAttachment.ATTACHMENT);
            email.attach(attachment);
        }

        return email;
    }
}
