package royaleserver.game;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.logic.Card;
import royaleserver.logic.Chest;
import royaleserver.logic.Rarity;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class ChestGenerator {
	public OpeningChest generateChest(Player player, Chest chest, Random random) {
		PlayerEntity entity = player.getEntity();

		boolean isDraft = chest.isDraftChest();
		OpeningChest.Builder builder = OpeningChest.builder(isDraft);

		final Rarity common = Rarity.by("Common");
		final Rarity rare = Rarity.by("Rare");
		final Rarity epic = Rarity.by("Epic");
		final Rarity legendary = Rarity.by("Legendary");

		float rewardMultiplier = chest.getArena().getChestRewardMultiplier() / 100;
		int minimumSpellsCount = (int)(chest.getRandomSpells() * rewardMultiplier);
		int minimumDifferentSpells = (int)(chest.getDifferentSpells() * rewardMultiplier);

		float minimumRare = (float)minimumSpellsCount / (float)chest.getRareChance();
		float minimumEpic = (float)minimumSpellsCount / (float)chest.getEpicChance();
		float minimumLegendary = (float)minimumSpellsCount / (float)chest.getLegendaryChance();

		float rareCount = minimumRare + entity.getRareChance();
		float epicCount = minimumEpic + entity.getEpicChance();
		float legendaryCount = minimumLegendary + entity.getLegendaryChance();
		float commonCount = minimumSpellsCount - rareCount - epicCount - legendaryCount;

		int differentRare = countDifferent(rareCount, minimumSpellsCount, minimumDifferentSpells);
		int differentEpic = countDifferent(epicCount, minimumSpellsCount, minimumDifferentSpells);
		int differentLegendary = countDifferent(legendaryCount, minimumSpellsCount, minimumDifferentSpells);
		int differentCommon = minimumDifferentSpells - differentRare - differentEpic - differentLegendary;

		int realSpellsCount = (int)(commonCount + rareCount + epicCount + legendaryCount);

		Map<Rarity, List<Card>> candidates = royaleserver.logic.Card.select(entity.getLogicArena());
		commonCount -= generateCards(builder, candidates.get(common), differentCommon, (int)commonCount, random);
		rareCount -= generateCards(builder, candidates.get(rare), differentRare, (int)rareCount, random);
		epicCount -= generateCards(builder, candidates.get(epic), differentEpic, (int)epicCount, random);
		legendaryCount -= generateCards(builder, candidates.get(legendary), differentLegendary, (int)legendaryCount, random);

		entity.setRareChance(rareCount);
		entity.setEpicChance(epicCount);
		entity.setLegendaryChance(legendaryCount);

		int minGold = chest.getMinGoldPerCard() * realSpellsCount;
		int maxGold = chest.getMaxGoldPerCard() * realSpellsCount;

		if (minGold == maxGold) {
			builder.gold(minGold);
		} else {
			builder.gold(minGold + random.nextInt(maxGold - minGold));
		}
		builder.gems(0); // TODO:

		return builder.build();
	}

	protected int generateCards(OpeningChest.Builder builder, List<Card> candidates,
	                            int different, int count, Random random) {
		int first;
		if (different == 1) {
			first = count;
		} else {
			first = (int)Math.ceil(count / different * (1f + random.nextFloat()));
		}

		int current = first;
		int sum = 0;

		while (different != 1 && sum + current <= count && candidates.size() > builder.optionSize() * 2) {
			if (addStack(builder, candidates, current, random)) {
				sum += current;
				current *= 1f + random.nextFloat();
				--different;
			}
		}

		if (sum < count) {
			current = count - sum;
			if (addStack(builder, candidates, current, random)) {
				sum += current;
				current *= 1f + random.nextFloat();
				--different;
			}
		}

		return sum;
	}

	protected boolean addStack(OpeningChest.Builder builder, List<royaleserver.logic.Card> candidates, int count,
	                         Random random) {
		if (candidates.size() > builder.optionSize()) {
			OpeningChest.CardStack[] stack = new OpeningChest.CardStack[builder.optionSize()];
			for (int i = 0; i < stack.length; ++i) {
				int index = random.nextInt(candidates.size());
				Card candidate = candidates.remove(index);

				stack[i] = new OpeningChest.CardStack(candidate, count);
			}
			builder.add(stack);

			return true;
		}

		return false;
	}

	protected int countDifferent(float rarityCount, int count, int different) {
		return (int)Math.ceil((float)different * (rarityCount / (float)count));
	}
}
