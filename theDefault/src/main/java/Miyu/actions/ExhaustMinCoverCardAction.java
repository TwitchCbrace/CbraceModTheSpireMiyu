package Miyu.actions;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ExhaustMinCoverCardAction extends AbstractGameAction {
	private static final float DURATION = Settings.ACTION_DUR_XFAST;
	private AbstractPlayer p;
	private AbstractCard c;
	private ArrayList<AbstractCard.CardTags> keyword = new ArrayList();

	public ExhaustMinCoverCardAction(AbstractPlayer player) {
		this.p = player;
		this.duration = DURATION;

		CardGroup hand = this.p.hand;
		CardGroup cardsHasCover = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
		AbstractCard minCoverCard = null;

		for (AbstractCard card1 : hand.group) {

			if (card1 instanceof ICoverCard) {

				cardsHasCover.addToBottom(card1);

			}

		}

		for (AbstractCard card2 : cardsHasCover.group) {

			if (minCoverCard == null
					|| ((AbstractDefaultCard) card2).baseCoverMagicNumber < ((AbstractDefaultCard) minCoverCard).baseCoverMagicNumber) {

				minCoverCard = card2;

			}

			c = minCoverCard;

		}
	}

	@Override
	public void update() {
		if (c != null) {
			AbstractDungeon.player.hand.moveToExhaustPile(c);
		}
		this.isDone = true;
	}
}