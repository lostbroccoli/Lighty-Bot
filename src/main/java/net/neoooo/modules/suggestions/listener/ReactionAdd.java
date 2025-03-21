package net.neoooo.modules.suggestions.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.suggestions.Suggestions;
import org.jetbrains.annotations.NotNull;

public class ReactionAdd extends ListenerAdapter {

    @Override
    public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
        if (event.getChannel() == null) return;
        if (!event.getChannelType().equals(ChannelType.TEXT)) return;
        if (Suggestions.getSuggestions().getManager().getQuery(event.getGuild().getId(), Suggestions.getSuggestions().getModulename()).get("suggestionchannel", String.class).equals(event.getChannel().asTextChannel().getId())) {
            // U+1f44d = Daumen hoch //
            // U+1f44e = Daumen runter //

            if(event.getEmoji().asUnicode().getName().equals("\uD83D\uDC4D")) return;
            if(event.getEmoji().asUnicode().getName().equals("\uD83D\uDC4E")) return;

            event.getReaction().clearReactions().queue();
        }
    }
}
