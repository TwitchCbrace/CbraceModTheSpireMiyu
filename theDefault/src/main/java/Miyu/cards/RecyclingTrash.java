package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.RecyclingTrashAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class RecyclingTrash extends AbstractDynamicCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 *
	 * Defend Gain 5 (8) block.
	 */

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(RecyclingTrash.class.getSimpleName());
	public static final String IMG = makeCardPath("RecyclingTrash.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;

	private static final int MAGIC = 1;

	// /STAT DECLARATION/

	public RecyclingTrash() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

		this.baseMagicNumber = MAGIC;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new RecyclingTrashAction(p));
		// int amount = 0;
		// amount = Math.min(p.getPower(TrashPower.POWER_ID).amount,
		// p.gameHandSize - p.hand.size());
		//
		// this.addToBot(new ExpertiseAction(p, amount));
		// this.addToBot(new ReducePowerAction(p, p, TrashPower.POWER_ID, amount));

	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.upgradeBaseCost(0);
			initializeDescription();
		}
	}
}
