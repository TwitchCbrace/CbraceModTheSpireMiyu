package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.WannaBeAloneAction;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class WannaBeAlone extends AbstractDynamicCard implements ICoverCard {

	// * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(WannaBeAlone.class.getSimpleName());
	public static final String IMG = makeCardPath("WannaBeAlone.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = -2;
	private static final int COVER = 30;
	private static final int UPGRADE_PLUS_COVER = 10;

	// /STAT DECLARATION/

	public WannaBeAlone() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
		selfRetain = true;
	}

	public void triggerOnCovered(AbstractPlayer p) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));

		// 손에 있는 엄폐물에게 휘발성을 부여하는 액션을 actionManager 맨 밑에 추가
		addToBot(new WannaBeAloneAction(this));
	}
	public void triggerOnExhaust() {
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new ApplyPowerAction(p, p, new TrashPower(p, p, 3)));
	}

	public void triggerOnGlowCheck() {
		Covered covered = (Covered) AbstractDungeon.player.getPower("Miyu:Covered");

		if (covered != null && covered.sourceCover == this) {
			beginGlowing();
			this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
		} else {
			stopGlowing();
		}
	}

	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		return false;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
	}

	public AbstractCard makeCopy() {
		return new WannaBeAlone();
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
