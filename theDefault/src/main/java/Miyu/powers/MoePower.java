package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.cards.OnReduceTrash;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makePowerPath;

//Gain 1 dex for the turn for each card played.

public class MoePower extends AbstractPower {
	public static final String POWER_ID = DefaultMod.makeID("MoePower");
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	private int damage;
	// repeat 추가했습니다.
	private int repeat;
	private static int bombIdOffset;
	// 받아오는 매개변수로 repeat 값을 추가 했습니다.
	public MoePower(AbstractCreature owner, int turns, int damage, int repeat) {
		this.name = NAME;
		this.ID = "MoePower" + bombIdOffset;
		++bombIdOffset;
		this.owner = owner;
		this.amount = turns;
		this.damage = damage;
// repeat 값 할당.
		this.repeat = repeat;
		this.updateDescription();
		this.loadRegion("the_bomb");
	}

	public void atEndOfTurn(boolean isPlayer) {
		if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
			this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
			if (this.amount == 1) {
// 여기서 repeat 수 만큼 폭발을 반복 했습니다.
				for (int i = 0; i < repeat; i++) {
					this.addToBot(new DamageAllEnemiesAction((AbstractCreature) null,
							DamageInfo.createDamageMatrix(this.damage, true), DamageInfo.DamageType.THORNS,
							AbstractGameAction.AttackEffect.FIRE));
				}
			}
		}

	}

	// 파워 설명을 추가 했습니다. "n 번 반복합니다." 라고 일단 적었습니다.
	public void updateDescription() {
		if (this.amount == 1) {
			this.description = String.format(DESCRIPTIONS[1], this.damage , this.repeat);
		} else {
			this.description = String.format(DESCRIPTIONS[0], this.amount, this.damage , this.repeat);
		}

	}
	// 이것도 repeat 추가 했습니다.
	public AbstractPower makeCopy() {
		return new MoePower(owner, amount, damage, repeat);
	}
// static {
// powerStrings = CardCrawlGame.languagePack.getPowerStrings("MoePower");
// NAME = powerStrings.NAME;
// DESCRIPTIONS = powerStrings.DESCRIPTIONS;
// }
}
