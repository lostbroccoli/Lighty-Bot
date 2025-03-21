package net.neoooo.modules.slotchanger.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.neoooo.modules.slotchanger.SlotChanger;

import java.awt.*;
import java.util.ArrayList;

public class SlashCommand extends ListenerAdapter {
    
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild() == null) return;
        if(!event.getName().equals(SlotChanger.getSlotchanger().getModulename())) return;

        switch (event.getSubcommandName()){
            case "setup":
                if(event.getOption("panelchannel") == null){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }
                if(!event.getOption("panelchannel").getAsChannel().getType().equals(ChannelType.TEXT)){
                    event.reply("Please provide me with a valid textchannel!").setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("message_title", String.class));
                builder.setDescription(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("message_description", String.class));
                builder.setColor(Color.decode(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("message_color", String.class)));
                builder.setFooter(SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("message_footer", String.class));

                SlotChanger.getSlotchanger().getManager().updateValue(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename(), "panelchannel", event.getOption("panelchannel").getAsChannel().getId());

                event.getGuild().getTextChannelById(event.getOption("panelchannel").getAsChannel().getId()).sendMessageEmbeds(builder.build()).addActionRow(
                        Button.primary("slotchange", "Limit Usercount")
                ).and(event.reply("Successfully sent!").setEphemeral(true)).queue();
                break;

            case "ignorecategory":
                if(event.getOption("ignored_category") == null){
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ignored_category").getAsChannel().getType().equals(ChannelType.CATEGORY)){
                    event.reply("Please provide me with a valid category!").setEphemeral(true).queue();
                    return;
                }

                ArrayList<String> ignored_categories = SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_categories", ArrayList.class);
                if(ignored_categories == null){
                    ignored_categories = new ArrayList<>();
                }
                ignored_categories.add(event.getOption("ignored_category").getAsString());

                SlotChanger.getSlotchanger().getManager().updateValue(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename(), "ignored_categories", ignored_categories);
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "ignorechannel":
                if(event.getOption("ignored_channel") == null){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ignored_channel").getAsChannel().getType().equals(ChannelType.VOICE)){
                    event.reply("Please provide me with a valid channel!").setEphemeral(true).queue();
                    return;
                }

                ArrayList<String> ignored_channels = SlotChanger.getSlotchanger().getManager().getQuery(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename()).get("ignored_channels", ArrayList.class);
                if(ignored_channels == null){
                    ignored_channels = new ArrayList<>();
                }
                ignored_channels.add(event.getOption("ignored_channel").getAsString());

                SlotChanger.getSlotchanger().getManager().updateValue(event.getGuild().getId(), SlotChanger.getSlotchanger().getModulename(), "ignored_channels", ignored_channels);
                event.reply("Successful!").setEphemeral(true).queue();
                break;
        }
    }
}
