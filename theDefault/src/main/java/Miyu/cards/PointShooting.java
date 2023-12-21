package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.ExhaustMinCoverCardAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class PointShooting extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(PointShooting.class.getSimpleName()); // USE THIS ONE FOR THE
																							// TEMPLATE;
	public static final String IMG = makeCardPath("PointShooting.png");// "public static final String IMG =
																		// makeCardPath("PebbleMagic.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ALL_ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int DAMAGE = 3;
	private static final int UPGRADE_PLUS_DMG = 0;

	private static final int MAGIC = 3;
	private static final int UPGRADE_PLUS_MAGIC = 1;
	private static final int RANGE = 0;

	// /STAT DECLARATION/

	public PointShooting() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		// this.baseRangeMagicNumber = this.rangeMagicNumber = RANGE;
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	// @Override
	// public void triggerWhenDrawn() {
	// int p = 0;
	// p = AbstractDungeon.player.hand.size();
	// this.baseRangeMagicNumber = p + 1;
	// this.rangeMagicNumber = p + 1;
	// isRangeMagicNumberModified = true;
	//
	// }
	// public void applyPowers() {
	// int realBaseDamage = this.baseDamage;
	// this.baseDamage -= this.rangeMagicNumber;
	// this.isDamageModified = true;
	// super.applyPowers();
	// this.baseDamage = realBaseDamage;
	// }

	// public void calculateCardDamage(AbstractMonster mo) {
	// int realBaseDamage = this.baseDamage;
	// this.baseDamage -= this.rangeMagicNumber;
	// super.calculateCardDamage(mo);
	// this.baseDamage = realBaseDamage;
	// this.isDamageModified = this.damage != this.baseDamage;
	// }

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(m);
		for (int i = 0; i < this.magicNumber; ++i) {
			this.addToBot(new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
		}
		this.addToBot(new ExhaustMinCoverCardAction(p));

	}

	public AbstractCard makeCopy() {
		return new PointShooting();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			initializeDescription();
		}
	}
}
