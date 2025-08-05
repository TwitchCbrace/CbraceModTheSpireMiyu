package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.actions.MoveAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Specialist extends AbstractDynamicCard {

	public static final String ID = DefaultMod.makeID(Specialist.class.getSimpleName());
	public static final String IMG = makeCardPath("Specialist.png");

	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

	private static final CardRarity RARITY = CardRarity.RARE;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

	private static final int COST = 1;
	private static final int DRAW = 1;
	private static final int UPGRADE_PLUS_DRAW = 2;

	public Specialist() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = DRAW;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.addToBot(new DrawCardAction(p, this.magicNumber));

		for (AbstractCard c : p.hand.group) {
			if (c instanceof ICoverCard) {
				this.addToBot(new MoveAction(p, c));
			}
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Specialist();
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADE_PLUS_DRAW);
			initializeDescription();
		}
	}
}