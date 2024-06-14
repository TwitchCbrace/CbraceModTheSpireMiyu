package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class CatCollarPower extends AbstractPower implements CloneablePowerInterface {
	public int selfEsteemAmount;
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("CatCollarPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	// We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
	// There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent
	// file.
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CatCollarPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CatCollarPower32.png"));

	public CatCollarPower(final AbstractCreature owner, final AbstractCreature source, final int amount,
			final int selfEsteemAmount) {
		name = NAME;
		ID = POWER_ID;

		this.owner = owner;
		this.amount = amount;
		this.selfEsteemAmount = selfEsteemAmount;
		this.source = source;

		type = PowerType.BUFF;
		isTurnBased = false;

		// We load those txtures here.
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
	}
	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		this.selfEsteemAmount += stackAmount - 1;
		this.updateDescription();
	}

	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		AbstractPower a = power;
		if (a.ID == Covered.POWER_ID) {
			this.flash();
			addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount), amount));
			addToBot(new ApplyPowerAction(owner, owner, new PresencePower(owner, owner, selfEsteemAmount),
					selfEsteemAmount));
		}
	}
	// public void onUseCard (AbstractCard card, UseCardAction action) {
	// AbstractGameAction thisAction = action;
	// if (thisAction instanceof OnMoveAction) {
	// this.flash();
	// addToBot(new ApplyPowerAction(owner, owner,
	// new VigorPower(owner, amount), amount));
	// addToBot(new ApplyPowerAction(owner, owner,
	// new PresencePower(owner, owner, selfEsteemAmount), selfEsteemAmount));
	// }
	// }

	@Override
	public void updateDescription() {
		description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + selfEsteemAmount + DESCRIPTIONS[2];
	}

	@Override
	public AbstractPower makeCopy() {
		return new CatCollarPower(owner, source, amount, selfEsteemAmount);
	}
}
