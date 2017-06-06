package royaleserver;

import royaleserver.logic.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OpeningChest {
	public static class CardStack {
		public final Card card;
		public final int count;

		public CardStack(Card card, int count) {
			this.card = card;
			this.count = count;
		}
	}

	public static class Builder {
		private final int optionSize;

		private final List<CardStack[]> cards = new ArrayList<>();
		private int gold = 0;
		private int gems = 0;

		private Builder(int optionSize) {
			this.optionSize = optionSize;
		}

		public int optionSize() {
			return optionSize;
		}

		public Builder add(CardStack[] option) {
			if (option.length != optionSize) {
				throw new RuntimeException("Option length is not right.");
			}

			cards.add(option);
			return this;
		}

		public Builder gold(int gold) {
			this.gold = gold;
			return this;
		}

		public Builder gems(int gems) {
			this.gems = gems;
			return this;
		}

		public OpeningChest build() {
			cards.sort(Comparator.comparingInt(a -> a[0].card.getRarity().getSortCapacity() * a[0].count));
			return new OpeningChest(cards, gold, gems);
		}
	}

	private final List<CardStack[]> cards;
	private final int gold, gems;
	private int currentCard = 0;

	private OpeningChest(List<CardStack[]> cards, int gold, int gems) {
		this.cards = Collections.unmodifiableList(cards);
		this.gold = gold;
		this.gems = gems;
	}

	public int optionSize() {
		return cards.size() == 0 ? 0 : cards.get(0).length;
	}

	public List<CardStack[]> cards() {
		return cards;
	}

	public int gold() {
		return gold;
	}

	public int gems() {
		return gems;
	}

	public CardStack next(int selection) {
		CardStack card = null;
		if (currentCard < cards.size()) {
			card = cards.get(currentCard)[selection >= optionSize() ? 0 : selection];
		}

		++currentCard;
		return card;
	}

	public void end() {
		while (next(0) != null);
	}

	public static Builder builder(boolean draft) {
		return new Builder(draft ? 2 : 1);
	}
}
