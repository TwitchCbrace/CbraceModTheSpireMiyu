package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.RicochetAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Ricochet extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(Ricochet.class.getSimpleName());
	public static final String IMG = makeCardPath("Ricochet.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.NONE;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int DAMAGE = 5;
	private static final int UPGRADE_PLUS_DMG = 2;
	private static final int MAGIC = 0;
	// /STAT DECLARATION/

	public Ricochet() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = DAMAGE;
		this.baseMagicNumber = MAGIC;

	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new RicochetAction(this));
	}

	public AbstractCard makeCopy() {
		return new Ricochet();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			initializeDescription();
		}
	}
}
