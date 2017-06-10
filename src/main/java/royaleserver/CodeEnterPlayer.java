package royaleserver;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.network.NetworkSession;
import royaleserver.network.NetworkSessionHandler;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.client.messages.AccountUnlock;
import royaleserver.network.protocol.client.messages.Login;
import royaleserver.network.protocol.client.messages.Ping;
import royaleserver.network.protocol.server.messages.AccountUnlockFailed;
import royaleserver.network.protocol.server.messages.AccountUnlockOk;
import royaleserver.network.protocol.server.messages.LoginFailed;

public class CodeEnterPlayer extends NetworkSession implements ClientMessageHandler {
	public CodeEnterPlayer(Server server, NetworkSessionHandler session) {
		super(server, session);

		LoginFailed loginFailed = new LoginFailed();
		loginFailed.errorCode = LoginFailed.ERROR_CODE_ACCOUNT_BLOCKED;
		loginFailed.resourceFingerprintData = "";
		loginFailed.redirectDomain = "";
		loginFailed.contentURL = "";
		loginFailed.updateURL = "";
		loginFailed.reason = "";
		loginFailed.secondsUntilMaintenanceEnd = 0;
		loginFailed.unknown_7 = (byte)0;
		loginFailed.unknown_8 = "";
		session.sendMessage(loginFailed);
	}

	@Override
	public boolean handleAccountUnlock(AccountUnlock message) throws Throwable {
		if (server.dataManager.getUnlockCodeService().use(message.unlockCode)) {
			AccountUnlockOk response = new AccountUnlockOk();
			session.sendMessage(response);

			PlayerEntity playerEntity = server.dataManager.getPlayerService().create();
			session.replace(new Player(playerEntity, server, session));
		} else {
			AccountUnlockFailed response = new AccountUnlockFailed();
			session.sendMessage(response);
		}

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
