package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.BunnyHopAction;
import Miyu.actions.DiveAction;
import Miyu.actions.MoveAction;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Dive extends AbstractDynamicCard implements ICoverCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Dive.class.getSimpleName());
	public static final String IMG = makeCardPath("Dive.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = -1;
	private static final int COVER = 9;
	private static final int UPGRADE_PLUS_COVER = 3;

	private static final int MAGIC = 9;
	private static final int UPGRADE_PLUS_MAGIC = 3;

	// /STAT DECLARATION/

	public Dive() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
		exhaust = true;
	}
	public void triggerOnCovered(AbstractPlayer p) {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));
	}

	public void triggerOnExhaust() {
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new ApplyPowerAction(p, p, new TrashPower(p, p, 3)));
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractCard c = this;
		this.addToTop(new DiveAction(p, this.baseMagicNumber, this.freeToPlayOnce, this.energyOnUse, c));
		this.addToBot(new MoveAction(p, c));
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			initializeDescription();
		}
	}
}
