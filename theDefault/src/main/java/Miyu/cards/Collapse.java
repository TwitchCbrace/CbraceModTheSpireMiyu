package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

import java.util.Iterator;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.CollapsePower;

public class Collapse
    extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(Collapse.class.getSimpleName());

    public static final String IMG = makeCardPath("Collapse.png");

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;

    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardType TYPE = CardType.POWER;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int UPGRADED_COST = 0;

    private static final int MAGIC = 1;

    // /STAT DECLARATION/

    public Collapse() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = MAGIC;
    }

    // Actions the card should do.
    // @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean powerExists = false;
        Iterator var4 = p.powers.iterator();

        while (var4.hasNext()) {
            AbstractPower pow = (AbstractPower) var4.next();
            if (pow.ID.equals("Miyu:CollapsePower")) {
                powerExists = true;
                break;
            }
        }

        if (!powerExists) {
            this.addToBot(new ApplyPowerAction(p, p, new CollapsePower(p, p, 1)));
        }
    }

    public AbstractCard makeCopy() {
        return new Collapse();
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
