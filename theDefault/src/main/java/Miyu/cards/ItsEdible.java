package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Miyu.DefaultMod.makeCardPath;

public class ItsEdible extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(ItsEdible.class.getSimpleName());
	public static final String IMG = makeCardPath("ItsEdible.png");

	// /TEXT DECLARATION/
	// STAT DECLARATION
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	private static final int MAGIC = 0;

	// /STAT DECLARATION/

	public ItsEdible() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		exhaust = true;
	}

	public void applyPowers() {
		int count = 0;
		Iterator var2 = AbstractDungeon.player.hand.group.iterator();

		while (var2.hasNext()) {
			AbstractCard c = (AbstractCard) var2.next();

			if (c instanceof ICoverCard) {
				++count;
			}
		}
		if (count >= 1) {
			this.magicNumber = count;
		} else {
			this.magicNumber = 0;
		}

		isMagicNumberModified = true;
		super.applyPowers();
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(magicNumber));
	}

	public AbstractCard makeCopy() {
		return new ItsEdible();
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
