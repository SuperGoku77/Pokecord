package com.calculusmaster.pokecord.game.moves;

import com.calculusmaster.pokecord.game.duel.Duel;
import com.calculusmaster.pokecord.game.Move;
import com.calculusmaster.pokecord.game.Pokemon;
import com.calculusmaster.pokecord.game.enums.elements.Stat;
import com.calculusmaster.pokecord.game.enums.elements.StatusCondition;

import java.util.Random;

public class SteelMoves
{
    public String IronDefense(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        user.changeStatMultiplier(Stat.DEF, 2);

        return user.getName() + "'s Defense rose by 2 stages!";
    }

    public String FlashCannon(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int damage = move.getDamage(user, opponent);
        opponent.damage(damage);

        if(new Random().nextInt(100) < 10)
        {
            opponent.changeStatMultiplier(Stat.SPDEF, -1);
            return move.getDamageResult(opponent, damage) + " " + opponent.getName() + "'s Special Defense was lowered by 1 stage!";
        }

        return move.getDamageResult(opponent, damage);
    }

    public String MetalBurst(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int damage = (int)(duel.data(user.getUUID()).lastDamageTaken * 1.5);
        opponent.damage(damage);

        return move.getDamageResult(opponent, damage);
    }

    public String MetalClaw(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int damage = move.getDamage(user, opponent);
        opponent.damage(damage);

        if(new Random().nextInt(100) < 10)
        {
            user.changeStatMultiplier(Stat.ATK, 1);

            return move.getDamageResult(opponent, damage) + " " + user.getName() + "'s Attack rose by 1 stage!";
        }

        return move.getDamageResult(opponent, damage);
    }

    public String MagnetBomb(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String MirrorShot(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String MetalSound(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        opponent.changeStatMultiplier(Stat.SPDEF, -2);
        return opponent.getName() + "'s Special Defense was lowered by 2 stages!";
    }

    public String GyroBall(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        move.setPower((int)(25 * (double)opponent.getStat(Stat.SPD) / user.getStat(Stat.SPD)));

        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String IronTail(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        boolean lower = new Random().nextInt(100) < 30;

        if(lower) opponent.changeStatMultiplier(Stat.DEF, -1);

        return Move.simpleDamageMove(user, opponent, duel, move) + (lower ? " " + opponent.getName() + "'s Defense was lowered by 1 stage!" : "");
    }

    public String Autotomize(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        user.changeStatMultiplier(Stat.SPD, 2);
        return user.getName() + "'s Speed rose by 2 stages!";
    }

    public String SunsteelStrike(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String HeavySlam(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        double ratio = user.getWeight() / opponent.getWeight();

        if(ratio >= 5) move.setPower(120);
        else if(ratio >= 4) move.setPower(100);
        else if(ratio >= 3) move.setPower(80);
        else if(ratio >= 2) move.setPower(40);
        else move.setPower(20);

        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String DoubleIronBash(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.statusDamageMove(user, opponent, duel, move, StatusCondition.FLINCHED, 30) + " " + Move.statusDamageMove(user, opponent, duel, move, StatusCondition.FLINCHED, 30);
    }

    public String BehemothBash(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        if(opponent.isDynamaxed()) move.setPower(move.getPower() * 2);
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String BehemothBlade(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        if(opponent.isDynamaxed()) move.setPower(move.getPower() * 2);
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String IronHead(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.statusDamageMove(user, opponent, duel, move, StatusCondition.FLINCHED, 30);
    }

    public String BulletPunch(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.simpleDamageMove(user, opponent, duel, move);
    }

    public String MeteorMash(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        return Move.statChangeDamageMove(user, opponent, duel, move, Stat.ATK, 1, 20, true);
    }

    public String KingsShield(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        duel.data(user.getUUID()).kingsShieldUsed = true;

        if(user.getName().equals("Aegislash Blade")) user.changeForm("Aegislash");

        return user.getName() + " defended itself with its Shield!";
    }

    public String SteelBeam(Pokemon user, Pokemon opponent, Duel duel, Move move)
    {
        int userDamage = user.getHealth() / 2;
        user.damage(userDamage);
        return Move.simpleDamageMove(user, opponent, duel, move) + " " + user.getName() + " lost " + userDamage + " HP!";
    }
}
