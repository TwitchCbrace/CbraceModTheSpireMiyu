//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Miyu.actions;

import Miyu.DefaultMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class RicochetAction extends AbstractGameAction {
	public static final Logger logger = LogManager.getLogger(DefaultMod.class.getName());
	private AbstractCard card;

	public RicochetAction(AbstractCard card) {
		this.duration = Settings.ACTION_DUR_XFAST;
		this.card = card;
	}

	public void update() {
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		ArrayList<AbstractMonster> aliveMonsters = new ArrayList<>();
		for (AbstractMonster monster : monsters) {
			if (!monster.isDeadOrEscaped()) {
				aliveMonsters.add(monster);
			}
		}

		if (aliveMonsters.isEmpty()) {
			this.isDone = true;
			return;
		}

		ArrayList<AbstractMonster> hitMonsters = new ArrayList<>();

		while (hitMonsters.size() < aliveMonsters.size()) {
			ArrayList<AbstractMonster> attackableMonsters = new ArrayList<>();
			for (AbstractMonster monster : aliveMonsters) {
				if (!monster.isDeadOrEscaped()) {
					attackableMonsters.add(monster);
				}
			}

			if (attackableMonsters.isEmpty()) {
				break;
			}

			AbstractMonster randomMonster = attackableMonsters
					.get(AbstractDungeon.cardRandomRng.random(attackableMonsters.size() - 1));

			if (!hitMonsters.contains(randomMonster)) {
				hitMonsters.add(randomMonster);
			}

			this.card.calculateCardDamage(randomMonster);
			this.addToBot(new DamageAction(randomMonster,
					new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn)));
			if (randomMonster != null && randomMonster.hb != null) {
				this.addToBot(new VFXAction(new ThrowDaggerEffect(randomMonster.hb.cX, randomMonster.hb.cY)));
			}
		}

		this.isDone = true;
	}

}