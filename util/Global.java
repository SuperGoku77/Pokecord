package com.calculusmaster.pokecord.util;

import com.calculusmaster.pokecord.game.Pokemon;
import org.bson.Document;
import org.jetbrains.annotations.Nls;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Global
{
    public static final List<String> STARTERS = Arrays.asList("bulbasaur", "charmander", "squirtle", "chikorita", "quilava", "totodile", "treecko", "torchic", "mudkip", "turtwig", "chimchar", "piplup", "snivy", "tepig", "oshawott", "chespin", "fennekin", "froakie", "rowlet", "litten", "popplio");
    public static final List<String> POKEMON = new ArrayList<>();

    public static void logInfo(Class<?> clazz, String method, String msg)
    {
        LoggerFactory.getLogger(clazz).info("#" + method + ": " + msg);
    }

    public static void logTime(Class<?> clazz, String command, long timeI, long timeF, OffsetDateTime timestamp)
    {
        LoggerFactory.getLogger(clazz).info(command + " took " + (timeF - timeI) + " ms to complete!");
        //addPerformanceEntry(command, timeI, timeF, timestamp);
    }

    private static void addPerformanceEntry(String command, long timeI, long timeF, OffsetDateTime timestamp)
    {
        Document data = new Document("command", command).append("time", timeF - timeI).append("timestamp", timestamp.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        Mongo.PerformanceData.insertOne(data);
    }

    public static String getDeerlingImage(boolean shiny)
    {
        return switch(LocalDateTime.now().getMonth().getValue()) {
            case 12, 1, 2 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/d/d4/585Deerling-Winter.png/600px-585Deerling-Winter.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0w05h-76dbc251-20a7-4f06-bdf0-00c00ac06a84.png";
            case 3, 4, 5 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/6/68/585Deerling-Spring.png/600px-585Deerling-Spring.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/ddl4hmv-9c94d2dc-9485-4619-9358-a2b676019e31.png";
            case 6, 7, 8 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/d/d7/585Deerling-Summer.png/600px-585Deerling-Summer.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0vzzu-209e9d51-8a6b-4396-a336-6019608c1356.png";
            case 9, 10, 11 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/e/e3/585Deerling-Autumn.png/600px-585Deerling-Autumn.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0w01m-79f5f916-c432-4dd6-b102-bf989fb47263.png";
            default -> Pokemon.getWIPImage();
        };
    }

    public static String getSawsbuckImage(boolean shiny)
    {
        return switch(LocalDateTime.now().getMonth().getValue()) {
            case 12, 1, 2 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/c/c5/586Sawsbuck-Winter.png/600px-586Sawsbuck-Winter.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0vzxq-041bb5b8-a704-4570-92a0-c9960df3da2a.png";
            case 3, 4, 5 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/8/8d/586Sawsbuck-Spring.png/600px-586Sawsbuck-Spring.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/ddl4hph-9c0caef0-5418-43a4-a069-32103ad59b89.png";
            case 6, 7, 8 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/4/44/586Sawsbuck-Summer.png/600px-586Sawsbuck-Summer.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0vzmz-c4345def-aff9-4092-b4af-de2892b2dbb9.png";
            case 9, 10, 11 -> !shiny ? "https://archives.bulbagarden.net/media/upload/thumb/c/ca/586Sawsbuck-Autumn.png/600px-586Sawsbuck-Autumn.png" : "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/i/e48d6b9d-3b1d-46a0-a254-3a448ec3a8a5/de0vzrv-cc9abb2f-eda1-46c5-b5dd-e925f9e7ea41.png";
            default -> Pokemon.getWIPImage();
        };
    }

    public static boolean isStarter(String s)
    {
        return STARTERS.contains(s.toLowerCase());
    }

    public static Object getEnumFromString(Object[] enumValues, String s)
    {
        for(Object o : enumValues) if(s.toLowerCase().equals(o.toString().toLowerCase())) return o;
        return null;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    public static String normalCase(String s)
    {
        if(s.toLowerCase().startsWith("nidoran"))
        {
            return s.toLowerCase().contains("f") ? "NidoranF" : "NidoranM";
        }

        StringBuilder sb = new StringBuilder();
        for(String str : s.replaceAll("-", " ").split("\\s+")) sb.append(str.substring(0, 1).toUpperCase()).append(str.substring(1).toLowerCase()).append(" ");
        return s.contains("-") ? sb.toString().replaceAll("\\s", "-").trim() : sb.toString().trim();
    }
}
