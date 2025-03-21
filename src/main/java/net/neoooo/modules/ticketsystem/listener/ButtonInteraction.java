package net.neoooo.modules.ticketsystem.listener;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.concrete.Category;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;
import net.neoooo.modules.ticketsystem.TicketSystem;
import net.neoooo.modules.ticketsystem.utils.DiscordHtmlTranscripts;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ButtonInteraction extends ListenerAdapter {
    
    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        if(event.getGuild() == null) return;

        switch (event.getButton().getId()){
            case "ticket_open":

                if(new TicketSystem().hasTicket(event.getMember())) {
                    event.reply("You already have a open ticket!").setEphemeral(true).queue();
                    return;
                }

                Category ticketgory = event.getGuild().getCategoryById(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketcategory", String.class));
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_title", String.class));
                builder.setDescription(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_description", String.class));
                builder.setFooter(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_footer", String.class));
                builder.setColor(Color.decode(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_color", String.class)));

                ChannelAction ticketchannelaction = event.getGuild().createTextChannel("ticket_" + event.getMember().getEffectiveName()).setParent(ticketgory);
                for(Object roleid : TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("teamrole", ArrayList.class)) {
                    ticketchannelaction.addRolePermissionOverride(Long.valueOf((String) roleid), Arrays.asList(Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL), null);
                }
                TextChannel ticketchannel = (TextChannel) ticketchannelaction.addMemberPermissionOverride(Long.valueOf(event.getMember().getId()), Arrays.asList(Permission.MESSAGE_HISTORY, Permission.MESSAGE_SEND, Permission.VIEW_CHANNEL), null).complete();

                if(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).getBoolean("teamping_enabled")) {
                    ticketchannel.sendMessage("<@" + event.getUser().getId() + ">").and(event.reply("<#" + ticketchannel.getId() + "> successfully created!").setEphemeral(true))
                            .and(ticketchannel.sendMessageEmbeds(builder.build()).addActionRow(
                                    Button.danger("ticket_close_confirm", "Close Ticket"))).queue();

                    String msg = "<@&";
                    String message = "";
                    for(Object roleids : TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("teamrole", ArrayList.class)) {
                        message += msg + roleids + "> ";
                    }
                    ticketchannel.sendMessage(message).queue();

                    return;
                }
                ticketchannel.sendMessageEmbeds(builder.build()).addActionRow(
                    Button.danger("ticket_close_confirm", "Close Ticket")).and(event.reply("<#" + ticketchannel.getId() + "> successfully created!").setEphemeral(true)).queue();
                break;

            case "ticket_close_confirm":

                if(!new TicketSystem().hasRole(event.getMember())){
                    event.reply("Only a team member can close the ticket!").setEphemeral(true).queue();
                    return;
                }

                EmbedBuilder closebuilder = new EmbedBuilder();
                closebuilder.setTitle("You sure you want to close the ticket?");
                closebuilder.setColor(Color.decode(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_color", String.class)));
                closebuilder.setFooter(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketmessage_footer", String.class));

                event.replyEmbeds(closebuilder.build()).addActionRow(
                        Button.danger("ticket_close", "Close Ticket")
                ).setEphemeral(true).queue();

                break;

            case "ticket_close":
                TextChannel logchannel = event.getGuild().getTextChannelById(TicketSystem.getTicketSystem().getManager().getQuery(event.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketlogchannel", String.class));

                try {
                    logchannel.sendFiles(DiscordHtmlTranscripts.getInstance().createTranscript(event.getChannel().asGuildMessageChannel())).queue();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                event.getChannel().delete().queue();
                break;

        }
    }
}
