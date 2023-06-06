package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.HandSizeUp;
import Miyu.powers.SelfEsteem;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import static Miyu.DefaultMod.makeCardPath;

public class Hyauu extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Hyauu.class.getSimpleName()); // USE THIS ONE FOR THE TEMPLATE;
    public static final String IMG = makeCardPath("Hyauu.png");// "public static final String IMG = makeCardPath("PebbleMagic.png");
    // This does mean that you will need to have an image with the same NAME as the card in your image folder for it to run correctly.
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON; //  Up to you, I like auto-complete on these
    private static final CardTarget TARGET = CardTarget.ENEMY;  //   since they don't change much.
    private static final CardType TYPE = CardType.ATTACK;       //
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 6;

    private static final int BLOCK = 0;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC = 0;

    // /STAT DECLARATION/


    public Hyauu() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseBlock = this.block = BLOCK;
    }

    public void applyPowers() {
        AbstractPower selfEsteem = AbstractDungeon.player.getPower(SelfEsteem.POWER_ID);
        if (selfEsteem != null && selfEsteem.amount < 0) {
            this.baseBlock = Math.abs(selfEsteem.amount);
        } else {
            this.baseBlock = 0;
        }
        super.applyPowers();
        this.initializeDescription();
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new WaitAction(0.1F));
        if (p != null && m != null) {
            this.addToBot(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5F));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    }



    public AbstractCard makeCopy() {
        return new Hyauu();
    }

    // Upgraded stats.
        @Override
        public void upgrade() {
            if (!upgraded) {
                upgradeName();
                upgradeDamage(UPGRADE_PLUS_DMG);
                isDamageModified = true;
                initializeDescription();
            }
        }
    }
