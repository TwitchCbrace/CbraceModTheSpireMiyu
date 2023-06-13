package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.CatCollarPower;
import Miyu.powers.TacticalReloadPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class CatCollar extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(CatCollar.class.getSimpleName());
	public static final String IMG = makeCardPath("CatCollar.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 2;
	private static final int MAGIC = 4;
	private static final int UPGRADED_PLUS_MAGIC = 2;
	private static final int SECOND_MAGIC = 3;
	private static final int UPGRADED_PLUS_SECOND_MAGIC = 2;

	// /STAT DECLARATION/

	public CatCollar() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = magicNumber = MAGIC;
		this.baseSecondMagicNumber = secondMagicNumber = SECOND_MAGIC;
	}

	// Actions the card should do.
	// @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p, new CatCollarPower(p, m, magicNumber, secondMagicNumber), magicNumber));
	}

	public AbstractCard makeCopy() {
		return new CatCollar();
	}
	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
			upgradeSecondMagicNumber(UPGRADED_PLUS_SECOND_MAGIC);
			initializeDescription();
		}
	}
}
