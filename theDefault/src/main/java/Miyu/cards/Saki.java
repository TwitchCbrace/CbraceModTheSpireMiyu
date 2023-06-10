package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

import java.util.Iterator;

import com.evacipated.cardcrawl.mod.stslib.powers.StunMonsterPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;

public class Saki
    extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Saki.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;

    public static final String IMG = makeCardPath("Saki.png");// "public static final String IMG =
                                                              // makeCardPath("Inevitably.png");

    private static final CardRarity RARITY = CardRarity.RARE; // Up to you, I like auto-complete on these

    private static final CardTarget TARGET = CardTarget.ALL_ENEMY; // since they don't change much.

    private static final CardType TYPE = CardType.SKILL; //

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 8; // COST = 1

    private static final int UPGRADED_COST = 5; // UPGRADED_COST = 1

    private static final int RANGE = 0;
    // /STAT DECLARATION/

    public Saki() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseRangeMagicNumber = rangeMagicNumber = RANGE;
        this.exhaust = true;

    }

    @Override
    public void triggerWhenDrawn() {
        int p = 0;
        this.updateCost(this.cost);
        p = AbstractDungeon.player.hand.size();
        this.baseRangeMagicNumber = p + 1;
        this.rangeMagicNumber = p + 1;
        isRangeMagicNumberModified = true;
        this.updateCost(-p - 1);
        if (this.cost < 0) {
            this.cost = 0;
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while (var3.hasNext()) {
            AbstractMonster mo = (AbstractMonster) var3.next();
            addToBot(new ApplyPowerAction(mo, p, new StunMonsterPower(mo)));
        }

    }

    public AbstractCard makeCopy() {
        return new Saki();
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
