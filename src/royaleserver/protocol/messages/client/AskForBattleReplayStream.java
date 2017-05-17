package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AskForBattleReplayStream extends Message {
	public static final short ID = Info.ASK_FOR_BATTLE_REPLAY_STREAM;

	public int accountID_hi;
        public int accountID_lo;

	public AskForBattleReplayStream() {
		super(ID);

		accountID_hi = 0;
                accountID_lo = 0;
	}

	@Override
	public void encode(DataStream stream) {
            super.encode(stream);

            stream.putBInt(accountID_hi);
            stream.putBInt(accountID_lo);
	}

	@Override
	public void decode(DataStream stream) {
            super.decode(stream);
            
            accountID_hi = stream.getLInt();
            accountID_lo = stream.getLInt();
        }

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleAskForBattleReplayStream(this);
	}
}
