package royaleserver.network;

import royaleserver.Server;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.client.messages.*;
import royaleserver.network.protocol.server.messages.LoginFailed;

public abstract class NetworkSession implements ClientMessageHandler {
	protected final Server server;
	protected final NetworkSessionHandler session;
	private boolean closed = false;

	public NetworkSession(Server server, NetworkSessionHandler session) {
		this.server = server;
		this.session = session;
	}

	public Server getServer() {
		return server;
	}

	/**
	 * WARNING. Internal usage only!
	 * @return
	 */
	public NetworkSessionHandler getSession() {
		return session;
	}

	public boolean isClosed() {
		return closed;
	}

	/**
	 * Disconnect player showing the reason.
	 *
	 * @param reason message
	 */
	public final void disconnect(String reason) {
		close(reason, true);
	}

	/**
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public final boolean close() {
		return close(null, true);
	}

	/**
	 * @param reason Reason of closing
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public final boolean close(final String reason) {
		return close(reason, true);
	}

	/**
	 * @param reason         Reason of closing
	 * @param sendDisconnect If true, then server will send LoginFailed packet
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public boolean close(String reason, final boolean sendDisconnect) {
		if (closed) {
			return false;
		}

		closed = true;

		if (reason == null) {
			reason = "";
		}

		if (sendDisconnect) {
			LoginFailed loginFailed = new LoginFailed();
			loginFailed.errorCode = LoginFailed.ERROR_CODE_REASON_MESSAGE;
			loginFailed.resourceFingerprintData = "";
			loginFailed.redirectDomain = "";
			loginFailed.contentURL = "";
			loginFailed.updateURL = "";
			loginFailed.reason = reason;
			loginFailed.secondsUntilMaintenanceEnd = 0;
			loginFailed.unknown_7 = (byte)0;
			loginFailed.unknown_8 = "";
			session.sendMessage(loginFailed);
		}

		session.close();

		return true;
	}

	@Override
	public boolean handleAccountUnlock(AccountUnlock message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanAskData(ClanAskData message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanAskJoinable(ClanAskJoinable message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanChatMessage(ClanChatMessage message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanCreate(ClanCreate message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanJoin(ClanJoin message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanLeave(ClanLeave message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClanSearch(ClanSearch message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClientCommands(ClientCommands message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleClientHello(ClientHello message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleConnectionInfo(ConnectionInfo message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleHomeAskData(HomeAskData message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleHomeAskDataOwn(HomeAskDataOwn message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleInboxAsk(InboxAsk message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleLogin(Login message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleMatchmakeCancel(MatchmakeCancel message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleMatchmakeStart(MatchmakeStart message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleNameChange(NameChange message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleNameCheck(NameCheck message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handlePing(Ping message) throws Throwable {
		throw new UnhandledMessageException(message);
	}

	@Override
	public boolean handleTournamentAskJoinable(TournamentAskJoinable message) throws Throwable {
		throw new UnhandledMessageException(message);
	}
}
