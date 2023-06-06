package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.actions.NextTargetAction;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.unique.RitualDaggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Iterator;

import static Miyu.DefaultMod.makeCardPath;

public class NextTarget extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(NextTarget.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("NextTarget.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;  // COST = 2
    private static final int DAMAGE = 15;    // DAMAGE = 10
    private static final int UPGRADE_PLUS_DAMAGE = 5;
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    // /STAT DECLARATION/


    public NextTarget() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;

    }


    public void applyPowers() {
//        if (AbstractDungeon.player != null) {
//            if (AbstractDungeon.player.hasPower(VigorPower.POWER_ID)) {
//                this.damage = AbstractDungeon.player.getPower(VigorPower.POWER_ID).amount *= (this.magicNumber - 1);
//            }
//        }

        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        if (vigor != null) {
            vigor.amount *= this.magicNumber;
        }

        super.applyPowers();
        if (vigor != null) {
            vigor.amount /= this.magicNumber;
        }

//        super.applyPowers();
    }
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower vigorPower = AbstractDungeon.player.getPower("Vigor");
        if (vigorPower != null) {
            vigorPower.amount *= this.magicNumber;
        }

        super.calculateCardDamage(mo);
        if (vigorPower != null) {
            vigorPower.amount /= this.magicNumber;
        }

    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        this.addToBot(new NextTargetAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));


    }



    public AbstractCard makeCopy() {
        return new NextTarget();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DAMAGE);
                upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
                initializeDescription();
            }
        }
    }
