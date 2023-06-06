package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Iterator;

import static Miyu.DefaultMod.makeCardPath;

public class Urban_S extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Urban_S.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Urban_S.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 4;  // COST = 2
    private static final int UPGRADED_COST = 3; // UPGRADED_COST = 2

    private static final int DAMAGE = 5;    // DAMAGE = 10
    private static final int UPGRADE_PLUS_DMG = 2;  // UPGRADE_PLUS_DMG = 4

    // /STAT DECLARATION/


    public Urban_S() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = DAMAGE;

    }


    public void applyPowers() {
        int count = 0;
        Iterator var2 = AbstractDungeon.player.hand.group.iterator();

        while(var2.hasNext()) {
            AbstractCard c = (AbstractCard)var2.next();

            if (c instanceof ICoverCard) {
                ++count;
            }
        }
        this.setCostForTurn(this.cost - count);
        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.calculateCardDamage(m);
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
    }



    public AbstractCard makeCopy() {
        return new Urban_S();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DMG);
                upgradeBaseCost(UPGRADED_COST);
                initializeDescription();
            }
        }
    }
