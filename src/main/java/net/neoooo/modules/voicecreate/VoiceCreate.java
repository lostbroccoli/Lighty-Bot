package net.neoooo.modules.voicecreate;

import com.mongodb.BasicDBObject;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.neoooo.modules.BotModule;
import net.neoooo.modules.voicecreate.listener.SlashCommand;
import net.neoooo.modules.voicecreate.listener.VoiceUpdate;
import org.bson.Document;

public class VoiceCreate extends BotModule {

    private static BotModule voiceCreate;

    public void init(){
        voiceCreate = new BotModule(
                "voicecreate",
                new Object[]{new SlashCommand(), new VoiceUpdate()},
                Commands.slash("voicecreate", "Setup a voicechannel to automatically create voice channels, when users join in").setDefaultPermissions(DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR))
                        .addSubcommands(
                                new SubcommandData("setup", "Setup the voice create")
                                        .addOption(OptionType.CHANNEL, "category", "Select the category where the channel should appear")
                        )
        );
    }

    public static BotModule getVoiceCreate() {
        return voiceCreate;
    }

    public static void put(String serverid) {
        if(voiceCreate.getCollection().find(new BasicDBObject("serverid", serverid)).cursor().hasNext()) return;

        voiceCreate.getCollection().insertOne(new Document()
                .append("serverid", serverid)
                .append("joincategory", "")
                .append("joinchannel", ""));
    }
}
