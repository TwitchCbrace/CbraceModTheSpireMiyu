package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class NextTargetAction
    extends AbstractGameAction {

    private int increaseAmount;

    private DamageInfo info;

    public NextTargetAction(AbstractCreature target, DamageInfo info, int incAmount) {
        this.info = info;
        this.setValues(target, info);
        this.increaseAmount = incAmount;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList
                .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HORIZONTAL));
            this.target.damage(this.info);
            if ((this.target.isDying || this.target.currentHealth <= 0) && !this.target.halfDead) {
                AbstractDungeon.actionManager.addToBottom(new CoverSelectAction(AbstractDungeon.player, 1));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }
}
