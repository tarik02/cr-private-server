package royaleserver.protocol.messages.client;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.utils.DataStream;

public class ChangeAvatarName extends Message {
    public static final short ID = Info.CHANGE_AVATAR_NAME;
    
    public String username;

    public ChangeAvatarName() {
        super(ID);
        
        username = "";
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);
        
        username = stream.getString();
    }

    public boolean handle(MessageHandler handler) throws Throwable {
        return handler.handleChangeAvatarName(this);
    }
}
