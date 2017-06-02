package royaleserver.utils;

import java.util.HashMap;
import java.util.Map;

public class UsefulTools {
    public static int getTypeRes(String resourceName)
    {
        Map<String, Integer> map = new HashMap<>();
        
        map.put("Gold", 1);
        map.put("ChallengesWins", 2);
        map.put("ChestCount", 3);
        map.put("StarCount", 4);
        map.put("FreeGold", 5);
        map.put("HighestTrophies", 6);
        map.put("ThreeCrownWins", 7);
        map.put("CardsFound", 8);
        map.put("FavoriteCard", 9);
        map.put("CardsGiven", 10);
        map.put("Unk_N11", 11);
        map.put("Unk_0", 12);
        map.put("RewardGold", 13);
        map.put("RewardCount", 14);
        map.put("Unk_1", 15); // wins?
        map.put("CardRedBadge", 16); // card red badge    or shopDayCount
        map.put("ChallengeCardsWon", 17);
        
        map.put("Unk_2", 18);
        map.put("Unk_3", 19);
        map.put("MaxWins", 20);
        map.put("ChallengeCardsWon", 21);
        map.put("TournamentCardsWon", 22);
        map.put("Unk_4", 25);
        map.put("Unk_5", 26);
        map.put("Unk_N27", 27);
        map.put("Unk_6", 28);
        map.put("GameMode", 29);
        
        if (map.get(resourceName) != null)
            return map.get(resourceName);
        
        return -1;
    }
}
