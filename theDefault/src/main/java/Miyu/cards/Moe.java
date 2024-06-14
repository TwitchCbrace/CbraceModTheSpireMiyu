package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.MoePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Miyu.DefaultMod.makeCardPath;

public class Moe extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(Moe.class.getSimpleName());
	public static final String IMG = makeCardPath("Moe.png");

	// /TEXT DECLARATION/
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;
	private static final int MAGIC = 0;

	private static final int SECONDMAGIC = 0;

	// /STAT DECLARATION/

	public Moe() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = magicNumber = MAGIC;
		this.baseSecondMagicNumber = secondMagicNumber = SECONDMAGIC;
	}

	public void applyPowers() {
		int acount = 0;
		int bcount = 0;
		Iterator var2 = AbstractDungeon.player.hand.group.iterator();

		while (var2.hasNext()) {
			AbstractCard c = (AbstractCard) var2.next();
			if (c.cost > 0) {
				acount += c.cost;
			}
			bcount++;
		}
		this.baseMagicNumber = acount;
		this.baseSecondMagicNumber = bcount;
		super.applyPowers();
		this.initializeDescription();
	}

	// 이거 수정 했습니다. MoePower에 repeat 라는 값을 베이스매직넘버 2(핸드 수)를 넣었습니다.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(
				new ApplyPowerAction(p, p, new MoePower(p, 3, this.baseMagicNumber, this.baseSecondMagicNumber), 3));
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
