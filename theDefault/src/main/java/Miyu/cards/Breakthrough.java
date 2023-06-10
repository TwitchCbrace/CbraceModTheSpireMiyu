package Miyu.cards;

import static Miyu.DefaultMod.makeCardPath;

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

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.HandSizeUp;

public class Breakthrough
    extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Breakthrough.class.getSimpleName()); // USE THIS ONE FOR THE
                                                                                           // TEMPLATE;

    public static final String IMG = makeCardPath("Breakthrough.png");// "public static final String IMG =
                                                                      // makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to
    // run correctly.

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; // Up to you, I like auto-complete on these

    private static final CardTarget TARGET = CardTarget.ENEMY; // since they don't change much.

    private static final CardType TYPE = CardType.ATTACK; //

    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int DAMAGE = 9;

    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int MAGIC = 1;

    private static final int UPGRADE_PLUS_MAGIC = 1;

    private static final int RANGE = 0;

    // /STAT DECLARATION/

    public Breakthrough() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }

    public void applyPowers() {
        super.applyPowers();
    }

    // public void calculateCardDamage(AbstractMonster mo) {
    // int realBaseDamage = this.baseDamage;
    // this.baseDamage -= this.rangeMagicNumber;
    // super.calculateCardDamage(mo);
    // this.baseDamage = realBaseDamage;
    // this.isDamageModified = this.damage != this.baseDamage;
    // }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
            AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));

        AbstractDungeon.actionManager
            .addToBottom(new ApplyPowerAction(p, p, new HandSizeUp(p, m, magicNumber), magicNumber));
    }

    public AbstractCard makeCopy() {
        return new Breakthrough();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            isDamageModified = true;
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            isMagicNumberModified = true;
            initializeDescription();
        }
    }
}
