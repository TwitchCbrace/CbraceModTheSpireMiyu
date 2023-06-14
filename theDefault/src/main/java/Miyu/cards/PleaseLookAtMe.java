package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.DummyCoverSelectAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import Miyu.powers.PleaseLookAtMePower;

import static Miyu.DefaultMod.makeCardPath;

public class PleaseLookAtMe extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(PleaseLookAtMe.class.getSimpleName());
	public static final String IMG = makeCardPath("PleaseLookAtMe.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;

	// /STAT DECLARATION/

	public PleaseLookAtMe() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.selfRetain = true;
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new PleaseLookAtMePower(AbstractDungeon.player, 1), 1, true));

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
