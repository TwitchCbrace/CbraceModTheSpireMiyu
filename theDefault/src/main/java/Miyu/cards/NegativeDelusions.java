package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.SelfEsteem;

public class NegativeDelusions
    extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(NegativeDelusions.class.getSimpleName());

    public static final String IMG = makeCardPath("NegativeDelusions.png");

    // /TEXT DECLARATION/
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    private static final CardTarget TARGET = CardTarget.SELF;

    private static final CardType TYPE = CardType.SKILL;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;

    private static final int MAGIC = 1;

    private static final int UPGRADE_PLUS_MAGIC = 1;

    // /STAT DECLARATION/

    public NegativeDelusions() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // 아무 효과 없음
    }

    public void triggerOnExhaust() {
        AbstractPlayer p = AbstractDungeon.player;
        this.addToBot(new ApplyPowerAction(p, p, new SelfEsteem(p, p, magicNumber)));
    }

    public void triggerOnEndOfTurnForPlayingCard() {

        if (AbstractDungeon.player.hasPower(SelfEsteem.POWER_ID)) {
            if (AbstractDungeon.player.getPower(SelfEsteem.POWER_ID).amount <= -1) {
                this.addToBot(new MakeTempCardInHandAction(this.makeStatEquivalentCopy(), 1, false));
            }
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            isMagicNumberModified = true;
            initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new NegativeDelusions();
    }
}
