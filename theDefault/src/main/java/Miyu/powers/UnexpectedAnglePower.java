package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.OnReduceCover;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Iterator;

import static Miyu.DefaultMod.makePowerPath;

public class UnexpectedAnglePower extends AbstractPower implements CloneablePowerInterface {
	private int damage;
	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("UnexpectedAnglePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Covered84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Covered32.png"));

	public UnexpectedAnglePower(final AbstractCreature owner, final AbstractCreature source, final int amount,
			int damage) {
		name = NAME;
		ID = POWER_ID;
		this.owner = owner;
		this.amount = 5;
		this.source = source;
		this.damage = damage;

		type = PowerType.BUFF;
		isTurnBased = false;
		isPostActionPower = true;

		// We load those txtures here.
		this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
		this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

		updateDescription();
	}

	public void stackPower(int stackAmount) {
		this.fontScale = 8.0F;
		this.damage += stackAmount;
		this.updateDescription();
	}
	public void playApplyPowerSfx() {
		CardCrawlGame.sound.play("POWER_PLATED", 0.05F);
	}
	public void onUseCard(AbstractCard card, UseCardAction action) {

		if (card.type == AbstractCard.CardType.ATTACK) {
			--this.amount;
		}

		if (this.amount == 0) {
			this.flash();
			this.amount = 5;
			this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
					new VigorPower(AbstractDungeon.player, damage), damage));
		}

		this.updateDescription();
	}

	@Override
	public void updateDescription() {
		if (damage == 1) {
			description = DESCRIPTIONS[0] + damage + DESCRIPTIONS[1];
		} else if (damage > 1) {
			description = DESCRIPTIONS[0] + damage + DESCRIPTIONS[2];
		}
	}

	@Override
	public AbstractPower makeCopy() {
		return new UnexpectedAnglePower(owner, source, amount, damage);
	}
}
