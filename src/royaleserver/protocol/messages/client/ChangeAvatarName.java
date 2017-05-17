// 
package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

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

    public boolean handle(Handler handler) throws Throwable {
        return handler.handleChangeAvatarName(this);
    }
}
