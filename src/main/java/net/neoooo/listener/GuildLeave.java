package net.neoooo.listener;

import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.module_management.ModuleManagement;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

public class GuildLeave extends ListenerAdapter {

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        for(BotModule module : new ModuleManagement().getBotModules().values()) {
            module.getCollection().deleteOne(new Document("serverid", event.getGuild().getId()));
        }
    }
}
