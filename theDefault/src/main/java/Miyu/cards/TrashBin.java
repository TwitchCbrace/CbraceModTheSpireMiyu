package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;

/*
Cover trigger가 있는 카드 클래스는 ICoverCard를 implement하도록 한다
by josh
 */

public class TrashBin extends AbstractDynamicCard implements ICoverCard {


//     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(TrashBin.class.getSimpleName());
    public static final String IMG = makeCardPath("Trashbox.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;



    private static final int COST = -2;
    private static final int COVER = 4;
    private static final int UPGRADE_PLUS_COVER = 3;

    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    // /STAT DECLARATION/


    public TrashBin() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
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
        this.addToBot(new ApplyPowerAction(p, p, new TrashPower(p, p, baseMagicNumber)));
    }


    public AbstractCard makeCopy() {
        return new TrashBin();
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
