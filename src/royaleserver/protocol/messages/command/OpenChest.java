package royaleserver.protocol.messages.command;

import royaleserver.protocol.Info;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.server.AvailableServerCommand;
import royaleserver.utils.DataStream;

public class OpenChest extends Command {

	public static final short ID = 503;

	public int tickStart;
	public int tickEnd;
	public long accountID;

	public byte unknown_0;

	// тут можно переделать
	public Session session;

	public OpenChest() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountID = stream.getRrsLong();
		unknown_0 = stream.getByte();
	}

	@Override
	public void Execute(Session session) {
		super.Execute(session);

		AvailableServerCommand response = new AvailableServerCommand();
		response.command.commandClass = new OpenChestOK();
		session.sendMessage(response);
	}
}
