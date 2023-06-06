package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.CoverSelectAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class RunAndGun extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(RunAndGun.class.getSimpleName());
    public static final String IMG = makeCardPath("RunAndGun.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 4;

    private static final int UPGRADE_PLUS_DAMAGE = 2;


    // /STAT DECLARATION/


    public RunAndGun() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        for (int i = 0; i <= 1; i++){
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SMASH, true)
            );
        }
        AbstractDungeon.actionManager.addToTop(new CoverSelectAction(p,1));

    }
    public AbstractCard makeCopy() { return new RunAndGun(); }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}
