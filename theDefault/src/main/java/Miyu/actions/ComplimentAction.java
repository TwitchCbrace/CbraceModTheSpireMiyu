package Miyu.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import Miyu.powers.SelfEsteem;

public class ComplimentAction
    extends AbstractGameAction {
    private int increaseSelfEsteem;

    private DamageInfo info;

    private static final float DURATION = 0.1F;

    private AbstractPlayer p;

    public ComplimentAction(AbstractPlayer player, AbstractCreature target, DamageInfo info, int selfEsteemAmount) {
        this.p = player;
        this.info = info;
        this.setValues(target, info);
        this.increaseSelfEsteem = selfEsteemAmount;
        this.actionType = ActionType.DAMAGE;
        this.duration = 0.1F;
    }

    public void update() {
        if (this.duration == 0.1F && this.target != null) {
            AbstractDungeon.effectList
                .add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.BLUNT_HEAVY));

            boolean isDamagedOverBlock = isDamagedOverBlock(target);

            this.target.damage(this.info);

            if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0 || isDamagedOverBlock)) {
                addToBot(new ApplyPowerAction(p, p, new SelfEsteem(p, p, increaseSelfEsteem), increaseSelfEsteem));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.tickDuration();
    }

    private Boolean isDamagedOverBlock(AbstractCreature m) {
        return m.currentBlock > 0 && this.info.output > m.currentBlock;
    }
}
