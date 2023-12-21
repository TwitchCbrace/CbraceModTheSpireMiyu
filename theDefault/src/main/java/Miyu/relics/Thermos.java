package Miyu.relics;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import static Miyu.DefaultMod.makeRelicOutlinePath;
import static Miyu.DefaultMod.makeRelicPath;

public class Thermos extends CustomRelic {

	// ID, images, text.
	public static final String ID = DefaultMod.makeID("Thermos");
	private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Thermos.png"));
	private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("Thermos.png"));
	public AbstractCard.CardColor color;

	public Thermos() {
		super(ID, IMG, OUTLINE, AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.MAGICAL);
	}

	// Flash at the start of Battle.
	@Override
	public void atBattleStartPreDraw() {
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(2));
		flash();

	}
	@Override
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new Thermos();
	}
	// Description
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
}
