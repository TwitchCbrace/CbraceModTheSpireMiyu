package Miyu.actions;

import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BreakthroughAction extends AbstractGameAction {

	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CoverCardSelect");
	public static final String[] TEXT = uiStrings.TEXT;
	private AbstractPlayer p;

	public BreakthroughAction() {
		this.p = AbstractDungeon.player;
		this.setValues(this.p, AbstractDungeon.player, this.amount);
		this.actionType = ActionType.CARD_MANIPULATION;
		this.duration = Settings.ACTION_DUR_XFAST;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_XFAST) {
			CardGroup hand = this.p.hand;
			CardGroup cardsHasCover = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

			for (AbstractCard card : hand.group) {
				if (card instanceof ICoverCard) {
					cardsHasCover.addToBottom(card);
				}
			}

			if (cardsHasCover.isEmpty()) {
				this.isDone = true;
				return;
			}

			if (cardsHasCover.size() == 1) {
				AbstractCard card = cardsHasCover.getTopCard();
				ICoverCard coverCard = (ICoverCard) card;
				this.addToTop(new GainBlockAction(p, p, coverCard.getCoverValue()));
				this.addToTop(new ExhaustSpecificCardAction(card, p.hand, true));
				this.isDone = true;
				return;
			}

			AbstractDungeon.gridSelectScreen.open(cardsHasCover, 1, TEXT[0], false);
			this.tickDuration();
		}

		if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
				ICoverCard coverCard = (ICoverCard) c;
				this.addToTop(new GainBlockAction(p, p, coverCard.getCoverValue()));
				this.addToTop(new ExhaustSpecificCardAction(c, p.hand, true));
			}
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			this.isDone = true;
		}
	}
}
