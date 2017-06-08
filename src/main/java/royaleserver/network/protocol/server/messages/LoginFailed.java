package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class LoginFailed extends ServerMessage {
	public static final short ID = Messages.LOGIN_FAILED;

	public static int ERROR_CODE_RESET_ACCOUNT = 3;
	public static int ERROR_CODE_NEW_ASSETS = 7;
	public static int ERROR_CODE_MAINTENANCE = 10;
	public static int ERROR_CODE_BANNED = 11;
	public static int ERROR_CODE_ACCOUNT_BLOCKED = 13;

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
	}

	@Override
	public ServerMessage create() {
		return new LoginFailed();
	}

	@Override
	public void encode(DataStream stream) {
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
}
