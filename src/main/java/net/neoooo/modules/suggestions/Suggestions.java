package net.neoooo.modules.suggestions;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.suggestions.listener.MessageSend;
import net.neoooo.modules.suggestions.listener.ReactionAdd;
import net.neoooo.modules.suggestions.listener.SlashCommand;
import org.bson.Document;

public class Suggestions extends BotModule {

    private static BotModule suggestions;


    public void init() {
        suggestions = new BotModule(
        "suggestions",
                new Object[]{new SlashCommand(), new MessageSend(), new ReactionAdd()},
                Commands.slash("suggestions", "Enables the bot to like and autolike a post from a forum channel").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addSubcommands(new SubcommandData("setupchannel", "Setup the Suggestion Channel to autolike and -dislike on")
                                .addOption(OptionType.CHANNEL, "channel", "Select the Suggestion channel"))
        );
    }

    public static void put(String serverid) {
        suggestions.getCollection().insertOne(new Document()
                .append("serverid", serverid)
                .append("suggestionchannel", ""));
    }

    public static BotModule getSuggestions() {
        return suggestions;
    }
}
