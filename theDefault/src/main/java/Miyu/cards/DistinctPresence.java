package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.powers.PresencePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Miyu.DefaultMod.makeCardPath;

public class DistinctPresence extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(DistinctPresence.class.getSimpleName());
	public static final String IMG = makeCardPath("DistinctPresence.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = CardColor.COLORLESS;

	private static final int COST = 0;
	private static final int MAGIC = 0;

	private static final int UPGRADED_MAGIC = 5;

	// /STAT DECLARATION/

	public DistinctPresence() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = MAGIC;
		this.exhaust = true;
		this.isEthereal = true;
	}
	public void setDP(int amount) {
		this.baseMagicNumber = this.magicNumber = amount;
		if (this.upgraded) {
			this.baseMagicNumber += this.UPGRADED_MAGIC;
		}
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (Settings.FAST_MODE) {
			this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.1F));
		} else {
			this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.5F));
		}

		this.addToBot(new ApplyPowerAction(p, p, new VigorPower(p, this.baseMagicNumber), this.baseMagicNumber));
		if (p.hasPower(PresencePower.POWER_ID)) {
			this.addToBot(
					new ApplyPowerAction(p, p, new PresencePower(p, p, -p.getPower(PresencePower.POWER_ID).amount),
							-p.getPower(PresencePower.POWER_ID).amount));
		}
	}

	public AbstractCard makeCopy() {
		return new DistinctPresence();
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_MAGIC);
			initializeDescription();
		}
	}
}
