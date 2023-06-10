package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;

public class RabbitHole
    extends AbstractDynamicCard
    implements ICoverCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(RabbitHole.class.getSimpleName());

    public static final String IMG = makeCardPath("RabbitHole.png");

    // /TEXT DECLARATION/
    // STAT DECLARATION
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardType TYPE = CardType.SKILL;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = -2;

    private static final int COVER = 8;

    private static final int UPGRADE_PLUS_COVER = 4;

    private static final int MAGIC = 2;

    private static final int UPGRADE_PLUS_MAGIC = 1;
    // /STAT DECLARATION/

    public RabbitHole() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
    }

    public void triggerOnCovered(AbstractPlayer p) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(magicNumber));
        AbstractDungeon.actionManager.addToBottom(new PutOnDeckAction(p, p, 1, false));
    }

    public void triggerOnGlowCheck() {
        Covered covered = (Covered) AbstractDungeon.player.getPower("Miyu:Covered");

        if (covered != null && covered.sourceCover == this) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
        else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public AbstractCard makeCopy() {
        return new RabbitHole();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.isCoverMagicNumberModified = true;
            this.upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            this.isMagicNumberModified = true;
            initializeDescription();
        }
    }
}
