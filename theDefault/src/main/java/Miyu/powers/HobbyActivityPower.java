package Miyu.powers;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makePowerPath;

public class HobbyActivityPower
    extends AbstractPower
    implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("HobbyActivityPower");

    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public static final String NAME = powerStrings.NAME;

    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("ContestedAreaPower84.png"));

    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("ContestedAreaPower32.png"));

    public HobbyActivityPower(final AbstractCreature owner, final AbstractCreature source) {
        name = NAME;
        ID = POWER_ID;

        // amount = 파워스택 = 배수. amount가 2면 2배, 3이면 3배;
        this.amount = 1;
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
        Pebble pebble = (Pebble) AbstractDungeon.player.getPower(Pebble.POWER_ID);
        int pebbleAmount = pebble != null ? pebble.amount : 0;
        description = DESCRIPTIONS[0] + amount * pebbleAmount + DESCRIPTIONS[1];

    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == Pebble.POWER_ID) {
            this.flash();

            // onApplyPower가 조약돌을 얻는 행위보다 먼저 일어나므로
            // 조약돌 power가 없을 때 조약돌 파워를 가져오면 null임 -> null pointer exception이 발생함
            Pebble pebble = (Pebble) AbstractDungeon.player.getPower(Pebble.POWER_ID);
            // 조약돌이 없을 때 조약돌을 얻으면 1, 조약돌파워가 있을 때는 보유한 조약돌 + 1만큼으로 설정
            int pebbleAmount = pebble != null ? pebble.amount + 1 : 1;

            addToBot(new ApplyPowerAction(owner, owner, new VigorPower(owner, pebbleAmount * amount),
                pebbleAmount * amount));
            addToBot(new ApplyPowerAction(owner, owner, new SelfEsteem(owner, owner, pebbleAmount * amount),
                pebbleAmount * amount));

            updateDescription();
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HobbyActivityPower(owner, source);
    }
}
