package Miyu.actions;

import Miyu.powers.TrashPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class RecyclingTrashAction extends AbstractGameAction {


    public RecyclingTrashAction(AbstractCreature source) {
        this.setValues(this.target, source, amount);
        this.actionType = ActionType.WAIT;

    }

    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(TrashPower.POWER_ID)) {
            int toDraw = Math.min(p.drawPile.size() + p.discardPile.size(),
                    Math.min(p.getPower(TrashPower.POWER_ID).amount,
                            BaseMod.MAX_HAND_SIZE - p.hand.size())
            );
            if (toDraw > 0) {
                this.addToTop(new DrawCardAction(this.source, toDraw));
                this.addToTop(new ReducePowerAction(p, p, p.getPower(TrashPower.POWER_ID), toDraw));
            }
        }
        this.isDone = true;
    }
}
