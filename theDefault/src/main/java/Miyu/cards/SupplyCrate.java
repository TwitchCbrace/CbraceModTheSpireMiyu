package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static Miyu.DefaultMod.makeCardPath;

public class SupplyCrate extends AbstractDynamicCard implements ICoverCard {


    public static final String ID = DefaultMod.makeID(SupplyCrate.class.getSimpleName());
    public static final String IMG = makeCardPath("SupplyCrate.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;



    private static final int COST = -2;
    private static final int COVER = 10;
    private static final int UPGRADE_PLUS_COVER = 5;

    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    // /STAT DECLARATION/


    public SupplyCrate() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        selfRetain = true;
    }
    public void triggerOnCovered(AbstractPlayer p) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber)
        );

    }

    public void triggerOnGlowCheck() {
        Covered covered =
                (Covered)AbstractDungeon.player.getPower("Miyu:Covered");

        if (covered != null && covered.sourceCover == this) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

//     Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void triggerOnExhaust(){
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy() {
        return new SupplyCrate();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            this.isCoverMagicNumberModified = true;
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.isMagicNumberModified = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
