package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;

public class DontForgetMeAction
    extends AbstractGameAction {

    public DontForgetMeAction(AbstractCreature source, int amount) {

        this.setValues(this.target, source, amount);
        this.actionType = ActionType.WAIT;

    }

    public void update() {

        addToBot(new DrawCardAction(amount, new AbstractGameAction() {

            @Override

            public void update() {

                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.selfRetain == true) {
                        c.selfRetain = false;
                        CardModifierManager.addModifier(c, new EtherealMod());
                    }
                    else {
                        CardModifierManager.addModifier(c, new EtherealMod());
                    }
                }

                this.isDone = true;

            }

        }));
        this.isDone = true;
    }

}
