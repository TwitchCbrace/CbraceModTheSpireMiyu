package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.UnexpectedAnglePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PanachePower;

import static Miyu.DefaultMod.makeCardPath;

public class UnexpectedAngle extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(UnexpectedAngle.class.getSimpleName()); // USE THIS ONE FOR THE
																								// TEMPLATE;
	public static final String IMG = makeCardPath("UnexpectedAngle.png");// "public static final String IMG =
																			// makeCardPath("PebbleMagic.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.SELF; // since they don't change much.
	private static final CardType TYPE = CardType.POWER; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 2;
	private static final int UPGRADE_COST = 1;
	private static final int MAGIC = 20;

	// /STAT DECLARATION/

	public UnexpectedAngle() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new ApplyPowerAction(p, p, new UnexpectedAnglePower(p, p, this.magicNumber, this.magicNumber),
				this.magicNumber));
	}

	public AbstractCard makeCopy() {
		return new UnexpectedAngle();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADE_COST);
			initializeDescription();
		}
	}
}
