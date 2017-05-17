package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class VisitHome extends Message {

    public static final short ID = Info.VISIT_HOME;

    public long accountID;

    public VisitHome() {
        super(ID);

        accountID = 0;
    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);

        stream.putBLong(accountID);
    }

    @Override
    public void decode(DataStream stream) {
        super.decode(stream);

        accountID = stream.getBLong();
    }

    public boolean handle(Handler handler) throws Throwable {
        return handler.handleVisitHome(this);
    }
}
