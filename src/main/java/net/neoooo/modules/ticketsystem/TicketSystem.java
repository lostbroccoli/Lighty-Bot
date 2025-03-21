package net.neoooo.modules.ticketsystem;

import com.mongodb.BasicDBObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.*;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.ticketsystem.listener.ButtonInteraction;
import net.neoooo.modules.ticketsystem.listener.SlashCommand;
import org.bson.Document;

import java.util.ArrayList;

public class TicketSystem extends BotModule {

    private static BotModule ticketSystem;

    public void init(){
        ticketSystem = new BotModule(
                "ticketsystem",
        new Object[]{new ButtonInteraction(), new SlashCommand()},
                Commands.slash("ticketsystem", "Create a ticketsystem on your server")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addSubcommands(new SubcommandData("ticketpanelchannel", "Sets where the panel message with the button will be sent to")
                                .addOption(OptionType.CHANNEL, "ticketpanelchannel", "The Panel Channel where the users will be able to open a ticket", true))
                        .addSubcommands(new SubcommandData("ticketlogchannel", "Sets where the logged tickets should be sent to")
                                .addOption(OptionType.CHANNEL, "ticketlogchannel", "The channel where the bot will send the ticketlogs to", true))
                        .addSubcommands(new SubcommandData("ticketcategory", "Sets where the tickets will be opened")
                                .addOption(OptionType.CHANNEL, "ticketcategory", "The Ticket Category where the ticket channels are created", true))
                        .addSubcommands(new SubcommandData("ticketteammember", "The Teamrole which is able to interact with tickets and handle them")
                                .addOption(OptionType.ROLE, "teamrole", "The Moderation Role which should be able to work with tickets (also see them)", true))
                        .addSubcommands(new SubcommandData("enable_roleping", "Should the bot ping all ticketrolemembers, when a new ticket is opened?")
                                .addOption(OptionType.BOOLEAN, "teamping_enabled", "Enable/Disable the roleping in new tickets", true)
                        ));
    }

    public static void put(String serverid){
        if(ticketSystem.getCollection().find(new BasicDBObject("serverid", serverid)).cursor().hasNext()) return;

        ticketSystem.getCollection().insertOne(new Document()
                .append("serverid", serverid)
                .append("ticketcategory", "")
                .append("ticketpanelchannel", "")
                .append("ticketpanel_title", "Report a bug")
                .append("ticketpanel_description", "Open Ticket")
                .append("ticketpanel_footer", "Lighty Bot | Made with cookies by Neoooo ♡")
                .append("ticketpanel_color", "#fffff")
                .append("ticketmessage_title", "Please say whats on your mind")
                .append("ticketmessage_description", "Describe in Detail whats an your mind. A team member will be here shortly to support you.")
                .append("ticketmessage_footer", "Lighty Bot | Made with cookies by Neoooo ♡")
                .append("ticketmessage_color", "#fffff")
                .append("teamping_enabled", true)
                .append("teamrole", new ArrayList<String>())
                .append("ticketlogchannel", ""));
    }

    public boolean hasTicket(Member member){
       for(TextChannel channel : member.getGuild().getCategoryById(ticketSystem.getManager().getQuery(member.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("ticketcategory", String.class)).getTextChannels()){
           if(channel.getName().contains(member.getEffectiveName().toLowerCase())) {
               return true;
           }
       }
       return false;
    }

    public boolean hasRole(Member member) {
        ArrayList<String> roles = ticketSystem.getManager().getQuery(member.getGuild().getId(), TicketSystem.getTicketSystem().getModulename()).get("teamrole", ArrayList.class);

        for(Role role : member.getRoles()){
            if(roles.contains(role.getId())) return true;
        }

        return false;
    }

    public static BotModule getTicketSystem() { return ticketSystem; }
}
