package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class DontComeClose extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(DontComeClose.class.getSimpleName()); // USE THIS ONE FOR THE
																							// TEMPLATE;
	public static final String IMG = makeCardPath("DontComeClose.png");// "public static final String IMG =
																		// makeCardPath("PebbleMagic.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int DAMAGE = 8;
	private static final int UPGRADE_PLUS_DMG = 3;

	private static final int MAGIC = 0;
	private static final int RANGE = 0;

	// /STAT DECLARATION/

	public DontComeClose() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = damage = DAMAGE;
		this.baseRangeMagicNumber = this.rangeMagicNumber = RANGE;
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public void triggerWhenDrawn() {
		int p = 0;
		p = AbstractDungeon.player.hand.size();
		this.baseRangeMagicNumber = p + 1;
		this.rangeMagicNumber = p + 1;
		isRangeMagicNumberModified = true;

	}
	public void applyPowers() {
		int p = 0;
		p = AbstractDungeon.player.hand.size();
		this.magicNumber = rangeMagicNumber - p;
		if (this.magicNumber <= 0) {
			this.magicNumber = 0;
		}
		isMagicNumberModified = true;
		super.applyPowers();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		for (int i = 0; i < this.magicNumber; ++i) {
			AbstractDungeon.actionManager
					.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn),
							AbstractGameAction.AttackEffect.BLUNT_HEAVY, true));
		}

	}

	public AbstractCard makeCopy() {
		return new DontComeClose();
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
