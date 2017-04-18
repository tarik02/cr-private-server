package com.tarik02.clashroyale.server.protocol;

import com.tarik02.clashroyale.server.protocol.messages.client.ClientHello;
import com.tarik02.clashroyale.server.protocol.messages.client.Login;
import com.tarik02.clashroyale.server.protocol.messages.client.ClientCapabilities;
import com.tarik02.clashroyale.server.protocol.messages.client.KeepAlive;
import com.tarik02.clashroyale.server.protocol.messages.client.InboxOpened;
import com.tarik02.clashroyale.server.protocol.messages.client.EndClientTurn;
import com.tarik02.clashroyale.server.protocol.messages.client.VisitHome;
import com.tarik02.clashroyale.server.protocol.messages.client.HomeBattleReplay;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForAllianceData;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForJoinableAlliancesList;
import com.tarik02.clashroyale.server.protocol.messages.client.JoinAlliance;
import com.tarik02.clashroyale.server.protocol.messages.client.LeaveAlliance;
import com.tarik02.clashroyale.server.protocol.messages.client.DonateAllianceUnit;
import com.tarik02.clashroyale.server.protocol.messages.client.SearchAlliances;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForAllianceRankingList;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForTVContent;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForAvatarRankingList;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForAvatarLocalRanking;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForAvatarStream;
import com.tarik02.clashroyale.server.protocol.messages.client.AskForBattleReplayStream;

public interface Handler {
	boolean handleClientHello(ClientHello message) throws Throwable;
	boolean handleLogin(Login message) throws Throwable;
	boolean handleClientCapabilities(ClientCapabilities message) throws Throwable;
	boolean handleKeepAlive(KeepAlive message) throws Throwable;
	boolean handleInboxOpened(InboxOpened message) throws Throwable;
	boolean handleEndClientTurn(EndClientTurn message) throws Throwable;
	boolean handleVisitHome(VisitHome message) throws Throwable;
	boolean handleHomeBattleReplay(HomeBattleReplay message) throws Throwable;
	boolean handleAskForAllianceData(AskForAllianceData message) throws Throwable;
	boolean handleAskForJoinableAlliancesList(AskForJoinableAlliancesList message) throws Throwable;
	boolean handleJoinAlliance(JoinAlliance message) throws Throwable;
	boolean handleLeaveAlliance(LeaveAlliance message) throws Throwable;
	boolean handleDonateAllianceUnit(DonateAllianceUnit message) throws Throwable;
	boolean handleSearchAlliances(SearchAlliances message) throws Throwable;
	boolean handleAskForAllianceRankingList(AskForAllianceRankingList message) throws Throwable;
	boolean handleAskForTVContent(AskForTVContent message) throws Throwable;
	boolean handleAskForAvatarRankingList(AskForAvatarRankingList message) throws Throwable;
	boolean handleAskForAvatarLocalRanking(AskForAvatarLocalRanking message) throws Throwable;
	boolean handleAskForAvatarStream(AskForAvatarStream message) throws Throwable;
	boolean handleAskForBattleReplayStream(AskForBattleReplayStream message) throws Throwable;
}
