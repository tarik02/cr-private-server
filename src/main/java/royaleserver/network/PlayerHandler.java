package royaleserver.network;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import royaleserver.Server;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.service.PlayerService;
import royaleserver.game.CodeEnterPlayer;
import royaleserver.game.Player;
import royaleserver.network.protocol.Message;
import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.messages.ClientHello;
import royaleserver.network.protocol.client.messages.Login;
import royaleserver.network.protocol.server.messages.LoginFailed;
import royaleserver.network.protocol.server.messages.ServerHello;
import royaleserver.utils.Dumper;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public class PlayerHandler extends SimpleChannelInboundHandler<ClientMessage> implements NetworkSessionHandler {
	private static final Logger logger = LogManager.getLogger(PlayerHandler.class);

	private enum Status {
		HELLO,
		LOGIN,
		CONNECTED,
		DISCONNECTING
	}

	private final Server server;
	private final NetworkServer networkServer;
	private NetworkSession session;

	private Channel channel;
	private Status status;

	public PlayerHandler(NetworkServer networkServer) {
		this.server = networkServer.getServer();
		this.networkServer = networkServer;

		status = Status.HELLO;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext context) throws Exception {
		channel = context.channel();
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext context) throws Exception {
		if (session != null) {
			session.close("", false);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext context, ClientMessage message) throws Exception {
		logger.debug("> %s", message.getClass().getSimpleName());

		switch (status) {
		case HELLO:
			if (message.id != Messages.CLIENT_HELLO) {
				logger.warn("Excepted ClientHello, received %s. Disconnecting...", message.getClass().getSimpleName());
				close();
				return;
			}

			ClientHello clientHello = (ClientHello)message;

			ServerHello serverHello = new ServerHello();
			serverHello.sessionKey = NetworkServer.SESSION_KEY;
			sendMessage(serverHello);

			status = Status.LOGIN;
			break;
		case LOGIN:
			if (message.id != Messages.LOGIN) {
				logger.warn("Excepted Login, received %s. Disconnecting...", message.getClass().getSimpleName());
				close();
				return;
			}

			Login login = (Login)message;

			if (!login.resourceSha.equals(server.getContentHash())) {
				LoginFailed loginFailed = new LoginFailed();
				loginFailed.errorCode = LoginFailed.ERROR_CODE_NEW_ASSETS;
				loginFailed.resourceFingerprintData = server.getResourceFingerprint();
				loginFailed.redirectDomain = "";
				loginFailed.contentURL = server.getContentUrl();
				loginFailed.updateURL = "";
				loginFailed.reason = "";
				loginFailed.secondsUntilMaintenanceEnd = 0;
				loginFailed.unknown_7 = (byte)0;
				loginFailed.unknown_8 = "";
				sendMessage(loginFailed);

				close();
				return;
			}

			PlayerService playerService = server.getDataManager().getPlayerService();
			PlayerEntity playerEntity = null;

			if (login.accountId == 0 && login.passToken.isEmpty()) {
				if (!networkServer.isRequireLoginCode()) {
					playerEntity = playerService.create();
				}
			} else {
				playerEntity = playerService.get(login.accountId);

				if (playerEntity == null || !login.passToken.equals(playerEntity.getPassToken())) {
					LoginFailed loginFailed = new LoginFailed();
					loginFailed.errorCode = LoginFailed.ERROR_CODE_REASON_MESSAGE;
					loginFailed.resourceFingerprintData = "";
					loginFailed.redirectDomain = "";
					loginFailed.contentURL = "";
					loginFailed.updateURL = "";
					loginFailed.reason = "Sorry, we can't find your account in our database. We suggest you to clean app data and try again.";
					loginFailed.secondsUntilMaintenanceEnd = 0;
					loginFailed.unknown_7 = (byte)0;
					loginFailed.unknown_8 = "";
					sendMessage(loginFailed);

					close();
					return;
				}
			}

			if (playerEntity == null) {
				session = new CodeEnterPlayer(server, this);
				status = Status.CONNECTED;
			} else {
				session = new Player(playerEntity, server, this);
				status = Status.CONNECTED;
			}
			break;
		case CONNECTED:
			try {
				if (!message.handle(session)) {
					logger.warn("Failed to handle message %s:\n%s", message.getClass().getSimpleName(), Dumper.dump(message));
				}
			} catch (UnhandledMessageException e2) {
				close();
			} catch (Throwable e2) {
				logger.error("Failed to handle message %s:\n%s. Error threw:", e2, message.getClass().getSimpleName(), Dumper.dump(message));
			}
			break;
		case DISCONNECTING:
			break;
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext context, Throwable cause) throws Exception {
		logger.error("Exception from downstream", cause);
		context.channel().close();
	}

	@Override
	public void sendMessage(Message message) {
		logger.debug("< %s", message.getClass().getSimpleName());
		channel.write(message);
	}

	@Override
	public void replace(NetworkSession newSession) {
		session = newSession;
	}

	@Override
	public void close() {
		status = Status.DISCONNECTING;
		channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}
}
