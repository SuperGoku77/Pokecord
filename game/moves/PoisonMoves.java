package com.calculusmaster.pokecord.game.moves;

import com.calculusmaster.pokecord.game.duel.Duel;
import com.calculusmaster.pokecord.game.Move;
import com.calculusmaster.pokecord.game.Pokemon;
import com.calculusmaster.pokecord.game.duel.DuelHelper;
import com.calculusmaster.pokecord.game.enums.elements.Stat;
import com.calculusmaster.pokecord.game.enums.elements.StatusCondition;

import java.util.Random;

public class PoisonMoves
{
    public String Toxic(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        opponent.addStatusCondition(StatusCondition.BADLY_POISONED);
        duel.data(opponent.getUUID()).badlyPoisonedTurns++;

        return opponent.getName() + " was badly poisoned!";
    }

    public String Venoshock(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int damage = move.getDamage(user, opponent);

        if(opponent.hasStatusCondition(StatusCondition.POISONED)) damage *= 2;

        opponent.damage(damage);

        return move.getDamageResult(opponent, damage);
    }

    public String PoisonPowder(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        opponent.addStatusCondition(StatusCondition.POISONED);
        return opponent.getName() + " is poisoned";
    }

    public String ToxicSpikes(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        duel.hazardData(opponent.getUUID()).addHazard(DuelHelper.EntryHazard.TOXIC_SPIKES);
        return user.getName() + " laid a Toxic Spikes trap!";
    }

    public String PoisonJab(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int damage = move.getDamage(user, opponent);
        opponent.damage(damage);

        if(new Random().nextInt(100) < 30)
        {
            opponent.addStatusCondition(StatusCondition.POISONED);

            return move.getDamageResult(opponent, damage) + " " + opponent.getName() + " was poisoned!";
        }

        return move.getDamageResult(opponent, damage);
    }

    public String AcidArmor(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        user.changeStatMultiplier(Stat.DEF, 2);
        return user.getName() + "'s Defense rose by 2 stages!";
    }
}
