package Miyu.cards;
import basemod.abstracts.CustomCard;

public abstract class AbstractDefaultCard extends CustomCard {

	// Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic
	// number,
	// if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
	// by default, that you need in your own cards.

	// In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
	// simply use that in our cards, so long as we put "extends AbstractDynamicCard" instead of "extends CustomCard" at
	// the start.
	// In simple terms, it's for things that we don't want to define again and again in every single card we make.
	public int coverMagicNumber;

	public int baseCoverMagicNumber;

	public int rangeMagicNumber; // Just like magic number, or any number for that matter, we want our regular,
									// modifiable stat
	public int baseRangeMagicNumber; // And our base stat - the number in it's base state. It will reset to that by
										// default.

	public int secondMagicNumber;
	public int baseSecondMagicNumber;

	public boolean upgradedRangeMagicNumber; // A boolean to check whether the number has been upgraded or not.

	public boolean upgradedSecondMagicNumber;
	public boolean isRangeMagicNumberModified; // A boolean to check whether the number has been modified or not, for
												// coloring purposes. (red/green)
	public boolean isSecondMagicNumberModified;
	public boolean isCoverMagicNumberModified;

	public boolean upgradedCoverMagicNumber;

	public AbstractDefaultCard(final String id, final String name, final String img, final int cost,
			final String rawDescription, final CardType type, final CardColor color, final CardRarity rarity,
			final CardTarget target) {

		super(id, name, img, cost, rawDescription, type, color, rarity, target);

		// Set all the things to their default values.
		isCostModified = false;
		isCostModifiedForTurn = false;
		isDamageModified = false;
		isBlockModified = false;
		isMagicNumberModified = false;
		isSecondMagicNumberModified = false;
		isCoverMagicNumberModified = false;
		isRangeMagicNumberModified = false;
	}

	public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
		super.displayUpgrades();
		if (upgradedRangeMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
			rangeMagicNumber = baseRangeMagicNumber; // Show how the number changes, as out of combat, the base number
														// of a card is shown.
			isRangeMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being
												// changed.
		}
		if (upgradedCoverMagicNumber) {
			coverMagicNumber = baseCoverMagicNumber;
			isCoverMagicNumberModified = true;
		}
		if (upgradedSecondMagicNumber) {
			secondMagicNumber = baseSecondMagicNumber;
			isSecondMagicNumberModified = true;
		}
	}

	public void upgradeRangeMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade"
														// and NOT "upgraded" - 2 different things. One is a boolean,
														// and then this one is what you will usually use - change the
														// integer by how much you want to upgrade.
		baseRangeMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
		rangeMagicNumber = baseRangeMagicNumber; // Set the number to be equal to the base value.
		upgradedRangeMagicNumber = true; // Upgraded = true - which does what the above method does.
	}

	public void upgradeCoverMagicNumber(int amount) {
		baseCoverMagicNumber += amount;
		coverMagicNumber = baseCoverMagicNumber;
		upgradedCoverMagicNumber = true;
	}

	public void upgradeSecondMagicNumber(int amount) {
		baseSecondMagicNumber += amount;
		secondMagicNumber = baseSecondMagicNumber;
		upgradedSecondMagicNumber = true;
	}

	public AbstractCard makeStatEquivalentCopy() {
		AbstractDefaultCard card = (AbstractDefaultCard) super.makeStatEquivalentCopy();
		card.coverMagicNumber = this.coverMagicNumber;
		card.baseCoverMagicNumber = this.baseCoverMagicNumber;
		return card;
	}
	
	public AbstractCard makeSameInstanceOf() {
		AbstractDefaultCard card = (AbstractDefaultCard) super.makeSameInstaneOf();
		card.rangeMagicNumber = this.rangeMagicNumber;
		card.baseRangeMagicNumber = this.baseRangeMagicNumber;
		return card;
	}

	public int getCoverMagicNumber() {
		return this.coverMagicNumber;
	}
}
