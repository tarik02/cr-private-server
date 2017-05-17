package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.Card;
import royaleserver.protocol.messages.component.Deck;
import royaleserver.protocol.messages.component.HomeChest;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;
import royaleserver.utils.SCID;
import royaleserver.utils.UsefulTools;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class OwnHomeData extends Message {
    public static final short ID = Info.OWN_HOME_DATA;

    public long homeId;
    public int accountCreatedTime;
    public int loginTime;

    public String username;
    public int gold;
    public int gems;

	public int trophies;
	public int arena;
	public int lastArena;

    public int level;
    public int lastLevel;
    public int levelExperience;

	public HomeChest homeChests[];

	public Card[] cards;

	public Deck currentDeck;
    public Deck[] decks;

	public String[] offers; // JSON
	public String[] challenges; // JSON

    public OwnHomeData() {
        super(ID);

        accountCreatedTime = (int)(System.currentTimeMillis() / 1000); // I think that it must be devided
	    loginTime = (int)(System.currentTimeMillis() / 1000); // It's also

        offers = new String[]{
                //"{\"ShopOffers\":[{\"PurchasedImage\":{\"Path\":\"\\/6d26b49e892198356e5b72119f64c977\",\"Checksum\":\"6d26b49e892198356e5b72119f64c977\",\"File\":\"shop_retro_royale_01_sold.png\"},\"Rewards\":[{\"Type\":\"spell\",\"Amount\":50,\"Spell\":\"MiniPekka\"}],\"Title\":\"Mini P.E.K.K.A\",\"Image\":{\"Path\":\"\\/e0f1800e761e52fb859bd8dd48bd229f\",\"Checksum\":\"e0f1800e761e52fb859bd8dd48bd229f\",\"File\":\"shop_retro_royale_01.png\"},\"CostType\":\"gems\",\"Cost\":200,\"Template\":\"shop_item3_text\"},{\"PurchasedImage\":{\"Path\":\"\\/9808af35e14b32e6e358f85e7e027eb0\",\"Checksum\":\"9808af35e14b32e6e358f85e7e027eb0\",\"File\":\"shop_retro_royale_02_sold.png\"},\"Rewards\":[{\"Type\":\"spell\",\"Amount\":10,\"Spell\":\"BabyDragon\"}],\"Title\":\"Baby Dragon\",\"Image\":{\"Path\":\"\\/6fae6a66d43a6a430aa9c4496377760a\",\"Checksum\":\"6fae6a66d43a6a430aa9c4496377760a\",\"File\":\"shop_retro_royale_02.png\"},\"CostType\":\"gems\",\"Cost\":400,\"Template\":\"shop_item3_text\"},{\"PurchasedImage\":{\"Path\":\"\\/c799ecc30f63f3aa8098363986558c31\",\"Checksum\":\"c799ecc30f63f3aa8098363986558c31\",\"File\":\"shop_retro_royale_03_sold.png\"},\"Rewards\":[{\"Type\":\"spell\",\"Amount\":10,\"Spell\":\"Prince\"}],\"Title\":\"Prince\",\"Image\":{\"Path\":\"\\/5eb151c16be8e91653f11d926ddf3c6c\",\"Checksum\":\"5eb151c16be8e91653f11d926ddf3c6c\",\"File\":\"shop_retro_royale_03.png\"},\"CostType\":\"gems\",\"Cost\":400,\"Template\":\"shop_item3_text\"}],\"Subtitle\":\"Power up your classic cards!\",\"Title\":\"Retro Royale Stack Offers!\",\"StartNotification\":\"Power up your classic cards with Retro Royale Stack Offers! Available now!\",\"EndNotification\":\"Only two hours left to power up your classic cards with Retro Royale Stack Offers!\"}"//"{\"Title\":\"Тестовое предложение\",\"Subtitle\":\"Тестовое предложение\",\"ShopOffers\":[{\"Rewards\":[{\"Type\":\"gems\",\"Amount\":10000}],\"Multiplier\":2,\"Image\":{\"Path\":\"\\/6f64e932ac74096596f6dcfc3ca9d1ec\",\"Checksum\":\"6f64e932ac74096596f6dcfc3ca9d1ec\",\"File\":\"2v2_double_01.png\"},\"CostType\":\"IAP\",\"CostIAP\":\"com.supercell.scroll.specialoffertier1\",\"Template\":\"shop_item2_no_text\"},{\"Rewards\":[{\"Type\":\"gold\",\"Amount\":6000}],\"Multiplier\":2,\"Image\":{\"Path\":\"/047a47702dff2d04c204232c9d7f07db\",\"Checksum\":\"047a47702dff2d04c204232c9d7f07db\",\"File\":\"2v2_double_02.png\"},\"CostType\":\"IAP\",\"CostIAP\":\"com.supercell.scroll.specialoffertier2\",\"Template\":\"shop_item2_no_text\"}],\"EndNotification\":\"Начинай яй!\",\"StartNotification\":\"ЧТО?\"}"
        };

        challenges = new String[]{
                //"{\"GameMode\":\"CardReleaseDraft\",\"Title\":\"Тестовое испытание\",\"JoinCost\":100,\"JoinCostResource\":\"Gems\",\"MaxLosses\":3,\"Rewards\":[{\"Gold\":1000,\"Cards\":500},{\"Gold\":10000,\"Milestone\":{\"Type\":\"Spell\",\"Amount\":1000,\"Spell\":\"DarkWitch\"},\"Cards\":3},{\"Gold\":240,\"Milestone\":{\"Type\":\"Spell\",\"Amount\":1000,\"Spell\":\"DarkWitch\"},\"Cards\":5},{\"Gold\":10000,\"Milestone\":{\"Type\":\"Spell\",\"Amount\":1000,\"Spell\":\"DarkWitch\"},\"Cards\":5000}],\"IconExportName\":\"icon_tournament_card_release\",\"WinIconExportName\":\"tournament_open_wins_badge_draft\",\"Arena\":\"All\",\"MilestoneHighlightInUI\":3,\"Subtitle\":\"Тестовое испытание\",\"Description\":\"Бла бла бла...\",\"StartNotification\":\"Тестовое испытание началось! Поробуй!\",\"EndNotification\":\"Тестовое испытание закончится через 2 часа! Успей!\",\"CardTheme\":\"DarkWitch\",\"FreePass\":0}"
        };

        cards = new Card[0];

        currentDeck = new Deck();
	    decks = new Deck[3];
	    for (int i = 0; i < 3; ++i) {
		    decks[i] = new Deck();
	    }

        homeChests = new HomeChest[4];

        homeChests[0] = new HomeChest();
        homeChests[0].chestID = new SCID(19, 45);
	    homeChests[0].first = true;
	    homeChests[0].slot = 1;
        homeChests[0].status = HomeChest.STATUS_OPENING;
        homeChests[0].ticksToOpen = 3 * 60 * 20 - 120;
        homeChests[0].openTicks = 3 * 60 * 20;

        homeChests[1] = new HomeChest();
        homeChests[1].chestID = new SCID(19, 45);
        homeChests[1].slot = 2;
        homeChests[1].status = HomeChest.STATUS_OPENED;

        homeChests[2] = new HomeChest();
        homeChests[2].chestID = new SCID(19, 45);
        homeChests[2].slot = 3;
        homeChests[2].status = HomeChest.STATUS_OPENED;

        homeChests[3] = new HomeChest();
        homeChests[3].chestID = new SCID(19, 45);
        homeChests[3].slot = 4;
        homeChests[3].status = HomeChest.STATUS_OPENING;
        homeChests[3].ticksToOpen = 3 * 60 * 20 - 120;
        homeChests[3].openTicks = 3 * 60 * 20;
    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);

	    if (homeChests.length != 4) {
		    throw new RuntimeException("homeChests.length must be 4");
	    }

        stream.putBLong(homeId);

        stream.putRrsInt32(4194); // age / time?
        stream.putRrsInt32(1529); // age / time?

        stream.putRrsInt32(945100); // donation Cooldown Seconds NextFreeChest ?
        stream.putRrsInt32(945100); // donationCapacity

        stream.putRrsInt32(loginTime);

        stream.putByte((byte)0);

        /*stream.putRrsInt32(decks.length);
        for (Deck deck : decks) {
        	deck.encode(stream);
        }*/
        // deck count?
        stream.putRrsInt32(3);

        int[][] decks = new int[][]{
                {26000021, 28000001, 28000011, 27000003, 28000003, 28000004, 26000000, 26000041},
                {26000032, 26000045, 28000004, 28000012, 28000006, 28000001, 26000012, 26000043},
                {27000008, 26000030, 26000010, 26000031, 26000005, 28000000, 27000003, 28000011}
        };

        for (int i = 0; i < decks.length; i++) {
            stream.putRrsInt32(decks[i].length);

            for (int j = 0; j < decks[i].length; j++) {
                stream.putRrsInt32(decks[i][j]);
            }
        }

        stream.put(Hex.toByteArray("ff"));

        // Cards | Deck

        // Да, тот самый говнокод. Нужно сделать двумерный массив:
		/*
			cardID =>
			[
				level
				unk
				count
				unk
				typeOfCard (can be 00/01/02) (1 = upgradable , 2 = card is new)
			]
		*/

	    currentDeck.encode(stream);

	    stream.putRrsInt32(cards.length);
	    for (Card card : cards) {
	    	card.encode(stream);
	    }

        stream.putRrsInt32(0);

        stream.put(Hex.toByteArray("ff"));
        stream.putRrsInt32(60);
        stream.putRrsInt32(6);

        // Пока что непонятно, что эта за структура. Я конечно могу предположить, что это как-то связано с offers/challenges, но это не точно
        for (int i = 0; i < 8; i++) {
            stream.putByte((byte)127);

            stream.putRrsInt32(0);
            stream.putRrsInt32(0);
            stream.putRrsInt32(0);
            stream.putRrsInt32(0);

            stream.putRrsInt32(1);

            // already showed? (offers/challenge id)
            stream.putRrsInt32(i);
        }

        stream.putRrsInt32((int)System.currentTimeMillis());
        stream.putRrsInt32(1);
        stream.putRrsInt32(0);

        // offers struct
        int countOfOffers = offers.length;
        int countOfChallenges = challenges.length;

        stream.putRrsInt32(countOfOffers + countOfChallenges);

        for (int i = 0; i < countOfOffers; i++) {
            stream.putByte((byte) i);
            stream.putString("TestOffer");

            // type
            // 1 - to buy
            // 2 - challenge
            stream.putRrsInt32(1);

            // time
            stream.putRrsInt32((int) System.currentTimeMillis()); // start
            stream.putRrsInt32(1494930622); // end
            stream.putRrsInt32((int) System.currentTimeMillis()); // start?

            stream.put(Hex.toByteArray("00 00 00 00 00 00 00 00"));

            stream.putString("TestOffer");
            stream.putString(offers[i]); // json
        }

        // challenges struct
        for (int i = 0; i < countOfChallenges; i++) {
            stream.putByte((byte) (countOfOffers + i)); // няя
            stream.putString("Test Challenge");

            // type
            // 1 - to buy
            // 2 - challenge
            stream.putRrsInt32(2);

            // time
            stream.putRrsInt32((int) System.currentTimeMillis()); // start
            stream.putRrsInt32(1494937500); // end
            stream.putRrsInt32((int) System.currentTimeMillis()); // start?

            stream.put(Hex.toByteArray("00 00 00 00 00 00 00 00"));

            stream.putString("Test Challenge");
            stream.putString(challenges[i]); // json
        }

        // Пока что руки не дошли до этого, если будет желание, то изучи.
        stream.put(Hex.toByteArray("00011b00012800119c91cf4502019c91cf4504019d91cf4501019d91cf4502019d91cf4503019d91cf4505019d91cf450601a091cf450101a091cf450201a091cf4503019f91cf4503019f91cf450601a391cf450101a391cf450201a391cf450301a491cf450401a891cf4502010e0080c4be8b0b010000042701280129012a05"));

        // year[4]-month[2]-day[2]
        stream.putString("{\"ID\":\"CARD_RELEASE\",\"Params\":{\"Assassin\":\"20170324\",\"Heal\":\"20170501\"}}");

        // 251 - draft chest (максимальная лига)
        // 45 - SuperMagicChest

        //SCID chestID = new SCID(19, 45);
        // STRUCT: 04 = means that now will chest?, 1 = start slot?, (19 ChestID) == SCID (1900000045), ChestStatus, (<= 08 = opening) time to open, time to open, timestamp, unk)

	    for (HomeChest chest : homeChests) {
	    	chest.encode(stream);
	    }

	    // Not decoded yet
        stream.put(Hex.toByteArray("000098ef1a9ca41d91e6f1900b00007f0000000000000000001dbeae2ba1400900a8802884d4d20195baf2900ba8ee8701a0a0d201b59ff7900b00a8850a80c33da5faf0900b030000000000000002"));

        stream.putRrsInt32(lastLevel);
        stream.putByte((byte)36); // unk
        stream.putByte((byte)lastArena);

        stream.put(Hex.toByteArray("c5d9c1ba0902028cb56c8cb56c8ff186910b03018201020000000000001c0100018201020000000000001a1c01018201020000000000001a06020000007f00007f00007f1411b31f901b000381030800011a270109008e0c0000fa0716079ef3b0171f0108003d0689c3b21797030005002a08a9c4d317870c00070081010700050008000109009f050006000c0686e8ab17100005000f06b9a6ab171e0003002806809bb817b60200040002aeeae51890fcd91a02aeeae51890fcd91a00028ed2f83e8fd2f83e048dd2f83e8cd2f83e8ed2f83e9cd2f83e019dd2f83e019081a1fe0b00b90101018ae6bf3301139f0301a9410e7fb012880300000000000000"));

        stream.putRrsLong(homeId);
        stream.putRrsLong(homeId);
        stream.putRrsLong(homeId);

        // username
        stream.putString(username);

        stream.putRrsInt32(0); // changes of username. Если 1, то нельзя будет сменить

        stream.putByte((byte)arena);

        // trophies
        stream.putRrsInt32(trophies);

        // unk
        stream.putRrsInt32(657);

        // # STATS #
        // # Legend Trophies
        stream.putRrsInt32(0);

        // # Current trophies = Season Highest
        stream.putRrsInt32(0);

        // # UNK
        stream.putRrsInt32(0);

        // # Best season => leaderboard
        stream.putRrsInt32(0);

        // # Best season => trophies
        stream.putRrsInt32(0);

        // # UNK
        stream.putRrsInt32(0);
        stream.putRrsInt32(31);

        stream.putRrsInt32(0); // # Leaderboard nr  => previous season
        stream.putRrsInt32(0); // # Trophies        => previous season

        // # UNK
        stream.putRrsInt32(0);
        stream.putRrsInt32(0);
        stream.putRrsInt32(0);

        stream.putRrsInt32(7);

        // самая интересная часть. Только я не понимаю одного, зачем отправлять это в OwnHomeData, когда это должно быть в VisitedHomeData?
        // Можно конечно сделать по-другому, но как быаа можно и так. Зато всё понятно и работает

        Map<String, Integer> mp = new HashMap<String, Integer>();

        mp.put("Gold", gold);
        mp.put("ChallengesWins", 0);
        mp.put("ChestCount", 3);
        mp.put("StarCount", 0);

        mp.put("FreeGold", gold);
        mp.put("Unk_0", 827);

        mp.put("RewardGold", 0);
        mp.put("RewardCount", 0);
        mp.put("Unk_1", 557);
        mp.put("CardRedBadge", 229);
        mp.put("ChallengeCardsWon", 242);

        mp.put("Unk_2", 231);
        mp.put("Unk_3", 234);

        mp.put("TournamentCardsWon", 759);
        mp.put("Unk_4", 1307334552); // какое-то значение

        mp.put("Unk_5", 13);
        mp.put("Unk_6", 0);

        // Прописывай тут остальные gamemod'ы
        // 72000012 - home
        // 72000006 = 1v1

        mp.put("GameMode", 72000012);

        // # COUNT OF RESOURCES #
        stream.putRrsInt32(mp.size());

        Set<String> keys = mp.keySet();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.toArray()[i].toString();
            int typeOfItem = UsefulTools.getTypeRes(key);

            // тип данных (5 - профильные данные)
            stream.putByte((byte) 5);
            stream.putRrsInt32(typeOfItem);
            stream.putRrsInt32(mp.get(key));
        }
        // # ENDSTAT #

        stream.put(Hex.toByteArray("001e3c001a3c0199e3013c0299e3013c0399e3013c040a3c050a3c060a3c0785013c0885013c0985013c0a013c0b860c3c0c860c3c0d860c3c0e013c0f013c10013c118d0b3c120b3c130b3c140b3c15b9013c16b9013c17b9013c180d3c190d3c1a0d3c1b99e3013c1c99e3013c1d99e301173c00013c01013c02013c03013c04013c05013c06013c07013c08013c09013c0a013c0e013c11013c12013c13013c15013c16013c17013c18013c19013c1a013c1b013c1c01090506b1350507"));

        stream.putRrsInt32(0); // three crowns wins

        stream.put(Hex.toByteArray("050885010509a2eae518050a99e301050b1f05140b0515881b051b0a88011a00001a01001a02001a03001a04001a05001a06001a07001a08001a09001a0a001a0b001a0c001a0d001a0e001a0f001a10001a11001a12001a13001a14001a15001a16001a17001a18001a19001a1a001a1b001a1c001a1d221a1e001a1f001a20001a21001a22001a23001a24001a25001a26001a27001a28001a29001a2aa2021a2b001a2d001a2e001b00001b01001b02001b03001b04001b05001b06001b07001b08001b09001b0a001c00001c01001c02001c03001c04001c05001c06001c07001c08001c09001c0a001c0b001c0c001c0d001c100900"));

        stream.putRrsInt32(gems);
        stream.putRrsInt32(gems);

        stream.putRrsInt32(levelExperience);
	    stream.putRrsInt32(level);

        stream.putRrsInt32(1);

        // Тут должна быть система кланов. У меня она реализована, так что в скором времени перенесу.

		/*if (!player.data[2].equals("")) {
			// ClanData
			ClanWorker clanWorker = new ClanWorker(this.player);
			String[] clanData = clanWorker.getClanDataU("ClanID", this.player.data[2]);

			if (clanData != null) {
				// hasName|hasClan // 9 - hasClan and username
				stream.putRrsInt32(9);

				// ClanTAG
				stream.putRrsLong(Long.parseLong(clanData[0]));

				// clan info: 1) name 2) badge
				stream.putString(clanData[1]);
				stream.putRrsInt32(Integer.parseInt(clanData[2]) + 1); // why +1?!

				// role
				stream.putRrsInt32(4);
			}
		} else {*/
        stream.putRrsInt32(1);
        //}

        // Games played
        stream.putRrsInt32(0);

        // Matches played => tournament stats
        stream.putRrsInt32(0);

        // ?
        stream.putRrsInt32(0);

        // Wins
        stream.putRrsInt32(0);

        // Losses
        stream.putRrsInt32(0);

        // WinStreak
        stream.putByte((byte) 127); // 127 = -64 (varint) = null

        // Tutorial Step (06 = setName , 08 = lastDone)
        stream.putRrsInt32(8);

        // Tournament?
        stream.putRrsInt32(0);

        stream.put(Hex.toByteArray("0000"));

        // timestamp countdown?
        stream.putRrsInt32(119049974);

        // account create date/time
        stream.putRrsInt32(accountCreatedTime);

        // time played?
        stream.putRrsInt32(1881931);
    }
}
