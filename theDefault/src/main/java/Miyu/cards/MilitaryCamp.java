package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class MilitaryCamp extends AbstractDynamicCard implements ICoverCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(MilitaryCamp.class.getSimpleName());
	public static final String IMG = makeCardPath("MilitaryCamp.png");

	// /TEXT DECLARATION/
	// STAT DECLARATION
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 4;
	private static final int COVER = 8;
	private static final int UPGRADE_PLUS_COVER = 4;
	private static final int MAGIC = 4;
	private static final int UPGRADE_PLUS_MAGIC = 1;
	// /STAT DECLARATION/
	private boolean isCostReducedThisTurn = false;

	public MilitaryCamp() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
		this.selfRetain = true;
	}
	public void triggerOnCovered(AbstractPlayer p) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));

		if (this.cost >= 1) {
			this.setCostForTurn(this.costForTurn - 1);
			isCostModified = true;
		} else {
			this.setCostForTurn(0);
			isCostModified = true;
		}
		this.isCostReducedThisTurn = true;
	}

	@Override
	public void atTurnStart() {
		this.resetAttributes();
		this.applyPowers();
		this.isCostReducedThisTurn = false;
	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		if (this.isCostReducedThisTurn) {
			this.setCostForTurn(this.costForTurn - 1);
			this.isCostModified = true;
		}
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

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
	}

	public AbstractCard makeCopy() {
		return new MilitaryCamp();
	}
	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			this.isMagicNumberModified = true;
			this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
			this.isCoverMagicNumberModified = true;
			rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}