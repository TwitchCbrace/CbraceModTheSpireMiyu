package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.PresencePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class AppropriateDistance extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(AppropriateDistance.class.getSimpleName());
	public static final String IMG = makeCardPath("AppropriateDistance.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

	private static final CardRarity RARITY = CardRarity.COMMON;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int RANGE = 0;

	public AppropriateDistance() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseBlock = 0;
		this.baseMagicNumber = this.magicNumber = 0;
		this.baseRangeMagicNumber = rangeMagicNumber = RANGE;
	}

	@Override
	public void triggerWhenDrawn() {
		int p = AbstractDungeon.player.hand.size();
		this.baseRangeMagicNumber = p + 1;
		this.rangeMagicNumber = p + 1;
		isRangeMagicNumberModified = true;
	}

	@Override
	public void applyPowers() {
		this.baseBlock = this.rangeMagicNumber;
		this.baseMagicNumber = this.magicNumber = this.rangeMagicNumber;
		super.applyPowers();
		initializeDescription();
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p, new PresencePower(p, p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new AppropriateDistance();
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeBlock(3);
			initializeDescription();
		}
	}
}