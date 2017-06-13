package royaleserver.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import royaleserver.crypto.ServerCrypto;
import royaleserver.network.protocol.MessageHeader;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public class PacketEncoder extends MessageToByteEncoder<ServerMessage> {
	private final ServerCrypto crypto;

	private final DataStream stream = new DataStream();
	private final MessageHeader header = new MessageHeader();

	public PacketEncoder(ServerCrypto crypto) {
		this.crypto = crypto;
	}

	@Override
	protected void encode(ChannelHandlerContext context, ServerMessage message, ByteBuf out) throws Exception {
		message.encode(stream);

		header.id = message.id;
		header.decrypted = stream.getBuffer();
		stream.reset(true);
		crypto.encryptPacket(header);

		out.writeShort(message.id);
		out.writeMedium(header.payload.length);
		out.writeShort((short)5);

		out.writeBytes(header.payload);
	}
}
