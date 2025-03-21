package net.neoooo.listener;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.slotchanger.SlotChanger;
import net.neoooo.modules.suggestions.Suggestions;
import net.neoooo.modules.ticketsystem.TicketSystem;
import net.neoooo.modules.voicecreate.VoiceCreate;
import org.jetbrains.annotations.NotNull;

public class GuildJoin extends ListenerAdapter {

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        SlotChanger.put(event.getGuild().getId());
        Suggestions.put(event.getGuild().getId());
        TicketSystem.put(event.getGuild().getId());
        VoiceCreate.put(event.getGuild().getId());
    }
}
