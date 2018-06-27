package br.com.util.email;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Email  {

    public boolean enviar(String tituloMensagem, StringBuilder corpomensagem, File pdfBytes, String emaildest, String emaildestinatariosCCO, boolean isProducao) throws Exception {
        try {

            //Anexar no email o pdf
            Multipart mp = new MimeMultipart();
            if (pdfBytes != null) {
                MimeBodyPart attachFilePart = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(pdfBytes.getAbsoluteFile());
                attachFilePart.setDataHandler(new DataHandler(fds));
                attachFilePart.setFileName("documento.pdf");
                attachFilePart.setHeader("Content-Type", "application/pdf");
                mp.addBodyPart(attachFilePart);
            }

            // Mensagem de corpo do texto
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(corpomensagem.toString());
            messageBodyPart.addHeader("Content-Type", "text/html; charset=UTF-8");
            mp.addBodyPart(messageBodyPart);

            Properties mailProps = new Properties();

            //Homologação
            if (isProducao == false) {
                mailProps.put("mail.smtp.timeout", 100);
                mailProps.put("mail.smtp.connectiontimeout", 100);

                mailProps.put("mail.smtp.host", Constantes.SERVER_SMTP_HOMOL_DESENV);
                mailProps.put("mail.smtp.port", Constantes.PORTA);
                mailProps.put("mail.from", Constantes.FROM_HOM);
            } else {
                mailProps.put("mail.smtp.host", Constantes.SERVER_SMTP_PRODUCAO);
                mailProps.put("mail.smtp.port", Constantes.PORTA);
                mailProps.put("mail.from", Constantes.FROM);
            }

            MimeMessage mensagem = null;

            //Homologação
            if (isProducao == false) {
                Session sessaoUsuario = Session.getDefaultInstance(mailProps);
                sessaoUsuario.getProperties().setProperty("mail.from", Constantes.FROM_HOM);

                mensagem = new MimeMessage(sessaoUsuario);
                mensagem.setSentDate(new Date());
                mensagem.setSubject(tituloMensagem);
                mensagem.setContent(mp);

                InternetAddress[] myTOList = InternetAddress.parse(Constantes.FROM_HOM);
                mensagem.addRecipients(Message.RecipientType.TO, myTOList);
                mensagem.saveChanges();

            } else {

                Session sessaoUsuario = Session.getDefaultInstance(mailProps);
                sessaoUsuario.getProperties().setProperty("mail.from", Constantes.FROM);

                mensagem = new MimeMessage(sessaoUsuario);
                mensagem.setSentDate(new Date());
                mensagem.setSubject(tituloMensagem);
                mensagem.setContent(mp);

                InternetAddress[] myTOList = InternetAddress.parse(emaildest);
                mensagem.addRecipients(Message.RecipientType.TO, myTOList);
                if (emaildestinatariosCCO != null) {
                    InternetAddress[] myBccList = InternetAddress.parse(emaildestinatariosCCO);
                    mensagem.addRecipients(Message.RecipientType.BCC, myBccList);
                }
                mensagem.saveChanges();
            }


            System.err.println("--- Transporte send ---" + (new Date()));
            //TODO: Comentado para transporte
            //Transport.send(mensagem);
            System.err.println("--- Transporte send OK ---");

            return true;

        } catch (MessagingException e) {
            System.err.println("--- Erro de envio de email =====>>> " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable e) {
            System.err.println("--- Erro de envio de email =====>>> " + e.getMessage());
            throw e;
        }

        System.out.println("--- Fim do processo de enviar email");

        return false;

    }

}
