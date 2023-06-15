package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.actions.InvisibleFormAction;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

public class InvisibleFormPower extends AbstractPower implements CloneablePowerInterface {

	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("InvisibleFormPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("InvisibleFormPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("InvisibleFormPower32.png"));

	public InvisibleFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
		int a = 0;
		for (a = amount; a > 0; a--) {
			addToBot(new InvisibleFormAction());
		}
		super.atStartOfTurnPostDraw();
	}

	@Override
	public void updateDescription() {

		description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

	}

	@Override
	public AbstractPower makeCopy() {
		return new InvisibleFormPower(owner, source, amount);
	}
}
