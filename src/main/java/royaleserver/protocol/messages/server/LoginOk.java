package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class LoginOk extends Message {
	public static final short ID = Info.LOGIN_OK;

	public long userId;
	public long homeId;
	public String userToken;
	public String gameCenterId;
	public String facebookId;
	public int serverMajorVersion;
	public int serverBuild;
	public int contentVersion;
	public String environment;
	public int sessionCount;
	public int playTimeSeconds;
	public int daysSinceStartedPlaying;
	public String facebookAppId;
	public String serverTime;
	public String accountCreatedDate;
	public int unknown_16;
	public String googleServiceId;
	public String unknown_18;
	public String unknown_19;
	public String region;
	public String contentURL;
	public String eventAssetsURL;
	public byte unknown_23;

	public LoginOk() {
		super(ID);

		userId = 0;
		homeId = 0;
		userToken = "";
		gameCenterId = "";
		facebookId = "";
		serverMajorVersion = 0;
		serverBuild = 0;
		serverBuild = 0;
		contentVersion = 0;
		environment = "";
		sessionCount = 0;
		playTimeSeconds = 0;
		daysSinceStartedPlaying = 0;
		facebookAppId = "";
		serverTime = "";
		accountCreatedDate = "";
		unknown_16 = 0;
		googleServiceId = "";
		unknown_18 = "";
		unknown_19 = "";
		region = "";
		contentURL = "";
		eventAssetsURL = "";
		unknown_23 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(userId);
		stream.putBLong(homeId);
		stream.putString(userToken);
		stream.putString(gameCenterId);
		stream.putString(facebookId);
		stream.putRrsInt32(serverMajorVersion);
		stream.putRrsInt32(serverBuild);
		stream.putRrsInt32(serverBuild);
		stream.putRrsInt32(contentVersion);
		stream.putString(environment);
		stream.putRrsInt32(sessionCount);
		stream.putRrsInt32(playTimeSeconds);
		stream.putRrsInt32(daysSinceStartedPlaying);
		stream.putString(facebookAppId);
		stream.putString(serverTime);
		stream.putString(accountCreatedDate);
		stream.putRrsInt32(unknown_16);
		stream.putString(googleServiceId);
		stream.putString(unknown_18);
		stream.putString(unknown_19);
		stream.putString(region);
		stream.putString(contentURL);
		stream.putString(eventAssetsURL);
		stream.putByte(unknown_23);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		userId = stream.getBLong();
		homeId = stream.getBLong();
		userToken = stream.getString();
		gameCenterId = stream.getString();
		facebookId = stream.getString();
		serverMajorVersion = stream.getRrsInt32();
		serverBuild = stream.getRrsInt32();
		serverBuild = stream.getRrsInt32();
		contentVersion = stream.getRrsInt32();
		environment = stream.getString();
		sessionCount = stream.getRrsInt32();
		playTimeSeconds = stream.getRrsInt32();
		daysSinceStartedPlaying = stream.getRrsInt32();
		facebookAppId = stream.getString();
		serverTime = stream.getString();
		accountCreatedDate = stream.getString();
		unknown_16 = stream.getRrsInt32();
		googleServiceId = stream.getString();
		unknown_18 = stream.getString();
		unknown_19 = stream.getString();
		region = stream.getString();
		contentURL = stream.getString();
		eventAssetsURL = stream.getString();
		unknown_23 = stream.getByte();
	}
}
