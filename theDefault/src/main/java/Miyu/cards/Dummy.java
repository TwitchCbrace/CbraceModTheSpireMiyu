package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.CoverSelectAction;
import Miyu.actions.DummyCoverSelectAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Dummy extends AbstractDynamicCard {

	/*
	 * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
	 *
	 * Defend Gain 5 (8) block.
	 */

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Dummy.class.getSimpleName());
	public static final String IMG = makeCardPath("Dummy.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;
	private static final int BLOCK = 0;
	// private static final int UPGRADE_PLUS_BLOCK = 0;

	// /STAT DECLARATION/

	public Dummy() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		// baseBlock = BLOCK;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
		AbstractDungeon.actionManager.addToTop(new DummyCoverSelectAction(p, 1));

	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			// upgradeBlock(UPGRADE_PLUS_BLOCK);
			upgradeBaseCost(UPGRADE_COST);
			initializeDescription();
		}
	}
}
