package net.neoooo.modules.slotchanger.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.slotchanger.SlotChanger;

import java.util.ArrayList;

public class VoiceChannel extends ListenerAdapter {


    @Override
    public void onGuildVoiceUpdate(GuildVoiceUpdateEvent event) {
        if(event.getGuild() == null) return;
        if(event.getChannelLeft() == null) return;
        if(event.getChannelLeft().getType() != ChannelType.VOICE) return;
        if(!event.getChannelLeft().asVoiceChannel().getMembers().isEmpty()) return;
        if(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("panelchannel", String.class).isEmpty()) return;
        if(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_categories", ArrayList.class).contains(event.getChannelLeft().getParentCategoryId())) return;
        if(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_channels", ArrayList.class).contains(event.getChannelLeft().getId())) return;

        if(event.getChannelLeft().getParentCategoryId().equals("1315756590418231397")){
            event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(2).queue();
            return;
        }

        event.getChannelLeft().asVoiceChannel().getManager().setUserLimit(2).queue();
    }
}
