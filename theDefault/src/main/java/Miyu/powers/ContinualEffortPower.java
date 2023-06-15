package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.actions.InvisibleFormAction;
import Miyu.cards.NegativeDelusions;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.SmokeBombEffect;

import java.util.Iterator;

import static Miyu.DefaultMod.makePowerPath;

public class ContinualEffortPower extends AbstractPower implements CloneablePowerInterface {

	public AbstractCreature source;

	public static final String POWER_ID = DefaultMod.makeID("ContinualEffortPower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ContinualEffortPower84.png"));
	private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ContinualEffortPower32.png"));

	public ContinualEffortPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
		addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, amount), amount));
		super.atStartOfTurnPostDraw();
	}

	public boolean CanEscape() {
		Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

		AbstractMonster m;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			m = (AbstractMonster) var1.next();
			if (m.hasPower("BackAttack")) {
				return false;
			}
		} while (m.type != AbstractMonster.EnemyType.BOSS);

		return false;
	}

	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		if (target.hasPower(SelfEsteem.POWER_ID)) {
			if (power.ID == SelfEsteem.POWER_ID) {
				if (target.getPower(SelfEsteem.POWER_ID).amount + power.amount <= -20) {
					if (CanEscape()) {
						if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
							AbstractDungeon.getCurrRoom().smoked = true;
							this.addToBot(new VFXAction(new SmokeBombEffect(target.hb.cX, target.hb.cY)));
							AbstractDungeon.player.hideHealthBar();
							AbstractDungeon.player.isEscaping = true;
							AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
							AbstractDungeon.overlayMenu.endTurnButton.disable();
							AbstractDungeon.player.escapeTimer = 2.5F;
						}
					}
				}
			}
		}
		super.onApplyPower(power, target, source);
	}

	@Override
	public void updateDescription() {

		description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];

	}

	@Override
	public AbstractPower makeCopy() {
		return new ContinualEffortPower(owner, source, amount);
	}
}
