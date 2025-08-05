package Miyu.actions;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class DummyCoverSelectAction extends AbstractGameAction {

	/*
	 * uistring을 확장하였음. 추가된 uistring은 localization 폴더에 다른 애들처럼 같이 있음 by josh
	 */

	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CoverCardSelect");
	public static final String[] TEXT = uiStrings.TEXT;

	private static final float DURATION = Settings.ACTION_DUR_XFAST;

	private final int amount;
	private int def;
	private AbstractPlayer p;
	private ArrayList<AbstractCard.CardTags> keyword = new ArrayList();

	// card 장수를 넣으면 되구요
	// by josh
	public DummyCoverSelectAction(AbstractPlayer player, int amount) {
		this.p = player;
		this.duration = DURATION;
		this.amount = amount;
	}

	@Override
	public void update() {

		if (this.duration == DURATION) {

			CardGroup hand = this.p.hand;
			CardGroup cardsHasCover = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

			for (AbstractCard card : hand.group) {
				if (card instanceof ICoverCard) {
					cardsHasCover.addToBottom(card);
				}
			}
			if (cardsHasCover.isEmpty()) {
				isDone = true;
				return;
			}
			if (cardsHasCover.size() <= amount) {
				for (AbstractCard card : cardsHasCover.group) {
					((ICoverCard) card).triggerOnCovered(p);
					def = ((AbstractDefaultCard) card).baseCoverMagicNumber;
					AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, def));
				}
				this.addToBot(new ClearCardTooltipAction());
				isDone = true;
				return;
			}

			AbstractDungeon.gridSelectScreen.open(cardsHasCover, amount, false, TEXT[0]);
			tickDuration();
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
		}

		if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
			for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
				((ICoverCard) card).triggerOnCovered(p);
				def = ((AbstractDefaultCard) card).baseCoverMagicNumber;
				AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, def));
			}
			this.addToBot(new ClearCardTooltipAction());
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			isDone = true;
		}

	}
}
