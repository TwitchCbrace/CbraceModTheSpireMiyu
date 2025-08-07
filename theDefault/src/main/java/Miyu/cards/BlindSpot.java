package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.MoveAction;
import Miyu.characters.TheDefault;
import Miyu.powers.PresencePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.ArrayList;

import static Miyu.DefaultMod.makeCardPath;

public class BlindSpot extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(BlindSpot.class.getSimpleName()); // USE THIS ONE FOR THE
	// TEMPLATE;
	public static final String IMG = makeCardPath("BlindSpot.png");
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.ENEMY;
	private static final CardType TYPE = CardType.ATTACK;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int DAMAGE = 9;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int MAGIC = 0;

	// /STAT DECLARATION/

	public BlindSpot() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseDamage = this.damage = DAMAGE;
		this.baseMagicNumber = this.magicNumber = MAGIC;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {

		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_VERTICAL));

		ArrayList<AbstractCard> coverCardsInHand = new ArrayList<>();
		for (AbstractCard c : p.hand.group) {
			if (c instanceof ICoverCard) {
				coverCardsInHand.add(c);
			}
		}

		if (!coverCardsInHand.isEmpty()) {
			AbstractCard cardToMove = coverCardsInHand
					.get(AbstractDungeon.cardRandomRng.random(coverCardsInHand.size() - 1));
			this.addToBot(new MoveAction(p, cardToMove));
		}
	}

	public AbstractCard makeCopy() {
		return new BlindSpot();
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