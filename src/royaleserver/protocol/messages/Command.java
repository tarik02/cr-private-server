package royaleserver.protocol.messages;

import royaleserver.utils.DataStream;

public abstract class Command extends Component {
	public final short id;

	protected Command(short id) {
		this.id = id;
	}

	@Override
	public void encode(DataStream stream) {}

	@Override
	public void decode(DataStream stream) {}

	public boolean handle(CommandHandler handler) throws Throwable {
		return false;
	}
}
