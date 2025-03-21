package net.neoooo.modules.ticketsystem.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.neoooo.modules.ticketsystem.TicketSystem;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class SlashCommand extends ListenerAdapter {
    
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if(event.getGuild() == null) return;
        if(!event.getName().equals(TicketSystem.getTicketSystem().getModulename())) return;
        
        switch (event.getSubcommandName()){
            case "ticketpanelchannel":

                if(event.getOption("ticketpanelchannel") == null) {
                    event.reply("Please provide me with a valid ticketpanelchannel!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ticketpanelchannel").getAsChannel().getType().equals(ChannelType.TEXT)) {
                    event.reply("Please provide me with a valid ticketpanelchannel!").setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketpanel_title", String.class));
                builder.setDescription(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketpanel_description", String.class));
                builder.setFooter(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketpanel_footer", String.class));
                builder.setColor(Color.decode(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketpanel_color", String.class)));

                TicketSystem.getTicketSystem().getManager().updateValue(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename(), "ticketpanelchannel", event.getOption("ticketpanelchannel").getAsChannel().asTextChannel().getId());

                event.getOption("ticketpanelchannel").getAsChannel().asTextChannel().sendMessageEmbeds(builder.build()).addActionRow(Button.primary("ticket_open", "Open Ticket")).queue();
                event.reply("Successful!").setEphemeral(true).queue();

                break;
            case "ticketlogchannel":

                if(event.getOption("ticketlogchannel") == null) {
                    event.reply("Please provide me with a valid ticketlogchannel!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ticketlogchannel").getAsChannel().getType().equals(ChannelType.TEXT)) {
                    event.reply("Please provide me with a valid ticketlogchannel!").setEphemeral(true).queue();
                    return;
                }

                TicketSystem.getTicketSystem().getManager().updateValue(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename(), "ticketlogchannel", event.getOption("ticketlogchannel").getAsChannel().asTextChannel().getId());
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "ticketcategory":

                if(event.getOption("ticketcategory") == null) {
                    event.reply("Please provide me with a valid ticketcategory!").setEphemeral(true).queue();
                    return;
                }

                if(!event.getOption("ticketcategory").getAsChannel().getType().equals(ChannelType.CATEGORY)) {
                    event.reply("Please provide me with a valid ticketcategory!").setEphemeral(true).queue();
                    return;
                }

                TicketSystem.getTicketSystem().getManager().updateValue(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename(), "ticketcategory", event.getOption("ticketcategory").getAsChannel().asCategory().getId());
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "ticketteammember":

                if(event.getOption("teamrole") == null) {
                    event.reply("Please provide me with a valid ticketteammember!").setEphemeral(true).queue();
                    return;
                }

                ArrayList<String> roleids = new ArrayList<>();
                for(Object roleid : TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("teamrole", ArrayList.class)){
                    roleids.add((String) roleid);
                }
                roleids.add(event.getOption("teamrole").getAsRole().getId());

                TicketSystem.getTicketSystem().getManager().updateValue(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename(), "teamrole", roleids);
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "enable_roleping":

                if(event.getOption("teamping_enabled") == null) {
                    event.reply("Please provide me with a valid argument!").setEphemeral(true).queue();
                    return;
                }

                TicketSystem.getTicketSystem().getManager().updateValue(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename(), "teamping_enabled", event.getOption("teamping_enabled").getAsBoolean());
                event.reply("Successful!").setEphemeral(true).queue();
                break;

            case "role_message":

        }
    }
}
