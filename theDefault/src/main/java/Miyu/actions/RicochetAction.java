//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;
import com.sun.tools.javac.main.Option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class RicochetAction extends AbstractGameAction {
	private AbstractCard card;

	public RicochetAction(AbstractCard card) {
		this.duration = Settings.ACTION_DUR_XFAST;
		this.card = card;
	}

	public void update() {
		/* 가정: 내 공격턴 중에 몬스터가 분열하거나 위치를 바꾸지 않는다. */

		// 몬스터가 존재할 수 있는 위치를 가져옵니다
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;

		// n번째 위치를 타격한 적이 있는지 저장하는 Boolean Array
		ArrayList<Boolean> isHit = new ArrayList<>();

		// 만약 빈 공간 or 죽은 몬스터가 있던 공간인 경우, 타격한 적이 있는 것으로 처리
		for (AbstractMonster monster : monsters) {
			isHit.add(monster.isDeadOrEscaped());
		}

		// 모든 위치의 몬스터를 타격할 때까지 반복
		while (isHit.contains(false)) {

			// 모든 위치 중 랜덤한 숫자를 하나 뽑는다
			do {
				int randomIndex = ThreadLocalRandom.current().nextInt(0, monsters.size());

				// 그 위치를 타격할 예정
				isHit.set(randomIndex, true);

				// 타겟 위치 업데이트
				this.target = monsters.get(randomIndex);

				// 그 공간이 빈 공간이거나 죽은 몬스터가 있던 공간인 경우, 위치를 다시 선택
			} while (this.target.isDeadOrEscaped());

			// 때림
			this.card.calculateCardDamage((AbstractMonster) this.target);
			this.addToBot(new DamageAction(this.target,
					new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn)));
			if (this.target != null && this.target.hb != null) {
				this.addToBot(new VFXAction(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY)));
			}
		}

		this.isDone = true;
	}

}
