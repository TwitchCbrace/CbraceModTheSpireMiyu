package Miyu.actions;

import Miyu.powers.Covered;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class FoundItAction extends AbstractGameAction {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private int magicAmount;
    private AbstractPlayer p;

    public FoundItAction(AbstractPlayer p, int magicAmount) {
        this.p = p;
        this.magicAmount = magicAmount;
    }

    @Override
    public void update() {
        // 현재 미유가 엄폐중인 카드를 가져온다.
        Covered covered = (Covered) p.getPower(Covered.POWER_ID);

        // 엄폐중이라면 엄폐중인 카드, 엄폐중이 아니라면 null
        AbstractCard coveredSource = covered != null ? covered.sourceCover : null;

        for (AbstractCard c : DrawCardAction.drawnCards) {
            // 엄폐중인 카드를 뽑았다면 자존감과 활력을 magicAmount 만큼 얻는다.
            if (c == coveredSource) {
                addToBot(new ApplyPowerAction(p, p, new SelfEsteem(p, p, magicAmount), magicAmount));
                addToBot(new ApplyPowerAction(p, p, new VigorPower(p, magicAmount), magicAmount));
            }
        }

        this.isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("PutOnDeckAction");
        TEXT = uiStrings.TEXT;
    }
}
