package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class LoginFailed extends Message {
	public static final short ID = Info.LOGIN_FAILED;

	public static int ERROR_CODE_RESET_ACCOUNT = 1;
	public static int ERROR_CODE_NEW_ASSETS = 7;

	public int errorCode;
	public String resourceFingerprintData;
	public String redirectDomain;
	public String contentURL;
	public String updateURL;
	public String reason;
	public int secondsUntilMaintenanceEnd;
	public byte unknown_7;
	public String unknown_8;

	public LoginFailed() {
		super(ID);

		errorCode = 0;
		resourceFingerprintData = "";
		redirectDomain = "";
		contentURL = "";
		updateURL = "";
		reason = "";
		secondsUntilMaintenanceEnd = 0;
		unknown_7 = 0;
		unknown_8 = "";
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(errorCode);
		stream.putString(resourceFingerprintData);
		stream.putString(redirectDomain);
		stream.putString(contentURL);
		stream.putString(updateURL);
		stream.putString(reason);
		stream.putRrsInt32(secondsUntilMaintenanceEnd);
		stream.putByte(unknown_7);
		stream.putString(unknown_8);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		errorCode = stream.getRrsInt32();
		resourceFingerprintData = stream.getString();
		redirectDomain = stream.getString();
		contentURL = stream.getString();
		updateURL = stream.getString();
		reason = stream.getString();
		secondsUntilMaintenanceEnd = stream.getRrsInt32();
		unknown_7 = stream.getByte();
		unknown_8 = stream.getString();
	}
}
