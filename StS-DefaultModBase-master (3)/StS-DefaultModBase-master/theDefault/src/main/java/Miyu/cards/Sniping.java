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

import static Miyu.DefaultMod.makeCardPath;

public class Sniping extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Sniping.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Sniping.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;  // COST = 2
    private static final int DAMAGE = 12;    // DAMAGE = 10
    private static final int UPGRADE_PLUS_DMG = 4;  // UPGRADE_PLUS_DMG = 4

    private static final int MAGIC = 0;
    private static final int RANGE = 0;

    // /STAT DECLARATION/


    public Sniping() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
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
            this.baseMagicNumber = rangeMagicNumber / 4;
        } else {
            this.baseMagicNumber = rangeMagicNumber / 5;
        }
//        if (this.baseMagicNumber != 0) {
//            this.isMagicNumberModified = true;
//        }

    }
    public void triggerOnGlowCheck() {
        if (this.baseMagicNumber != 0 && canPlay(this)) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public void applyPowers() {
        int realBaseDamage = this.baseDamage;
        int realBaseMagicNumber = this.baseMagicNumber;

        for (int i = baseMagicNumber; i > 0; i--)
        {
            this.baseDamage *= 2;
        }
        super.applyPowers();
//        isDamageModified = true;
        initializeDescription();
        this.baseDamage = realBaseDamage;
        this.baseMagicNumber = realBaseMagicNumber;
        isDamageModified = true;
    }
    public void calculateCardDamage(AbstractMonster mo) {
        int realBaseDamage = this.baseDamage;
        int realBaseMagicNumber = this.baseMagicNumber;

        for (int i = baseMagicNumber; i > 0; i--)
        {
            this.baseDamage *= 2;
        }
        super.calculateCardDamage(mo);
//        isDamageModified = true;
        initializeDescription();
        this.baseDamage = realBaseDamage;
        this.baseMagicNumber = realBaseMagicNumber;
        isDamageModified = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(
        new DamageAction(m, new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH, true));
    }



    public AbstractCard makeCopy() {
        return new Sniping();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DMG);
                rawDescription = UPGRADE_DESCRIPTION;
                initializeDescription();
            }
        }
    }
