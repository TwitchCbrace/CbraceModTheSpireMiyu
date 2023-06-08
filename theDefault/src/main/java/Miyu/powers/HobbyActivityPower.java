package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makePowerPath;

public class HobbyActivityPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("HobbyActivityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ContestedAreaPower84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ContestedAreaPower32.png"));

    private Integer amount;

    public HobbyActivityPower(
            final AbstractCreature owner,
            final AbstractCreature source,
            final Integer amount
    ) {
        name = NAME;
        ID = POWER_ID;

        this.amount = amount;
        this.owner = owner;
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
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == Pebble.POWER_ID) {
            this.flash();
            this.amount += power.amount;
            addToBot(new ApplyPowerAction(
                    owner,
                    owner,
                    new VigorPower(owner, amount),
                    amount)
            );
            addToBot(new ApplyPowerAction(
                    owner,
                    owner,
                    new SelfEsteem(owner, owner, amount),
                    amount)
            );

            updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HobbyActivityPower(owner, source, amount);
    }
}
