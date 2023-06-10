package Miyu.cards;

import com.megacrit.cardcrawl.cards.DamageInfo;

public interface OnReduceCover {
	void CoverReduced(DamageInfo info, int amount);
}
