package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

public class ThingsForgettablePower extends AbstractPower implements CloneablePowerInterface {

	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("ThingsForgettablePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ThingsForgettablePower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ThingsForgettablePower32.png"));

	public ThingsForgettablePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
		name = NAME;
		ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.source = source;

		type = PowerType.BUFF;
		isPostActionPower = true;

		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
	}

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.amount += stackAmount;
		this.updateDescription();
	}

	public void onExhaust(AbstractCard card) {
		this.flash();
		AbstractPlayer p = AbstractDungeon.player;
		this.addToBot(new ApplyPowerAction(p, p, new TrashPower(p, p, this.amount), this.amount));
	}

	@Override
	public void updateDescription() {

		description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

	}

	@Override
	public AbstractPower makeCopy() {
		return new ThingsForgettablePower(owner, source, amount);
	}
}
