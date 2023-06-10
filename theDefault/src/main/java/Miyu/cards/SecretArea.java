package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.DummyCoverSelectAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class SecretArea extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(SecretArea.class.getSimpleName());
	public static final String IMG = makeCardPath("SecretArea.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int BLOCK = 0;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	private static final int MAGIC = 0;

	// /STAT DECLARATION/

	public SecretArea() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = BLOCK;

	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
		AbstractDungeon.actionManager.addToBottom(new DummyCoverSelectAction(p, 1));

	}
	public void applyPowers() {
		this.baseBlock = AbstractDungeon.player.exhaustPile.size();
		if (this.upgraded) {
			this.baseBlock += UPGRADE_PLUS_BLOCK;
		}
		this.initializeDescription();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
			initializeDescription();
		}
	}
}
