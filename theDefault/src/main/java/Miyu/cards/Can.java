package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

import static Miyu.DefaultMod.makeCardPath;

public class Can extends AbstractDynamicCard {

    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID(Can.class.getSimpleName());
    public static final String IMG = makeCardPath("Can.png");

    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int UPGRADE_COST = 0;

    // /STAT DECLARATION/
    public Can() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        TrashPower trashPower = (TrashPower) AbstractDungeon.player.getPower("Miyu:TrashPower");

        if (trashPower != null && trashPower.amount > 0) {
            AbstractDungeon.actionManager.addToTop(
                    new ApplyPowerAction(p, p,
                            new PlatedArmorPower(p, trashPower.amount)
                    )
            );
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        TrashPower trashPower = (TrashPower) AbstractDungeon.player.getPower("Miyu:TrashPower");

        if (trashPower != null && trashPower.amount > 0) {
            beginGlowing();
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }


    public AbstractCard makeCopy() {
        return new Can();
    }

}
