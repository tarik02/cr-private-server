package royaleserver;

import royaleserver.network.NetworkSession;
import royaleserver.network.NetworkSessionHandler;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.client.messages.AccountUnlock;
import royaleserver.network.protocol.client.messages.Login;
import royaleserver.network.protocol.client.messages.Ping;

public class CodeEnterPlayer extends NetworkSession implements ClientMessageHandler {
	public CodeEnterPlayer(Server server, NetworkSessionHandler session) {
		super(server, session);
	}

	@Override
	public boolean handleAccountUnlock(AccountUnlock message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleLogin(Login message) throws Throwable {
		// If server sends LoginFailed with Account Blocked, then client shows activation window but sends Login one
		// more time, so we will ignore it
		return true;
	}

	@Override
	public boolean handlePing(Ping message) throws Throwable {
		// Server do not have to response ping message, when account activation window is opened, so ignore it.
		return true;
	}
}
