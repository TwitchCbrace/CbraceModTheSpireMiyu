package Miyu.patches;

import Miyu.powers.Covered;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

@SpirePatch(clz = AbstractPlayer.class, method = "renderHealth")
public class CoveredHpBarPatch {
    public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
        if (CardCrawlGame.isInBattle()) {
            AbstractPower power = AbstractDungeon.player.getPower(Covered.POWER_ID);
            if (power != null) {
                int amount = power.amount;
                // I'm guessing at the coordinates here. I may need to adjust them.
                FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(amount), __instance.hb.cX + __instance.hb.width / 2.0F + 30.0f, __instance.hb.cY + 10.0f, 1.0f, Color.CYAN);
            }
        }
    }
}
