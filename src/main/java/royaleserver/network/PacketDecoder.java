package royaleserver.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import royaleserver.crypto.ServerCrypto;
import royaleserver.network.protocol.MessageHeader;
import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageFactory;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {
	private static final Logger logger = LogManager.getLogger(PacketDecoder.class);

	private final ServerCrypto crypto;

	private boolean hasHeader = false;
	private final MessageHeader header = new MessageHeader();

	public PacketDecoder(ServerCrypto crypto) {
		this.crypto = crypto;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (!hasHeader) {
			if (in.readableBytes() >= 7) {
				short id = in.readShort();
				int payloadLength = in.readMedium();
				short version = in.readShort();

				hasHeader = true;
				header.id = id;
				header.payload = new byte[payloadLength];
			}
		} else {
			if (in.readableBytes() >= header.payload.length) {
				in.readBytes(header.payload);
				hasHeader = false;

				crypto.decryptPacket(header);

				ClientMessage message = processPacket();
				if (message != null) {
					out.add(message);
				}
			}
		}
	}

	private ClientMessage processPacket() {
		ClientMessage message = ClientMessageFactory.instance.create(header.id);

		if (header.decrypted == null) {
			logger.error("Failed to decrypt packet %d, encrypted payload:\n%s", header.id, Hex.dump(header.payload));
			return null;
		}

		if (message == null) {
			String name = null;
			if (Messages.messagesMap.containsKey(header.id)) {
				name = Messages.messagesMap.get(header.id);
			}

			if (name == null) {
				logger.warn("Received unknown packet %d:\n%s", header.id, Hex.dump(header.decrypted));
			} else {
				logger.warn("Received undefined packet %s:\n%s", name, Hex.dump(header.decrypted));
			}

			return null;
		}

		try {
			DataStream dataStream = new DataStream(header.decrypted);
			message.decode(dataStream);

			// Messages must be decoded fully, because it can contain useful information, so we will try to decode it fully
			if (!dataStream.eof()) {
				logger.warn("Message %s is not decoded fully.\n\tDecoded part:\n%s\n\tOther part:\n%s",
						message.getClass().getSimpleName(),
						Hex.dump(header.decrypted, 0, dataStream.getOffset()),
						Hex.dump(header.decrypted, dataStream.getOffset(), header.decrypted.length - dataStream.getOffset()));
			}
		} catch (Exception e) {
			logger.error("Failed to decode packet %s, payload:\n%s", message.getClass().getSimpleName(),
					Hex.dump(header.decrypted));
			return null;
		}

		return message;
	}
}
