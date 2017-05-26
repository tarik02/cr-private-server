package royaleserver.protocol.messages.command;

import royaleserver.logic.Card;
import royaleserver.logic.Rarity;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.component.ChestItem;
import royaleserver.utils.DataStream;

import java.util.*;

public class OpenChestOK extends Command {
	public static final short ID = 210;

	public ChestItem[] chestItems;
	int cardsCount;

	int gold;
	int gems;

	public OpenChestOK() {
		super(ID);

		cardsCount = 0;

		gold = 1000;
		gems = 1500;
	}

	public int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public int smartCount(String rarityString) {
		int count = 0;

		// что за магия? поставь большее количество и будет ошибка из рояля (10099) :/
		switch (rarityString) {
		case "Common":
			count = randInt(40, 60);
			break;
		case "Rare":
			count = randInt(25, 50);
			break;
		case "Epic":
			count = randInt(2, 8);
			break;
		case "Legendary":
			count = randInt(1, 2);
			break;
		}

		return count;
	}

	// generate cards
	public ChestItem[] generateCards() {
		int lastCount = 0;

		Map<String, Integer> count = new HashMap<>();
		Map<String, List<Card>> raritiesCards = new HashMap<>();

		String[] raritiesString = {"Common", "Rare", "Epic", "Legendary"};

		count.put(raritiesString[0], 10);
		count.put(raritiesString[1], 2);
		count.put(raritiesString[2], 1);
		count.put(raritiesString[3], 1);

		// ыыы
		for (int c : count.values())
			cardsCount += c;

		chestItems = new ChestItem[cardsCount];

		for (String rarityString : raritiesString) {
			Rarity rarity = Rarity.by(rarityString);
			List<Card> cards = Card.getAllBy(rarity);
			Collections.shuffle(cards);

			raritiesCards.put(rarityString, cards);
		}

		for (String rarityString : raritiesString) {
			for (int i = 0; i < count.get(rarityString); i++) {
				chestItems[lastCount] = new ChestItem();
				chestItems[lastCount].card = raritiesCards.get(rarityString).get(i).getIndex();
				chestItems[lastCount].unknown_1 = 0;
				chestItems[lastCount].unknown_2 = 0;
				chestItems[lastCount].quantity = smartCount(rarityString);
				chestItems[lastCount].unknown_4 = 0;
				chestItems[lastCount].unknown_5 = 0;
				chestItems[lastCount].unknown_6 = 0;

				lastCount++;
			}
		}

		return null;
	}


	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		generateCards();

		stream.putByte((byte)1);
		stream.putByte((byte)0);

		stream.putRrsInt32(chestItems.length);
		for (ChestItem chestItem : chestItems) {
			chestItem.encode(stream);
		}

		stream.putByte((byte)127);

		stream.putRrsInt32(gold);
		stream.putRrsInt32(gems);

		stream.putRrsInt32(500);

		stream.putByte((byte)2);
		stream.putByte((byte)2);
	}
}
