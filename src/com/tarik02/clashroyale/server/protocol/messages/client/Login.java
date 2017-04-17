package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class Login extends Message {
	public static final short ID = Info.LOGIN;

	public long accountId;
	public String passToken;
	public String resourceSha;
	public String UDID;
	public String openUdid;
	public String macAddress;
	public String device;
	public String advertisingGuid;
	public String osVersion;
	public byte isAndroid;
	public String androidID;
	public String preferredDeviceLanguage;
	public byte preferredLanguage;
	public String facebookAttributionId;
	public byte advertisingEnabled;
	public String appleIFV;
	public String kunlunSSO;
	public String kunlunUID;

	public Login() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(accountId);
		stream.putString(passToken);
		stream.putString(resourceSha);
		stream.putString(UDID);
		stream.putString(openUdid);
		stream.putString(macAddress);
		stream.putString(device);
		stream.putString(advertisingGuid);
		stream.putString(osVersion);
		stream.putByte(isAndroid);
		stream.putString(androidID);
		stream.putString(preferredDeviceLanguage);
		stream.putByte(preferredLanguage);
		stream.putString(facebookAttributionId);
		stream.putByte(advertisingEnabled);
		stream.putString(appleIFV);
		stream.putString(kunlunSSO);
		stream.putString(kunlunUID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		accountId = stream.getBLong();
		passToken = stream.getString();
		resourceSha = stream.getString();
		UDID = stream.getString();
		openUdid = stream.getString();
		macAddress = stream.getString();
		device = stream.getString();
		advertisingGuid = stream.getString();
		osVersion = stream.getString();
		isAndroid = stream.getByte();
		androidID = stream.getString();
		preferredDeviceLanguage = stream.getString();
		preferredLanguage = stream.getByte();
		facebookAttributionId = stream.getString();
		advertisingEnabled = stream.getByte();
		appleIFV = stream.getString();
		kunlunSSO = stream.getString();
		kunlunUID = stream.getString();
	}
}
