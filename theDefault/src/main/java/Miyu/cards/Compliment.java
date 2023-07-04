package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.ComplimentAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Compliment extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Compliment.class.getSimpleName());
	public static final String IMG = makeCardPath("Compliment.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;

	private static final int DAMAGE = 12;
	private static final int UPGRADE_PLUS_DAMAGE = 4;

	private static final int MAGIC = 10;

	// /STAT DECLARATION/

	public Compliment() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		baseDamage = DAMAGE;
		this.baseMagicNumber = MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(m);

		// 칭찬 Action 실행
		addToBot(new ComplimentAction(p, m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				this.baseMagicNumber));
	}

	public AbstractCard makeCopy() {
		return new Compliment();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DAMAGE);
			initializeDescription();
		}
	}
}
