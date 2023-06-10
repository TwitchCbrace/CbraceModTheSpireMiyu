package Miyu.powers;

import static Miyu.DefaultMod.makePowerPath;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import Miyu.DefaultMod;
import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.OnReduceCover;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;

public class Covered
    extends AbstractPower
    implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("Covered");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("Covered84.png"));

    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("Covered32.png"));

    public AbstractDefaultCard sourceCover;

    public CardGroup findPlaceCard(AbstractPlayer p, AbstractDefaultCard c) {
        Iterator var1 = p.hand.group.iterator();

        while (var1.hasNext()) {
            AbstractCard tmp = (AbstractCard) var1.next();
            if (tmp.uuid == c.uuid) {
                return p.hand;
            }
        }
        var1 = p.drawPile.group.iterator();
        while (var1.hasNext()) {
            AbstractCard tmp = (AbstractCard) var1.next();
            if (tmp.uuid == c.uuid)
                return p.drawPile;
        }
        var1 = p.discardPile.group.iterator();
        while (var1.hasNext()) {
            AbstractCard tmp = (AbstractCard) var1.next();
            if (tmp.uuid == c.uuid) {
                return p.discardPile;
            }
        }
        return p.exhaustPile;
    }

    public Covered(
        final AbstractCreature owner,
        final AbstractCreature source,
        final int amount,
        AbstractDefaultCard sourceCover) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.sourceCover = sourceCover;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount = stackAmount;
        if (this.amount > 999) {
            this.amount = 999;
        }

        this.updateDescription();
    }

    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_PLATED", 0.05F);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {

        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS
            && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {

            int reduceDamage = Math.min(this.amount, damageAmount);

            this.addToTop(new ReducePowerAction(this.owner, this.owner, this.ID, reduceDamage));

            sourceCover.baseCoverMagicNumber -= reduceDamage;
            sourceCover.coverMagicNumber -= reduceDamage;
            sourceCover.isCoverMagicNumberModified = true;
            if (sourceCover.baseCoverMagicNumber < 1) {
                AbstractPlayer p;
                p = AbstractDungeon.player;
                CardGroup cg = findPlaceCard(p, sourceCover);
                cg.moveToExhaustPile(sourceCover);

            }

            for (AbstractPower p : owner.powers) {
                if (p instanceof OnReduceCover) {
                    ((OnReduceCover) p).CoverReduced(info, reduceDamage);
                }
            }
            return damageAmount - reduceDamage;

        }
        else {

            return damageAmount;

        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else if (amount > 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new Covered(owner, source, amount, sourceCover);
    }
}
