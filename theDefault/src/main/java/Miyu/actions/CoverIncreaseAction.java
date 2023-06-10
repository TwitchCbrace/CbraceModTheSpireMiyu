package Miyu.actions;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;

public class CoverIncreaseAction
    extends AbstractGameAction {

    private int increaseAmount;

    private final int amount;

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CoverCardSelect");

    public static final String[] TEXT = uiStrings.TEXT;

    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    private AbstractPlayer p;

    private ArrayList<AbstractDefaultCard.CardTags> keyword = new ArrayList();

    public CoverIncreaseAction(AbstractPlayer player, int amount, int magicNumber) {
        this.p = player;
        this.duration = DURATION;
        this.increaseAmount = magicNumber;
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
                // for (AbstractCard card : cardsHasCover.group) {
                // ((ICoverCard)card).triggerOnCovered(p);
                // }
                for (AbstractCard card : cardsHasCover.group) {

                    if (card instanceof AbstractDefaultCard) {
                        ((AbstractDefaultCard) card).baseCoverMagicNumber += increaseAmount;
                        ((AbstractDefaultCard) card).coverMagicNumber += increaseAmount;
                    }

                }
                isDone = true;
                return;
            }

            AbstractDungeon.gridSelectScreen.open(cardsHasCover, amount, false, TEXT[0]);
            tickDuration();
        }

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                if (card instanceof AbstractDefaultCard) {
                    ((AbstractDefaultCard) card).baseCoverMagicNumber += increaseAmount;
                    ((AbstractDefaultCard) card).coverMagicNumber += increaseAmount;
                }
            }
            isDone = true;
        }

    }
}
