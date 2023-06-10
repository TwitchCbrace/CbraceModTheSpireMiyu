package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.FoundItAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
        // 카드를 baseMagicNumber만큼 뽑고, 엄폐중인 카드를 뽑았을 때의 후처리를 위해 FoundItAction을 FollowAction으로 넣어준다.
        addToBot(new DrawCardAction(baseMagicNumber, new FoundItAction(p, secondMagicNumber)));

        // 카드 1장을 덱 맨 위로 올리는 액션
        this.addToBot(new PutOnDeckAction(p, p, 1, false));
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

    @Override
    public AbstractCard makeCopy() {
        return new FoundIt();
    }
}
