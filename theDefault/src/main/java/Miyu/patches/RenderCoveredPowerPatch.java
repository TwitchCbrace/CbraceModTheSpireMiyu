package Miyu.patches;

import Miyu.powers.Covered;
import Miyu.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makePowerPath;

@SpirePatch(clz = com.megacrit.cardcrawl.characters.AbstractPlayer.class, method = "renderHealth")
public class RenderCoveredPowerPatch {

    private static final Texture coverIcon = TextureLoader.getTexture(makePowerPath("Covered84.png"));

    @SpirePostfixPatch
    public static void Postfix(AbstractPlayer __instance, SpriteBatch sb) {
        if (!__instance.isDead && !__instance.isDying) {
            AbstractPower coveredPower = __instance.getPower(Covered.POWER_ID);
            if (coveredPower != null && coveredPower.amount > 0) {
                renderCoveredAmount(__instance, sb, coveredPower.amount);
            }
        }
    }

    private static void renderCoveredAmount(AbstractPlayer p, SpriteBatch sb, int amount) {
        // Coordinates for the block icon
        float blockIconX = p.hb.cX - 80.0F * Settings.scale;
        float blockIconY = p.hb.cY - 48.0F * Settings.scale;

        // Position our Covered amount above the block icon
        float coveredY = blockIconY + 45.0F * Settings.scale;

        // Render the Covered icon
        sb.setColor(Color.WHITE);
        sb.draw(coverIcon, blockIconX - 40.0F * Settings.scale, coveredY - 24.0F * Settings.scale, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale * 0.5f, Settings.scale * 0.5f, 0.0F, 0, 0, 84, 84, false, false);

        // Render the Covered amount text
        FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(amount), blockIconX + 10.0F * Settings.scale, coveredY, Color.CYAN.cpy());
    }
}
