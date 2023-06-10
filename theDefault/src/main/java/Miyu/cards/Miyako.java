package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.HandSizeUp;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Miyako extends AbstractDynamicCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 *
	 * Defend Gain 5 (8) block.
	 */

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Miyako.class.getSimpleName());
	public static final String IMG = makeCardPath("Miyako.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int MAGIC = 5;
	private static final int UPGRADE_MAGIC = 3;

	// /STAT DECLARATION/

	public Miyako() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = magicNumber = MAGIC;
	}

	// Actions the card should do.
	// @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new HandSizeUp(p, m, magicNumber), magicNumber));
		// BaseMod.MAX_HAND_SIZE += magicNumber;
	}

	public AbstractCard makeCopy() {
		return new Miyako();
	}
	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_MAGIC);
			initializeDescription();
		}
	}
}
