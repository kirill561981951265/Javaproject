package by.kir.spring_lr2.service;

public interface MailSender {
    public void send(String emailTo, String subject, String message);
}
