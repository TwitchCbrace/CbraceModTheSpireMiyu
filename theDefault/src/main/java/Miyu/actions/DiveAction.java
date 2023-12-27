package Miyu.actions;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateHopAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class DiveAction extends AbstractGameAction {
	public int[] multiDamage;
	private boolean freeToPlayOnce = false;
	private AbstractPlayer p;
	private AbstractCard c;
	private int energyOnUse = -1;

	public DiveAction(AbstractPlayer p, int amount, boolean freeToPlayOnce, int energyOnUse, AbstractCard c) {
		this.amount = amount;
		this.p = p;
		this.c = c;
		this.freeToPlayOnce = freeToPlayOnce;
		this.duration = Settings.ACTION_DUR_XFAST;
		this.actionType = ActionType.SPECIAL;
		this.energyOnUse = energyOnUse;
	}

	public void update() {
		int effect = EnergyPanel.totalCount;
		if (this.energyOnUse != -1) {
			effect = this.energyOnUse;
		}

		if (this.p.hasRelic("Chemical X")) {
			effect += 2;
			this.p.getRelic("Chemical X").flash();
		}

		if (effect > 0) {
			for (int i = 0; i < effect; ++i) {
				// 엄폐물 증가.
				((AbstractDefaultCard) c).baseCoverMagicNumber += amount;
				c.superFlash();

			}

			if (!this.freeToPlayOnce) {
				this.p.energy.use(EnergyPanel.totalCount);
			}
		}
		this.isDone = true;
	}
}
