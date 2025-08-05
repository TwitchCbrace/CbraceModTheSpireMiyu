package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.TacticalReloadPower;
import Miyu.powers.ThingsForgettablePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class ThingsForgettable extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(ThingsForgettable.class.getSimpleName());
	public static final String IMG = makeCardPath("ThingsForgettable.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int MAGIC = 1;
	private static final int UPGRADE_PLUS_MAGIC = 1;

	// /STAT DECLARATION/

	public ThingsForgettable() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	// Actions the card should do.
	// @Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new ThingsForgettablePower(p, m, magicNumber), magicNumber));
	}

	public AbstractCard makeCopy() {
		return new ThingsForgettable();
	}
	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			initializeDescription();
		}
	}
}
