package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.protocol.Info;
import royaleserver.utils.DataStream;

public class Login extends ClientMessage {
	public static final short ID = Info.LOGIN;

	public long accountId;
	public String passToken;
	public int clientMajorVersion;
	public int clientMinorVersion;
	public int clientBuild;
	public String resourceSha;
	public String UDID;
	public String openUdid;
	public String macAddress;
	public String device;
	public String advertisingGuid;
	public String osVersion;
	public byte isAndroid;
	public String unknown_13;
	public String androidID;
	public String preferredDeviceLanguage;
	public byte unknown_16;
	public byte preferredLanguage;
	public String facebookAttributionId;
	public byte advertisingEnabled;
	public String appleIFV;
	public int appStore;
	public String kunlunSSO;
	public String kunlunUID;
	public String unknown_24;
	public String unknown_25;
	public byte unknown_26;

	public Login() {
		super(ID);
	}

	public void decode(DataStream stream) {
		accountId = stream.getBLong();
		passToken = stream.getString();
		clientMajorVersion = stream.getRrsInt32();
		clientMinorVersion = stream.getRrsInt32();
		clientBuild = stream.getRrsInt32();
		resourceSha = stream.getString();
		UDID = stream.getString();
		openUdid = stream.getString();
		macAddress = stream.getString();
		device = stream.getString();
		advertisingGuid = stream.getString();
		osVersion = stream.getString();
		isAndroid = stream.getByte();
		unknown_13 = stream.getString();
		androidID = stream.getString();
		preferredDeviceLanguage = stream.getString();
		unknown_16 = stream.getByte();
		preferredLanguage = stream.getByte();
		facebookAttributionId = stream.getString();
		advertisingEnabled = stream.getByte();
		appleIFV = stream.getString();
		appStore = stream.getRrsInt32();
		kunlunSSO = stream.getString();
		kunlunUID = stream.getString();
		unknown_24 = stream.getString();
		unknown_25 = stream.getString();
		unknown_26 = stream.getByte();
	}

	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleClientMessageLogin(this);
	}
}
