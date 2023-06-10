package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.BackTrackingAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class FoundIt extends AbstractDynamicCard {


    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(FoundIt.class.getSimpleName());
    public static final String IMG = makeCardPath("Dummy.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int DRAW = 2;
    private static final int UPGRADE_DRAW = 1;

    private static final int MAGIC = 5;
    private static final int UPGRADE_MAGIC = 3;


    // /STAT DECLARATION/

    public FoundIt() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = DRAW;
        this.baseSecondMagicNumber = MAGIC;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(baseMagicNumber));

        this.addToBot(new BackTrackingAction(p, p));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_DRAW);
            isMagicNumberModified = true;
            upgradeSecondMagicNumber(UPGRADE_MAGIC);
            isSecondMagicNumberModified = true;
            initializeDescription();
        }
    }
}
