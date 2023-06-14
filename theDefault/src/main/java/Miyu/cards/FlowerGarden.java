package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makeCardPath;

public class FlowerGarden extends AbstractDynamicCard implements ICoverCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(FlowerGarden.class.getSimpleName());
	public static final String IMG = makeCardPath("FlowerGarden.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 2;
	private static final int MAGIC = 5;
	private static final int UPGRADE_PLUS_MAGIC = 3;
	private static final int COVER = 4;
	private static final int UPGRADE_PLUS_COVER = 1;

	// /STAT DECLARATION/

	public FlowerGarden() {
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

	public void triggerWhenDrawn() {
		this.baseCoverMagicNumber *= 2;
		if (this.baseCoverMagicNumber >= 999) {
			this.baseCoverMagicNumber = 999;
		}
		this.coverMagicNumber = this.baseCoverMagicNumber;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.magicNumber), this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new FlowerGarden();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			initializeDescription();
		}
	}
}
