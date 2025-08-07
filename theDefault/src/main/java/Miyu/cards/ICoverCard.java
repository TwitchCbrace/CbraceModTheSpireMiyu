package Miyu.cards;

import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public interface ICoverCard {
	int getCoverValue();
	void triggerOnCovered(AbstractPlayer p);

	default void triggerOnExhaust() {
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new TrashPower(p, p, 3), 3));
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Rock(), 1));
	}
}