package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;

import static Miyu.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class DoubleDamage extends AbstractPower implements CloneablePowerInterface {
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("DoubleDamage");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

	// We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
	// There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent
	// file.
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("DoubleDamage84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("DoubleDamage32.png"));

	public DoubleDamage(final AbstractCreature owner, final int amount) {
		name = NAME;
		ID = POWER_ID;

		this.owner = owner;
		this.amount = amount;

		type = PowerType.BUFF;
		isTurnBased = true;

		// We load those txtures here.
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
	}

	public void onUseCard(AbstractCard card, UseCardAction action) {
		if (card.type == AbstractCard.CardType.ATTACK) {
			this.addToBot(new ReducePowerAction(this.owner, this.owner, DoubleDamage.POWER_ID, 1));
		}
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			this.addToBot(new ReducePowerAction(this.owner, this.owner, DoubleDamage.POWER_ID, 1));
		}

	}
	@Override
	public void updateDescription() {
		if (amount == 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
		} else if (amount > 1) {
			description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
		}
	}
	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		return type == DamageInfo.DamageType.NORMAL ? damage * 2.0F : damage;
	}
	@Override
	public AbstractPower makeCopy() {
		return new DoubleDamage(owner, amount);
	}

}
