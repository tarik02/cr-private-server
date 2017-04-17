package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class LoginOk extends Message {
	public static final short ID = Info.LOGIN_OK;

	public long userId;
	public long homeId;
	public String userToken;
	public String gameCenterId;
	public String facebookId;
	public String environment;
	public String facebookAppId;
	public String serverTime;
	public String accountCreatedDate;
	public String googleServiceId;
	public String region;
	public String contentURL;
	public String eventAssetsURL;

	public LoginOk() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(userId);
		stream.putBLong(homeId);
		stream.putString(userToken);
		stream.putString(gameCenterId);
		stream.putString(facebookId);
		stream.putString(environment);
		stream.putString(facebookAppId);
		stream.putString(serverTime);
		stream.putString(accountCreatedDate);
		stream.putString(googleServiceId);
		stream.putString(region);
		stream.putString(contentURL);
		stream.putString(eventAssetsURL);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		userId = stream.getBLong();
		homeId = stream.getBLong();
		userToken = stream.getString();
		gameCenterId = stream.getString();
		facebookId = stream.getString();
		environment = stream.getString();
		facebookAppId = stream.getString();
		serverTime = stream.getString();
		accountCreatedDate = stream.getString();
		googleServiceId = stream.getString();
		region = stream.getString();
		contentURL = stream.getString();
		eventAssetsURL = stream.getString();
	}
}
