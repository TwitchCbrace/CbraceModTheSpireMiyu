package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.powers.Pebble;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class Rock extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Rock.class.getSimpleName());
    public static final String IMG = makeCardPath("Rock.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;

    private static final int COST = 0;
    private static final int MAGIC = 1;

    // /STAT DECLARATION/

    public Rock() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 조약돌 1 얻기
        addToBot(new ApplyPowerAction(p, p, new Pebble(p, p, magicNumber), magicNumber));
        // 자존감 1 얻기
        addToBot(new ApplyPowerAction(p, p, new SelfEsteem(p, p, magicNumber), magicNumber));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            initializeDescription();
        }
    }
}
