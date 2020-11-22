package envio.infra.mail;

import java.io.IOException;

public interface Sender {

    void sendMail(String to, String hash) throws IOException;
}
