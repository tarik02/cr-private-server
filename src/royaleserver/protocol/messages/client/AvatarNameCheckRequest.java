// 
package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AvatarNameCheckRequest extends Message {

    public static final short ID = Info.AVATAR_NAME_CHECK_REQUEST;
    
    public String nUsername;

    public AvatarNameCheckRequest() {
        super(ID);
        
        nUsername = "";
    }
    
    @Override
    public void encode(DataStream stream) {
        super.encode(stream);
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);
        
        nUsername = stream.getString();
    }

    public boolean handle(MessageHandler handler) throws Throwable {
        return handler.handleAvatarNameCheckRequest(this);
    }
}
