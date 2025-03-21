package net.neoooo.modules.suggestions.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.suggestions.Suggestions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SlashCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getGuild() == null) return;
        if(!event.getName().equals(Suggestions.getSuggestions().getModulename())) return;

        switch (event.getSubcommandName()){
            case "setupchannel":
                if(event.getOption("channel") == null){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }
                if(!event.getOption("channel").getAsChannel().getType().equals(ChannelType.TEXT)){
                    event.reply("Please provide me with a valid forumchannel!").setEphemeral(true).queue();
                    return;
                }

                Suggestions.getSuggestions().getManager().updateValue(event.getGuild().getId(), Suggestions.getSuggestions().getModulename(), "suggestionchannel", event.getOption("channel").getAsChannel().asTextChannel().getId());

                event.reply("Success!").setEphemeral(true).queue();
        }
    }
}
