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

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RicochetAction extends AbstractGameAction {
	private DamageInfo info;
	private AbstractCard card;

	public RicochetAction(AbstractCard card, DamageInfo info) {
		this.duration = Settings.ACTION_DUR_XFAST;
		this.info = info;
		this.card = card;
	}

	public void update() {
		// 전체 몬스터 그룹을 가져온다
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
		// n번째 몬스터가 맞았는지 체크할 boolean 어레이
		boolean[] isHit = new boolean[monsters.size()];
		// 모든 몬스터가 맞았는지 체크할 변수
		boolean isAllMonsterHit = false;
		// 몇 마리나 맞았는지 카운트
		int hitTargetCount = 0;

		// 모든 몬스터가 맞을때까지 반복
		while (!isAllMonsterHit) {
			// 몬스터 숫자 중 랜덤한 숫자 하나를 뽑는다
			int randomIndex = ThreadLocalRandom.current().nextInt(0, monsters.size());

			// 그 몬스터가 맞은적이 없다면
			if (!isHit[randomIndex]) {
				// 맞았다고 체크하고
				isHit[randomIndex] = true;
				// 카운트 + 1
				hitTargetCount += 1;

				// 카운트가 몬스터 숫자랑 일치하면
				if (hitTargetCount == monsters.size()) {
					// 다 맞았다!
					isAllMonsterHit = true;
				}
			}

			// n번째 몬스터를 가져와서
			this.target = monsters.get(randomIndex);

			// 때림
			this.card.calculateCardDamage((AbstractMonster) this.target);
			this.addToBot(new DamageAction(this.target, this.info, true));
			if (this.target != null && this.target.hb != null) {
				this.addToBot(new VFXAction(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY)));
			}
		}

		this.isDone = true;
	}

}
