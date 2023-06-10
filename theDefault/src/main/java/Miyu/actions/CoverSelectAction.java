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

import Miyu.cards.ICoverCard;
import Miyu.cards.OnMoveAction;

public class CoverSelectAction
    extends AbstractGameAction
    implements OnMoveAction {

    /*
     * uistring을 확장하였음. 추가된 uistring은 localization 폴더에 다른 애들처럼 같이 있음 by josh
     */

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("CoverCardSelect");

    public static final String[] TEXT = uiStrings.TEXT;

    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    private final int amount;

    private AbstractPlayer p;

    private ArrayList<AbstractCard.CardTags> keyword = new ArrayList();

    // card 장수를 넣으면 되구요
    // by josh
    public CoverSelectAction(AbstractPlayer player, int amount) {
        this.p = player;
        this.duration = DURATION;
        this.amount = amount;
    }

    @Override
    public void onMove() {

    }

    @Override
    public void update() {

        // 매 프레임마다 불리우는 녀석
        // by josh

        if (this.duration == DURATION) {
            /*
             * 지정된 duration이 지난 후 손 패 목록을 가져와서 ICoverCard interface를 따르는 카드만 추려서 목록에 넣음
             * 
             * by josh
             */
            CardGroup hand = this.p.hand;
            CardGroup cardsHasCover = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

            for (AbstractCard card : hand.group) {
                if (card instanceof ICoverCard) {
                    cardsHasCover.addToBottom(card);
                }
            }

            /*
             * 카드가 없거나 지정된 장수보다 적으면 trigger 호출하고 그걸로 선택 종료 by josh
             */

            if (cardsHasCover.isEmpty()) {
                isDone = true;
                return;
            }
            if (cardsHasCover.size() <= amount) {
                for (AbstractCard card : cardsHasCover.group) {
                    ((ICoverCard) card).triggerOnCovered(p);
                }
                isDone = true;
                return;
            }

            /*
             * 지정된 조건 ICoverCard interface를 따르는 녀석들이 들어있는 cardsHasCover를 보내서 카드 선택 팝업 띄움 gridSelectScreen은 커스텀 카드 목록으로
             * 카드 선택할 수 있도록 팝업을 띄우는 클래스
             * 
             * by josh
             */

            AbstractDungeon.gridSelectScreen.open(cardsHasCover, amount, false, TEXT[0]);
            tickDuration();
        }

        /*
         * 카드가 선택 됐으면 각 카드의 trigger 실행하고 선택 종료 by josh
         */

        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard card : AbstractDungeon.gridSelectScreen.selectedCards) {
                ((ICoverCard) card).triggerOnCovered(p);
            }
            isDone = true;

        }

    }
}
