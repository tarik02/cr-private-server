package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.CommandFactory;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.protocol.messages.Command;

public class CommandComponent extends Component {
	public int id;
	public Command command;

	public CommandComponent() {
		id = 0;
		command = null;
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getRrsInt32();
		command = CommandFactory.create(id, stream);
	}
}
