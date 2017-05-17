package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

import royaleserver.utils.SCID;

public class HomeBattleReplay extends Message {
	public static final short ID = Info.HOME_BATTLE_REPLAY;

	public long battleId;
	public byte unknown_1;
	public byte serverId;
	public byte unknown_3;
	public byte unknown_4;
	public byte feedPosition;
	public byte gameMode;
	public SCID arena;

	public HomeBattleReplay() {
		super(ID);

		battleId = 0;
		unknown_1 = 0;
		serverId = 0;
		unknown_3 = 0;
		unknown_4 = 0;
		feedPosition = 0;
		gameMode = 0;
		arena = new SCID();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(battleId);
		stream.putByte(unknown_1);
		stream.putByte(serverId);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
		stream.putByte(feedPosition);
		stream.putByte(gameMode);
		stream.putSCID(arena);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		battleId = stream.getBLong();
		unknown_1 = stream.getByte();
		serverId = stream.getByte();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
		feedPosition = stream.getByte();
		gameMode = stream.getByte();
		arena = stream.getSCID();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleHomeBattleReplay(this);
	}
}
