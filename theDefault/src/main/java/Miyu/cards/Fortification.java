package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.CoverIncreaseAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Fortification
    extends AbstractDynamicCard {

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Defend Gain 5 (8) block.
     */

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Fortification.class.getSimpleName());

    public static final String IMG = makeCardPath("Fortification.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;

    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardType TYPE = CardType.SKILL;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;

    // private static final int BLOCK = 8;
    // private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC = 30;

    private static final int UPGRADE_MAGIC = 10;

    // /STAT DECLARATION/

    public Fortification() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new CoverIncreaseAction(p, 1, magicNumber));

    }

    public AbstractCard makeCopy() {
        return new Fortification();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
            upgradeMagicNumber(UPGRADE_MAGIC);
        }
    }
}
