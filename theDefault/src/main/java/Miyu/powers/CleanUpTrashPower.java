package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.OnReduceTrash;
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

public class CleanUpTrashPower extends AbstractPower implements CloneablePowerInterface, OnReduceTrash {
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("CleanUpTrashPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	// We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
	// There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent
	// file.
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower32.png"));

	public CleanUpTrashPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		name = NAME;
		ID = POWER_ID;

		this.owner = owner;
		this.amount = amount;
		this.source = source;

		type = PowerType.BUFF;
		isTurnBased = false;
		isPostActionPower = true;

		// We load those txtures here.
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
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
		return new CleanUpTrashPower(owner, source, amount);
	}

	@Override
	public void trashReduced(int amount) {
		addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount), amount));
		addToBot(new ApplyPowerAction(owner, owner, new SelfEsteem(owner, owner, amount), amount));
	}
}
