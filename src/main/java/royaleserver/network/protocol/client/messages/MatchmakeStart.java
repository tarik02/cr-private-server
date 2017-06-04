package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class MatchmakeStart extends ClientMessage {
    public static final short ID = Messages.MATCHMAKE_START;

    public MatchmakeStart() {
        super(ID);
    }

    @Override
    public void decode(DataStream stream) {
    }

    @Override
    public boolean handle(ClientMessageHandler handler) throws Throwable {
        return handler.handleMatchmakeStart(this);
    }
}
