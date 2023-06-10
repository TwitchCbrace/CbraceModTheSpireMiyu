package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.Covered;

public class ConvenienceStore
    extends AbstractDynamicCard
    implements ICoverCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(ConvenienceStore.class.getSimpleName());

    public static final String IMG = makeCardPath("ConvenienceStore.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;

    private static final CardTarget TARGET = CardTarget.ENEMY;

    private static final CardType TYPE = CardType.ATTACK;

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int MAGIC = 1;

    private static final int DAMAGE = 6;

    private static final int UPGRADE_PLUS_DAMAGE = 2;

    private static final int COVER = 5;

    private static final int UPGRADE_PLUS_COVER = 3;

    // /STAT DECLARATION/

    public ConvenienceStore() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
        this.baseDamage = this.damage = DAMAGE;
        selfRetain = true;

    }

    public void triggerOnCovered(AbstractPlayer p) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Miyu:Covered"));
        AbstractDungeon.actionManager.addToBottom(
            new ApplyPowerAction(p, p, new Covered(p, p, this.baseCoverMagicNumber, this), this.baseCoverMagicNumber));
        this.baseMagicNumber++;
        this.magicNumber++;
        isMagicNumberModified = true;
        this.initializeDescription();
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
    // public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    // return false;
    // }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.baseMagicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
                new DamageInfo(p, this.damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH, true));
        }
    }

    public AbstractCard makeCopy() {
        return new ConvenienceStore();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            isDamageModified = true;
            isCoverMagicNumberModified = true;
            initializeDescription();
        }
    }
}
