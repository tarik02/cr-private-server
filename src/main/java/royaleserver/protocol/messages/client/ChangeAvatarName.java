// 
package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class ChangeAvatarName extends Message {

    public static final short ID = Info.CHANGE_AVATAR_NAME;
    
    public String nUsername;

    public ChangeAvatarName() {
        super(ID);
        
        nUsername = "";
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);
        
        nUsername = stream.getString();
    }

    public boolean handle(MessageHandler handler) throws Throwable {
        return handler.handleChangeAvatarName(this);
    }
}
