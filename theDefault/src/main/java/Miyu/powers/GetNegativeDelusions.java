package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.NegativeDelusions;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

import static Miyu.DefaultMod.makePowerPath;

public class GetNegativeDelusions extends AbstractPower implements CloneablePowerInterface {

	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("GetNegativeDelusions");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("GetNegativeDelusions84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("GetNegativeDelusions32.png"));

	public GetNegativeDelusions(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		this.updateDescription();
	}

	@Override
	public void atStartOfTurnPostDraw() {
		this.flash();
		addToBot(new MakeTempCardInHandAction(new NegativeDelusions(), amount));
		super.atStartOfTurnPostDraw();
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
		return new GetNegativeDelusions(owner, source, amount);
	}
}
