package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class ClaymoreMine
    extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(ClaymoreMine.class.getSimpleName()); // USE THIS ONE FOR THE
                                                                                           // TEMPLATE;

    public static final String IMG = makeCardPath("CleymoreMine.png");// "public static final String IMG =
                                                                      // makeCardPath("Strike.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE;

    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    private static final CardType TYPE = CardType.ATTACK;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;

    private static final int DAMAGE = 4;

    private static final int UPGRADE_PLUS_DMG = 2;

    // /STAT DECLARATION/
    private static final int MAGIC = 0;

    private static final int RANGE = 0;

    public ClaymoreMine() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseRangeMagicNumber = rangeMagicNumber = RANGE;
        this.isMultiDamage = true;

    }

    @Override
    public void triggerWhenDrawn() {
        int p = 0;
        p = AbstractDungeon.player.hand.size();
        this.baseRangeMagicNumber = p + 1;
        this.rangeMagicNumber = p + 1;
        isRangeMagicNumberModified = true;
    }

    public void applyPowers() {
        this.isMagicNumberModified = true;
        super.applyPowers();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        super.calculateCardDamage(mo);
        this.baseDamage = realBaseDamage;
        this.isDamageModified = this.damage != this.baseDamage;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = this.rangeMagicNumber;
        this.calculateCardDamage(m);
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, damageTypeForTurn,
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
            // new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
            // AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ClaymoreMine();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}
