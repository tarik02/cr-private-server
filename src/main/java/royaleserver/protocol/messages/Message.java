package royaleserver.protocol.messages;

import royaleserver.utils.DataStream;

public abstract class Message extends Component {
	public final short id;

	protected Message(short id) {
		this.id = id;
	}

	@Override
	public void encode(DataStream stream) {}

	@Override
	public void decode(DataStream stream) {}

	public boolean handle(MessageHandler handler) throws Throwable {
		return false;
	}
}
