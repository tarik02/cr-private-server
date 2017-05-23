package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.CommandComponent;
import royaleserver.utils.DataStream;

public class EndClientTurn extends Message {
    public static final short ID = Info.END_CLIENT_TURN;

    public int tick;
    public int checksum;
    public byte unknown_3;
    public byte unknown_4;
    public byte unknown_5;
    public byte unknown_6;
    public CommandComponent[] commands;

    public EndClientTurn() {
        super(ID);

        tick = 0;
        checksum = 0;
        unknown_3 = 0;
        unknown_4 = 0;
        unknown_5 = 0;
        unknown_6 = 0;
    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);

        stream.putRrsInt32(tick);
        stream.putRrsInt32(checksum);

        stream.putRrsInt32(commands.length);
        for (CommandComponent command : commands) {
            command.encode(stream);
        }
        
        stream.putByte(unknown_3);
        stream.putByte(unknown_4);
        stream.putByte(unknown_5);
        stream.putByte(unknown_6);
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);

        tick = stream.getRrsInt32();
        checksum = stream.getRrsInt32();

        int commandsCount = stream.getRrsInt32();
        commands = new CommandComponent[commandsCount];
        for (int i = 0; i < commandsCount; i++) {
            commands[i] = new CommandComponent();
            commands[i].decode(stream);
        }
    }

    public boolean handle(MessageHandler handler) throws Throwable {
        return handler.handleEndClientTurn(this);
    }
}
