package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.CleanUpTrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makeCardPath;

public class CleanUpTrash extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(CleanUpTrash.class.getSimpleName());
	public static final String IMG = makeCardPath("CleanUpTrash.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;

	private static final int MAGIC = 3;
	private static final int UPGRADE_PLUS_MAGIC = 2;
	private static final int SECOND_MAGIC = 1;
	private static final int UPGRADE_PLUS_SECOND_MAGIC = 1;

	public CleanUpTrash() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.baseSecondMagicNumber = this.secondMagicNumber = SECOND_MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractPower existingPower = p.getPower(CleanUpTrashPower.POWER_ID);
		if (existingPower != null) {
			((CleanUpTrashPower) existingPower).amount += this.magicNumber;
			((CleanUpTrashPower) existingPower).vigor += this.secondMagicNumber;
			existingPower.updateDescription();
		} else {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(p, p, new CleanUpTrashPower(p, p, this.magicNumber, this.secondMagicNumber)));
		}
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			upgradeSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC);
			initializeDescription();
		}
	}
}
