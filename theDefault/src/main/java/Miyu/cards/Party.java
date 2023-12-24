package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Miyu.DefaultMod.makeCardPath;

public class Party extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(Party.class.getSimpleName());
	public static final String IMG = makeCardPath("party.png");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.SKILL; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 2;
	private static final int BLOCK = 9;
	private static final int UPGRADE_BLOCK = 4;
	private static final int MAGIC = 2;
	private static final int UPGRADE_PLUS_MAGIC = 1;
	private static final int SECOND_MAGIC = 3;
	private static final int UPGRADE_PLUS_SECONDMAGIC = 1;

	// /STAT DECLARATION/

	public Party() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = this.block = BLOCK;
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseSecondMagicNumber = this.secondMagicNumber = SECOND_MAGIC;
		this.cardsToPreview = new Rock();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new GainBlockAction(p, block));
		this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.baseMagicNumber, false), this.baseMagicNumber));
		this.addToBot(new MakeTempCardInHandAction(new Rock(), secondMagicNumber));

	}

	public AbstractCard makeCopy() {
		return new Party();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_BLOCK);
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			upgradeSecondMagicNumber(UPGRADE_PLUS_SECONDMAGIC);
			isMagicNumberModified = true;
			initializeDescription();
		}
	}
}
