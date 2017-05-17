package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import static royaleserver.protocol.messages.client.SearchAlliances.ID;
import royaleserver.utils.DataStream;

import royaleserver.utils.SCID;

public class StartMission extends Message {

    public static final short ID = Info.START_MISSION;

    public String searchString;
    public SCID desiredRegion;
    public int minMembers;
    public int maxMembers;
    public int minTrophies;
    public boolean findOnlyJoinableClans;
    public int unknown_6;
    public int unknown_7;

    public StartMission() {
        super(ID);
    }

    @Override
    public void decode(DataStream stream) {

    }

    public boolean handle(Handler handler) throws Throwable {
        return handler.handleStartMission(this);
    }

}
