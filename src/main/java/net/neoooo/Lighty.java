package net.neoooo;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import net.neoooo.listener.GuildLeave;
import net.neoooo.modules.module_management.ModuleManagement;
import net.neoooo.modules.slotchanger.SlotChanger;
import net.neoooo.modules.suggestions.Suggestions;
import net.neoooo.modules.ticketsystem.TicketSystem;
import net.neoooo.modules.voicecreate.VoiceCreate;

import java.util.Map;

import static java.util.Map.entry;

public class Lighty {

    private static Dotenv dotenv = Dotenv.configure().load();
    private static JDA jda;
    private static ModuleManagement moduleManagement = new ModuleManagement();

    public static void main(String[] args) throws InterruptedException {
       new SlotChanger().init();
       new TicketSystem().init();
       new Suggestions().init();
       new VoiceCreate().init();

       jda = JDABuilder.createDefault(dotenv.get("bottoken"), GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_VOICE_STATES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MODERATION, GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_MESSAGES)
                .setAutoReconnect(true)
                .enableCache(CacheFlag.ROLE_TAGS, CacheFlag.MEMBER_OVERRIDES)
                .addEventListeners(new GuildLeave())
                .addEventListeners(new GuildLeave())
                .addEventListeners(SlotChanger.getSlotchanger().getListeners())
                .addEventListeners(TicketSystem.getTicketSystem().getListeners())
                .addEventListeners(Suggestions.getSuggestions().getListeners())
               .addEventListeners(VoiceCreate.getVoiceCreate().getListeners()).build();
       jda.getPresence().setPresence(Activity.watching("How u eat cookies!"), true);

       jda.updateCommands().addCommands(
                SlotChanger.getSlotchanger().getCommands(),
                TicketSystem.getTicketSystem().getCommands(),
                Suggestions.getSuggestions().getCommands(),
                VoiceCreate.getVoiceCreate().getCommands()
       ).queue();

        moduleManagement.setBotModules(Map.ofEntries(
                entry("ticketsystem", TicketSystem.getTicketSystem()),
                entry("slotchanger", SlotChanger.getSlotchanger()),
                entry("suggestions", Suggestions.getSuggestions()),
                entry("voicecreate", VoiceCreate.getVoiceCreate())
                ));
        // IMPORTANT!!!! OTHERWISE jda.getGuilds() will be NULL!
        jda.awaitReady();
        moduleManagement.setGuilds(jda.getGuilds());
        moduleManagement.init();
    }

    public static Dotenv getDotenv() {
        return dotenv;
    }

    public static JDA getJDA() {
        return jda;
    }
}
