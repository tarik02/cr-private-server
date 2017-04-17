package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class OwnHomeData extends Message {
	public static final short ID = Info.OWN_HOME_DATA;

	public int age;
	public int id;
	public long timeStamp;

	public OwnHomeData() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBInt(age);
		stream.putBInt(id);
		stream.putBLong(timeStamp);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		age = stream.getBInt();
		id = stream.getBInt();
		timeStamp = stream.getBLong();
	}
}
