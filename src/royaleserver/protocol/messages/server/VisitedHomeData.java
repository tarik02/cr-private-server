package royaleserver.protocol.messages.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import royaleserver.Player;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.client.VisitHome;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;
import royaleserver.utils.UsefulTools;

public class VisitedHomeData extends Message {

    public static final short ID = Info.VISITED_HOME_DATA;

    public long homeID;
    public String username;
    public int arena;
    public int trophies;
    public String deckCards;

    public VisitedHomeData(Player player) {
        super(ID);

    }

    @Override
    public void encode(DataStream stream) {
        super.encode(stream);

        stream.putRrsInt32(250);
        stream.putRrsInt32(1);

        stream.put(Hex.toByteArray("ff"));

        String[] deckCardsArray = "1,8,0,1162,0,0,0,6,2,0,1023,0,0,0,8,1,0,23,0,0,0,10,1,0,39,0,0,0,21,4,0,31,0,0,0,43,1,0,10,0,0,0,46,2,0,39,0,0,0,49,2,0,10,0,0,0".split(",");

        String playerCards = "";
        String[] playerCardsArray = playerCards.split(",");

        for (int i = 0; i < deckCardsArray.length; i++) {
            stream.putRrsInt32(Integer.parseInt(deckCardsArray[i].trim()));
        }

        stream.putBLong(homeID);

        stream.putByte((byte) 0);
        stream.putByte((byte) 0);

        stream.putRrsLong(homeID);
        stream.putRrsLong(homeID);
        stream.putRrsLong(homeID);

        stream.putString(username); // name
        stream.putRrsInt32(0); // name changes count
        stream.putRrsInt32(8); // arena

        stream.putRrsInt32(3500); // trophies
        stream.putRrsInt32(657); // unk_6

        stream.putRrsInt32(0); // trophies legendary
        stream.putRrsInt32(0); // trophies s h

        stream.putRrsInt32(0); // always30

        stream.putRrsInt32(0); // BestSeasonLeaderboardNr
        stream.putRrsInt32(0); // BestSeasonTrophies

        stream.putRrsInt32(0); // unk_6_b
        stream.putRrsInt32(31); // always30_2

        stream.putRrsInt32(0); // PreviousSeasonLeaderboardNr
        stream.putRrsInt32(0); // PreviousSeasonTrophies

        stream.putRrsInt32(0); // HighestTrophies

        stream.putRrsInt32(0); //unk_7 = p.readRRSInt32()
        stream.putRrsInt32(0); //unk_8 = p.readRRSInt32()
        stream.putRrsInt32(7); //always_7 = p.readRRSInt32()

        Map<String, Integer> mp = new HashMap<String, Integer>();

        mp.put("Gold", 15000);
        mp.put("ChallengesWins", 0);
        mp.put("ChestCount", 3);
        mp.put("StarCount", 0);

        mp.put("FreeGold", 15000);
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

        Set<String> keys = mp.keySet();

        // # COUNT OF RESOURCES #
        stream.putRrsInt32(mp.size());

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.toArray()[i].toString();
            int typeOfItem = UsefulTools.getTypeRes(key);

            stream.putByte((byte) 5);
            stream.putRrsInt32(typeOfItem);
            stream.putRrsInt32(mp.get(key));
        }

        stream.putRrsInt32(0);

        //System.out.println("len:" + playerInfo[8].split(",").length);
        //System.out.println("len:" + playerInfo[9].split(",").length);

        int cardsFound = (deckCardsArray.length / 7) + (playerCardsArray.length / 7); // + my cards
        int cardsGiven = 0;

        // Tournament
        int cardsWonT = 0;

        // Challenge
        int maxWinsC = 0;
        int cardsWonC = 0;

        int[] statItems = new int[]{
                26, cardsGiven, cardsGiven, cardsGiven,
                10, 10, 10,
                cardsFound, cardsFound, cardsFound,
                1, cardsWonT, cardsWonT, cardsWonT,
                1, 1, 1, 726, maxWinsC, maxWinsC, maxWinsC,
                121, 121, 121, 14, 14, 14,
                cardsGiven, cardsGiven, cardsGiven
        };

        stream.putRrsInt32(statItems.length);

        for (int i = 0; i < statItems.length; i++) {
            stream.putByte((byte) 60);
            stream.putRrsInt32(i);
            stream.putRrsInt32(statItems[i]);
        }

        // Unknown structure
        int lengthOfStruct = 28;

        stream.putRrsInt32(lengthOfStruct);

        for (int i = 0; i < lengthOfStruct; i++) {
            stream.putByte((byte) 60);
            stream.putRrsInt32(i);
            stream.putRrsInt32(1);
        }

        Map<String, Integer> sI2 = new HashMap<String, Integer>();

