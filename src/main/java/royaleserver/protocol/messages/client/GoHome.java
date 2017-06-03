package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class GoHome extends Message {

    public static final short ID = Info.GO_HOME;
    
    public GoHome() {
        super(ID);
    }

    @Override
    public void decode(DataStream stream) {
        
    }

    public boolean handle(MessageHandler handler) throws Throwable {
        return handler.handleGoHome(this);
    }
    
}
