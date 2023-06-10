package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import Miyu.powers.SelfEsteem;

public class TriggerAction
    extends AbstractGameAction {

    private AbstractPlayer p;

    public TriggerAction() {
        this.actionType = ActionType.WAIT;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_XFAST;
    }

    public void update() {
        if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower(SelfEsteem.POWER_ID)) {
            int selfAmt = this.p.getPower(SelfEsteem.POWER_ID).amount;
            this.addToTop(new ApplyPowerAction(this.p, this.p, new SelfEsteem(this.p, this.p, selfAmt), selfAmt));
        }

        this.tickDuration();
    }
}
