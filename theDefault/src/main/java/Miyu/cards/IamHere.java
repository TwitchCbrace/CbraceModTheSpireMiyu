package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static Miyu.DefaultMod.makeCardPath;

public class IamHere extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(IamHere.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("IamHere.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.RARE; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;  // COST = 2
    private static final int DAMAGE = 30;    // DAMAGE = 10
    private static final int UPGRADE_PLUS_DMG = 10;  // UPGRADE_PLUS_DMG = 4
        // /STAT DECLARATION/


    public IamHere() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;
    }
    @Override
    public void triggerWhenDrawn(){
        if (AbstractDungeon.player.hasPower(SelfEsteem.POWER_ID)) {
            if (AbstractDungeon.player.getPower(SelfEsteem.POWER_ID).amount < 0) {
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(this));
            }
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SelfEsteem(p, p, -3)));

    }

    public AbstractCard makeCopy() {
        return new IamHere();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DMG);
                initializeDescription();
            }
        }
    }
