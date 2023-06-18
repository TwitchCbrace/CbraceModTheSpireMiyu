package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static Miyu.DefaultMod.makeCardPath;

public class Urban_S extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(Urban_S.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
	public static final String IMG = makeCardPath("Urban_S.png");// "public static final String IMG =
																	// makeCardPath("PebbleMagic.png");
	// This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
	// run correctly.
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON; // Up to you, I like auto-complete on these
	private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.
	private static final CardType TYPE = CardType.ATTACK; //
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 4; // COST = 2
	private static final int UPGRADED_COST = 3; // UPGRADED_COST = 2

	private static final int DAMAGE = 5; // DAMAGE = 10
	private static final int UPGRADE_PLUS_DMG = 2; // UPGRADE_PLUS_DMG = 4

	private int internalCoverCount = 0;
	private int internalCost;
	private int internalBaseCost;

	// /STAT DECLARATION/

	public Urban_S() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = DAMAGE;
		internalBaseCost = internalCost = cost;

	}

	@Override
	public void triggerWhenDrawn() {
		super.triggerWhenDrawn();
		applyPowers();
	}

	@Override
	public void onMoveToDiscard() {
		super.onMoveToDiscard();
		updateCost(internalCoverCount);
		isCostModified = costForTurn != internalBaseCost;
		internalCoverCount = 0;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		int countDiff = internalCoverCount - coverCardCounter();
		internalCoverCount = coverCardCounter();
		internalCost += countDiff;
		if (countDiff < 0 || (countDiff > 0 && internalCost > 0 && internalCost <= internalBaseCost)) {
			updateCost(countDiff);
			internalCoverCount = coverCardCounter();
		}
	}
	/*
	 * 매 순간마다 손에 있는 엄폐물 카드 수의 변화량을 비용에 더합니다 만약 이 카드가 손패에 들어왔을 때 손에 엄폐물이 2장이라면, 비용에 -2를 더합니다 그 상황에서 엄폐물이 1장 없어지면, 비용에 1을
	 * 더합니다
	 * 
	 * updateCost(X): 비용에 X만큼 더합니다 internalCoverCount: UrbanS 카드들에게 내장시킨 카운터입니다. 처음엔 0입니다 coverCardCounter: 손에 있는 엄폐물
	 * 카드를 셉니다
	 */

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.calculateCardDamage(m);
		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HEAVY));
		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HEAVY));
		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_HEAVY));
	}

	@Override
	public AbstractCard makeStatEquivalentCopy() {
		Urban_S copyOfUrbanS = (Urban_S) super.makeStatEquivalentCopy();
		if (AbstractDungeon.player != null && CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null
				&& AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			copyOfUrbanS.internalCoverCount = internalCoverCount;
			copyOfUrbanS.internalCost = internalCost;
			copyOfUrbanS.internalBaseCost = internalBaseCost;
		}
		return copyOfUrbanS;
	}

	@Override
	public AbstractCard makeCopy() {
		Urban_S copy = (Urban_S) super.makeCopy();
		if (AbstractDungeon.player != null && CardCrawlGame.dungeon != null && AbstractDungeon.currMapNode != null
				&& AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
			copy.applyPowers();
		}
		return copy;
	}

	public int coverCardCounter() {
		int coverCount = 0;
		for (AbstractCard card : AbstractDungeon.player.hand.group) {
			if (card instanceof ICoverCard) {
				++coverCount;
			}
		}
		return coverCount;
	}

	public int thisIsRealUpgradeCost() {
		int costDifference = UPGRADED_COST - COST;
		int upgradedCost = cost + costDifference;
		if (upgradedCost < 0) {
			upgradedCost = 0;
		}
		return upgradedCost;
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
			upgradeBaseCost(thisIsRealUpgradeCost());
			internalBaseCost += UPGRADED_COST - COST;
			internalCost += UPGRADED_COST - COST;
			rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
