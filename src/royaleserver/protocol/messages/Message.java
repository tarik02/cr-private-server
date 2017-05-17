package royaleserver.protocol.messages;

import royaleserver.protocol.Handler;
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

	public boolean handle(Handler handler) throws Throwable {
		return false;
	}
}
