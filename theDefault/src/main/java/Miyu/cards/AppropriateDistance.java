package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.BunnyHopAction;
import Miyu.characters.TheDefault;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class AppropriateDistance extends AbstractRangeIconCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(AppropriateDistance.class.getSimpleName());
	public static final String IMG = makeCardPath("AppropriateDistance.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 0;
	private static final int BLOCK = 8;

	private static final int MAGIC = -7;
	private static final int UPGRADE_PLUS_BLOCK = 3;

	private static final int RANGE = 0;
	// /STAT DECLARATION/

	public AppropriateDistance() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = BLOCK;
		this.baseMagicNumber = magicNumber = MAGIC;
		this.baseRangeMagicNumber = rangeMagicNumber = RANGE;
		this.exhaust = true;
		this.isEthereal = true;
	}
	public void triggerWhenDrawn() {
		// int p = 0;
		// p = AbstractDungeon.player.hand.size();
		// this.baseRangeMagicNumber = p + 1;
		// this.rangeMagicNumber = p + 1;
		// isRangeMagicNumberModified = true;
		super.triggerWhenDrawn(); // 대체됨(이 코드(함수)를 지워도 됩니다)
	}

	public void applyPowers() {
		this.magicNumber = rangeMagicNumber - 7;
		isMagicNumberModified = true;
	}
	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block, true));
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new SelfEsteem(p, p, magicNumber), magicNumber));
	}

	public AbstractCard makeCopy() {
		return new AppropriateDistance();
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
