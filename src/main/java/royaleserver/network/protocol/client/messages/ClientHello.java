package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class ClientHello extends ClientMessage {
	public static final short ID = Messages.CLIENT_HELLO;

	public int protocol;
	public int keyVersion;
	public int majorVersion;
	public int minorVersion;
	public int build;
	public String contentHash;
	public int deviceType;
	public int appStore;

	public ClientHello() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		protocol = stream.getBInt();
		keyVersion = stream.getBInt();
		majorVersion = stream.getBInt();
		minorVersion = stream.getBInt();
		build = stream.getBInt();
		contentHash = stream.getString();
		deviceType = stream.getBInt();
		appStore = stream.getBInt();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClientHello(this);
	}
}
