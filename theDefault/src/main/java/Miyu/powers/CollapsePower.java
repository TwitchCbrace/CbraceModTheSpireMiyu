package Miyu.powers;

import static Miyu.DefaultMod.makePowerPath;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import Miyu.DefaultMod;
import Miyu.cards.OnReduceCover;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;

public class CollapsePower
    extends AbstractPower
    implements CloneablePowerInterface, OnReduceCover {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("CollapsePower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    // There's a fallback "missing texture" image, so the game shouldn't crash if you accidentally put a non-existent
    // file.
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower84.png"));

    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("CleanUpTrashPower32.png"));

    public CollapsePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = -1;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;
        isPostActionPower = true;

        // We load those txtures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void CoverReduced(DamageInfo info, int amount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
            && info.owner != null && info.owner != this.owner) {
            this.flash();
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, amount, DamageInfo.DamageType.THORNS),
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];

    }

    @Override
    public AbstractPower makeCopy() {
        return new CollapsePower(owner, source, amount);
    }

}
