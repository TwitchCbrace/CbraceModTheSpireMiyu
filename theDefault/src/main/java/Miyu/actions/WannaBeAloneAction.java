package Miyu.actions;

import Miyu.cards.ICoverCard;
import basemod.cardmods.EtherealMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Iterator;

public class WannaBeAloneAction extends AbstractGameAction {
    private AbstractCard sourceCard;

    public WannaBeAloneAction(AbstractCard source) {
        this.sourceCard = source;
    }

    @Override
    public void update() {
        Iterator iter = AbstractDungeon.player.hand.group.iterator();

        while(iter.hasNext()) {
            AbstractCard c = (AbstractCard)iter.next();

            if (c instanceof ICoverCard && c != sourceCard) {
                c.selfRetain = false;
                CardModifierManager.addModifier(c, new EtherealMod());
            }
        }

        this.tickDuration();
    }
}
