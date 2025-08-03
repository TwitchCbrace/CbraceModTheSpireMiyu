package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class ClearCardTooltipAction extends AbstractGameAction {
	public ClearCardTooltipAction() {
		this.actionType = ActionType.SPECIAL;
	}

	@Override
	public void update() {
		// 열려 있는 카드 팝업 (카드 미리보기 등)을 닫습니다.
		if (CardCrawlGame.cardPopup != null) {
			CardCrawlGame.cardPopup.close();
		}

		// 상단 패널의 모든 호버 상태를 해제합니다. (일반적인 UI 호버를 지울 수 있음)
		AbstractDungeon.topPanel.unhoverHitboxes();

		// 현재 활성화된 화면을 강제로 닫고, 화면이 활성화된 상태를 해제합니다.
		AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
		AbstractDungeon.isScreenUp = false;

		// 취소 버튼을 숨기고 현재 화면을 닫습니다.
		AbstractDungeon.overlayMenu.cancelButton.hide();
		AbstractDungeon.closeCurrentScreen();

		this.isDone = true;
	}
}