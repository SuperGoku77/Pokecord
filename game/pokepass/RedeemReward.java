package com.calculusmaster.pokecord.game.pokepass;

import com.calculusmaster.pokecord.mongo.PlayerDataQuery;

public class RedeemReward extends TierReward
{
    private int amount;

    public RedeemReward(int amount)
    {
        this.amount = amount;
    }

    @Override
    public String grantReward(PlayerDataQuery player)
    {
        player.changeRedeems(this.amount);

        return "You earned " + this.amount + " redeems!";
    }

    @Override
    public String getTierDescription()
    {
        return "Redeem Reward: **" + this.amount + "** Redeems!";
    }
}
