package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.HobbyActivityPower;
import Miyu.powers.Pebble;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class HobbyActivity extends AbstractDynamicCard {

	// TEXT DECLARATION
	public static final String ID = DefaultMod.makeID(HobbyActivity.class.getSimpleName());
	public static final String IMG = makeCardPath("HobbyActivity.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	// /TEXT DECLARATION/

	// STAT DECLARATION
	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.POWER;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	// /STAT DECLARATION/
	public HobbyActivity() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.cardsToPreview = new Rock();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		Pebble pebble = (Pebble) AbstractDungeon.player.getPower(Pebble.POWER_ID);

		// 현재 취미 생활 파워를 가지고 있는지 확인
		HobbyActivityPower hobbyActivity = (HobbyActivityPower) p.getPower(HobbyActivityPower.POWER_ID);

		if (hobbyActivity != null) {
			// 취미 생활 파워를 가지고 있으면 배수를 +1 해줌 (ex: 조약돌의 1배 -> 조약돌의 2배 -> 조약돌의 3배)
			hobbyActivity.stackPower(1);
			hobbyActivity.update(1);
		} else {
			// 취미 생활 파웍가 없으면 파워 적용
			addToBot(new ApplyPowerAction(p, p, new HobbyActivityPower(p, m), 1));
		}
	}

	public AbstractCard makeCopy() {
		return new HobbyActivity();
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADED_COST);
			initializeDescription();
		}
	}
}
