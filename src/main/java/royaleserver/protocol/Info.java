package royaleserver.protocol;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Info {
	private Info() {}

	// Client messages
	public static final short CLIENT_HELLO = 10100;
	public static final short LOGIN = 10101;
	public static final short CLIENT_CAPABILITIES = 10107;
	public static final short KEEP_ALIVE = 10108;
	public static final short AUTHENTICATION_CHECK = 10112;
	public static final short SET_DEVICE_TOKEN = 10113;
	public static final short RESET_ACCOUNT = 10116;
	public static final short REPORT_USER = 10117;
	public static final short ACCOUNT_SWITCHED = 10118;
	public static final short UNLOCK_ACCOUNT = 10121;
	public static final short APPLE_BILLING_REQUEST = 10150;
	public static final short GOOGLE_BILLING_REQUEST = 10151;
	public static final short KUNLUN_BILLING_REQUEST = 10159;
	public static final short CHANGE_AVATAR_NAME = 10212;
	public static final short ASK_FOR_PLAYING_GAMECENTER_FRIENDS = 10512;
	public static final short ASK_FOR_PLAYING_FACEBOOK_FRIENDS = 10513;
	public static final short INBOX_OPENED = 10905;
	public static final short UNBIND_FACEBOOK_ACCOUNT = 12211;
	public static final short REQUEST_SECTOR_STATE = 12903;
	public static final short SECTOR_COMMAND = 12904;
	public static final short GET_CURRENT_BATTLE_REPLAY_DATA = 12905;
	public static final short SEND_BATTLE_EVENT = 12951;
	public static final short GO_HOME = 14101;
	public static final short END_CLIENT_TURN = 14102;
	public static final short START_MISSION = 14104;
	public static final short HOME_LOGIC_STOPPED = 14105;
	public static final short CANCEL_MATCHMAKE = 14107;
	public static final short CHANGE_HOME_NAME = 14108;
	public static final short VISIT_HOME = 14113;
	public static final short HOME_BATTLE_REPLAY = 14114;
	public static final short HOME_BATTLE_REPLAY_VIEWED = 14117;
	public static final short ACCEPT_CHALLENGE = 14120;
	public static final short CANCEL_CHALLENGE_MESSAGE = 14123;
	public static final short BIND_FACEBOOK_ACCOUNT = 14201;
	public static final short BIND_GAMECENTER_ACCOUNT = 14212;
	public static final short BIND_GOOGLE_SERVICE_ACCOUNT = 14262;
	public static final short CREATE_ALLIANCE = 14301;
	public static final short ASK_FOR_ALLIANCE_DATA = 14302;
	public static final short ASK_FOR_JOINABLE_ALLIANCES_LIST = 14303;
	public static final short ASK_FOR_ALLIANCE_STREAM = 14304;
	public static final short JOIN_ALLIANCE = 14305;
	public static final short CHANGE_ALLIANCE_MEMBER_ROLE = 14306;
	public static final short KICK_ALLIANCE_MEMBER = 14307;
	public static final short LEAVE_ALLIANCE = 14308;
	public static final short DONATE_ALLIANCE_UNIT = 14310;
	public static final short CHAT_TO_ALLIANCE_STREAM = 14315;
	public static final short CHANGE_ALLIANCE_SETTINGS = 14316;
	public static final short REQUEST_JOIN_ALLIANCE = 14317;
	public static final short SELECT_SPELLS_FROM_CO_OPEN = 14318;
	public static final short OFFER_CHEST_FOR_CO_OPEN = 14319;
	public static final short RESPOND_TO_ALLIANCE_JOIN_REQUEST = 14321;
	public static final short SEND_ALLIANCE_INVITATION = 14322;
	public static final short JOIN_ALLIANCE_USING_INVITATION = 14323;
	public static final short SEARCH_ALLIANCES = 14324;
	public static final short SEND_ALLIANCE_MAIL = 14330;
	public static final short ASK_FOR_ALLIANCE_RANKING_LIST = 14401;
	public static final short ASK_FOR_TV_CONTENT = 14402;
	public static final short ASK_FOR_AVATAR_RANKING_LIST = 14403;
	public static final short ASK_FOR_AVATAR_LOCAL_RANKING = 14404;
	public static final short ASK_FOR_AVATAR_STREAM = 14405;
	public static final short ASK_FOR_BATTLE_REPLAY_STREAM = 14406;
	public static final short ASK_FOR_LAST_AVATAR_TOURNAMENT_RESULTS = 14408;
	public static final short REMOVE_AVATAR_STREAM_ENTRY = 14418;
	public static final short AVATAR_NAME_CHECK_REQUEST = 14600;
	public static final short LOGIC_DEVICE_LINK_CODE_STATUS = 16000;
	public static final short ASK_FOR_JOINABLE_TOURNAMENTS = 16103;
	public static final short SEARCH_TOURNAMENTS = 16113;

	// Server messages
	public static final short SERVER_HELLO = 20100;
	public static final short LOGIN_FAILED = 20103;
	public static final short LOGIN_OK = 20104;
	public static final short FRIEND_LIST = 20105;
	public static final short KEEP_ALIVE_OK = 20108;
	public static final short CHAT_ACCOUNT_BAN_STATUS = 20118;
	public static final short BILLING_REQUEST_FAILED = 20121;
	public static final short UNLOCK_ACCOUNT_OK = 20132;
	public static final short UNLOCK_ACCOUNT_FAILED = 20133;
	public static final short APPLE_BILLING_PROCESSED_BY_SERVER = 20151;
	public static final short GOOGLE_BILLING_PROCESSED_BY_SERVER = 20152;
	public static final short KUNLUN_BILLING_PROCESSED_BY_SERVER = 20156;
	public static final short SHUTDOWN_STARTED = 20161;
	public static final short AVATAR_NAME_CHANGE_FAILED = 20205;
	public static final short AVATAR_IN_GAME_STATUS_UPDATED = 20206;
	public static final short ALLIANCE_ONLINE_STATUS_UPDATED = 20207;
	public static final short BATTLE_RESULT = 20225;
	public static final short AVATAR_NAME_CHECK_RESPONSE = 20300;
	public static final short OPPONENT_LEFT_MATCH_NOTIFICATION = 20801;
	public static final short OPPONENT_REJOINS_MATCH_NOTIFICATION = 20802;
	public static final short SECTOR_HEARBEAT = 21902;
	public static final short SECTOR_STATE = 21903;
	public static final short BATTLE_EVENT = 22952;
	public static final short PVP_MATCHMAKE_NOTIFICATION = 22957;
	public static final short OWN_HOME_DATA = 24101;
	public static final short OWN_AVATAR_DATA = 24102;
	public static final short OUT_OF_SYNC = 24104;
	public static final short STOP_HOME_LOGIC = 24106;
	public static final short MATCHMAKE_INFO = 24107;
	public static final short MATCHMAKE_FAILED = 24108;
	public static final short AVAILABLE_SERVER_COMMAND = 24111;
	public static final short UDP_CONNECTION_INFO = 24112;
	public static final short VISITED_HOME_DATA = 24113;
	public static final short HOME_BATTLE_REPLAY_DATA = 24114;
	public static final short SERVER_ERROR = 24115;
	public static final short HOME_BATTLE_REPLAY_FAILED = 24116;
	public static final short CHALLENGE_FAILED = 24121;
	public static final short CANCEL_CHALLENGE_DONE = 24124;
	public static final short CANCEL_MATCHMAKE_DONE = 24125;
	public static final short FACEBOOK_ACCOUNT_BOUND = 24201;
	public static final short FACEBOOK_ACCOUNT_ALREADY_BOUND = 24202;
	public static final short GAMECENTER_ACCOUNT_ALREADY_BOUND = 24212;
	public static final short FACEBOOK_ACCOUNT_UNBOUND = 24213;
	public static final short GOOGLE_SERVICE_ACCOUNT_BOUND = 24261;
	public static final short GOOGLE_SERVICE_ACCOUNT_ALREADY_BOUND = 24262;
	public static final short ALLIANCE_DATA = 24301;
	public static final short ALLIANCE_JOIN_FAILED = 24302;
	public static final short ALLIANCE_JOIN_OK = 24303;
	public static final short JOINABLE_ALLIANCE_LIST = 24304;
	public static final short ALLIANCE_LEAVE_OK = 24305;
	public static final short CHANGE_ALLIANCE_MEMBER_ROLE_OK = 24306;
	public static final short KICK_ALLIANCE_MEMBER_OK = 24307;
	public static final short ALLIANCE_MEMBER = 24308;
	public static final short ALLIANCE_MEMBER_REMOVED = 24309;
	public static final short ALLIANCE_LIST = 24310;
	public static final short ALLIANCE_STREAM = 24311;
	public static final short ALLIANCE_STREAM_ENTRY = 24312;
	public static final short ALLIANCE_STREAM_ENTRY_REMOVED = 24318;
	public static final short ALLIANCE_JOIN_REQUEST_OK = 24319;
	public static final short ALLIANCE_JOIN_REQUEST_FAILED = 24320;
	public static final short ALLIANCE_INVITATION_SEND_FAILED = 24321;
	public static final short ALLIANCE_INVITATION_SENT_OK = 24322;
	public static final short ALLIANCE_FULL_ENTRY_UPDATE = 24324;
	public static final short ALLIANCE_CREATE_FAILED = 24332;
	public static final short ALLIANCE_CHANGE_FAILED = 24333;
	public static final short ALLIANCE_RANKING_LIST = 24401;
	public static final short ALLIANCE_LOCAL_RANKING_LIST = 24402;
	public static final short AVATAR_RANKING_LIST = 24403;
	public static final short AVATAR_LOCAL_RANKING_LIST = 24404;
	public static final short ROYAL_TV_CONTENT = 24405;
	public static final short LAST_AVATAR_TOURNAMENT_RESULTS = 24407;
	public static final short AVATAR_STREAM = 24411;
	public static final short AVATAR_STREAM_ENTRY = 24412;
	public static final short BATTLE_REPORT_STREAM = 24413;
	public static final short AVATAR_STREAM_ENTRY_REMOVED = 24418;
	public static final short INBOX_LIST = 24445;
	public static final short INBOX_GLOBAL = 24446;
	public static final short INBOX_COUNT = 24447;
	public static final short DISCONNECTED = 25892;
	public static final short LOGIC_DEVICE_LINK_CODE_RESPONSE = 26002;
	public static final short LOGIC_DEVICE_LINK_NEW_DEVICE_LINKED = 26003;
	public static final short LOGIC_DEVICE_LINK_CODE_DEACTIVATED = 26004;
	public static final short LOGIC_DEVICE_LINK_RESPONSE = 26005;
	public static final short LOGIC_DEVICE_LINK_DONE = 26007;
	public static final short LOGIC_DEVICE_LINK_ERROR = 26008;
	public static final short TOURNAMENT_LIST_SEND = 26108;

	public static final Map<Short, String> messagesMap = createMessagesMap();

	private static Map<Short, String> createMessagesMap() {
		Map<Short, String> map = new HashMap<>();

		for (Field field : Info.class.getFields()) {
			int modifiers = field.getModifiers();
			if (Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && field.getType() == short.class) {
				try {
					map.put(field.getShort(null), field.getName());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return Collections.unmodifiableMap(map);
	}
}
