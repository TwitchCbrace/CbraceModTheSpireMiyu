package Miyu.relics;

import Miyu.DefaultMod;
import Miyu.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import Miyu.cards.ICoverCard;

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
		CardGroup drawPile = AbstractDungeon.player.drawPile;
		if (!drawPile.isEmpty()) {
			CardGroup coverCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			for (AbstractCard c : drawPile.group) {
				if (c instanceof ICoverCard) {
					coverCards.addToRandomSpot(c);
				}
			}
			addToTop(new FetchAction(drawPile, Predicate.isEqual(coverCards.getBottomCard()), 1));
		}
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
