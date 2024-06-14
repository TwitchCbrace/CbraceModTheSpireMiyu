package Miyu.cards;

import Miyu.DefaultMod;
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

	public void applyPowers() {
		AbstractPower selfEsteem = AbstractDungeon.player.getPower(PresencePower.POWER_ID);
		if (selfEsteem != null && selfEsteem.amount > 0) {
			this.magicNumber = Math.abs(selfEsteem.amount);
		} else {
			this.magicNumber = 0;
		}
		super.applyPowers();
		this.initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.magicNumber >= 1) {
			this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicNumber)));
		}
		this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_VERTICAL));
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
