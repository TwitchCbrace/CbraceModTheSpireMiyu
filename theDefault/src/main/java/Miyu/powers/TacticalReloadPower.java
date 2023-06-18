package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.OnReduceTrash;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

public class TacticalReloadPower extends AbstractPower implements CloneablePowerInterface {
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("TacticalReloadPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("TacticalReloadPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("TacticalReloadPower32.png"));

	public TacticalReloadPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
	}

	public void atStartOfTurnPostDraw() {
		this.flash();
		this.addToBot(new DrawCardAction(this.owner, this.amount));
		this.addToBot(new PutOnDeckAction(this.owner, this.owner, this.amount, false));
	}

	@Override
	public void updateDescription() {
		if (amount == 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
		} else if (amount > 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
		}
	}

	@Override
	public AbstractPower makeCopy() {
		return new TacticalReloadPower(owner, source, amount);
	}
}
