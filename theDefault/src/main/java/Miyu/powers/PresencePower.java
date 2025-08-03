package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.DistinctPresence;
import Miyu.cards.FaintPresence;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.tempCards.Expunger;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

public class PresencePower extends AbstractPower implements CloneablePowerInterface {
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("PresencePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Presence84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Presence32.png"));

	public PresencePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		name = NAME;
		ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.source = source;

		type = PowerType.BUFF;
		isTurnBased = true;

		// We load those txtures here.
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
		this.canGoNegative = true;
	}
	@Override
	public void atStartOfTurnPostDraw() {
		if (this.amount > 0) {
			DistinctPresence c = new DistinctPresence();
			c.setDP(this.amount);
			this.addToBot(new MakeTempCardInHandAction(c, 1, false));
			this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Miyu:PresencePower"));;
		} else {
			FaintPresence c = new FaintPresence();
			c.setFP(Math.abs(this.amount));
			this.addToBot(new MakeTempCardInHandAction(c, 1, false));
			this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Miyu:PresencePower"));;
		}
	}

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		if (this.amount == 0) {
			this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Miyu:PresencePower"));
		}

		if (this.amount > 999) {
			this.amount = 999;
		}

		if (this.amount <= -999) {
			this.amount = -999;
		}

		this.updateDescription();
	}
	public void playApplyPowerSfx() {
		CardCrawlGame.sound.play("POWER_FOCUS", 0.05F);
	}

	// @Override
	// public void onUseCard(final AbstractCard card, final UseCardAction action) {
	// AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
	// new Covered(owner, amount), amount));
	// }

	// Note: If you want to apply an effect when a power is being applied you have 3 options:
	// onInitialApplication is "When THIS power is first applied for the very first time only."
	// onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
	// onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."

	// Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
	@Override
	public void updateDescription() {
		if (amount >= 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
		} else {
			description = DESCRIPTIONS[0] + Math.abs(amount) + DESCRIPTIONS[2];
		}
	}
	// @Override

	@Override
	public AbstractPower makeCopy() {
		return new PresencePower(owner, source, amount);
	}
}
