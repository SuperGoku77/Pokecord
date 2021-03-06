package com.calculusmaster.pokecord.commands.pokemon;

import com.calculusmaster.pokecord.commands.Command;
import com.calculusmaster.pokecord.game.Pokemon;
import com.calculusmaster.pokecord.game.enums.elements.Stat;
import com.calculusmaster.pokecord.game.enums.elements.Type;
import com.calculusmaster.pokecord.util.helpers.CacheHelper;
import com.calculusmaster.pokecord.util.Global;
import com.calculusmaster.pokecord.util.PokemonRarity;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.collections4.list.TreeList;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommandPokemon extends Command
{
    private List<Pokemon> pokemon;
    private List<String> team;
    private List<String> favorites;

    public CommandPokemon(MessageReceivedEvent event, String[] msg)
    {
        super(event, msg);
        //this.buildList();
        this.pokemon = new TreeList<>(CacheHelper.POKEMON_LISTS.get(this.player.getId()));
        this.team = List.copyOf(this.playerData.getTeam());
        this.favorites = List.copyOf(this.playerData.getFavorites());
    }

    @Override
    public Command runCommand()
    {
        List<String> msg = Arrays.asList(this.msg);
        //Stream<Pokemon> stream = msg.size() == 1 ? null : this.pokemon.stream();
        Stream<Pokemon> stream = this.pokemon.stream();

        if(msg.contains("--name") && msg.indexOf("--name") + 1 < msg.size())
        {
            StringBuilder name = new StringBuilder();

            for(int i = msg.indexOf("--name") + 1; i < msg.size(); i++)
            {
                if(!msg.get(i).contains("--")) name.append(msg.get(i)).append(" ");
                else i = msg.size();
            }

            name = new StringBuilder(Global.normalCase(name.toString().trim()));

            String searchName = name.toString().toLowerCase();

            //String name = msg.get(msg.indexOf("--name") + 1);
            stream = stream.filter(p -> p.getName().toLowerCase().contains(searchName));
        }

        if(msg.contains("--level") && msg.indexOf("--level") + 1 < msg.size())
        {
            int index = msg.indexOf("--level") + 1;
            String after = msg.get(index);
            boolean validIndex = index + 1 < msg.size();
            if(after.equals(">") && validIndex && isNumeric(index + 1)) stream = stream.filter(p -> p.getLevel() > getInt(index + 1));
            else if(after.equals("<") && validIndex && isNumeric(index + 1)) stream = stream.filter(p -> p.getLevel() < getInt(index + 1));
            else if(isNumeric(index)) stream = stream.filter(p -> p.getLevel() == getInt(index));
        }

        if(msg.contains("--iv") && msg.indexOf("--iv") + 1 < msg.size())
        {
            int index = msg.indexOf("--iv") + 1;
            String after = msg.get(index);
            boolean validIndex = index + 1 < msg.size();
            if(after.equals(">") && validIndex && isNumeric(index + 1)) stream = stream.filter(p -> p.getTotalIVRounded() > getInt(index + 1));
            else if(after.equals("<") && validIndex && isNumeric(index + 1)) stream = stream.filter(p -> p.getTotalIVRounded() < getInt(index + 1));
            else if(isNumeric(index)) stream = stream.filter(p -> (int)p.getTotalIVRounded() == getInt(index));
        }

        if(msg.contains("--team"))
        {
            stream = stream.filter(p -> this.team.contains(p.getUUID()));
        }

        if(msg.contains("--fav") || msg.contains("--favorites"))
        {
            stream = stream.filter(p -> this.favorites.contains(p.getUUID()));
        }

        if(msg.contains("--type") && msg.indexOf("--type") + 1 < msg.size() && Type.cast(msg.get(msg.indexOf("--type") + 1)) != null)
        {
            Type t = Type.cast(msg.get(msg.indexOf("--type") + 1));
            stream = stream.filter(p -> p.isType(t));
        }

        if(msg.contains("--maintype") && msg.indexOf("--maintype") + 1 < msg.size() && Type.cast(msg.get(msg.indexOf("--maintype") + 1)) != null)
        {
            Type t = Type.cast(msg.get(msg.indexOf("--maintype") + 1));
            stream = stream.filter(p -> p.getType()[0].equals(t));
        }

        if(msg.contains("--sidetype") && msg.indexOf("--sidetype") + 1 < msg.size() && Type.cast(msg.get(msg.indexOf("--sidetype") + 1)) != null)
        {
            Type t = Type.cast(msg.get(msg.indexOf("--sidetype") + 1));
            stream = stream.filter(p -> p.getType()[1].equals(t));
        }

        if(msg.contains("--shiny"))
        {
            stream = stream.filter(Pokemon::isShiny);
        }

        stream = this.sortIVs(stream, msg, "--hpiv", "--healthiv", Stat.HP);

        stream = this.sortIVs(stream, msg, "--atkiv", "--attackiv", Stat.ATK);

        stream = this.sortIVs(stream, msg, "--defiv", "--defenseiv", Stat.DEF);

        stream = this.sortIVs(stream, msg, "--spatkiv", "--specialattackiv", Stat.SPATK);

        stream = this.sortIVs(stream, msg, "--spdefiv", "--specialdefenseiv", Stat.SPDEF);

        stream = this.sortIVs(stream, msg, "--spdiv", "--speediv", Stat.SPD);

        if(msg.contains("--legendary") || msg.contains("--leg"))
        {
            stream = stream.filter(p -> PokemonRarity.LEGENDARY.contains(p.getName()));
        }

        if(msg.contains("--mythical") || msg.contains("--myth"))
        {
            stream = stream.filter(p -> PokemonRarity.MYTHICAL.contains(p.getName()));
        }

        if(msg.contains("--ub") || msg.contains("--ultrabeast") || msg.contains("--ultra") || msg.contains("--beast"))
        {
            stream = stream.filter(p -> PokemonRarity.ULTRA_BEAST.contains(p.getName()));
        }

        if(msg.contains("--mega"))
        {
            stream = stream.filter(p -> p.getName().toLowerCase().contains("mega") || p.getName().toLowerCase().contains("primal"));
        }

        //Convert Stream to List
        this.pokemon = stream.collect(Collectors.toList());

        if(msg.contains("--order") && msg.indexOf("--order") + 1 < msg.size())
        {
            String order = msg.get(msg.indexOf("--order") + 1);
            boolean asc = msg.indexOf("--order") + 2 < msg.size() && msg.get(msg.indexOf("--order") + 2).equals("a");
            OrderSort o = OrderSort.cast(order);
            if(o != null) this.sortOrder(o, !asc);
        }
        else this.sortOrder(OrderSort.NUMBER, false);

        if(!this.pokemon.isEmpty()) this.createListEmbed();
        else this.embed.setDescription("You have no Pokemon with those characteristics!");

        return this;
    }

    private Stream<Pokemon> sortIVs(Stream<Pokemon> stream, List<String> msg, String tag1, String tag2, Stat iv)
    {
        boolean hasTag1 = msg.contains(tag1) && msg.indexOf(tag1) + 1 < msg.size();
        boolean hasTag2 = msg.contains(tag2) && msg.indexOf(tag2) + 1 < msg.size();

        if(hasTag1 || hasTag2)
        {
            int index = msg.indexOf(hasTag1 ? tag1 : tag2) + 1;
            String after = msg.get(index);
            boolean validIndex = index + 1 < msg.size();
            if(after.equals(">") && validIndex && isNumeric(index + 1)) return stream.filter(p -> p.getIVs().get(iv) > getInt(index + 1));
            else if(after.equals("<") && validIndex && isNumeric(index + 1)) return stream.filter(p -> p.getIVs().get(iv) < getInt(index + 1));
            else if(isNumeric(index)) return stream.filter(p -> p.getIVs().get(iv) == getInt(index));
        }

        return stream;
    }

    private void sortOrder(OrderSort o, boolean descending)
    {
        switch (o)
        {
            case NUMBER -> this.pokemon.sort(Comparator.comparingInt(Pokemon::getNumber));
            case IV -> this.pokemon.sort(Comparator.comparingDouble(Pokemon::getTotalIVRounded));
            case LEVEL -> this.pokemon.sort(Comparator.comparingInt(Pokemon::getLevel));
            case NAME -> this.pokemon.sort(Comparator.comparing(Pokemon::getName));
        }

        if(descending) Collections.reverse(this.pokemon);
    }

    enum OrderSort
    {
        NUMBER,
        IV,
        LEVEL,
        NAME;

        static OrderSort cast(String s)
        {
            for(OrderSort o : values()) if(o.toString().equals(s.toUpperCase())) return o;
            return null;
        }
    }

    //Do sorting before this
    private void createListEmbed()
    {
        StringBuilder sb = new StringBuilder();
        boolean hasPage = this.msg.length >= 2 && this.isNumeric(1);
        int perPage = 20;
        int startIndex = hasPage ? ((getInt(1) - 1) * perPage > this.pokemon.size() ? 0 : getInt(1)) : 0;
        if(startIndex != 0) startIndex--;

        startIndex *= perPage;
        int endIndex = Math.min(startIndex + perPage, this.pokemon.size());
        for(int i = startIndex; i < endIndex; i++)
        {
            sb.append(this.getLine(this.pokemon.get(i)));
        }

        this.embed.setDescription(sb.toString());
        this.embed.setTitle(this.player.getName() + "'s Pokemon");
        this.embed.setFooter("Showing Numbers " + (startIndex + 1) + " to " + (endIndex) + " out of " + this.pokemon.size() + " Pokemon");
    }

//    @Deprecated
//    private void buildList()
//    {
//        //long l = System.currentTimeMillis();
//        for(int i = 0; i < this.playerData.getPokemonList().length(); i++) this.pokemon.add(Pokemon.buildCore(this.playerData.getPokemonList().getString(i), i));
//        //System.out.println("Creating the full list took " + (System.currentTimeMillis() - l) + "ms");
//    }

    private String getLine(Pokemon p)
    {
        return "**" + p.getName() + "**" + (p.isShiny() ? ":star2:" : "") + " " + (this.team.contains(p.getUUID()) ? "(T) " : "") + (this.favorites.contains(p.getUUID()) ? "(F) " : "") + "| Number: " + p.getNumber() + " | Level " + p.getLevel() + " | Total IV: " + p.getTotalIV() + "\n";
    }
}
