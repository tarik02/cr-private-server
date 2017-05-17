package royaleserver.protocol.messages.component;

import org.reflections.Reflections;
import royaleserver.Server;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.CommandFactory;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import royaleserver.protocol.messages.Command;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public class CommandComponent extends Component {
	public int id;
	public Command command;
	public Session session;

	public CommandComponent() {
		id = 0;
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getRrsInt32();
		command = CommandFactory.create(id);

		if (command != null) {
			command.decode(stream);
		}

	}
}
