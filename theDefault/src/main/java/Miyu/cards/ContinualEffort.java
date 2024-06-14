package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.ContinualEffortPower;
import Miyu.powers.GetNegativeDelusions;
import Miyu.powers.PresencePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import java.util.Iterator;

import static Miyu.DefaultMod.makeCardPath;

public class ContinualEffort extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(ContinualEffort.class.getSimpleName());
	public static final String IMG = makeCardPath("ContinualEffort.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int MAGIC = 6;
	private static final int UPGRADE_PLUS_MAGIC = 4;

	public ContinualEffort() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = magicNumber = MAGIC;
		this.cardsToPreview = new NegativeDelusions();
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new ContinualEffortPower(p, p, magicNumber), magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GetNegativeDelusions(p, p, 1), 1));
		if (p.hasPower(PresencePower.POWER_ID)) {
			if (p.getPower(PresencePower.POWER_ID).amount <= -20) {
				if (CanEscape()) {
					if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
						AbstractDungeon.getCurrRoom().smoked = true;
						AbstractDungeon.player.hideHealthBar();
						AbstractDungeon.player.isEscaping = true;
						AbstractDungeon.player.flipHorizontal = !AbstractDungeon.player.flipHorizontal;
						AbstractDungeon.overlayMenu.endTurnButton.disable();
						AbstractDungeon.player.escapeTimer = 2.5F;
					}
				}
			}
		}
	}

	private boolean CanEscape() {
		Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

		AbstractMonster m;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			m = (AbstractMonster) var1.next();
			if (m.hasPower("BackAttack")) {
				return false;
			}
		} while (m.type != AbstractMonster.EnemyType.BOSS);

		return false;
	}

	public AbstractCard makeCopy() {
		return new ContinualEffort();
	}
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			initializeDescription();
		}
	}
}
