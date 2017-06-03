package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Handler;
import royaleserver.network.protocol.client.messages.*;

public interface ClientMessageHandler extends Handler {
	boolean handleClientMessageLogin(Login login);
}
