package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.BaseMod;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

public class SelfEsteem extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("SelfEsteem");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("SelfEsteem84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("SelfEsteem32.png"));



    public SelfEsteem(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
        this.canGoNegative = true;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, "Miyu:SelfEsteem"));
        }

        if (this.amount > 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }

        this.updateDescription();
    }
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_PLATED", 0.05F);
    }


//    @Override
//    public void onUseCard(final AbstractCard card, final UseCardAction action) {
//         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
//                new Covered(owner, amount), amount));
//    }

//     Note: If you want to apply an effect when a power is being applied you have 3 options:
//    onInitialApplication is "When THIS power is first applied for the very first time only."
//    onApplyPower is "When the owner applies a power to something else (only used by Sadistic Nature)."
//    onReceivePowerPower from StSlib is "When any (including this) power is applied to the owner."


//     Update the description when you apply this power. (i.e. add or remove an "s" in keyword(s))
    @Override
    public void updateDescription() {
        if (amount == 1 || amount == -1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
//@Override


    @Override
    public AbstractPower makeCopy() {
        return new SelfEsteem(owner, source, amount);
    }
}
