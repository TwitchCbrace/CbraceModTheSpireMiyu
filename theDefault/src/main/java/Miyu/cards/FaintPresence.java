package Miyu.cards;

import Miyu.DefaultMod;
import Miyu.powers.Pebble;
import Miyu.powers.PresencePower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Miyu.DefaultMod.makeCardPath;

public class FaintPresence extends AbstractDynamicCard {

	// TEXT DECLARATION

	public static final String ID = DefaultMod.makeID(FaintPresence.class.getSimpleName());
	public static final String IMG = makeCardPath("FaintPresence.png");

	// /TEXT DECLARATION/

	// STAT DECLARATION

	private static final CardRarity RARITY = CardRarity.SPECIAL;
	private static final CardTarget TARGET = CardTarget.SELF;
	private static final CardType TYPE = CardType.SKILL;
	public static final CardColor COLOR = CardColor.COLORLESS;

	private static final int COST = 0;
	private static final int MAGIC = 0;

	private static final int UPGRADED_MAGIC = 10;

	// /STAT DECLARATION/

	public FaintPresence() {
		super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
		this.baseMagicNumber = this.magicNumber = 0;
		this.exhaust = true;
		this.isEthereal = true;
	}
	public void setFP(int amount) {
		this.baseMagicNumber = this.magicNumber = amount;
		if (this.upgraded) {
			this.baseMagicNumber += this.UPGRADED_MAGIC;
		}
	}
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (AbstractDungeon.miscRng.random(0, 100) <= Math.abs(baseMagicNumber)) {
			this.addToBot(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
			this.addToBot(new SkipEnemiesTurnAction());
			this.addToBot(new PressEndTurnButtonAction());
			if (p.hasPower(PresencePower.POWER_ID)) {
				this.addToBot(
						new ApplyPowerAction(p, p, new PresencePower(p, p, -p.getPower(PresencePower.POWER_ID).amount),
								-p.getPower(PresencePower.POWER_ID).amount));
			}
		}
	}

	@Override
	public void upgrade() {
		if (!upgraded) {
			upgradeName();
			upgradeMagicNumber(UPGRADED_MAGIC);
			initializeDescription();
		}
	}
}