        sI2.put("HighestTrophies", trophies);
        sI2.put("ThreeCrownWins", 0);
        sI2.put("CardsFound", cardsFound);
        sI2.put("FavoriteCard", 0); // 27000008
        sI2.put("CardsGiven", cardsGiven);
        sI2.put("Unk_N11", 31);
        sI2.put("MaxWins", maxWinsC);
        sI2.put("ChallengeCardsWon", cardsWonC);
        sI2.put("Unk_N27", 10);

        Set<String> keysI2 = sI2.keySet();

        // # COUNT OF RESOURCES #
        stream.putRrsInt32(sI2.size());

        for (int i = 0; i < keysI2.size(); i++) {
            String key = keysI2.toArray()[i].toString();
            int typeOfItem = UsefulTools.getTypeRes(key);

            stream.putByte((byte) 5);
            stream.putRrsInt32(typeOfItem);
            stream.putRrsInt32(sI2.get(key));
        }

        // Unknown structure 2
        int lengthOfStruct_2 = 46;
        int lengthOfStruct_3 = 11;
        int lengthOfStruct_4 = 15;

        stream.putRrsInt32(lengthOfStruct_2 + lengthOfStruct_3 + lengthOfStruct_4);

        for (int i = 0; i < lengthOfStruct_2; i++) {
            stream.putByte((byte) 26);
            stream.putRrsInt32(i);
            stream.putRrsInt32(0);
        }

        for (int i = 0; i < lengthOfStruct_3; i++) {
            stream.putByte((byte) 27);
            stream.putRrsInt32(i);
            stream.putRrsInt32(0);
        }

        for (int i = 0; i < lengthOfStruct_4; i++) {
            stream.putByte((byte) 28);
            stream.putRrsInt32(i);
            stream.putRrsInt32(0);
        }

        // 0
        stream.putRrsInt32(0);

        // Gems | Free gems
        stream.putRrsInt32(10000);
        stream.putRrsInt32(10000);

        // Experience
        stream.putRrsInt32(0);

        // Level
        stream.putRrsInt32(13);

        // isMyProfile // 0 - not // 1 - yes, it's my profile
        // it's for "invite" button
        // System.out.println(this.player.id);
        // System.out.println(Long.parseLong(playerInfo[0]));

        //if (this.player.id == Long.parseLong(playerInfo[0])) {

        stream.putRrsInt32(1);

        /*
        if (!playerInfo[2].equals("")) {
            // ClanData
            ClanWorker clanWorker = new ClanWorker(this.player);
            String[] clanData = clanWorker.getClanDataU("ClanID", playerInfo[2]);

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

        // # END CLAN INFO # //
        // Matches played (global/tournament/challenge)?
        stream.putRrsInt32(0); // global?
        stream.putRrsInt32(0); // tournament
        stream.putRrsInt32(0); // unk

        // Player Wins
        stream.putRrsInt32(0);

        // Player Losses
        stream.putRrsInt32(0);

        // Unknown
        stream.putRrsInt32(1);

        // ?Crowns earned => ladder?
        stream.putRrsInt32(0);

        // Tutorial Step (06 = setName , 08 = lastDone)
        stream.putRrsInt32(8);

        // Tournament?
        stream.putRrsInt32(0);

        // Unexplored data
        stream.put(Hex.toByteArray("0419bbb1bb0519bbb1bb0519bbb1bb0500000004587365740008ae3480080000000000001f000000000007120501ade8030502b0070503000504000505ade803050cbb0c050d00050e00050fad080510a5030511b2030512a7030513aa030516b70b051998dee2de09051a0d051c00051d8e88d544001e3c001a3c0192e7013c0292e7013c0392e7013c040a3c050a3c060a3c0785013c0885013c0985013c0a013c0b8e0c3c0c8e0c3c0d8e0c3c0e013c0f013c10013c11960b3c120b3c130b3c140b3c15b9013c16b9013c17b9013c180e3c190e3c1a0e3c1b92e7013c1c92e7013c1d92e701173c00013c01013c02013c03013c04013c05013c06013c07013c08013c09013c0a013c0e013c11013c12013c13013c15013c16013c17013c18013c19013c1a013c1b013c1c01090506b13505078a0305088501050988f3df19050a92e701050b1f05140b05158d1b051b0a88011a00001a01001a02001a03001a04001a05001a06001a07001a08001a09001a0a001a0b001a0c001a0d001a0e001a0f001a10001a11001a12001a13001a14001a15001a16001a17001a18001a19001a1a001a1b001a1c001a1d2a1a1e001a1f001a20001a21001a22001a23001a24001a25001a26001a27001a28001a29001a2aab021a2b001a2d001a2e001b00001b01001b02001b03001b04001b05001b06001b07001b08001b09001b0a001c00001c01001c02001c03001c04001c05001c06001c07001c08001c09001c0a001c0b001c0c001c0d001c100900a101a1018689020aa84109009fe0030000000c4241434b20494e2055535352920204b31f8c1b00920d970d019303170000"));
    }
}
