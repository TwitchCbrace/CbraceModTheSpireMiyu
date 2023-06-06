package Miyu.variables;

import Miyu.cards.AbstractDefaultCard;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static Miyu.DefaultMod.makeID;

public class MiyuCoverMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("C");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractDefaultCard) card).isCoverMagicNumberModified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractDefaultCard) card).coverMagicNumber;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractDefaultCard) card).baseCoverMagicNumber;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractDefaultCard) card).upgradedCoverMagicNumber;
    }
}