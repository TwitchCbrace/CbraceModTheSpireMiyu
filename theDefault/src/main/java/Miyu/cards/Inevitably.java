package Miyu.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Miyu.DefaultMod;
import Miyu.characters.TheDefault;

import static Miyu.DefaultMod.makeCardPath;

public class Inevitably extends AbstractDynamicCard {

	/*
	 * "Hey, I wanna make a bunch of cards now." - You, probably. ok cool my dude no problem here's the most convenient
	 * way to do it:
	 *
	 * Copy all of the code here (Ctrl+A > Ctrl+C) Ctrl+Shift+A and search up "file and code template" Press the +
	 * button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
	 * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with Inevitably
	 * And then you can do custom ones like 6 and ENEMY if you want. I'll leave some comments on things you might
	 * consider replacing with what.
	 *
	 * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template
	 * you'll likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe
	 * you want a universal template where you delete everything unnecessary - up to you)
	 *
	 * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
	 */

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Inevitably.class.getSimpleName()); // USE THIS ONE FOR THE
																							// TEMPLATE;
	public static final String IMG = makeCardPath("Inevitably.png");// "public static final String IMG =
																	// makeCardPath("Inevitably.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1; // COST = 1
	private static final int DAMAGE = 16; // DAMAGE = 6
	private static final int UPGRADE_PLUS_DMG = 6; // UPGRADE_PLUS_DMG = 9

	private static final int MAGIC = 1;
	private static final int UPGRADE_MAGIC = 0;

	private static final int RANGE = 0;
	// /STAT DECLARATION/

	public Inevitably() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		baseDamage = DAMAGE;
		magicNumber = MAGIC;
		this.baseRangeMagicNumber = rangeMagicNumber = RANGE;

	}
	@Override
	public void triggerWhenDrawn() {
		int p = AbstractDungeon.player.hand.size();
		this.baseRangeMagicNumber = p + 1;
		this.rangeMagicNumber = p + 1;
		isRangeMagicNumberModified = true;
	}
	public void triggerOnGlowCheck() {
		if (rangeMagicNumber > 5) {
			beginGlowing();
			this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
		} else {
			this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
			// stopGlowing();
		}
	}
	public void applyPowers() {
		int realBaseDamage = this.baseDamage;
		this.baseDamage -= this.rangeMagicNumber;
		this.isDamageModified = true;
		super.applyPowers();
		this.baseDamage = realBaseDamage;
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

		if (rangeMagicNumber > 5) {
			this.addToBot(new DrawCardAction(p, 1));
		}
		this.calculateCardDamage(m);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn),
				AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
	}
	public AbstractCard makeCopy() {
		return new Inevitably();
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
