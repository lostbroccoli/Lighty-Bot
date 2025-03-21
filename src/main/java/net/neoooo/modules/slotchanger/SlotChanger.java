package net.neoooo.modules.slotchanger;

import com.mongodb.BasicDBObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.slotchanger.listener.ButtonInteraction;
import net.neoooo.modules.slotchanger.listener.ModalInteraction;
import net.neoooo.modules.slotchanger.listener.SlashCommand;
import net.neoooo.modules.slotchanger.listener.VoiceChannel;
import org.bson.Document;

import java.util.ArrayList;

public class SlotChanger extends BotModule {

    private static BotModule slotchanger;

    public void init(){
        slotchanger = new BotModule(
                "slotchanger",
        new Object[]{new ButtonInteraction(), new ModalInteraction(), new SlashCommand(), new VoiceChannel()},
                Commands.slash("slotchanger", "Used to setup the bot")
                        .setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addSubcommands(new SubcommandData("setup", "Setup the message and sends it to channel")
                                .addOption(OptionType.CHANNEL, "panelchannel", "Provide me with the channel where i am supposed to send the clickable message in!"))
                        .addSubcommands(new SubcommandData("ignorecategory", "Add a category to ignore for the slotchange")
                                .addOption(OptionType.CHANNEL, "ignored_category", "The category i should ignore"))
                        .addSubcommands(new SubcommandData("ignorechannel", "Add a channel to ignore for the slotchange")
                                .addOption(OptionType.CHANNEL, "ignored_channel", "The channel i should ignore"))
        );
    }


    public static void put(String serverid){
        if(slotchanger.getCollection().find(new BasicDBObject("serverid", serverid)).cursor().hasNext()) return;

        slotchanger.getCollection().insertOne(new Document()
                .append("serverid", serverid)
                .append("ignored_categories", new ArrayList<String>())
                .append("ignored_channels", new ArrayList<String>())
                .append("panelchannel", "")
                .append("message_title", "Change Slotlimit")
                .append("message_description", "Press the button to change the slotlimit!")
                .append("message_color", "#fffff")
                .append("message_footer", "Lighty Bot | Made with cookies by Neooo ♡"));
    }

    public static BotModule getSlotchanger() {
        return slotchanger;
    }
}
