package Miyu.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Miyu.DefaultMod;
import Miyu.characters.TheDefault;

import static Miyu.DefaultMod.makeCardPath;

public class Strike extends AbstractDynamicCard {

	/*
	 * "Hey, I wanna make a bunch of cards now." - You, probably. ok cool my dude no problem here's the most convenient
	 * way to do it:
	 *
	 * Copy all of the code here (Ctrl+A > Ctrl+C) Ctrl+Shift+A and search up "file and code template" Press the +
	 * button at the top and name your template whatever it is for - "AttackCard" or "PowerCard" or something up to you.
	 * Read up on the instructions at the bottom. Basically replace anywhere you'd put your cards name with Strike And
	 * then you can do custom ones like 6 and ENEMY if you want. I'll leave some comments on things you might consider
	 * replacing with what.
	 *
	 * Of course, delete all the comments and add anything you want (For example, if you're making a skill card template
	 * you'll likely want to replace that new DamageAction with a gain Block one, and add baseBlock instead, or maybe
	 * you want a universal template where you delete everything unnecessary - up to you)
	 *
	 * You can create templates for anything you ever want to. Cards, relics, powers, orbs, etc. etc. etc.
	 */

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Strike.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
	public static final String IMG = makeCardPath("Strike.png");// "public static final String IMG =
																// makeCardPath("Strike.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.BASIC; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1; // COST = 1
	private static final int DAMAGE = 6; // DAMAGE = 6
	private static final int UPGRADE_PLUS_DMG = 3; // UPGRADE_PLUS_DMG = 3

	// /STAT DECLARATION/

	public Strike() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		baseDamage = DAMAGE;
		this.tags.add(CardTags.STARTER_STRIKE);
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
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
