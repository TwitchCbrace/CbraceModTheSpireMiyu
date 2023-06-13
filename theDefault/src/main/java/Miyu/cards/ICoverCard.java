package Miyu.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;

/*
    trigger가 존재한다는 약속으루다가 interface 하나 만들어둠.
    cover가 있는 카드는 ICoverCard를 implement해야함.
by josh
 */

public interface ICoverCard {
	void triggerOnCovered(AbstractPlayer p);
}
