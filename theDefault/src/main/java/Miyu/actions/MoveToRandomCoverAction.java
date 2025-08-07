package Miyu.actions;

import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class MoveToRandomCoverAction extends AbstractGameAction {
	private AbstractPlayer p;
	private boolean isDelayed;

	public MoveToRandomCoverAction(AbstractPlayer p) {
		this(p, false);
	}

	public MoveToRandomCoverAction(AbstractPlayer p, boolean isDelayed) {
		this.p = p;
		this.actionType = ActionType.SPECIAL;
		this.isDelayed = isDelayed;
		this.duration = Settings.ACTION_DUR_FAST; // For delayed actions
	}

	@Override
	public void update() {
		if (isDelayed) {
			// If this is a delayed action, add the actual action to the top and complete this one
			this.addToTop(new MoveToRandomCoverAction(p, false));
			this.isDone = true;
			return;
		}

		// Original logic for non-delayed action
		ArrayList<AbstractCard> coverCardsInHand = new ArrayList<>();
		for (AbstractCard c : p.hand.group) {
			if (c instanceof ICoverCard && ((ICoverCard) c).getCoverValue() > 0) {
				coverCardsInHand.add(c);
			}
		}

		if (!coverCardsInHand.isEmpty()) {
			AbstractCard cardToMove = coverCardsInHand
					.get(AbstractDungeon.cardRandomRng.random(coverCardsInHand.size() - 1));
			this.addToTop(new MoveAction(p, cardToMove));
		}
		this.isDone = true;
	}
}
