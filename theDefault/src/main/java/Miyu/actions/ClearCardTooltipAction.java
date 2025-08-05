package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ClearCardTooltipAction extends AbstractGameAction {
	public ClearCardTooltipAction() {
		this.actionType = ActionType.SPECIAL;
	}

	@Override
	public void update() {
		// Close any open card popups (like card previews)
		if (CardCrawlGame.cardPopup != null) {
			CardCrawlGame.cardPopup.close();
		}

		// Unhover all hitboxes in the top panel
		AbstractDungeon.topPanel.unhoverHitboxes();

		// Force the current screen to close and reset screen state
		AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
		AbstractDungeon.isScreenUp = false;

		// Hide the cancel button and close the current screen
		AbstractDungeon.overlayMenu.cancelButton.hide();
		AbstractDungeon.closeCurrentScreen();

		// Unhover and remove tooltips from all cards in hand
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			c.unhover();
			c.untip();
		}

		this.isDone = true;
	}
}
