package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.RecyclingTrashAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 0;

	private static final int MAGIC = 1;

	// /STAT DECLARATION/

	public RecyclingTrash() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = MAGIC;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DrawCardAction(1));
		this.addToBot(new RecyclingTrashAction(p));

	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			this.selfRetain = true;
			upgradeName();
			rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
