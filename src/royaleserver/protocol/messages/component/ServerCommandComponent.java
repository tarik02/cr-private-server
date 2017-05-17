package royaleserver.protocol.messages.component;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class ServerCommandComponent extends Component {
	public int id;
	public Command commandClass;

	public ServerCommandComponent() {
		id = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
		stream.putRrsInt32(commandClass.id);
		commandClass.encode(stream);
	}
}
