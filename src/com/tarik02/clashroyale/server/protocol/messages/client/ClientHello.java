package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ClientHello extends Message {
	public static final short ID = Info.CLIENT_HELLO;

	public int protocol;
	public int keyVersion;
	public int majorVersion;
	public int minorVersion;
	public int build;
	public String contentHash;
	public int deviceType;
	public int appStore;

	public ClientHello() {
		super(ID);

		protocol = 0;
		keyVersion = 0;
		majorVersion = 0;
		minorVersion = 0;
		build = 0;
		contentHash = "";
		deviceType = 0;
		appStore = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBInt(protocol);
		stream.putBInt(keyVersion);
		stream.putBInt(majorVersion);
		stream.putBInt(minorVersion);
		stream.putBInt(build);
		stream.putString(contentHash);
		stream.putBInt(deviceType);
		stream.putBInt(appStore);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		protocol = stream.getBInt();
		keyVersion = stream.getBInt();
		majorVersion = stream.getBInt();
		minorVersion = stream.getBInt();
		build = stream.getBInt();
		contentHash = stream.getString();
		deviceType = stream.getBInt();
		appStore = stream.getBInt();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleClientHello(this);
	}
}
