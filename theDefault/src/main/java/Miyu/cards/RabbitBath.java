package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

public class RabbitBath extends AbstractDynamicCard implements ICoverCard {

    public static final String ID = DefaultMod.makeID(RabbitBath.class.getSimpleName());
    public static final String IMG = makeCardPath("RabbitBath.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;



    private static final int COST = -2;
    private static final int COVER = 8;
    private static final int UPGRADE_PLUS_COVER = 4;

    private static final int MAGIC = 6;
    private static final int UPGRADE_PLUS_MAGIC = 4;
    // /STAT DECLARATION/


    public RabbitBath() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
        selfRetain = true;
        this.tags.add(CardTags.HEALING);
    }
    public void triggerOnCovered(AbstractPlayer p) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
            new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber)
        );
        int a = 0;
        if (this.baseMagicNumber > 6)
        {
            a = 6;
        }
        else {
            a = this.baseMagicNumber;
        }
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, a));
        this.baseMagicNumber -= 6;
        this.magicNumber -= 6;
        if (this.baseMagicNumber <= 0) {
            this.baseMagicNumber = 0;
        }
        if (this.magicNumber <= 0) {
            this.magicNumber = 0;
        }
        isMagicNumberModified = true;
    }


    public void triggerOnGlowCheck() {
        Covered covered =
                (Covered)AbstractDungeon.player.getPower("Miyu:Covered");

        if (covered != null && covered.sourceCover == this) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            stopGlowing();
        }
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

//     Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public AbstractCard makeCopy() {
        return new RabbitBath();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.isMagicNumberModified = true;
            this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            this.isCoverMagicNumberModified = true;
            initializeDescription();
        }
    }
}
