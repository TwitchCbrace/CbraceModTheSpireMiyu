package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.BackTrackingAction;
import Miyu.characters.TheDefault;
import Miyu.powers.HandSizeUp;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class BackTracking extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(BackTracking.class.getSimpleName());
	public static final String IMG = makeCardPath("BackTracking.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 2;
	private static final int BLOCK = 13;
	private static final int UPGRADE_PLUS_BLOCK = 3;

	// /STAT DECLARATION/

	public BackTracking() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		baseBlock = BLOCK;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
		this.addToBot(new BackTrackingAction(p, p));
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}
}
