package Miyu.actions;

import Miyu.cards.AbstractDefaultCard;
import Miyu.cards.ICoverCard;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class TriggerAction extends AbstractGameAction {

	private AbstractPlayer p;

	public TriggerAction() {
		this.actionType = ActionType.WAIT;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_XFAST;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_XFAST && this.p.hasPower(SelfEsteem.POWER_ID)) {
			int selfAmt = this.p.getPower(SelfEsteem.POWER_ID).amount;
			this.addToTop(new ApplyPowerAction(this.p, this.p, new SelfEsteem(this.p, this.p, selfAmt), selfAmt));
		}

		this.tickDuration();
	}
}
