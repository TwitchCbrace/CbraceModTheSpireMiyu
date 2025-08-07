package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makePowerPath;

public class CleanUpTrashPower extends AbstractPower implements CloneablePowerInterface {
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("CleanUpTrashPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower32.png"));
	public int vigor;

	public CleanUpTrashPower(final AbstractCreature owner, final AbstractCreature source, final int amount,
			final int vigor) {
		name = NAME;
		ID = POWER_ID;

		this.owner = owner;
		this.amount = amount;
		this.source = source;
		this.vigor = vigor;

		type = PowerType.BUFF;
		isTurnBased = false;

		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
	}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (AbstractDungeon.player.hasPower(TrashPower.POWER_ID)
				&& AbstractDungeon.player.getPower(TrashPower.POWER_ID).amount > 0) {
			this.flash();
			this.addToBot(new ReducePowerAction(this.owner, this.owner, TrashPower.POWER_ID, 1));
			this.addToBot(new ApplyPowerAction(this.owner, this.owner,
					new PresencePower(this.owner, this.owner, this.amount), this.amount));
			this.addToBot(
					new ApplyPowerAction(this.owner, this.owner, new VigorPower(this.owner, this.vigor), this.vigor));
		}
	}

	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.vigor + DESCRIPTIONS[2];
	}

	@Override
	public AbstractPower makeCopy() {
		return new CleanUpTrashPower(owner, source, amount, vigor);
	}
}