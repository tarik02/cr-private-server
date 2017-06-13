package royaleserver.network;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import royaleserver.Server;
import royaleserver.config.Config;
import royaleserver.network.protocol.client.ClientCommandFactory;
import royaleserver.network.protocol.client.ClientMessageFactory;
import royaleserver.network.protocol.server.ServerCommandFactory;
import royaleserver.network.protocol.server.ServerMessageFactory;
import royaleserver.utils.Hex;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public final class NetworkServer {
	private static final Logger logger = LogManager.getLogger(NetworkServer.class);
	public static final byte[] SERVER_KEY = Hex.toByteArray("9e6657f2b419c237f6aeef37088690a642010586a7bd9018a15652bab8370f4f");
	public static final byte[] SESSION_KEY = Hex.toByteArray("74794DE40D62A03AC6F6E86A9815C6262AA12BEDD518F883");

	private final Server server;
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	private Channel channel;

	private final boolean requireLoginCode;

	public NetworkServer(Server server, Config config) {
		this.server = server;

		requireLoginCode = config.get("server.require_login_code").getAsBoolean();

		ClientCommandFactory.instance.init();
		ClientMessageFactory.instance.init();
		ServerCommandFactory.instance.init();
		ServerMessageFactory.instance.init();
	}

	public void start() {
		bossGroup = new NioEventLoopGroup();
		workerGroup = new NioEventLoopGroup();

		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true)
				.childHandler(new PlayerInitializer(this));

		try {
			channel = bootstrap.bind(server.getConfig().get("server.port").getAsShort()).sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void stop() {
	}

	public void close() {
		try {
			channel.closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	public Server getServer() {
		return server;
	}

	public boolean isRequireLoginCode() {
		return requireLoginCode;
	}
}
