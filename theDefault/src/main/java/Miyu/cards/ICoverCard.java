package Miyu.cards;

import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/*
    trigger가 존재한다는 약속으루다가 interface 하나 만들어둠.
    cover가 있는 카드는 ICoverCard를 implement해야함.
by josh
 */

public interface ICoverCard {
	int getCoverValue();
	void triggerOnCovered(AbstractPlayer p);
	void triggerOnExhaust();

}
