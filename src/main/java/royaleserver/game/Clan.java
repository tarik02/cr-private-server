package royaleserver.game;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.network.Filler;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.ClanMember;
import royaleserver.network.protocol.server.messages.ClanEvent;
import royaleserver.network.protocol.server.messages.ClanMemberAdd;
import royaleserver.network.protocol.server.messages.ClanMemberRemove;
import royaleserver.network.protocol.server.messages.ClanOnlineUpdate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Clan {
	private final long id;
	private final Map<Long, Player> players = new ConcurrentHashMap<>();

	public Clan(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void addPlayer(Player player) {
		if (!players.containsKey(player.entity.getId())) {
			players.put(player.entity.getId(), player);
			updateOnline();
		}
	}

	public void removePlayer(Player player) {
		if (players.remove(player.entity.getId(), player)) {
			if (players.size() == 0) {
				player.getServer().removeClan(this);
			} else {
				updateOnline();
			}
		}
	}

	public void joinPlayer(Player player) {
		addPlayer(player);

		PlayerEntity playerEntity = player.entity;

		ClanMemberAdd clanMemberAdd = new ClanMemberAdd();
		Filler.fill(clanMemberAdd.member = new ClanMember(), playerEntity);
		broadcastExcept(clanMemberAdd, player);

		ClanEvent clanEvent = new ClanEvent();
		clanEvent.entryId = id;
		clanEvent.senderAvatarId = playerEntity.getId();
		clanEvent.senderAvatarId2 = playerEntity.getId();
		clanEvent.senderName = playerEntity.getName();
		clanEvent.senderLevel = playerEntity.getLogicExpLevel();
		clanEvent.senderRole = playerEntity.getLogicClanRole();
		clanEvent.ageSeconds = 0;
		clanEvent.isRemoved = 0;

		clanEvent.event = ClanEvent.EVENT_JOINED;
		clanEvent.initiatorId = playerEntity.getId();
		clanEvent.initiatorName = playerEntity.getName();
		broadcast(clanEvent);
	}

	public void leavePlayer(Player player) {
		PlayerEntity playerEntity = player.entity;

		ClanMemberRemove clanMemberRemove = new ClanMemberRemove();
		Filler.fill(clanMemberRemove.member = new ClanMember(), playerEntity);
		broadcastExcept(clanMemberRemove, player);

		ClanEvent clanEvent = new ClanEvent();
		clanEvent.entryId = id;
		clanEvent.senderAvatarId = playerEntity.getId();
		clanEvent.senderAvatarId2 = playerEntity.getId();
		clanEvent.senderName = playerEntity.getName();
		clanEvent.senderLevel = playerEntity.getLogicExpLevel();
		clanEvent.senderRole = playerEntity.getLogicClanRole();
		clanEvent.ageSeconds = 0;
		clanEvent.isRemoved = 0;

		clanEvent.event = ClanEvent.EVENT_LEFT;
		clanEvent.initiatorId = playerEntity.getId();
		clanEvent.initiatorName = playerEntity.getName();
		broadcast(clanEvent);

		removePlayer(player);
	}

	public boolean hasPlayer(Player player) {
		return players.containsKey(player.entity.getId());
	}


	public void broadcast(ServerMessage message) {
		for (Player player : players.values()) {
			player.getSession().sendMessage(message);
		}
	}

	public void broadcastExcept(ServerMessage message, Player exception) {
		for (Player player : players.values()) {
			if (player == exception) {
				continue;
			}

			player.getSession().sendMessage(message);
		}
	}

	public void updateOnline() {
		ClanOnlineUpdate message = new ClanOnlineUpdate();
		message.membersOnline = players.size();
		//broadcast(message);
	}
}
