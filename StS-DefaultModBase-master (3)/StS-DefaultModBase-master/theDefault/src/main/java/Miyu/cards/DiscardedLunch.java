package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.characters.TheDefault;
import Miyu.powers.CleanUpTrashPower;
import Miyu.powers.Pebble;
import Miyu.powers.TrashPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.ReinforcedBody;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static Miyu.DefaultMod.makeCardPath;

public class DiscardedLunch extends AbstractDynamicCard {

    // TEXT DECLARATION

    public static final String ID = DefaultMod.makeID(DiscardedLunch.class.getSimpleName());
    public static final String IMG = makeCardPath("DiscardedLunch.png");

    // /TEXT DECLARATION/

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int COVER = 1;
    private static final int UPGRADE_PLUS_COVER = 1;


    // /STAT DECLARATION/


    public DiscardedLunch() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseCoverMagicNumber = this.coverMagicNumber = COVER;
        this.exhaust = true;
        this.isEthereal = true;
        this.tags.add(CardTags.HEALING);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new HealAction(p, p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new TrashPower(p, p, coverMagicNumber), coverMagicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
                new VigorPower(p, coverMagicNumber), coverMagicNumber));

    }

    public AbstractCard makeCopy() {
        return new DiscardedLunch();
    }
    public void applyPowers() {
    }

    public void calculateCardDamage(AbstractMonster mo) {
    }
    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeCoverMagicNumber(UPGRADE_PLUS_COVER);
            initializeDescription();
        }
    }
}
