package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class PanicShot extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(PanicShot.class.getSimpleName()); // USE THIS ONE FOR THE
																						// TEMPLATE;
	public static final String IMG = makeCardPath("PanicShot.png");// "public static final String IMG =
																	// makeCardPath("PebbleMagic.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1; // COST = 2
	private static final int UPGRADED_COST = 0; // UPGRADED_COST = 2

	private static final int DAMAGE = 7; // DAMAGE = 10
	private static final int RANGE = 0;

	// /STAT DECLARATION/

	public PanicShot() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.baseRangeMagicNumber = rangeMagicNumber = RANGE;
		this.shuffleBackIntoDrawPile = true;

	}

	@Override
	public void triggerWhenDrawn() {
		int p = 0;
		p = AbstractDungeon.player.hand.size();
		this.baseRangeMagicNumber = p + 1;
		this.rangeMagicNumber = p + 1;
		isRangeMagicNumberModified = true;
	}
	public void applyPowers() {
		int realBaseDamage = this.baseDamage;
		this.baseDamage += this.rangeMagicNumber;
		this.isDamageModified = true;
		super.applyPowers();
		this.baseDamage = realBaseDamage;
	}
	public void calculateCardDamage(AbstractMonster mo) {
		int realBaseDamage = this.baseDamage;
		this.baseDamage += this.rangeMagicNumber;
		super.calculateCardDamage(mo);
		this.baseDamage = realBaseDamage;
		this.isDamageModified = this.damage != this.baseDamage;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(m);
		// this.damage += this.rangeMagicNumber;
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn),
				AbstractGameAction.AttackEffect.SMASH, true));
		this.addToBot(new MakeTempCardInDrawPileAction(this.makeStatEquivalentCopy(), 1, false, false));

	}

	public AbstractCard makeCopy() {
		return new PanicShot();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}
}
