package royaleserver.protocol.messages;


import royaleserver.protocol.Session;
import royaleserver.utils.DataStream;

public class Command extends Component {
	public final short id;

	protected Command(short id) {
		this.id = id;
	}

	@Override
	public void encode(DataStream stream) {
	}

	@Override
	public void decode(DataStream stream) {
	}

	public void Execute(Session session) {

	}
}
