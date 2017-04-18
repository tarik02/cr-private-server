package com.tarik02.clashroyale.server;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.protocol.Session;
import com.tarik02.clashroyale.server.protocol.messages.client.*;
import com.tarik02.clashroyale.server.protocol.messages.server.KeepAliveOk;

public class Player implements Handler {
	protected Server server;
	protected Session session;

	public Player(Server server, Session session) {
		this.server = server;
		this.session = session;
	}

	public void close() {

	}

	@Override
	public boolean handleClientHello(ClientHello message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleLogin(Login message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleClientCapabilities(ClientCapabilities message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleKeepAlive(KeepAlive message) throws Throwable {
		KeepAliveOk response = new KeepAliveOk();
		session.sendMessage(message);
		return true;
	}

	@Override
	public boolean handleInboxOpened(InboxOpened message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleEndClientTurn(EndClientTurn message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleVisitHome(VisitHome message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleHomeBattleReplay(HomeBattleReplay message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForAllianceData(AskForAllianceData message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForJoinableAlliancesList(AskForJoinableAlliancesList message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleJoinAlliance(JoinAlliance message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleLeaveAlliance(LeaveAlliance message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleDonateAllianceUnit(DonateAllianceUnit message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleSearchAlliances(SearchAlliances message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForAllianceRankingList(AskForAllianceRankingList message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForTVContent(AskForTVContent message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForAvatarRankingList(AskForAvatarRankingList message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForAvatarLocalRanking(AskForAvatarLocalRanking message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForAvatarStream(AskForAvatarStream message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleAskForBattleReplayStream(AskForBattleReplayStream message) throws Throwable {
		return true;
	}
}
