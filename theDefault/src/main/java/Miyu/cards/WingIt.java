package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makeCardPath;

public class WingIt extends AbstractDynamicCard implements ICoverCard {

	public static final String ID = DefaultMod.makeID(WingIt.class.getSimpleName()); // USE THIS ONE FOR THE
																						// TEMPLATE;
	public static final String IMG = makeCardPath("WingIt.png");
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 0;
	private static final int DAMAGE = 12;
	private static final int UPGRADE_PLUS_DMG = 4;

	private static final int COVER = 1;

	public WingIt() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
		this.baseDamage = this.damage = DAMAGE;
		this.isEthereal = true;
	}

	public void triggerOnCovered(AbstractPlayer p) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));
		this.initializeDescription();
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
		Covered covered = (Covered) AbstractDungeon.player.getPower("Miyu:Covered");
		if (covered != null && covered.sourceCover == this) {
			return true;
		} else {
			return false;
		}
	}

	public void calculateCardDamage(AbstractMonster mo) {
		int realBaseDamage = this.baseDamage;
		this.baseDamage -= this.rangeMagicNumber;
		super.calculateCardDamage(mo);
		this.baseDamage = realBaseDamage;
		this.isDamageModified = this.damage != this.baseDamage;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.damage -= this.rangeMagicNumber;

		if (rangeMagicNumber > 7) {
			this.addToBot(new DrawCardAction(p, 1));
		}
		this.calculateCardDamage(m);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn),
				AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
	}
	public AbstractCard makeCopy() {
		return new WingIt();
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
