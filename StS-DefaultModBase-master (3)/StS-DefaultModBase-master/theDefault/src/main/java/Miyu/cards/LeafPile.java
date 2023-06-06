package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Miyu.DefaultMod.makeCardPath;


public class LeafPile extends AbstractDynamicCard implements ICoverCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(LeafPile.class.getSimpleName());
    public static final String IMG = makeCardPath("LeafPile.png");

    // /TEXT DECLARATION/
    // STAT DECLARATION
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int COVER = 8;
    private static final int UPGRADE_PLUS_COVER = 3;

    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    // /STAT DECLARATION/


    public LeafPile() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = BLOCK;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
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

//     Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
    }

    public void triggerOnExhaust(){
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new TrashPower(p, p, baseCoverMagicNumber)));
    }


    public AbstractCard makeCopy() {
        return new LeafPile();
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.isCoverMagicNumberModified = true;
            this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            this.isMagicNumberModified = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
