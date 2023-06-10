package Miyu.actions;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class BackTrackingAction
    extends AbstractGameAction {

    private static final UIStrings uiStrings;

    public static final String[] TEXT;

    private AbstractPlayer p;

    public BackTrackingAction(AbstractCreature target, AbstractCreature source) {

        this.target = target;

        this.p = (AbstractPlayer) target;

        this.setValues(target, source, amount);

        this.actionType = ActionType.CARD_MANIPULATION;

    }

    public void update() {

        if (this.duration == 0.5F) {

            if (this.p.hand.size() == 0) {

                this.isDone = true;

                return;

            }

            AbstractDungeon.handCardSelectScreen.open(TEXT[0], p.hand.size(), true, true);

            this.tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            Iterator<AbstractCard> var3 = AbstractDungeon.handCardSelectScreen.selectedCards.group.iterator();

            while (var3.hasNext()) {

                AbstractCard c = (AbstractCard) var3.next();

                this.p.hand.moveToDeck(c, false);

            }

            AbstractDungeon.player.hand.refreshHandLayout();

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;

        }

        this.tickDuration();

    }

    static {

        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");

        TEXT = uiStrings.TEXT;

    }

}
