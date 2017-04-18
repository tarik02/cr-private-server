package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class OwnHomeData extends Message {
	public static final short ID = Info.OWN_HOME_DATA;

	public int age;
	public int id;
	public long timeStamp;
	public int unknown_3;
	public int unknown_4;
	public int unknown_5;

	public OwnHomeData() {
		super(ID);

		age = 0;
		id = 0;
		timeStamp = 0;
		unknown_3 = 0;
		unknown_4 = 0;
		unknown_5 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBInt(age);
		stream.putBInt(id);
		stream.putBLong(timeStamp);
		stream.putBInt(unknown_3);
		stream.putBInt(unknown_4);
		stream.putBInt(unknown_5);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		age = stream.getBInt();
		id = stream.getBInt();
		timeStamp = stream.getBLong();
		unknown_3 = stream.getBInt();
		unknown_4 = stream.getBInt();
		unknown_5 = stream.getBInt();
	}
}
