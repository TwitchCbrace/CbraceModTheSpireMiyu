package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.PresencePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class DoNotDisturb extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(DoNotDisturb.class.getSimpleName());
	public static final String IMG = makeCardPath("DoNotDisturb.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 1;
	private static final int MAGIC = 5;
	private static final int UPGRADE_MAGIC_NUMBER = 5;
	private static final int ENERGY_BASE = 2;
	private static final int ENERGY_UPGRADE = 1;

	// /STAT DECLARATION/

	public DoNotDisturb() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseSecondMagicNumber = this.secondMagicNumber = ENERGY_BASE;
		this.exhaust = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(secondMagicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PresencePower(p, p, -magicNumber)));
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_MAGIC_NUMBER);
			upgradeSecondMagicNumber(ENERGY_UPGRADE);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
