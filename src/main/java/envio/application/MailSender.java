package envio.application;

import java.io.IOException;

public interface MailSender {

    void send() throws IOException;
}
