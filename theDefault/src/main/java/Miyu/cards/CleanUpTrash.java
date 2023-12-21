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

import static Miyu.DefaultMod.makeCardPath;

public class CleanUpTrash extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(CleanUpTrash.class.getSimpleName());
	public static final String IMG = makeCardPath("CleanUpTrash.png");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	// private static final int UPGRADE_COST = 0;

	private static final int MAGIC = 4;
	private static final int UPGRADED_PLUS_MAGIC = 2;

	public CleanUpTrash() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new CleanUpTrashPower(p, p, magicNumber), magicNumber)
				// new Covered(p, p, magicNumber), magicNumber)
				);
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_PLUS_MAGIC);
			initializeDescription();
		}
	}
}