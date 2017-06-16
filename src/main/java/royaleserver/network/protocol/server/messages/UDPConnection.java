package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public class UDPConnection extends ServerMessage {
	public static final short ID = Messages.UDP_CONNECTION;

	public String ip;
	public short port;

	public byte[] unknown_2;
	public String unknown_3;

	public UDPConnection() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new UDPConnection();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(port);
		stream.putString(ip);

		stream.putByteSet(unknown_2);
		stream.putString(unknown_3);
	}
}
