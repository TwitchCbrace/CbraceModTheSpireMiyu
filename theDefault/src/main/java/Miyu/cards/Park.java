package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Park extends AbstractDynamicCard implements ICoverCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Park.class.getSimpleName());
	public static final String IMG = makeCardPath("Park.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = -2;

	private static final int MAGIC = 1;
	private static final int COVER = 8;
	private static final int UPGRADE_PLUS_COVER = 4;

	// /STAT DECLARATION/

	public Park() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
		this.baseMagicNumber = this.magicNumber = MAGIC;
		selfRetain = true;
	}
	public void triggerOnCovered(AbstractPlayer p) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));
	}

	public void triggerOnGlowCheck() {
		Covered covered = (Covered) AbstractDungeon.player.getPower("Miyu:Covered");

		if (covered != null && covered.sourceCover == this) {
			beginGlowing();
			this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
		} else {
			this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
		}
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return false;
	}

	public void triggerWhenDrawn() {
		this.addToTop(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
		this.addToTop(new MakeTempCardInHandAction(this.makeStatEquivalentCopy()));
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
	}

	public AbstractCard makeCopy() {
		return new Park();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
			this.isCoverMagicNumberModified = true;
			initializeDescription();
		}
	}
}
