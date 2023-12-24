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
		// * 아래 로직은 엄밀히 말하자면, `카드를 시행하면, 그 순간 존재하는 모든 적의 위치를 타격하면 멈춘다.` 에 가깝습니다.
		// * 그래서 아래 가정 2개가 깨질 경우, 버그가 생길 수 있습니다.
		// * 가정 1: 나의 공격 턴 중에 몬스터가 분열하거나 위치를 바꾸지 않는다.
		// * 가정 2: 1개의 도탄 공격이 시행 중일 때 몬스터가 죽은 경우, 바로 그 자리에 다른 개체가 생성되어 다음 도탄 공격들의 대상이 되지 않는다.

		// * 부메랑 칼날, 탄성 플라스크의 적 선택 메커니즘을 사용하지 않은 이유
		// * `AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true,
		// AbstractDungeon.cardRandomRng);`
		// * 위 함수는 `무작위 적`을 선택하는 함수로서, `특정 적을 때렸는가?`를 파악하기에 적절하지 않습니다.

		// * 참고: id 만으로 몇 번째 몬스터인지 파악할 수 없음에 주의하세요.
		// * `fight 4_Byrd` 의 경우, 4개의 섀가 등장하는데,
		// * 이 4개의 섀는 모두 `AbstractMonster.id==Byrd`, `AbstractMonster.name==섀` 값을 가지고 있습니다.
		// * 즉, stringID만 가지고 몇 번째 섀를 골라 낼 수 있는 방법은 아직 제대로 파악하지 못했습니다.

		// 몬스터가 존재할 수 있는 위치들을 가져옵니다
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;

		// n번째 위치를 타격한 적이 있는지 저장하는 Boolean Array
		ArrayList<Boolean> isHit = new ArrayList<>();

		// Logger 출력 대비
		ArrayList<Integer> hitAttemptHistory = new ArrayList<>();
		ArrayList<Integer> hitRealHistory = new ArrayList<>();

		// 만약 빈 공간 or 죽은 몬스터가 있던 공간인 경우, 타격한 것으로 처리
		for (AbstractMonster monster : monsters) {
			isHit.add(monster.isDeadOrEscaped());
		}

		// 모든 위치의 몬스터를 타격할 때까지 반복
		while (isHit.contains(false)) {
			int randomIndex;

			// 모든 위치 중 랜덤한 숫자를 하나 뽑는다
			// 단, 그 공간이 빈 공간이거나 죽은 몬스터가 있던 공간이거나 적이 거의 죽어가고 있는 위치인 경우, 다시 숫자를 뽑습니다.
			do {
				randomIndex = AbstractDungeon.cardRandomRng.random(0, monsters.size() - 1);

				// 타겟 위치 마킹 및 타격 가능 여부 확인
				isHit.set(randomIndex, true);
				this.target = monsters.get(randomIndex);

				// Logger 타격 시도 위치 추가
				hitAttemptHistory.add(randomIndex);

			} while (this.target.isDeadOrEscaped());
			// Logger 실제 타격 위치 추가
			hitRealHistory.add(randomIndex);

			// 때림
			this.card.calculateCardDamage((AbstractMonster) this.target);
			this.addToBot(new DamageAction(this.target,
					new DamageInfo(AbstractDungeon.player, this.card.damage, this.card.damageTypeForTurn)));
			if (this.target != null && this.target.hb != null) {
				this.addToBot(new VFXAction(new ThrowDaggerEffect(this.target.hb.cX, this.target.hb.cY)));
			}
		}

		// Logger 출력: 타격 시도 위치, 실제 타격 위치
		logger.info("hitAttemptHistory: " + hitAttemptHistory.toString());
		logger.info("hitRealHistory: " + hitRealHistory.toString());

		this.isDone = true;
	}

}