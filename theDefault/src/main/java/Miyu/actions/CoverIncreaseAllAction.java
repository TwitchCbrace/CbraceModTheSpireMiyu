package Miyu.actions;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class CoverIncreaseAllAction extends AbstractGameAction {
	private static final float DURATION = Settings.ACTION_DUR_XFAST;
	private AbstractPlayer p;
	private int ta = 0;
	private int ca = 0;
	private ArrayList<AbstractCard.CardTags> keyword = new ArrayList();

	public CoverIncreaseAllAction(AbstractPlayer player, int trashAmount, int coverAmount) {
		this.p = player;
		this.ta = trashAmount;
		this.ca = coverAmount;
		this.duration = DURATION;

		if (p.hasPower(TrashPower.POWER_ID) && p.getPower(TrashPower.POWER_ID).amount >= 1) {
			CardGroup hand = p.hand;
			CardGroup cardsHasCover = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

			for (AbstractCard card1 : hand.group) {
				if (card1 instanceof ICoverCard) {
					cardsHasCover.addToBottom(card1);
				}
			}
			int powerAmount = p.getPower(TrashPower.POWER_ID).amount;
			if (powerAmount < ta) {
				ta = powerAmount;
			}

			this.addToBot(new ReducePowerAction(p, p, p.getPower(TrashPower.POWER_ID), ta));

			for (AbstractCard card2 : cardsHasCover.group) {
				((AbstractDefaultCard) card2).baseCoverMagicNumber += (ta * ca);
				card2.superFlash();
				// ((AbstractDefaultCard) card2).isCoverMagicNumberModified = true;
			}

		}

	}

	@Override
	public void update() {

		this.isDone = true;
	}
}