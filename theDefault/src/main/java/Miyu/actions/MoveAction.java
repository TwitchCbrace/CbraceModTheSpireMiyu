package Miyu.actions;

import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class MoveAction extends AbstractGameAction {
	private static final float DURATION = Settings.ACTION_DUR_XFAST;
	private AbstractPlayer p;
	private AbstractCard c;
	private ArrayList<AbstractCard.CardTags> keyword = new ArrayList();

	public MoveAction(AbstractPlayer player, AbstractCard card) {
		this.p = player;
		this.c = card;
		this.duration = DURATION;
	}

	@Override
	public void update() {
		((ICoverCard) c).triggerOnCovered(p);
		this.isDone = true;
	}
}