package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Miyu.DefaultMod.makeCardPath;

public class LegShot extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LegShot.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("LegShot.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;  // COST = 2
    private static final int DAMAGE = 13;    // DAMAGE = 10
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;
    private static final int SECOND_MAGIC = 0;

    private static final int RANGE = 0;

    // /STAT DECLARATION/


    public LegShot() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseSecondMagicNumber = this.secondMagicNumber = SECOND_MAGIC;
        this.baseDamage = DAMAGE;
        this.baseRangeMagicNumber = rangeMagicNumber = RANGE;

    }

    @Override
    public void triggerWhenDrawn(){
        int p = 0;
        p = AbstractDungeon.player.hand.size();
        this.baseRangeMagicNumber = p + 1;
        this.rangeMagicNumber = p + 1;
        isRangeMagicNumberModified = true;
        if (this.upgraded) {
            this.baseSecondMagicNumber = rangeMagicNumber / 3;
        } else {
            this.baseSecondMagicNumber = rangeMagicNumber / 4;
        }

    }
    public void triggerOnGlowCheck() {
        if (this.baseSecondMagicNumber != 0 && canPlay(this)) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public void applyPowers() {
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(
        new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH, true));
        this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.baseMagicNumber, false), this.baseMagicNumber));
        if (baseSecondMagicNumber != 0) {
            this.addToBot(new ApplyPowerAction(m, p, new WeakPower(m, this.baseSecondMagicNumber, false), this.baseSecondMagicNumber));
        }
    }



    public AbstractCard makeCopy() {
        return new LegShot();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeMagicNumber(UPGRADE_MAGIC);
                initializeDescription();
            }
        }
    }
