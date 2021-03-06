package com.calculusmaster.pokecord.game.moves;

import com.calculusmaster.pokecord.game.duel.Duel;
import com.calculusmaster.pokecord.game.Move;
import com.calculusmaster.pokecord.game.Pokemon;
import com.calculusmaster.pokecord.game.duel.DuelHelper;
import com.calculusmaster.pokecord.game.enums.elements.Stat;

import java.util.Random;

public class GroundMoves
{
    public String Earthquake(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        if(duel.data(opponent.getUUID()).digUsed) move.setPower(2 * move.getPower());
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String EarthPower(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        boolean lower = new Random().nextInt(100) < 10;

        if(lower) opponent.changeStatMultiplier(Stat.SPDEF, -1);

        return Move.simpleDamageMove(user, opponent, duel, move) + (lower ? " " + opponent.getName() + "'s Special Defense was lowered by 1 stage!" : "");
    }

    public String Dig(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        if(duel.data(user.getUUID()).digUsed)
        {
            duel.data(user.getUUID()).digUsed = false;
            return Move.simpleDamageMove(user, opponent, duel, move);
        }
        else
        {
            duel.data(user.getUUID()).digUsed = true;
            return user.getName() + " burrowed underground!";
        }
    }

    public String Bulldoze(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        opponent.changeStatMultiplier(Stat.SPD, -1);

        return Move.simpleDamageMove(user, opponent, duel, move) + opponent.getName() + "'s Speed was lowered by 1 stage!";
    }

    public String Fissure(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        if(opponent.getLevel() > user.getLevel()) return move.getNoEffectResult(opponent);
        else
        {
            int damage = opponent.getHealth();
            opponent.damage(damage);

            return move.getDamageResult(opponent, damage);
        }
    }

    public String PrecipiceBlades(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String ThousandArrows(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        duel.data(opponent.getUUID()).isRaised = false;
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String ThousandWaves(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move) + " " + opponent.getName() + " cannot flee!";
    }

    public String Magnitude(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int r = new Random().nextInt(100);
        int magnitude;

        if(r < 5) magnitude = 4;
        else if(r < 5 + 10) magnitude = 5;
        else if(r < 5 + 10 + 20) magnitude = 6;
        else if(r < 5 + 10 + 20 + 30) magnitude = 7;
        else if(r < 5 + 10 + 20 + 30 + 20) magnitude = 8;
        else if(r < 5 + 10 + 20 + 30 + 20 + 10) magnitude = 9;
        else magnitude = 10;

        move.setPower(switch(magnitude) {
            case 4 -> 10;
            case 5 -> 30;
            case 6 -> 50;
            case 7 -> 70;
            case 8 -> 90;
            case 9 -> 110;
            case 10 -> 150;
            default -> 70;
        });

        if(duel.data(opponent.getUUID()).digUsed) move.setPower(2 * move.getPower());

        return Move.simpleDamageMove(user, opponent, duel, move) + " Magnitude " + magnitude + "!";
    }

    public String Spikes(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        duel.hazardData(opponent.getUUID()).addHazard(DuelHelper.EntryHazard.SPIKES);
        return user.getName() + " laid a Spikes trap!";
    }
}
