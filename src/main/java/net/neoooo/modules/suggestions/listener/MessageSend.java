package net.neoooo.modules.suggestions.listener;

import net.dv8tion.jda.api.entities.IPermissionHolder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ThreadChannelAction;
import net.neoooo.modules.suggestions.Suggestions;
import org.jetbrains.annotations.NotNull;

public class MessageSend extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getChannel() == null) return;
        if(!event.getChannelType().equals(ChannelType.TEXT)) return;
        if(Suggestions.getSuggestions().getManager().getQuery(event.getGuild().getId(), Suggestions.getSuggestions().getModulename()).get("suggestionchannel", String.class).equals(event.getChannel().asTextChannel().getId())){
            event.getMessage().addReaction(Emoji.fromUnicode("U+1F44D")).and(
                    event.getMessage().addReaction(Emoji.fromUnicode("U+1F44E"))
            ).and(event.getMessage().createThreadChannel("Vorschlag")).queue();
        }
    }
}
