package net.neoooo.modules.voicecreate.listener;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.neoooo.modules.ticketsystem.TicketSystem;
import net.neoooo.modules.voicecreate.VoiceCreate;
import org.jetbrains.annotations.NotNull;

public class SlashCommand extends ListenerAdapter {



    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getGuild() == null) return;
        if(!event.getName().equals(VoiceCreate.getVoiceCreate().getModulename())) return;

        switch (event.getSubcommandName()){
            case "setup":
                if(event.getOption("category") == null) {
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }
                if(!event.getOption("category").getAsChannel().getType().equals(ChannelType.CATEGORY)) {
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }

                event.getOption("category").getAsChannel().asCategory().createVoiceChannel("join-to-create").onSuccess(channel -> {
                    VoiceCreate.getVoiceCreate().getManager().updateValue(event.getGuild().getId(), VoiceCreate.getVoiceCreate().getModulename(), "joinchannel", channel.getId());
                    VoiceCreate.getVoiceCreate().getManager().updateValue(event.getGuild().getId(), VoiceCreate.getVoiceCreate().getModulename(), "joincategory", channel.getParentCategoryId());
                }).and(event.reply("Success!").setEphemeral(true)).queue();
        }
    }
}
