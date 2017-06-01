package royaleserver.protocol.messages;

import royaleserver.utils.DataStream;

public abstract class StreamCommand extends Component {
	public final short id;

	protected StreamCommand(short id) {
		this.id = id;
	}

	@Override
	public void encode(DataStream stream) {
	}

	@Override
	public void decode(DataStream stream) {
	}
}
