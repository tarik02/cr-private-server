package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class Login extends Message {
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

		accountId = 0;
		passToken = "";
		clientMajorVersion = 0;
		clientMinorVersion = 0;
		clientBuild = 0;
		resourceSha = "";
		UDID = "";
		openUdid = "";
		macAddress = "";
		device = "";
		advertisingGuid = "";
		osVersion = "";
		isAndroid = 0;
		unknown_13 = "";
		androidID = "";
		preferredDeviceLanguage = "";
		unknown_16 = 0;
		preferredLanguage = 0;
		facebookAttributionId = "";
		advertisingEnabled = 0;
		appleIFV = "";
		appStore = 0;
		kunlunSSO = "";
		kunlunUID = "";
		unknown_24 = "";
		unknown_25 = "";
		unknown_26 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(accountId);
		stream.putString(passToken);
		stream.putRrsInt32(clientMajorVersion);
		stream.putRrsInt32(clientMinorVersion);
		stream.putRrsInt32(clientBuild);
		stream.putString(resourceSha);
		stream.putString(UDID);
		stream.putString(openUdid);
		stream.putString(macAddress);
		stream.putString(device);
		stream.putString(advertisingGuid);
		stream.putString(osVersion);
		stream.putByte(isAndroid);
		stream.putString(unknown_13);
		stream.putString(androidID);
		stream.putString(preferredDeviceLanguage);
		stream.putByte(unknown_16);
		stream.putByte(preferredLanguage);
		stream.putString(facebookAttributionId);
		stream.putByte(advertisingEnabled);
		stream.putString(appleIFV);
		stream.putRrsInt32(appStore);
		stream.putString(kunlunSSO);
		stream.putString(kunlunUID);
		stream.putString(unknown_24);
		stream.putString(unknown_25);
		stream.putByte(unknown_26);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

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

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleLogin(this);
	}
}
