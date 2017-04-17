package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class LoginFailed extends Message {
	public static final short ID = Info.LOGIN_FAILED;

	public String resourceFingerprintData;
	public String redirectDomain;
	public String contentURL;
	public String updateURL;
	public String reason;

	public LoginFailed() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(resourceFingerprintData);
		stream.putString(redirectDomain);
		stream.putString(contentURL);
		stream.putString(updateURL);
		stream.putString(reason);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		resourceFingerprintData = stream.getString();
		redirectDomain = stream.getString();
		contentURL = stream.getString();
		updateURL = stream.getString();
		reason = stream.getString();
	}
}
