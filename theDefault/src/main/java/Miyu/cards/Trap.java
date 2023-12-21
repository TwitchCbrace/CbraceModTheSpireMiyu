package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.CoverIncreaseAllAction;
import Miyu.characters.TheDefault;
import Miyu.powers.HandSizeUp;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makeCardPath;

public class Trap extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(Trap.class.getSimpleName()); // USE THIS ONE FOR THE
																					// TEMPLATE;
	public static final String IMG = makeCardPath("Trap.png");// "public static final String IMG =
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

	private static final int COST = 1;
	private static final int DAMAGE = 10;
	private static final int UPGRADE_PLUS_DMG = 2;

	private static final int MAGIC = 1;
	private static final int UPGRADE_PLUS_MAGIC = 1;
	private static final int SECOND_MAGIC = 4;

	// /STAT DECLARATION/

	public Trap() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseSecondMagicNumber = this.secondMagicNumber = SECOND_MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		this.calculateCardDamage(m);
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

		addToBot(new CoverIncreaseAllAction(p, magicNumber, secondMagicNumber));

	}

	public AbstractCard makeCopy() {
		return new Trap();
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
