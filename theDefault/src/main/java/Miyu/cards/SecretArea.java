package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Comparator;

import static Miyu.DefaultMod.makeCardPath;

public class SecretArea extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(SecretArea.class.getSimpleName());
	public static final String IMG = makeCardPath("SecretArea.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.UNCOMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int MAGIC = 0;
	private static final int UPGRADE_PLUS_MAGIC = 3;

	// /STAT DECLARATION/

	public SecretArea() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = MAGIC;
	}

	@Override
	public void triggerWhenOtherCardExhausted() {
		// 다른 카드가 소멸될 때 방어도 계산. 그 카드가 아직 소멸되기 전이므로 +1 보정을 해줘야함
		updateBlockAndDescription(1);
	}

	@Override
	public void triggerWhenDrawn() {
		// 카드가 드로우될 때 방어도 계산. (이걸 안해주면 첫 턴에 드로우 됐을 때 방어도가 제대로 안나옴)
		updateBlockAndDescription(0);
	}

	@Override
	public void triggerWhenCopied() {
		// 카드가 복사될 때 방어도 계산. (이걸 안해주면 임의로 추가하거나 했을 때 방어도가 제대로 안나옴)
		updateBlockAndDescription(0);
	}

	private void updateBlockAndDescription(int correction) {
		this.magicNumber = AbstractDungeon.player.exhaustPile.size() + correction + this.baseMagicNumber;
		if (upgraded) {
			this.magicNumber += 3;
		}
		isMagicNumberModified = true;
		initializeDescription();
	}

	// Actions the card should do.
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		// 플레이어의 소멸 카드 덱
		ArrayList<AbstractCard> exhaustPile = p.exhaustPile.group;

		// 소멸 덱에서 엄폐물이 제일 높은 카드를 찾는다.
		AbstractCard c = exhaustPile.stream().filter((card) -> card instanceof ICoverCard)
				.max(Comparator.comparingInt(card -> ((AbstractDynamicCard) card).getCoverMagicNumber())).orElse(null);

		// 엄폐물이 존재하면 해당 카드로 이동
		if (c != null && ((AbstractDynamicCard) c).getCoverMagicNumber() > 0) {
			// addToBot(new ApplyPowerAction(p, p,
			// new Covered(p, p, ((AbstractDynamicCard) c).getCoverMagicNumber(), ((AbstractDynamicCard) c)),
			// ((AbstractDynamicCard) c).getCoverMagicNumber()));
			// 이동이 두 번 작동해서 주석처리함 cbrace
			((ICoverCard) c).triggerOnCovered(p);
		}

		// 방어도 얻음
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.magicNumber));
	}

	// Upgraded stats.
	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
			rawDescription = UPGRADE_DESCRIPTION;
			isMagicNumberModified = true;
			initializeDescription();
		}
	}
}
