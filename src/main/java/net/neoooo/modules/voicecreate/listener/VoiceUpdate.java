package net.neoooo.modules.voicecreate.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceUpdateEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.voicecreate.VoiceCreate;
import org.jetbrains.annotations.NotNull;

public class VoiceUpdate extends ListenerAdapter {

    @Override
    public void onGuildVoiceUpdate(@NotNull GuildVoiceUpdateEvent event) {
        if(event.getGuild() == null || event.getMember() == null) return;

        if(event.getChannelJoined() != null) {
            if(event.getChannelJoined().getType() != ChannelType.VOICE) return;
            if(!event.getChannelJoined().asVoiceChannel().getId().equals(VoiceCreate.getVoiceCreate().getManager().getQuery(event.getGuild().getId(), VoiceCreate.getVoiceCreate().getModulename()).get("joinchannel", String.class))) return;

            event.getChannelJoined().getParentCategory().createVoiceChannel(event.getMember().getEffectiveName()).onSuccess(channel -> {
                event.getGuild().moveVoiceMember(event.getMember(), channel).queue();
            }).queue();
        }

        if(event.getChannelLeft() != null){
            if(event.getChannelLeft().getType() != ChannelType.VOICE) return;
            if(event.getChannelLeft().asVoiceChannel().getId().equals(VoiceCreate.getVoiceCreate().getManager().getQuery(event.getGuild().getId(), VoiceCreate.getVoiceCreate().getModulename()).get("joinchannel", String.class))) return;

            if(event.getChannelLeft().asVoiceChannel().getMembers().isEmpty()) {
                event.getChannelLeft().asVoiceChannel().delete().queue();
            }
        }
    }
}
